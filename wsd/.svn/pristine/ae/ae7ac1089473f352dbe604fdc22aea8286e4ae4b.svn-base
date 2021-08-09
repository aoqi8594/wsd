package com.qzsoft.tah.dao;

import com.qzsoft.tah.entity.WsdDevAlarmL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


/**
 * 设备报警信息表(WsdDevAlarmL)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-23 11:33:34
 */
public interface WsdDevAlarmLDao extends JpaRepository<WsdDevAlarmL, Long> {

    @Query(value="SELECT a.id, b.id as devId, a.ignore_yn as ignoreYn, a.alarm_content as alarmContent," +
            "a.dev_no as devNo, a.dev_name as devName, e.notice_type as noticeType, e.sender_type as senderType," +
            "b.duty_login_name as dutyLoginName, b.operator_login_name as operatorLoginName, a.create_time as alarmTime, " +
            "b.operator_name as operatorName, b.duty_name as dutyName, e.alarm_type as alarmType, "+
            "MAX(case when e.notice_type LIKE '%YX%'  then 'Y' else 'N' end) as yx," +
            "MAX(case when e.notice_type LIKE '%DX%'  then 'Y' else 'N' end) as dx," +
            "MAX(case when e.notice_type LIKE '%APP%' then 'Y' else 'N' end) as app," +
            "MAX(case when e.notice_type LIKE '%PT%'  then 'Y' else 'N' end) as pt "+
            "FROM wsd_dev_alarm_l a " +
            "JOIN wsd_dev_b b ON a.dev_id = b.id " +
            "LEFT JOIN wsd_dev_alarm_cat_f d ON a.dev_id = d.dev_id " +
            "JOIN wsd_alarm_cat_b e ON d.alarm_cat_id = e.id " +
            "WHERE a.send_msg_yn='N' AND a.st = 'DCL' and b.st = 'QY' and e.st = 'QY'" +
            "GROUP BY a.id",nativeQuery = true)
    List<Map<String, Object>> queryDevBjInfo();
}