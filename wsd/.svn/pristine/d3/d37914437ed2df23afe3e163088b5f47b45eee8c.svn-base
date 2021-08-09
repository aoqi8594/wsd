package com.qzsoft.tah.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzsoft.tah.mapper.WsdSysParamCMapper;
import com.qzsoft.tah.model.WsdSysParamC;
import com.qzsoft.tah.service.WsdSysParamCService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("wsdSysParamCService")
public class WsdSysParamCServiceImpl extends ServiceImpl<WsdSysParamCMapper, WsdSysParamC> implements WsdSysParamCService {
    @Override
    public Integer getDevCJDate(String code) {
        QueryWrapper<WsdSysParamC> qw = new QueryWrapper<>();
        qw.lambda().eq(WsdSysParamC::getParamTypeCode, "DEV_CJ_DATE")
                .eq(WsdSysParamC::getParamCode, code);
        WsdSysParamC one = this.getOne(qw);
        if (one == null) {
            return 10;
        }
        return StringUtils.isEmpty(one.getParamVal()) ? 10 : Integer.parseInt(one.getParamVal());
    }
}