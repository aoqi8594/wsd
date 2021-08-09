package com.qzsoft.tah.service.impl;


import com.qzsoft.tah.dao.WsdAlarmDxLDao;
import com.qzsoft.tah.entity.WsdAlarmDxL;
import com.qzsoft.tah.service.WsdAlarmDxLService;
import com.qzsoft.tah.utils.UIDGenerator;
import com.qzsoft.tah.vo.DevBjInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 短信发送日志表(WsdAlarmDxL)表服务实现类
 *
 * @author makejava
 * @since 2021-04-23 15:17:18
 */
@Service("wsdAlarmDxLService")
public class WsdAlarmDxLServiceImpl implements WsdAlarmDxLService {
    @Resource
    private WsdAlarmDxLDao wsdAlarmDxLDao;

    @Override
    public WsdAlarmDxL save(DevBjInfoVo vo) {
        WsdAlarmDxL wsdAlarmDxL = new WsdAlarmDxL();
        wsdAlarmDxL.setId(UIDGenerator.getUID());
        wsdAlarmDxL.setDevNo(vo.getDevNo());
        wsdAlarmDxL.setSendNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        wsdAlarmDxL.setSenderName("SYSTEM");
        wsdAlarmDxL.setSendeeNo(Objects.equals(vo.getSenderType(), "ZJFZR") ? vo.getDutyLoginName() : vo.getOperatorLoginName());
        wsdAlarmDxL.setSendeeName(Objects.equals(vo.getSenderType(), "ZJFZR") ? vo.getDutyName() : vo.getOperatorName());
        wsdAlarmDxL.setTitle("设备报警");
        wsdAlarmDxL.setContent(vo.getAlarmContent());
        wsdAlarmDxL.setSendDm(LocalDateTime.now());
        wsdAlarmDxL.setBusinessType(vo.getAlarmType());
        wsdAlarmDxL.setMsgType("DXX");
        wsdAlarmDxL.setReceiveType("DX");
        wsdAlarmDxLDao.save(wsdAlarmDxL);
        return wsdAlarmDxL;
    }

    @Override
    public void update(WsdAlarmDxL wad) {
        wsdAlarmDxLDao.save(wad);
    }
}