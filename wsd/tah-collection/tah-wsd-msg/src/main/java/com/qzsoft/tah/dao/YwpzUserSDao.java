package com.qzsoft.tah.dao;

import com.qzsoft.tah.entity.YwpzUserS;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户表(YwpzUserS)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-23 15:59:56
 */
public interface YwpzUserSDao extends JpaRepository<YwpzUserS, Long> {

    YwpzUserS findByLoginName(String loginName);


}