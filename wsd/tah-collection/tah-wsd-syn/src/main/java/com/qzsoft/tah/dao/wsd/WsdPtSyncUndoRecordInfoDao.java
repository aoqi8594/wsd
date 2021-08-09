package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdPtSyncUndoRecordInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采集的具体数据
 */
@Repository
public interface WsdPtSyncUndoRecordInfoDao extends JpaRepository<WsdPtSyncUndoRecordInfo,String> {

    /**
     * 查询关联recordId的数据
     * @param recordId
     * @return
     */
    @Query(value = "select * from wsd_pt_sync_undo_record_info where record_id = :recordId",nativeQuery = true)
    List<WsdPtSyncUndoRecordInfo> selectByRecordId(String recordId);

    /**
     * 查询关联设备编号的数据
     * @param devNo
     * @return
     */
    @Query(value = "select * from wsd_pt_sync_undo_record_info where shebeibianhao = :devNo order by create_time DESC limit 1",nativeQuery = true)
    WsdPtSyncUndoRecordInfo selectLastInfoByDevNo(String devNo);

}
