package com.qzsoft.tah.service;

import com.qzsoft.tah.entity.WsdExportCatB;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 导出种类表(WsdExportCatB)表服务接口
 *
 * @author makejava
 * @since 2021-04-26 15:02:49
 */
public interface WsdExportCatBService {

    String recordExportCat(Long id, Boolean timely);

    List<WsdExportCatB> queryByExportTime(LocalDateTime now);
}