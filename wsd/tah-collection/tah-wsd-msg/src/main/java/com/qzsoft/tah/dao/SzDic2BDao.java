package com.qzsoft.tah.dao;

import com.qzsoft.tah.entity.SzDic2B;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 设置-字典表(SzDic2B)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-29 13:18:51
 */
public interface SzDic2BDao extends JpaRepository<SzDic2B, Long> {

}