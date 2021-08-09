package com.qzsoft.tah.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qzsoft.tah.model.WsdPtSyncUndoRecordInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author aq
 * @since 2021-07-14
 */
@Mapper
public interface WsdPtSyncUndoRecordInfoMapper extends BaseMapper<WsdPtSyncUndoRecordInfo> {

    void saveAll(List<WsdPtSyncUndoRecordInfo> list);

}
