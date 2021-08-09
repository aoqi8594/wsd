package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.dao.WsdDevAlarmLDao;
import com.qzsoft.tah.entity.WsdAlarmDxL;
import com.qzsoft.tah.entity.WsdAlarmYjL;
import com.qzsoft.tah.entity.WsdDevAlarmL;
import com.qzsoft.tah.entity.YwpzUserS;
import com.qzsoft.tah.notify.NotifyService;
import com.qzsoft.tah.service.*;
import com.qzsoft.tah.utils.JacksonUtil;
import com.qzsoft.tah.utils.RegexUtil;
import com.qzsoft.tah.vo.DevBjInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 设备报警信息表(WsdDevAlarmL)表服务实现类
 *
 * @author makejava
 * @since 2021-04-23 11:33:34
 */
@Service
public class WsdDevAlarmLServiceImpl implements WsdDevAlarmLService {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Resource
    private WsdDevAlarmLDao wsdDevAlarmLDao;

    @Resource
    private NotifyService notifyService;

    @Resource
    private WsdAlarmZnxLService WsdAlarmZnxLService;

    @Resource
    private WsdAlarmYjLService wsdAlarmYjLService;

    @Resource
    private WsdAlarmDxLService wsdAlarmDxLService;

    @Resource
    private YwpzUserSService ywpzUserSService;

    private static String emailModel = "<html>\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"utf-8\">\n" +
            "\t\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />\n" +
            "\t\t<title>报警通知</title>\n" +
            "\t\t<style type=\"text/css\">\n" +
            "\t\t\tbody{background-color: #fff;margin: 0;padding: 0;}\n" +
            "\t\t\theader{margin: 15px 3% 0;}\n" +
            "\t\t\t.content{margin: 0px 3%;font-size: 14px;padding-bottom: 10px;}\n" +
            "\t\t\t.remind{color: #7d7d7d;margin: 10px 0 0;}\n" +
            "\t\t\t.ulbox .libox{display: flex;margin: 10px 0;justify-content:space-between;}\n" +
            "\t\t\t.libox-left{width: 20%;color: #7d7d7d;}\n" +
            "\t\t\t.libox-right{width: 80%;}\n" +
            "\t\t\t.fontcolor1{color: #7d7d7d;}\n" +
            "\t\t\t.fontcolor2{color: #FF2121;}\n" +
            "\t\t\t.fontcolor3{color: #3242FF;}\n" +
            "\t\t</style>\n" +
            "\t</head>\n" +
            "\t<body>\n" +
            "\t\t<header>报警通知</header>\n" +
            "\t\t<div class=\"content\">\n" +
            "\t\t\t<p class=\"remind\">温湿度管理系统提醒您:</p>\n" +
            "\t\t\t<div class=\"ulbox\">\n" +
            "\t\t\t\t<div class=\"libox\">\n" +
            "\t\t\t\t\t<div class=\"libox-left\">设备编号:</div>\n" +
            "\t\t\t\t\t<div class=\"libox-right fontcolor1\">$deviceId</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t<div class=\"libox\">\n" +
            "\t\t\t\t\t<div class=\"libox-left\">报警类型:</div>\n" +
            "\t\t\t\t\t<div class=\"libox-right fontcolor2\">$alarmType</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t<div class=\"libox\">\n" +
            "\t\t\t\t\t<div class=\"libox-left\">报警设备:</div>\n" +
            "\t\t\t\t\t<div class=\"libox-right\">$devName</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t<div class=\"libox time\">\n" +
            "\t\t\t\t\t<div class=\"libox-left\">报警时间:</div>\n" +
            "\t\t\t\t\t<div class=\"libox-right\">$alarmTime</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t<div class=\"libox\">\n" +
            "\t\t\t\t\t<div class=\"libox-left\">备注:</div>\n" +
            "\t\t\t\t\t<div class=\"libox-right fontcolor3\">$mark</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "\t</body>\n" +
            "</html>\n";

    @Override
    public void sendDevBJ() {
        // 获取是否节假日信息
        String day = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        boolean holidayCache = HolidayManager.getHolidayCache(day);
        // 查询设备报警信息
        List<Map<String, Object>> maps = wsdDevAlarmLDao.queryDevBjInfo();

        List<DevBjInfoVo> res = JacksonUtil.parseBeanList(JacksonUtil.toJson(maps), DevBjInfoVo.class);
        if (!CollectionUtils.isEmpty(res)) {
            log.info("查询到{}条需要报警信息",res.size());
            for (DevBjInfoVo vo :res) {
                // 1.先发送站内信
                WsdAlarmZnxLService.save(vo);
                // 2.再根据设定的报警通知类型发送报警
                try {
                    sendDevBJProcess(holidayCache, vo);
                } catch (Exception e) {
                    log.error("发送报警出现错误=====》》》》{}", e.getMessage());
                }
                // 5.修改设备报警信息为已发送
                WsdDevAlarmL wsdDevAlarmL = wsdDevAlarmLDao.findById(vo.getId()).orElse(null);
                if (wsdDevAlarmL == null) {
                    log.error("该报警信息已不存在======》id={}", vo.getId());
                } else {
                    wsdDevAlarmL.setSendMsgYn("Y");
                    wsdDevAlarmLDao.save(wsdDevAlarmL);
                }
            }
        }
    }

    public void sendDevBJProcess(boolean holidayCache, DevBjInfoVo vo) {
        // 2.1 判断是否为节假日， 节假日 不发站内信以外的通知
        if (!holidayCache && "N".equals(vo.getIgnoreYn())) {
            // 3.获取负责人或运维人员信息
            YwpzUserS userInfo = ywpzUserSService.queryUserByAccount(Objects.equals(vo.getSenderType(), "ZJFZR") ? vo.getDutyLoginName() : vo.getOperatorLoginName());
            // 4.1 发送邮件
            if ("Y".equals(vo.getYx())) {
                WsdAlarmYjL way = wsdAlarmYjLService.save(vo);
                // 校验邮箱地址
                boolean email = RegexUtil.isEmail(userInfo.getEmail());
                if (!email || !notifyService.isMailEnable()) {
                    way.setSendSt("SB");
                    if (!email) {
                        way.setFailReason("邮箱校验失败=>>请检查邮箱地址");
                    } else {
                        way.setFailReason("未开启邮件服务=>>请联系管理员");
                    }
                    wsdAlarmYjLService.update(way);
                } else {
                    notifyService.notifyEmailHtml(userInfo.getEmail(), "设备报警", handleEmailContent(vo));
                }
            }
            // 4.2 发送短信
            if ("Y".equals(vo.getDx())) {
                WsdAlarmDxL wad = wsdAlarmDxLService.save(vo);
                // 校验手机号
                boolean mobilePhone = RegexUtil.isMobileSimple(userInfo.getMobilePhone());
                if (!mobilePhone || !notifyService.isSmsEnable()) {
                    wad.setSendSt("SB");
                    if (!mobilePhone) {
                        wad.setFailReason("手机号校验失败=>>请检查手机号正确性");
                    } else {
                        wad.setFailReason("未开启短信服务=>>请联系管理员");
                    }
                    wsdAlarmDxLService.update(wad);
                } else {
                    notifyService.notifySms(userInfo.getMobilePhone(), handleSMSContent(vo));
                }
            }
        }
    }



    private String handleEmailContent(DevBjInfoVo vo) {
        return emailModel.replace("$deviceId", vo.getDevNo())
                .replace("$alarmType", vo.getAlarmContent())
                .replace("$devName", vo.getDevName())
                .replace("$alarmTime", vo.getAlarmTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")))
                .replace("$mark", "");
    }

    private String handleSMSContent(DevBjInfoVo vo) {
        return "\r\n" +
                "报警设备编号:"+vo.getDevNo()+"\r\n" +
                "报警时间:"+vo.getAlarmTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"))+"\r\n" +
                "报警原因:"+vo.getAlarmContent()+"\r\n" +
                "请及时处理!";
    }
}