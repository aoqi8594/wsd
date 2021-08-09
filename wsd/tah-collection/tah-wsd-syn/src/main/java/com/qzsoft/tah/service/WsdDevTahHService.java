package com.qzsoft.tah.service;

import com.qzsoft.tah.entity.WsdDevB;
import com.qzsoft.tah.entity.WsdPtSyncUndoRecordInfo;

/**
 * 温湿度处理接口
 */
public interface WsdDevTahHService {

    /**
     * 更新温湿度并生成历史记录
     * @param wsdDevB 设备信息
     * @param info 设备采集信息
     */
    void saveDevTahH(WsdDevB wsdDevB, WsdPtSyncUndoRecordInfo info);

}
