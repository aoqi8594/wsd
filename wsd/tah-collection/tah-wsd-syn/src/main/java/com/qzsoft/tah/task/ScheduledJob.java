package com.qzsoft.tah.task;

import com.qzsoft.tah.dao.plrs.TemphumReportDao;
import com.qzsoft.tah.dao.wsd.*;
import com.qzsoft.tah.entity.WsdDevB;
import com.qzsoft.tah.entity.WsdPtSyncUndoRecordInfo;
import com.qzsoft.tah.entity.WsdSysParamC;
import com.qzsoft.tah.entity.plrs.TemphumReport;
import com.qzsoft.tah.service.CollectionSynInfoService;
import com.qzsoft.tah.service.WsdDevTahHService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 执行定时任务类(处理从平台拉取的数据)
 * @date 2021/04/15
 * @author zhw
 */
@Component
@Configuration
@EnableScheduling
public class ScheduledJob {

    private Logger logger = LoggerFactory.getLogger(ScheduledJob.class);

    @Autowired
    private WsdDevBDao wsdDevBDao;
    @Autowired
    private CollectionSysInfoDao collectionSysInfoDao;
    @Autowired
    private WsdPtSyncUndoRecordDao wsdPtSyncUndoRecordDao;
    @Autowired
    private WsdPtSyncUndoRecordInfoDao wsdPtSyncUndoRecordInfoDao;
    @Autowired
    private WsdPtSyncDoRecordDao wsdPtSyncDoRecordDao;
    @Autowired
    private WsdPtSyncDoRecordInfoDao wsdPtSyncDoRecordInfoDao;
    @Autowired
    private CollectionSynInfoService collectionSynInfoService;
    @Autowired
    private WsdDevTahHService wsdDevTahHService;
    @Autowired
    private TemphumReportDao temphumReportDao;
    @Autowired
    private WsdSysParamCDao wsdSysParamCDao;
    // 计算业务耗时
    private static volatile long businessStartTime = 0l;
    private static volatile long businessEndTime = 0l;

    /**
     * 根据定时配置时间执行定时任务
     * @throws JobExecutionException
     */
    public void execute() throws JobExecutionException {
        logger.info(String.format("\n\n【本地数据库数据拉取】 执行时间: %s \n\n",DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:dd")));
        businessExecute();
    }

    /**
     * 业务逻辑处理
     */
    public void businessExecute(){
        businessStartTime = System.currentTimeMillis();
        // 获取需要拿数据的设备
        List<WsdDevB> wsdDevBList = wsdDevBDao.findEffectiveList();
        for (int i = 0;i<wsdDevBList.size();i++) {
            WsdDevB wsdDevB = wsdDevBList.get(i);
            logger.info(String.format("设备【%s】采集数据如下：",wsdDevB.getDevNo()));
            int startNumber = wsdDevB.getStartNumber();
            int endNumber = wsdDevB.getEndNumber();
            // 判定起始序号是否和总序号一致  如果不一致就说明该序号并没有到同步时间 跳出本次循环
            if (startNumber + 1 != (endNumber)) {
                // 更新设备序列号
                wsdDevBDao.updateStartNumber(wsdDevB.getId(), startNumber + 1);
                logger.info(String.format("起始序号【%d】最终序号【%d】不一致不能采集，请继续等待",startNumber,endNumber));
                continue;
            }
            // 只要需要 创建时间、温度、湿度三个变量
            WsdPtSyncUndoRecordInfo info = null;
            WsdSysParamC wsdSysParamC = wsdSysParamCDao.selecByParamName(wsdDevB.getManufacturer());
            if(wsdSysParamC == null || StringUtils.isBlank(wsdSysParamC.getParamCode())){
                logger.info("设备厂商为空，无法确定采集方式，采集结束");
                continue;
            }
            String manufacturerCode = wsdSysParamC.getParamCode();
            if ("ZJZL".equals(manufacturerCode)) {
                logger.info(String.format("设备厂商【%s】",wsdDevB.getManufacturer()));
                // 查询中集制冷设备最新的温湿度
                info = wsdPtSyncUndoRecordInfoDao.selectLastInfoByDevNo(wsdDevB.getDevNo());
            } else if ("PLRS".equals(manufacturerCode)){// PLRS 普拉瑞斯
                logger.info(String.format("设备厂商【%s】",wsdDevB.getManufacturer()));
                // 获取普拉瑞思设备最新的温湿度
                TemphumReport temphumReport = temphumReportDao.selectByDevMac(wsdDevB.getDevMac());
                if (temphumReport != null) {
                    info = new WsdPtSyncUndoRecordInfo();
                    info.setCreateTime(temphumReport.getThrCollecttime());
                    info.setTemperature01(temphumReport.getThrTemperature().setScale(1, BigDecimal.ROUND_HALF_UP).toString());
                    info.setHumidity(temphumReport.getThrHumidity().setScale(1, BigDecimal.ROUND_HALF_UP).toString());
                }
            }else {
                logger.info("设备厂商不在采集范围内，采集结束");
                continue;
            }
            // 更新温湿度
            wsdDevTahHService.saveDevTahH(wsdDevB, info);
            /**
            // 获取上一次同步时间(2 是本地同步)
            CollectionSysInfo collectionSysInfo = collectionSysInfoDao.findLastRecord(2);
            List<WsdPtSyncUndoRecord> recordLists = new ArrayList<>();
            List<WsdPtSyncUndoRecordInfo> recordListInfos = new ArrayList<>();
            if (collectionSysInfo != null) {
                recordLists = wsdPtSyncUndoRecordDao.selectByDevNo(devNo, collectionSysInfo.getCreateTime());
            } else {
                recordLists = wsdPtSyncUndoRecordDao.selectByDevNo(devNo);
            }
            List<WsdPtSyncDoRecord> doRecords = new ArrayList<>();
            List<WsdPtSyncDoRecordInfo> infoLists = new ArrayList<>();
            // 温度 湿度统计值
            BigDecimal avghumiditys = new BigDecimal(0);
            BigDecimal avgtemperature01s = new BigDecimal(0);
            for (WsdPtSyncUndoRecord wsdPtSyncUndoRecord : recordLists) {
                String wsdPtSyncUndoRecordId = wsdPtSyncUndoRecord.getId();
                List<WsdPtSyncUndoRecordInfo> infoList = wsdPtSyncUndoRecordInfoDao.selectByRecordId(wsdPtSyncUndoRecordId);
                // 赋值
                WsdPtSyncDoRecord wsdPtSyncDoRecord = new WsdPtSyncDoRecord();
                BeanUtils.copyProperties(wsdPtSyncUndoRecord, wsdPtSyncDoRecord);
                doRecords.add(wsdPtSyncDoRecord);
                infoList.forEach(info -> {
                    WsdPtSyncDoRecordInfo wsdPtSyncDoRecordInfo = new WsdPtSyncDoRecordInfo();
                    BeanUtils.copyProperties(info, wsdPtSyncDoRecordInfo);
                    infoLists.add(wsdPtSyncDoRecordInfo);
                });
                recordListInfos.addAll(infoList);
                // 湿度
                avghumiditys = new BigDecimal(infoList.get(0).getHumidity());
                // 温度
                avgtemperature01s = new BigDecimal(infoList.get(0).getTemperature01());
            }
            // 保存
            if (doRecords.size() > 0) {
                wsdPtSyncDoRecordDao.saveAll(doRecords);
                if (infoLists.size() > 0) {
                    wsdPtSyncDoRecordInfoDao.saveAll(infoLists);
                }
            }

            // 删除旧数据 更新温湿度值
            if (recordLists.size() < 1) continue;
            // 删除原数据
            wsdPtSyncUndoRecordDao.deleteAll(recordLists);
            if (recordListInfos.size()>0){
                wsdPtSyncUndoRecordInfoDao.deleteAll(recordListInfos);
            }
            // 汇总统计
            double avghumidity =  recordLists.size()<1?0.00:avghumiditys.divide(new BigDecimal(recordLists.size()), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            avghumidity = new BigDecimal(avghumidity).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            // 平均 温度
            double avgtemperature01 = recordLists.size()<1?0.00:avgtemperature01s.divide(new BigDecimal(recordLists.size()),2,BigDecimal.ROUND_HALF_UP).doubleValue();
            avgtemperature01 = new BigDecimal(avgtemperature01).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
             **/
            /**
            // 结束时间（用来计算本次同步所耗费时间S）
            businessEndTime = System.currentTimeMillis();
            // 生成同步记录
            collectionSynInfoService.saveCollectionSynInfo(createTime,infoLists.size(),(int) ((businessEndTime-businessStartTime)/100));
             **/
        }
    }

}
