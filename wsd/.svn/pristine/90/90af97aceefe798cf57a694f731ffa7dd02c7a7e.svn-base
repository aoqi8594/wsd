package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.dao.YwpzUserSDao;
import com.qzsoft.tah.entity.YwpzUserS;
import com.qzsoft.tah.service.YwpzUserSService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户表(YwpzUserS)表服务实现类
 *
 * @author makejava
 * @since 2021-04-23 15:59:56
 */
@Service("ywpzUserSService")
public class YwpzUserSServiceImpl implements YwpzUserSService {
    @Resource
    private YwpzUserSDao ywpzUserSDao;

    @Override
    public YwpzUserS queryUserByAccount(String account) {
        return ywpzUserSDao.findByLoginName(account);
    }
}