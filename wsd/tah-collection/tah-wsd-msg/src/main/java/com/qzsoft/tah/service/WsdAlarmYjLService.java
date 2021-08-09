package com.qzsoft.tah.service;

import com.qzsoft.tah.entity.WsdAlarmYjL;
import com.qzsoft.tah.vo.DevBjInfoVo;

/**
 * 邮件发送日志表(WsdAlarmYjL)表服务接口
 *
 * @author makejava
 * @since 2021-04-23 11:33:34
 */
public interface WsdAlarmYjLService {


    WsdAlarmYjL save(DevBjInfoVo vo);

    void update(WsdAlarmYjL wsdAlarmYjL);
}