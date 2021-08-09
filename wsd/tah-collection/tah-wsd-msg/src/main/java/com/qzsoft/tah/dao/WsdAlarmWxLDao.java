package com.qzsoft.tah.dao;

import com.qzsoft.tah.entity.WsdAlarmWxL;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 微信发送日志表(WsdAlarmWxL)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-23 11:33:34
 */
public interface WsdAlarmWxLDao extends JpaRepository<WsdAlarmWxL, Long> {


}