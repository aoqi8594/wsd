package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdDevAlarmL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 车辆DAO层
 */
@Repository
public interface WsdDevAlarmLDao extends JpaRepository<WsdDevAlarmL,Long> {



}
