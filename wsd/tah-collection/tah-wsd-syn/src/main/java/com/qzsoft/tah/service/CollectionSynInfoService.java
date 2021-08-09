package com.qzsoft.tah.service;

import java.util.Date;

/**
 * 同步service
 */
public interface CollectionSynInfoService {

    /**
     * 生成本次同步记录
     * @param startTime 开始时间
     * @param equipmentNumber 同步数量
     * @param sysTime 同步耗时
     */
    void saveCollectionSynInfo(Date startTime, int equipmentNumber,int sysTime);

}
