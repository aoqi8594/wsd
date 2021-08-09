package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.mapper.WsdPtSyncUndoRecordInfoMapper;
import com.qzsoft.tah.model.WsdPtSyncUndoRecordInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzsoft.tah.service.WsdPtSyncUndoRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aq
 * @since 2021-07-14
 */
@Service
public class WsdPtSyncUndoRecordInfoServiceImpl extends ServiceImpl<WsdPtSyncUndoRecordInfoMapper, WsdPtSyncUndoRecordInfo> implements WsdPtSyncUndoRecordInfoService {

    @Autowired
    private WsdPtSyncUndoRecordInfoMapper wsdPtSyncUndoRecordInfoMapper;

    @Override
    public boolean saveRecordInfo(List<WsdPtSyncUndoRecordInfo> wsdPtSyncUndoRecordInfos) {
        wsdPtSyncUndoRecordInfoMapper.saveAll(wsdPtSyncUndoRecordInfos);
        return true;
    }
}
