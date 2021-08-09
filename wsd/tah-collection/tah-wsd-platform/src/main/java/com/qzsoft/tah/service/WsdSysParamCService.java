package com.qzsoft.tah.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qzsoft.tah.model.WsdSysParamC;

/**
 * 系统配置
 */
public interface WsdSysParamCService extends IService<WsdSysParamC> {
    Integer getDevCJDate(String code);

}
