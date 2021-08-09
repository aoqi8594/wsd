package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.mapper.WsdDevBMapper;
import com.qzsoft.tah.model.WsdDevB;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzsoft.tah.service.WsdDevBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author aq
 * @since 2021-07-14
 */
@Service
public class WsdDevBServiceImpl extends ServiceImpl<WsdDevBMapper, WsdDevB> implements WsdDevBService {

    private static Logger logger = LoggerFactory.getLogger(WsdDevBServiceImpl.class);

    @Autowired
    private WsdDevBMapper wsdDevBMapper;

    @Override
    public WsdDevB selectDevByNo(String devNo){
        List<WsdDevB> devBList = wsdDevBMapper.selectDevByNoAndSt("QY",devNo);
        return devBList.size()>0? devBList.get(0): null;
    }


}
