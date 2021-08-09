package com.qzsoft.tah.dao;

import com.qzsoft.tah.entity.WsdAlarmYjL;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 邮件发送日志表(WsdAlarmYjL)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-23 11:33:34
 */
public interface WsdAlarmYjLDao  extends JpaRepository<WsdAlarmYjL, Long> {


}