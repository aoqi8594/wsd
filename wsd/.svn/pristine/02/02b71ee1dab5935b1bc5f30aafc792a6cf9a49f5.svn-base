package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.dao.WsdAlarmYjLDao;
import com.qzsoft.tah.entity.WsdAlarmYjL;
import com.qzsoft.tah.service.WsdAlarmYjLService;
import com.qzsoft.tah.utils.UIDGenerator;
import com.qzsoft.tah.vo.DevBjInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 邮件发送日志表(WsdAlarmYjL)表服务实现类
 *
 * @author makejava
 * @since 2021-04-23 11:33:34
 */
@Service("wsdAlarmYjLService")
public class WsdAlarmYjLServiceImpl implements WsdAlarmYjLService {

    @Resource
    private WsdAlarmYjLDao wsdAlarmYjLDao;

    @Override
    public WsdAlarmYjL save(DevBjInfoVo vo) {
        WsdAlarmYjL wsdAlarmYjL = new WsdAlarmYjL();
        wsdAlarmYjL.setId(UIDGenerator.getUID());
        wsdAlarmYjL.setDevNo(vo.getDevNo());
        wsdAlarmYjL.setSendNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        wsdAlarmYjL.setSenderName("SYSTEM");
        wsdAlarmYjL.setSendeeNo(Objects.equals(vo.getSenderType(), "ZJFZR") ? vo.getDutyLoginName() : vo.getOperatorLoginName());
        wsdAlarmYjL.setSendeeName(Objects.equals(vo.getSenderType(), "ZJFZR") ? vo.getDutyName() : vo.getOperatorName());
        wsdAlarmYjL.setTitle("设备报警");
        wsdAlarmYjL.setContent(vo.getAlarmContent());
        wsdAlarmYjL.setSendDm(LocalDateTime.now());
        wsdAlarmYjL.setBusinessType(vo.getAlarmType());
        wsdAlarmYjL.setMsgType("DXX");
        wsdAlarmYjL.setReceiveType("YJ");
        wsdAlarmYjLDao.save(wsdAlarmYjL);
        return wsdAlarmYjL;
    }

    @Override
    public void update(WsdAlarmYjL wsdAlarmYjL) {
        wsdAlarmYjLDao.save(wsdAlarmYjL);
    }
}