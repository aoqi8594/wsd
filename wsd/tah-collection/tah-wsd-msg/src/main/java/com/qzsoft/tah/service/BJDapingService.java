package com.qzsoft.tah.service;

import com.qzsoft.tah.entity.WsdDevTahH;

import java.util.List;
import java.util.Map;

/**
 * @author aq
 * @version 1.0 2021/7/31
 */
public interface BJDapingService {

    Map<String,Object> getDevsInfo();

    Map<String,Object> getCarsInfo();

    Map<String,Object> getCarDevsInfo(String carId);

    List<WsdDevTahH> getDevHistoryInfo(String devId);

}
