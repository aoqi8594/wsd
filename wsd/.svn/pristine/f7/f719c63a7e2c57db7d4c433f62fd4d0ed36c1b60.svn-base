package com.qzsoft.tah.task;

import com.qzsoft.tah.dao.wsd.*;
import com.qzsoft.tah.entity.*;
import com.qzsoft.tah.util.UIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 说的是弄一个定时任务循环扫描设备 计算到没到时间  到时间了就执行拿取数据
 */
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class CollectionScheduleSynTask {

    private static Logger logger = LoggerFactory.getLogger(CollectionScheduleSynTask.class);

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


    private static volatile long businessStartTime = 0l;
    private static volatile long businessEndTime = 0l;

    //@Async
    //@Scheduled(fixedDelay = 1000*60*5)  //间隔1分钟
    public void first(){
        businessStartTime = System.currentTimeMillis();
        Date createTime = new Date();
        // 获取需要拿数据的设备
        List<WsdDevB> wsdDevBList = wsdDevBDao.findEffectiveList();
        for (int i = 0;i<wsdDevBList.size();i++){
            WsdDevB wsdDevB = wsdDevBList.get(i);
            String devNo = wsdDevB.getDevNo();
            int startNumber = wsdDevB.getStartNumber();
            int endNumber = wsdDevB.getEndNumber();
            // 判定起始序号是否和总序号一致  如果不一致就说明该序号并没有到同步时间 跳出本次循环
            if (startNumber+1 != (endNumber)){
                // 更新设备序列号
                wsdDevBDao.updateStartNumber(wsdDevB.getId(),startNumber+1);
                continue;
            }
            // 获取上一次同步时间
            CollectionSysInfo collectionSysInfo = collectionSysInfoDao.findLastRecord(2);
            List<WsdPtSyncUndoRecord> recordLists = new ArrayList<>();
            List<WsdPtSyncUndoRecordInfo> recordListInfos = new ArrayList<>();
            if (collectionSysInfo!=null){
                recordLists = wsdPtSyncUndoRecordDao.selectByDevNo(devNo,collectionSysInfo.getCreateTime());
            }else{
                recordLists = wsdPtSyncUndoRecordDao.selectByDevNo(devNo);
            }
            List<WsdPtSyncDoRecord> doRecords = new ArrayList<>();
            List<WsdPtSyncDoRecordInfo> infoLists = new ArrayList<>();
            // 温度 湿度统计值
            BigDecimal avghumiditys = new BigDecimal(0);
            BigDecimal avgtemperature01s = new BigDecimal(0);
            for(WsdPtSyncUndoRecord wsdPtSyncUndoRecord:recordLists){
                String wsdPtSyncUndoRecordId = wsdPtSyncUndoRecord.getId();
                List<WsdPtSyncUndoRecordInfo> infoList = wsdPtSyncUndoRecordInfoDao.selectByRecordId(wsdPtSyncUndoRecordId);
                // 赋值
                WsdPtSyncDoRecord wsdPtSyncDoRecord = new WsdPtSyncDoRecord();
                BeanUtils.copyProperties(wsdPtSyncUndoRecord,wsdPtSyncDoRecord);
                doRecords.add(wsdPtSyncDoRecord);
                infoList.forEach(info->{
                    WsdPtSyncDoRecordInfo wsdPtSyncDoRecordInfo = new WsdPtSyncDoRecordInfo();
                    BeanUtils.copyProperties(info,wsdPtSyncDoRecordInfo);
                    infoLists.add(wsdPtSyncDoRecordInfo);
                });
                recordListInfos.addAll(infoList);
                // 湿度
                avghumiditys.add(new BigDecimal(wsdPtSyncUndoRecord.getAvghumidity()));
                // 温度
                avgtemperature01s.add(new BigDecimal(wsdPtSyncUndoRecord.getAvgtemperature01()));
            }
            // 保存
            if (doRecords.size()>0){
                wsdPtSyncDoRecordDao.saveAll(doRecords);
                if (infoLists.size()>0){
                    wsdPtSyncDoRecordInfoDao.saveAll(infoLists);
                }
            }
            // 起始序列号等于总序列号  就会回归0
            wsdDevBDao.updateStartNumber(wsdDevB.getId(),0);

            // 删除旧数据 更新温湿度值
            if (recordLists.size()<1) continue;
            // 删除原数据
            wsdPtSyncUndoRecordDao.deleteAll(recordLists);
            wsdPtSyncUndoRecordInfoDao.deleteAll(recordListInfos);
            // 汇总统计
            double avghumidity = avghumiditys.divide(new BigDecimal(recordLists.size()),20,BigDecimal.ROUND_HALF_UP).doubleValue();
            avghumidity = new BigDecimal(avghumidity).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            // 平均 温度
            double avgtemperature01 = avgtemperature01s.doubleValue()/recordLists.size();
            avgtemperature01 = new BigDecimal(avgtemperature01).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            // 更新温湿度
            //wsdDevBDao.updateTemperatureAndHumidity(wsdDevB.getId(),avghumidity,avgtemperature01);
            // 结束时间（用来计算本次同步所耗费时间S）
            businessEndTime = System.currentTimeMillis();
            // 生成同步记录
            saveCollectionSynInfo(createTime,infoLists.size());
        }
    }

    /**
     * 生成本地同步记录
     * @param startTime 创建时间
     * @param equipmentNumber 同步记录数
     */
    public void saveCollectionSynInfo(Date startTime,int equipmentNumber){
        // 记录执行任务
        Date endTime = new Date();
        CollectionSysInfo collectionSysInfo = new CollectionSysInfo();
        collectionSysInfo.setId(UIDGenerator.getUID()+"");
        collectionSysInfo.setCreateTime(startTime);
        collectionSysInfo.setEndTime(endTime);
        collectionSysInfo.setSt("CG");
        collectionSysInfo.setSysTime((int) ((businessEndTime-businessStartTime)/100));
        collectionSysInfo.setEquipmentNumber(equipmentNumber);
        collectionSysInfo.setType(2);// 本地同步
        collectionSysInfoDao.save(collectionSysInfo);
    }
}
