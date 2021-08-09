//package com.qzsoft.tah.task;
//
//import com.qzsoft.tah.dao.plrs.TemphumReportDao;
//import com.qzsoft.tah.dao.wsd.CollectionSysInfoDao;
//import com.qzsoft.tah.dao.temp.TemphumReportDao;
//import com.qzsoft.tah.dao.wsd.WsdDevBDao;
//import com.qzsoft.tah.dao.wsd.WsdPtSyncDoRecordInfoDao;
//import com.qzsoft.tah.entity.CollectionSysInfo;
//import com.qzsoft.tah.entity.TemphumReport;
//import com.qzsoft.tah.entity.WsdDevB;
//import com.qzsoft.tah.entity.WsdPtSyncDoRecordInfo;
//import com.qzsoft.tah.util.UIDGenerator;
//import org.apache.http.client.utils.DateUtils;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * 执行实验室定时任务类
// * @date 2021/04/15
// * @author zhw
// */
//@Component
//@Configuration
//@EnableScheduling
//public class ScheduledTempJob {
//
//    private Logger logger = LoggerFactory.getLogger(ScheduledTempJob.class);
//
//    @Autowired
//    private WsdDevBDao wsdDevBDao;
//
//    @Autowired
//    private CollectionSysInfoDao collectionSysInfoDao;
//
//    @Autowired
//    private TemphumReportDao temphumReportDao;
//
//    @Autowired
//    private WsdPtSyncDoRecordInfoDao wsdPtSyncDoRecordInfoDao;
//
//    // 计算业务耗时
//    private static volatile long businessStartTime = 0l;
//    private static volatile long businessEndTime = 0l;
//
//    /**
//     * 根据定时配置时间执行定时任务
//     * @throws JobExecutionException
//     */
//    public void execute() throws JobExecutionException {
//        logger.info(String.format("\n\n【本地实验室数据拉取】 执行时间: %s \n\n", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:dd")));
//        //businessExecute();
//    }
//
//    /**
//     * 执行实验室业务逻辑
//     */
//    public void businessExecute(){
//        businessStartTime = System.currentTimeMillis();
//        Date createTime = new Date();
//        // 获取需要拉取的设备
//        List<WsdDevB> wsdDevBList = wsdDevBDao.findEffectiveMacList();
//        for (int i = 0;i<wsdDevBList.size();i++) {
//            WsdDevB wsdDevB = wsdDevBList.get(i);
//            // 设备mac地址
//            String devMac = wsdDevB.getDevMac();
//            int startNumber = wsdDevB.getStartNumber();
//            int endNumber = wsdDevB.getEndNumber();
//            // 判定起始序号是否和总序号一致  如果不一致就说明该序号并没有到同步时间 跳出本次循环
//            if (startNumber + 1 != (endNumber)) {
//                // 更新设备序列号
//                wsdDevBDao.updateStartNumber(wsdDevB.getId(), startNumber + 1);
//                continue;
//            }
//            // 获取上一次同步时间(3 实验室同步)
//            CollectionSysInfo collectionSysInfo = collectionSysInfoDao.findLastRecord(3);
//            List<TemphumReport> reportList = new ArrayList<>();
//            if (collectionSysInfo!=null){
//                // 按照上次同步时间来算
//                Date sysInfoCreateTime = collectionSysInfo.getCreateTime();
//                reportList = temphumReportDao.selectByDevMacAndTime(devMac,sysInfoCreateTime);
//            }else {
//                // 如果没有同步记录 则全部拿到该设备的采集数据
//                reportList = temphumReportDao.selectByDevMac(devMac);
//            }
//            // 温度 湿度统计值
//            BigDecimal avghumiditys = new BigDecimal(0);
//            BigDecimal avgtemperature01s = new BigDecimal(0);
//            List<WsdPtSyncDoRecordInfo> wsdPtSyncDoRecordInfos = new ArrayList<>();
//            for (TemphumReport temphumReport:reportList){
//                // 湿度
//                BigDecimal thrHumidity = temphumReport.getThrHumidity();
//                avghumiditys = avghumiditys.add(thrHumidity);
//                // 温度
//                BigDecimal temperature = temphumReport.getThrTemperature();
//                avgtemperature01s = avgtemperature01s.add(temperature);
//                // 数据转换
//                WsdPtSyncDoRecordInfo wsdPtSyncDoRecordInfo = setRecordInfoByTemphumReport(temphumReport,createTime);
//                wsdPtSyncDoRecordInfos.add(wsdPtSyncDoRecordInfo);
//            }
//            // 保存记录
//            if (wsdPtSyncDoRecordInfos.size()>0){
//                wsdPtSyncDoRecordInfoDao.saveAll(wsdPtSyncDoRecordInfos);
//            }
//            // 起始序列号等于总序列号  就会回归0
//            wsdDevBDao.updateStartNumber(wsdDevB.getId(), 0);
//            // 汇总统计
//            double avghumidity = wsdPtSyncDoRecordInfos.size()<1?0.00:avghumiditys.divide(new BigDecimal(wsdPtSyncDoRecordInfos.size()), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            avghumidity = new BigDecimal(avghumidity).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            // 平均 温度
//            double avgtemperature01 = wsdPtSyncDoRecordInfos.size()<1?0.00:avgtemperature01s.divide(new BigDecimal(wsdPtSyncDoRecordInfos.size()),2,BigDecimal.ROUND_HALF_UP).doubleValue();
//            avgtemperature01 = new BigDecimal(avgtemperature01).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            // 更新温湿度
//            //wsdDevBDao.updateTemperatureAndHumidity(wsdDevB.getId(), avghumidity, avgtemperature01);
//            // 结束时间（用来计算本次同步所耗费时间S）
//            businessEndTime = System.currentTimeMillis();
//            // 生成同步记录
//            saveCollectionSynInfo(createTime, wsdPtSyncDoRecordInfos.size());
//            // 归零
//            businessStartTime = 0;
//            businessEndTime = 0;
//        }
//
//    }
//
//    /**
//     * 生成本地同步记录
//     * @param startTime 创建时间
//     * @param equipmentNumber 同步记录数
//     */
//    public void saveCollectionSynInfo(Date startTime,int equipmentNumber){
//        // 记录执行任务
//        Date endTime = new Date();
//        CollectionSysInfo collectionSysInfo = new CollectionSysInfo();
//        collectionSysInfo.setId(UIDGenerator.getUID()+"");
//        collectionSysInfo.setCreateTime(startTime);
//        collectionSysInfo.setEndTime(endTime);
//        collectionSysInfo.setSt("CG");
//        collectionSysInfo.setSysTime((int) ((businessEndTime-businessStartTime)/100));
//        collectionSysInfo.setEquipmentNumber(equipmentNumber);
//        collectionSysInfo.setType(3);// 数据库同步
//        collectionSysInfoDao.save(collectionSysInfo);
//    }
//
//    /**
//     * 数据转换
//     * @param temphumReport 采集数据
//     * @param createTime 创建时间
//     * @return
//     */
//    public WsdPtSyncDoRecordInfo setRecordInfoByTemphumReport(TemphumReport temphumReport,Date createTime){
//        WsdPtSyncDoRecordInfo wsdPtSyncDoRecordInfo = new WsdPtSyncDoRecordInfo();
//        wsdPtSyncDoRecordInfo.setId(UIDGenerator.getUID()+"");
//        wsdPtSyncDoRecordInfo.setCreateTime(createTime);
//        // 温度
//        BigDecimal temperature = temphumReport.getThrTemperature();
//        wsdPtSyncDoRecordInfo.setTemperature01(temperature+"");
//        // 湿度
//        BigDecimal thrHumidity = temphumReport.getThrHumidity();
//        wsdPtSyncDoRecordInfo.setHumidity(thrHumidity+"");
//        // 电量
//        BigDecimal thrCharge = temphumReport.getThrCharge();
//        wsdPtSyncDoRecordInfo.setPower(thrCharge+"");
//        // 采集时间
//        wsdPtSyncDoRecordInfo.setTime(DateUtils.formatDate(temphumReport.getThrCollecttime(),"yyyy-MM-dd HH:mm:dd"));
//        // 接收时间
//        wsdPtSyncDoRecordInfo.setServicetime(DateUtils.formatDate(temphumReport.getThrReceivetime(),"yyyy-MM-dd HH:mm:dd"));
//        // 信号强度
//        wsdPtSyncDoRecordInfo.setXinghaoqiangdu(temphumReport.getThrRssi().intValue());
//        return wsdPtSyncDoRecordInfo;
//    }
//
//}
