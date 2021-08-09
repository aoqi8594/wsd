package com.qzsoft.tah.notify;

import cn.hutool.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SmsSender {
    private final Logger log = LoggerFactory.getLogger(SmsSender.class);

    private String account;
    private String password;
    private String userid;
    private String sign;
    private String sendUrl;
    private String countUrl;

    public SmsResult send(String phone, String content) {
        try {
            log.info("正在发送==》{}", phone);
            Map<String, Object> maps = new HashMap<>();
            maps.put("account", account);
            maps.put("password", password);
            maps.put("userid", userid);
            maps.put("mobile", phone);
            maps.put("content", "【" + sign +"】" + content);
            String result = HttpRequest.post(sendUrl)
                    .form(maps)
                    .execute().body();
            log.info("发送完毕==》{}", result);
            SmsResult smsResult = new SmsResult();
            smsResult.setSuccessful(true);
            smsResult.setResult(result);
        } catch (Exception e) {
            log.error("短信发送异常=>[{}]", e.getMessage());
        }
        SmsResult smsResult = new SmsResult();
        smsResult.setSuccessful(false);
        return smsResult;
    }

    public String querySmsCount() {
        Map<String, Object> maps = new HashMap<>();
        maps.put("account", account);
        maps.put("password", password);
        maps.put("userid", userid);
        return HttpRequest.post(countUrl)
                .form(maps)
                .execute().body();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getCountUrl() {
        return countUrl;
    }

    public void setCountUrl(String countUrl) {
        this.countUrl = countUrl;
    }
}
