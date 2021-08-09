package com.qzsoft.tah.service;

import com.qzsoft.tah.entity.WsdCarB;
import com.qzsoft.tah.entity.WsdCarGpsL;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 温湿度车辆定位记录(WsdCarGpsL)表服务接口
 *
 * @author makejava
 * @since 2021-05-10 15:56:41
 */
public interface WsdCarGpsLService {

    void syncDeviceGps(List<WsdCarB> list);

    void saveGpsData(List<WsdCarGpsL> list, Long carTaskId, LocalDateTime now);
}