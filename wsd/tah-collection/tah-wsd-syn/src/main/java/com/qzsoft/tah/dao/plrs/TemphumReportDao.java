package com.qzsoft.tah.dao.plrs;

import com.qzsoft.tah.entity.plrs.TemphumReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemphumReportDao extends JpaRepository<TemphumReport,Long> {

    /**
     * 查询该设备的信息
     * @param devMac mac地址
     * @return
     */
    @Query(value = "select thr_id, thr_temperature, thr_humidity, thr_collecttime from temphum_report where thr_mac = :devMac ORDER BY thr_collecttime DESC LIMIT 1",nativeQuery = true)
    TemphumReport selectByDevMac(String devMac);

}

