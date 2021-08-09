package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdPtSyncDoRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WsdPtSyncDoRecordDao extends JpaRepository<WsdPtSyncDoRecord, String> {


}
