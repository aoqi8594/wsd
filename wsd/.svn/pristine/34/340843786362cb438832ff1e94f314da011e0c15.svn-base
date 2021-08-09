package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdDevTahH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 设备历史记录DAO层
 */
@Repository
public interface WsdDevTahHDao extends JpaRepository<WsdDevTahH,Long> {

    /**
     * 查询设备数据最新的历史记录
     * @param devNo
     * @return
     */
    @Query(value = "select * from wsd_dev_tah_h where dev_no = :devNo order by create_time DESC limit 1",nativeQuery = true)
    WsdDevTahH selectLastInfoByDevNo(String devNo);

}
