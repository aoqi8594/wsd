package com.qzsoft.tah.dao;


import com.qzsoft.tah.entity.WsdDevTahH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 设备历史记录表(WsdDevTahH)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-26 15:02:49
 */
public interface WsdDevTahHDao extends JpaRepository<WsdDevTahH, Long> {

    List<WsdDevTahH> findByCreateTimeBeforeAndCreateTimeAfterOrderByCreateTimeAsc(LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "select a.use_type as useType, a.icebox_name as iceboxName, " +
            "c.id as devId,c.dev_name as devName,d.create_time as dataTime, " +
            "c.oper_email as operEmail, c.duty_email as dutyEmail, c.duty_name as checkName, " +
            "DATE_FORMAT(concat(date(d.create_time), ' ', floor(HOUR(d.create_time)/:hour)*:hour), '%Y-%m-%d %H:%00') timeSlot, " +
            "AVG(d.temperature) as value " +
            "from wsd_icebox_b a " +
            "join wsd_icebox_dev_f b on a.id = b.icebox_id " +
            "join wsd_dev_b c on b.dev_id = c.id " +
            "JOIN wsd_dev_tah_h d ON d.dev_id = b.dev_id " +
            "WHERE b.dev_id in (:ids) AND (d.create_time BETWEEN :startTime AND :endTime)" +
            "GROUP BY devId, timeSlot", nativeQuery = true)
    List<Map<String, Object>>   queryDevExportDataByAvg(@Param("ids") List<Long> ids, @Param("hour") Integer hour,
                                                      @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(value = "select a.use_type as useType, a.icebox_name as iceboxName, " +
            "c.id as devId,c.dev_name as devName,d.create_time as dataTime, " +
            "c.oper_email as operEmail, c.duty_email as dutyEmail, c.duty_name as checkName, " +
            "DATE_FORMAT(concat(date(d.create_time), ' ', floor(HOUR(d.create_time)/:hour)*:hour), '%Y-%m-%d %H:%00') timeSlot," +
            "(SUM(d.temperature)-MAX(d.temperature)-MIN(d.temperature))/(COUNT(d.temperature)-2) as value " +
            "from wsd_icebox_b a " +
            "join wsd_icebox_dev_f b on a.id = b.icebox_id " +
            "join wsd_dev_b c on b.dev_id = c.id " +
            "JOIN wsd_dev_tah_h d ON d.dev_id = b.dev_id " +
            "WHERE b.dev_id in (:ids) AND (d.create_time BETWEEN :startTime AND :endTime)" +
            "GROUP BY devId, timeSlot", nativeQuery = true)
    List<Map<String, Object>> queryDevExportDataByZJZ(@Param("ids") List<Long> ids, @Param("hour") Integer hour,
                                                      @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);


    @Query(value = "select a.useType, a.iceboxName, a.devId, a.devName, a.dataTime, a.operEmail," +
            "a.dutyEmail, a.timeSlot, a.value, a.checkName " +
            "from (select a.use_type as useType, a.icebox_name as iceboxName, " +
            "c.id as devId,c.dev_name as devName,d.create_time as dataTime, c.duty_name as checkName," +
            "c.oper_email as operEmail, c.duty_email as dutyEmail, d.temperature as value, " +
            "DATE_FORMAT(concat(date(d.create_time), ' ', floor(HOUR(d.create_time)/:hour)*:hour), '%Y-%m-%d %H:%00') timeSlot, " +
            "(case a.use_type WHEN 'TPJ' THEN ABS(20 - d.temperature) " +
            "WHEN 'CWK' THEN ABS(23 - d.temperature) " +
            "WHEN 'LK' THEN ABS(-20 - d.temperature) END) ee " +
            "from wsd_icebox_b a " +
            "join wsd_icebox_dev_f b on a.id = b.icebox_id " +
            "join wsd_dev_b c on b.dev_id = c.id " +
            "JOIN wsd_dev_tah_h d ON d.dev_id = b.dev_id " +
            "WHERE b.dev_id in (:ids) AND (d.create_time BETWEEN :startTime AND :endTime)" +
            "ORDER BY ee,dataTime DESC LIMIT :count) a " +
            "GROUP BY a.devId, a.timeSlot ", nativeQuery = true)
    List<Map<String, Object>> queryDevExportDataByJBZ(@Param("ids") List<Long> ids, @Param("hour") Integer hour, @Param("count") Long count,
                                                      @Param("startTime")LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(value = "select COUNt(1) from wsd_dev_tah_h WHERE dev_id in (:ids) ", nativeQuery = true)
    Long queryDevTahHCount(@Param("ids") List<Long> ids);

    List<WsdDevTahH> findWsdDevTahHSByDevIdOrderByCreateTimeDesc(Long devId);
}