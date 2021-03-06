package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.mapper.WsdPtSyncUndoRecordMapper;
import com.qzsoft.tah.model.WsdPtSyncUndoRecord;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzsoft.tah.service.WsdPtSyncUndoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aq
 * @since 2021-07-14
 */
@Service
public class WsdPtSyncUndoRecordServiceImpl extends ServiceImpl<WsdPtSyncUndoRecordMapper, WsdPtSyncUndoRecord> implements WsdPtSyncUndoRecordService {

    @Autowired
    private WsdPtSyncUndoRecordMapper wsdPtSyncUndoRecordMapper;

    @Override
    public boolean saveRecord(WsdPtSyncUndoRecord wsdPtSyncUndoRecord) {
        wsdPtSyncUndoRecordMapper.save(wsdPtSyncUndoRecord);
        return true;
    }

}
