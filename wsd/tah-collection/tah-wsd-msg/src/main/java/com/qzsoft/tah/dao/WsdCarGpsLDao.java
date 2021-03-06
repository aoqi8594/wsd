package com.qzsoft.tah.dao;


import com.qzsoft.tah.entity.WsdCarGpsL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 温湿度车辆定位记录(WsdCarGpsL)表数据库访问层
 *
 * @author makejava
 * @since 2021-05-10 15:56:41
 */
public interface WsdCarGpsLDao extends JpaRepository<WsdCarGpsL, Long> {

    @Query(value = "select DeviceID as deviceId, latitude, longitude, dDate as createTime, lat, lng from GpsData where DeviceID=:id and dDate > :time order by dDate desc",nativeQuery = true)
    List<Map<String, Object>> queryGpsData(@Param("id") String id, @Param("time")LocalDateTime time);


    List<WsdCarGpsL> findWsdCarGpsLSByCarTaskId(Long carTaskId);

    List<WsdCarGpsL> findWsdCarGpsLSByCarTaskIdOrderByCreateTimeDesc(Long carTaskId);

}