package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.CollectionSysInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 每次采集记录
 */
@Repository
public interface CollectionSysInfoDao extends JpaRepository<CollectionSysInfo,String> {

    /**
     * 获取最后一次的同步记录
     * @return
     */
    @Query(value = "select * from collection_sys_info where type = :type order by create_time desc limit 1",nativeQuery = true)
    CollectionSysInfo findLastRecord(int type);
}
