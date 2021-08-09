package com.qzsoft.tah.dao;


import com.qzsoft.tah.entity.WsdDevExportF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 导出种类设备关联表(WsdDevExportF)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-26 15:02:49
 */
public interface WsdDevExportFDao extends JpaRepository<WsdDevExportF, Long> {

    @Query(value = "select dev_id from wsd_dev_export_f where export_id = :id", nativeQuery = true)
    List<Long> findDevIdByExportId(@Param("id") Long id);
}