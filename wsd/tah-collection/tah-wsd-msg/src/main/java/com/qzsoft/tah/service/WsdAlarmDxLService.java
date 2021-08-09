package com.qzsoft.tah.service;

import com.qzsoft.tah.entity.WsdAlarmDxL;
import com.qzsoft.tah.vo.DevBjInfoVo;

/**
 * 短信发送日志表(WsdAlarmDxL)表服务接口
 *
 * @author makejava
 * @since 2021-04-23 15:17:18
 */
public interface WsdAlarmDxLService {


    WsdAlarmDxL save(DevBjInfoVo vo);

    void update(WsdAlarmDxL wad);
}