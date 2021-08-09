package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.dao.WsdDevAlarmTreaMethBDao;
import com.qzsoft.tah.service.WsdDevAlarmTreaMethBService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WsdDevAlarmTreaMethBServiceImpl implements WsdDevAlarmTreaMethBService {

    @Resource
    private WsdDevAlarmTreaMethBDao wsdDevAlarmTreaMethBDao;

}
