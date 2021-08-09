package com.qzsoft.tah.dao;

import com.qzsoft.tah.entity.WsdAlarmZnxL;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 站内信发送日志表(WsdAlarmZnxL)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-23 11:33:34
 */
public interface WsdAlarmZnxLDao extends JpaRepository<WsdAlarmZnxL, Long> {

}
