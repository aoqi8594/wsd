package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdDevAlarmTreaMethB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 报警温度处理方式DAO层
 */
@Repository
public interface WsdDevAlarmTreaMethBDao extends JpaRepository<WsdDevAlarmTreaMethB,Long> {


    /**
     * 查询设备最近一次的处理方式
     * @param devId 设备id
     * @return
     */
    @Query(value = "select * from wsd_dev_alarm_trea_meth_b where dev_id = :devId order by create_time desc limit 1 ",nativeQuery = true)
    WsdDevAlarmTreaMethB selectByDevIdLastTime(Long devId);

}
