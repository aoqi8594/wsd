package com.qzsoft.tah.dao;


import com.qzsoft.tah.entity.WsdAlarmDxL;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 短信发送日志表(WsdAlarmDxL)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-23 15:17:18
 */
public interface WsdAlarmDxLDao extends JpaRepository<WsdAlarmDxL, Long> {

}