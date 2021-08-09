package com.qzsoft.tah.dao;


import com.qzsoft.tah.entity.WsdExportCatB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 导出种类表(WsdExportCatB)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-26 15:02:49
 */
public interface WsdExportCatBDao extends JpaRepository<WsdExportCatB, Long> {

    List<WsdExportCatB> findByEndTimeBeforeOrderByIdAsc(LocalDateTime now);

    List<WsdExportCatB> findByExportTimeAfterOrderByIdAsc(String nowTime);

}