package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdPtSyncDoRecord;
import com.qzsoft.tah.entity.WsdPtSyncUndoRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WsdPtSyncUndoRecordDao extends JpaRepository<WsdPtSyncUndoRecord, String> {

    /**
     * 根据设备编号查询大于上次同步时间的数据
     * @param devNo 设备编号
     * @param createTime 创建时间
     * @return
     */
    @Query(value = "select * from wsd_pt_sync_undo_record where dev_no = :devNo and create_time > :createTime ",nativeQuery = true)
    List<WsdPtSyncUndoRecord> selectByDevNo(String devNo, Date createTime);

    /**
     * 查询设备编号所获取的数据
     * @param devNo
     * @return
     */
    @Query(value = "select * from wsd_pt_sync_undo_record where dev_no = :devNo ",nativeQuery = true)
    List<WsdPtSyncUndoRecord> selectByDevNo(String devNo);
}
