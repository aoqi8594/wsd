package com.qzsoft.tah.notify.config;

import com.qzsoft.tah.notify.NotifyService;
import com.qzsoft.tah.notify.SmsSender;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(NotifyProperties.class)
public class NotifyAutoConfiguration {

    private final NotifyProperties properties;


    public NotifyAutoConfiguration(NotifyProperties properties) {
        this.properties = properties;
    }

    @Bean
    public NotifyService notifyService() {
        NotifyService notifyService = new NotifyService();

        NotifyProperties.Mail mailConfig = properties.getMail();
        if (mailConfig.isEnable()) {
            notifyService.setMailSender(mailSender());
            notifyService.setSendFrom(mailConfig.getSendfrom());
            notifyService.setSendTo(mailConfig.getSendto());
        }

        NotifyProperties.Sms smsConfig = properties.getSms();
        if (smsConfig.isEnable()) {
            notifyService.setSmsSender(smsSender());
        }
        return notifyService;
    }

    public JavaMailSender mailSender() {
        NotifyProperties.Mail mailConfig = properties.getMail();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfig.getHost());
        mailSender.setUsername(mailConfig.getUsername());
        mailSender.setPassword(mailConfig.getPassword());
        mailSender.setPort(mailConfig.getPort());
        Properties properties = new Properties();
        properties.put("mail.smtp.timeout", 5000);// 超时
        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.socketFactory.fallback", "false");

        //阿里云 必须加入配置 outlook配置又不需要 视情况而定.发送不成功多数是这里的配置问题
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.socketFactory.port", mailConfig.getPort());
        properties.put("debug", true);
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }


    public SmsSender smsSender() {
        NotifyProperties.Sms smsConfig = properties.getSms();
        SmsSender smsSender = new SmsSender();
        smsSender.setAccount(smsConfig.getAccount());
        smsSender.setPassword(smsConfig.getPassword());
        smsSender.setUserid(smsConfig.getUserid());
        smsSender.setSign(smsConfig.getSign());
        smsSender.setSendUrl(smsConfig.getSendUrl());
        smsSender.setCountUrl(smsConfig.getCountUrl());
        return smsSender;
    }

}
