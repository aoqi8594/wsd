package com.qzsoft.tah.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.io.FileUtil;
import com.qzsoft.tah.dao.WsdDevExportFDao;
import com.qzsoft.tah.dao.WsdExportCatBDao;
import com.qzsoft.tah.entity.WsdExportCatB;
import com.qzsoft.tah.notify.NotifyService;
import com.qzsoft.tah.service.WsdDevTahHService;
import com.qzsoft.tah.service.WsdExportCatBService;
import com.qzsoft.tah.service.WsdExportLService;
import com.qzsoft.tah.vo.DevExportDataVo;
import com.qzsoft.tah.vo.DevUseTypeVo;
import com.qzsoft.tah.vo.ExcelDevBXVo;
import com.qzsoft.tah.vo.ExcelDevVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 导出种类表(WsdExportCatB)表服务实现类
 *
 * @author makejava
 * @since 2021-04-26 15:02:49
 */
@Service("wsdExportCatBService")
public class WsdExportCatBServiceImpl implements WsdExportCatBService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private WsdExportCatBDao wsdExportCatBDao;

    @Resource
    private WsdDevTahHService wsdDevTahHService;

    @Resource
    private WsdDevExportFDao wsdDevExportFDao;

    @Resource
    private NotifyService notifyService;

    @Resource
    private WsdExportLService wsdExportLService;

    @Resource
    private Environment environment;

    @Override
    public List<WsdExportCatB> queryByExportTime(LocalDateTime now) {
        return wsdExportCatBDao.findByExportTimeAfterOrderByIdAsc(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Override
    public String recordExportCat(Long id, Boolean timely) {
        // 查询未结束的导出设置种类
        WsdExportCatB b = wsdExportCatBDao.findById(id).orElse(null);
        if (b != null) {
            // 查询导出设置种类的关联设备ID
            List<Long> devIds = wsdDevExportFDao.findDevIdByExportId(id);
            // 生成该种类的设备数据
            List<DevExportDataVo> vos = wsdDevTahHService.queryDevExportData(devIds, b);
            // timely判断是否即刻导出
            if (timely) {
                // 文件路径
                String path = environment.getProperty("tah.excel.path");
                if (!FileUtil.exist(path)) {
                    FileUtil.mkdir(path);
                }
                String tableName = "Now"+id+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                String filePath = path+tableName+".xls";
                if (FileUtil.isEmpty(new File(filePath))) {
                    return filePath;
                }
                // 即刻生成excel数据
                return handleNowExport(vos, filePath);
            } else {
                String senderType = b.getSenderType(); // 导出人员类型
                if(!CollectionUtils.isEmpty(vos)) {
                    // 处理种类中每个设备发送报表的人员
                    handleEmailDevExcel(vos, senderType);
                }
            }
        }
        return "";
    }

    private String handleNowExport(List<DevExportDataVo> vos, String filePath) {
        // 处理出不同的种类
        List<DevUseTypeVo> list = handleIcebox(vos);
        // 建立多sheet
        List<Map<String, Object>> exportList = new ArrayList<>();
        Map<String, Object> sheet;
        for (DevUseTypeVo vo : list) {
            sheet = new HashMap<>();
            List<ExcelDevBXVo> excelDevBXVos = handleExcelDevBXVo(vo.getExcelDevVos());
            ExportParams exportParams = new ExportParams();
            exportParams.setSheetName(vo.getUseType());
            exportParams.setTitle("冰箱/冰柜编号:"+vo.getUseType());
            sheet.put("title", exportParams);
            sheet.put("entity", ExcelDevBXVo.class);
            sheet.put("data", excelDevBXVos);
            exportList.add(sheet);
        }
        // 生成Excel
        try{
            String tableName = "Now" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            Workbook workbook = ExcelExportUtil.exportExcel(exportList, ExcelType.HSSF);
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            //释放资源
            workbook.close();
            fos.close();
            wsdExportLService.saveVo(tableName, filePath, "ZJDC");
        } catch (Exception e) {
            log.error("报表生成错误，错误信息data={}", e.getMessage());
            return e.getMessage();
        } finally {

        }
        return filePath;
    }

    private List<DevUseTypeVo> handleIcebox(List<DevExportDataVo> vos) {
        // 按设备排序
        vos = vos.stream()
                .sorted(Comparator.comparing(DevExportDataVo::getIceboxName))
                .collect(Collectors.toList());
        // 重新创建类
        List<DevUseTypeVo> list = new ArrayList<>();
        DevUseTypeVo vo = new DevUseTypeVo();
        List<ExcelDevVo> devVoList = new ArrayList<>();
        ExcelDevVo devVo;
        String iceboxName = "";

        for (DevExportDataVo dataVo : vos) {

            devVo = new ExcelDevVo();
            handleExcelDevVo(devVo, dataVo);
            if (CollectionUtils.isEmpty(devVoList)) {
                iceboxName = dataVo.getIceboxName();
                devVoList.add(devVo);
            } else if (iceboxName.equals(dataVo.getIceboxName())) {
                devVoList.add(devVo);
            } else {
                vo.setUseType(iceboxName);
                vo.setExcelDevVos(devVoList);
                list.add(vo);
                iceboxName = dataVo.getIceboxName();
                vo = new DevUseTypeVo();
                devVoList = new ArrayList<>();
                devVoList.add(devVo);
            }
        }
        vo.setUseType(iceboxName);
        vo.setExcelDevVos(devVoList);
        list.add(vo);
        return list;
    }

    /**
      * @Author Yang Chunhai
      * @Description  要考虑同一个设备且仪器关联的人员邮箱相同，才会把数据放在一个excel里面
      * @Date 2021/4/28 14:05
      **/
    private void handleEmailDevExcel(List<DevExportDataVo> vos, String senderType) {
        // 设备分类
        List<DevUseTypeVo> list = handleDev(vos);
        // 文件路径
        String path = environment.getProperty("tah.excel.path");

        for (DevUseTypeVo vo : list) {
//            String useType = vo.getUseType();
            List<ExcelDevVo> excelDevVos = vo.getExcelDevVos();
//            if ("BX".equals(useType)) {
                List<ExcelDevBXVo> excelDevBXVos = handleExcelDevBXVo(excelDevVos);
                // 按照邮件排序
                if (senderType.contains("YWRY")) {
                    excelDevBXVos = excelDevBXVos.stream()
                            .sorted(Comparator.comparing(ExcelDevBXVo::getYWEmail))
                            .collect(Collectors.toList());
                    sendEmailDevBXExcel(excelDevBXVos, "YWRY", path);

                }
                // 按照邮件排序
                if (senderType.contains("ZJFZR")) {
                    excelDevBXVos = excelDevBXVos.stream()
                            .sorted(Comparator.comparing(ExcelDevBXVo::getFZREmail))
                            .collect(Collectors.toList());
                    sendEmailDevBXExcel(excelDevBXVos, "ZJFZR", path);

                }
//            } else {
//                // 按照邮件排序
//                if (senderType.contains("YWRY")) {
//                    excelDevVos = excelDevVos.stream()
//                            .sorted(Comparator.comparing(ExcelDevVo::getYWEmail))
//                            .collect(Collectors.toList());
//                    sendEmailDevExcel(excelDevVos, "YWRY", path);
//
//
//                }
//                // 按照邮件排序
//                if (senderType.contains("ZJFZR")) {
//                    excelDevVos = excelDevVos.stream()
//                            .sorted(Comparator.comparing(ExcelDevVo::getFZREmail))
//                            .collect(Collectors.toList());
//                    sendEmailDevExcel(excelDevVos, "ZJFZR", path);
//                }
//            }
        }
    }


    private void sendEmailDevBXExcel(List<ExcelDevBXVo> devBXVos, String senderType, String path) {
        List<ExcelDevBXVo> devBXVosExcel = new ArrayList<>();
        String iceboxName = "";
        String fzrEmail = "";
        String ywEmail = "";
        for (int i = 0; i < devBXVos.size(); i++) {
            if (CollectionUtils.isEmpty(devBXVosExcel)) {
                iceboxName = devBXVos.get(i).getIceboxName();
                fzrEmail = devBXVos.get(i).getFZREmail();
                ywEmail = devBXVos.get(i).getYWEmail();
                devBXVosExcel.add(devBXVos.get(i));
                continue;
            } else if ( "ZJFZR".equals(senderType)? fzrEmail.equals(devBXVos.get(i).getFZREmail()) : ywEmail.equals(devBXVos.get(i).getYWEmail())){
                devBXVosExcel.add(devBXVos.get(i));
                if (i != devBXVos.size() - 1) {
                    continue;
                }
            }
            // 生成Excel
            try{
                String tableName = iceboxName + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
                String filePath = path+tableName+".xls";
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("编号:  " + iceboxName, tableName),
                        ExcelDevBXVo.class, devBXVosExcel);
                workbook.write(new FileOutputStream(filePath));
                //释放资源
                workbook.close();
                wsdExportLService.saveVo(tableName, filePath,"YJTS");
                // 发邮件
                if (!StringUtils.isEmpty(fzrEmail) && "ZJFZR".equals(senderType)) {
                    notifyService.notifyEmailFile(fzrEmail, tableName+"温度导出信息", new File(filePath));
                }
                if (!StringUtils.isEmpty(ywEmail) && "YWRY".equals(senderType)) {
                    notifyService.notifyEmailFile(ywEmail, tableName+"温度导出信息", new File(filePath));
                }
            } catch (Exception e) {
                log.error("报表生成错误，错误信息data={}", e.getMessage());
            }
            devBXVosExcel.clear();
            iceboxName = devBXVos.get(i).getIceboxName();
            fzrEmail = devBXVos.get(i).getFZREmail();
            ywEmail = devBXVos.get(i).getYWEmail();
            devBXVosExcel.add(devBXVos.get(i));
        }
    }

    private void sendEmailDevExcel(List<ExcelDevVo> devVos, String senderType, String path) {
        List<ExcelDevVo> devVosExcel = new ArrayList<>();
        String iceboxName = "";
        String fzrEmail = "";
        String ywEmail = "";
        for (int i = 0; i < devVos.size(); i++) {
            if (CollectionUtils.isEmpty(devVosExcel)) {
                iceboxName = devVos.get(i).getIceboxName();
                fzrEmail = devVos.get(i).getFZREmail();
                ywEmail = devVos.get(i).getYWEmail();
                devVosExcel.add(devVos.get(i));
                continue;
            } else if ( "ZJFZR".equals(senderType)? fzrEmail.equals(devVos.get(i).getFZREmail()) : ywEmail.equals(devVos.get(i).getYWEmail())){
                devVosExcel.add(devVos.get(i));
                if (i != devVos.size() - 1) {
                    continue;
                }
            }
            // 生成Excel
            try{
                String tableName = iceboxName + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
                String filePath = path+tableName+".xls";
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("编号:  " + iceboxName, tableName),
                        ExcelDevVo.class, devVosExcel);
                workbook.write(new FileOutputStream(filePath));
                //释放资源
                workbook.close();
                wsdExportLService.saveVo(tableName, filePath, "YJTS");
                // 发邮件
                if (!StringUtils.isEmpty(fzrEmail) && "ZJFZR".equals(senderType)) {
                    notifyService.notifyEmailFile(fzrEmail, tableName+"温度导出信息", new File(filePath));
                }
                if (!StringUtils.isEmpty(ywEmail) && "YWRY".equals(senderType)) {
                    notifyService.notifyEmailFile(ywEmail, tableName+"温度导出信息", new File(filePath));
                }
            } catch (Exception e) {
                log.error("报表生成错误，错误信息data={}", e.getMessage());
            }
            devVosExcel.clear();
            iceboxName = devVos.get(i).getIceboxName();
            fzrEmail = devVos.get(i).getFZREmail();
            ywEmail = devVos.get(i).getYWEmail();
            devVosExcel.add(devVos.get(i));
        }
    }

    private List<ExcelDevBXVo> handleExcelDevBXVo(List<ExcelDevVo> excelDevVos) {
        List<ExcelDevBXVo> list = new ArrayList<>();
        ExcelDevBXVo devBXVo;
        String devName = "";
        String date = "";
        List<Integer> index = new ArrayList<>();
        int indexT = 0;
        for (int i = 0; i < excelDevVos.size(); i++) {
            devBXVo = new ExcelDevBXVo();
            // 合并上下午
            if (!devName.equals(excelDevVos.get(i).getDevName()) || !date.equals(excelDevVos.get(i).getDate())){
                devName = excelDevVos.get(i).getDevName();
                date = excelDevVos.get(i).getDate();
                index.clear();
            }
            // 判断上下午
            if (Integer.parseInt(excelDevVos.get(i).getTime().substring(0,2)) < 12) {
                devBXVo.setSwTime(excelDevVos.get(i).getTime());
                devBXVo.setSwValue(excelDevVos.get(i).getValue());
                index.add(i);
            } else if (index.size() > 0){
                list.get(index.get(0) - indexT).setXwTime(excelDevVos.get(i).getTime());
                list.get(index.get(0) - indexT).setXwValue(excelDevVos.get(i).getValue());
                index.remove(0);
                indexT++;
                continue;
            } else {
                devBXVo.setXwTime(excelDevVos.get(i).getTime());
                devBXVo.setXwValue(excelDevVos.get(i).getValue());
            }
            devBXVo.setDevId(excelDevVos.get(i).getDevId());
            devBXVo.setDevName(excelDevVos.get(i).getDevName());
            devBXVo.setDate(excelDevVos.get(i).getDate());
            devBXVo.setCheckName(excelDevVos.get(i).getCheckName());
            devBXVo.setMark(excelDevVos.get(i).getMark());
            devBXVo.setIceboxName(excelDevVos.get(i).getIceboxName());
            devBXVo.setYWEmail(excelDevVos.get(i).getYWEmail());
            devBXVo.setFZREmail(excelDevVos.get(i).getFZREmail());
            list.add(devBXVo);
        }
//        List<ExcelDevBXVo> bxs = new ArrayList<>();
//        ExcelDevBXVo bx;
//        String icName = "";
//        String date = "";
//        for (int i = 0; i < list.size(); i++) {
//            bx = new ExcelDevBXVo();
//            if (list.get(i).getIceboxName().equals(icName) && list.get(i).getDate().equals(date)) {
//                if ()
//            } else {
//                bx = list.get(i);
//                bxs.add(bx);
//            }
//        }
        return list;
    }

    private List<DevUseTypeVo> handleDev(List<DevExportDataVo> vos) {
        // 按设备排序
        vos = vos.stream()
                .sorted(Comparator.comparing(DevExportDataVo::getUseType))
                .collect(Collectors.toList());
        // 重新创建类
        List<DevUseTypeVo> list = new ArrayList<>();
        DevUseTypeVo vo = new DevUseTypeVo();
        List<ExcelDevVo> devVoList = new ArrayList<>();
        ExcelDevVo devVo;
        String useType = "";

        for (DevExportDataVo dataVo : vos) {
            devVo = new ExcelDevVo();
            handleExcelDevVo(devVo, dataVo);
            if (CollectionUtils.isEmpty(devVoList)) {
                useType = dataVo.getUseType();
                devVoList.add(devVo);
            } else if (useType.equals(dataVo.getUseType())) {
                devVoList.add(devVo);
            } else {
                vo.setUseType(useType);
                vo.setExcelDevVos(devVoList);
                list.add(vo);
                useType = dataVo.getUseType();
                vo = new DevUseTypeVo();
                devVoList = new ArrayList<>();
                devVoList.add(devVo);
            }
        }
        vo.setUseType(useType);
        vo.setExcelDevVos(devVoList);
        list.add(vo);
        return list;
    }

    private void handleExcelDevVo(ExcelDevVo devVo, DevExportDataVo dataVo) {
        devVo.setDevName(dataVo.getDevName());
        devVo.setDate(dataVo.getTimeSlot().split(" ")[0]);
        devVo.setTime(dataVo.getTimeSlot().split(" ")[1]);
        devVo.setValue(dataVo.getValue());
        devVo.setCheckName(dataVo.getCheckName());
        devVo.setMark(dataVo.getMark());
        devVo.setIceboxName(dataVo.getIceboxName());
        devVo.setYWEmail(dataVo.getOperEmail());
        devVo.setFZREmail(dataVo.getDutyEmail());
        devVo.setUseType(dataVo.getUseType());
    }




}