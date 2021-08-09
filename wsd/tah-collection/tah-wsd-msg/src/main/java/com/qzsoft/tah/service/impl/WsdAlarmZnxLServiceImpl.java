package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.dao.WsdAlarmZnxLDao;
import com.qzsoft.tah.entity.WsdAlarmZnxL;
import com.qzsoft.tah.service.WsdAlarmZnxLService;
import com.qzsoft.tah.utils.UIDGenerator;
import com.qzsoft.tah.vo.DevBjInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class WsdAlarmZnxLServiceImpl implements WsdAlarmZnxLService {

    @Resource
    private WsdAlarmZnxLDao wsdAlarmZnxLDao;

    @Override
    public WsdAlarmZnxL save(DevBjInfoVo vo) {
        WsdAlarmZnxL wsdAlarmZnxL = new WsdAlarmZnxL();
        wsdAlarmZnxL.setId(UIDGenerator.getUID());
        wsdAlarmZnxL.setDevNo(vo.getDevNo());
        wsdAlarmZnxL.setSendNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        wsdAlarmZnxL.setSenderName("SYSTEM");
        wsdAlarmZnxL.setSendeeNo(Objects.equals(vo.getSenderType(), "ZJFZR") ? vo.getDutyLoginName() : vo.getOperatorLoginName());
        wsdAlarmZnxL.setSendeeName(Objects.equals(vo.getSenderType(), "ZJFZR") ? vo.getDutyName() : vo.getOperatorName());
        wsdAlarmZnxL.setTitle("设备报警");
        wsdAlarmZnxL.setContent(vo.getAlarmContent());
        wsdAlarmZnxL.setSendDm(LocalDateTime.now());
        wsdAlarmZnxL.setBusinessType(vo.getAlarmType());
        wsdAlarmZnxL.setMsgType("DXX");
        wsdAlarmZnxL.setReceiveType("ZNX");
        wsdAlarmZnxL.setIsreadYn("N");
        wsdAlarmZnxLDao.save(wsdAlarmZnxL);
        return wsdAlarmZnxL;
    }
}
