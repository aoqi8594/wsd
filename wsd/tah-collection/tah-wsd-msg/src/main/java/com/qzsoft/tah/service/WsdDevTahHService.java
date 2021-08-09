package com.qzsoft.tah.service;

import com.qzsoft.tah.entity.WsdExportCatB;
import com.qzsoft.tah.vo.DevExportDataVo;

import java.util.List;

/**
 * 设备历史记录表(WsdDevTahH)表服务接口
 *
 * @author makejava
 * @since 2021-04-26 15:02:49
 */
public interface WsdDevTahHService {

    List<DevExportDataVo> queryDevExportData(List<Long> devIds, WsdExportCatB b);
}