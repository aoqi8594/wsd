package com.qzsoft.tah.service.impl;


import com.qzsoft.tah.dao.WsdCarBDao;
import com.qzsoft.tah.service.WsdCarBService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 车辆表(WsdCarB)表服务实现类
 *
 * @author makejava
 * @since 2021-05-10 17:25:54
 */
@Service("wsdCarBService")
public class WsdCarBServiceImpl implements WsdCarBService {
    @Resource
    private WsdCarBDao wsdCarBDao;

}