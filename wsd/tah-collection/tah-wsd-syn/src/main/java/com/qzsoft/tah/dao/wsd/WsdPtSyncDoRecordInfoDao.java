package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdPtSyncDoRecordInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 采集的具体数据
 */
@Repository
public interface WsdPtSyncDoRecordInfoDao extends JpaRepository<WsdPtSyncDoRecordInfo,String> {


}
