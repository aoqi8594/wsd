package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.dao.wsd.CollectionSysInfoDao;
import com.qzsoft.tah.entity.CollectionSysInfo;
import com.qzsoft.tah.service.CollectionSynInfoService;
import com.qzsoft.tah.util.UIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 同步记录业务逻辑处理
 */
@Service
public class CollectionSynInfoServiceImpl implements CollectionSynInfoService {

    private Logger logger = LoggerFactory.getLogger(CollectionSynInfoServiceImpl.class);

    @Autowired
    private CollectionSysInfoDao collectionSysInfoDao;

    @Override
    public void saveCollectionSynInfo(Date startTime, int equipmentNumber,int sysTime) {
        // 记录执行任务
        Date endTime = new Date();
        CollectionSysInfo collectionSysInfo = new CollectionSysInfo();
        collectionSysInfo.setId(UIDGenerator.getUID()+"");
        collectionSysInfo.setCreateTime(startTime);
        collectionSysInfo.setEndTime(endTime);
        collectionSysInfo.setSt("CG");
        //collectionSysInfo.setSysTime((int) ((businessEndTime-businessStartTime)/100));
        collectionSysInfo.setEquipmentNumber(equipmentNumber);
        collectionSysInfo.setType(2);// 本地同步
        collectionSysInfoDao.save(collectionSysInfo);
    }
}
