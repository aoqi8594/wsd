package com.qzsoft.tah.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
  * @Author Yang Chunhai
  * @Description  服务通知类
  * @Date 2021/4/21 10:38
  **/
public class NotifyService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private JavaMailSender mailSender;
    private String sendFrom;
    private String sendTo;

    private SmsSender smsSender;

    public boolean isMailEnable() {
        return mailSender != null;
    }


    public boolean isSmsEnable() {
        return smsSender != null;
    }

    /**
      * @Author Yang Chunhai
      * @Description  邮件消息通知
      * @Date 2021/4/21 15:20
      * @Param [sendTo收件邮箱, subject 主题, content 内容]
      * @return void
      **/
    @Async
    public void notifyMail(String sendTo, String subject, String content) {
        if (mailSender == null)
            return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFrom);
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * @Author Yang Chunhai
     * @Description  带HTML格式的邮件
     * @Date  2021/4/21 15:20
     **/
    @Async
    public void notifyEmailHtml(String sendTo, String subject, String content) {
        //使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
        MimeMessage msg = mailSender.createMimeMessage();
        //创建MimeMessageHelper对象，处理MimeMessage的辅助类
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(msg, true, "UTF-8");
            //使用辅助类MimeMessage设定参数
            helper.setFrom(sendFrom);
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(content,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //发送邮件
        log.info("正在给{}发送邮件", sendTo);
        mailSender.send(msg);
    }

    /**
      * @Author Yang Chunhai
      * @Description  带附件邮件消息通知
      * @Date  2021/4/21 15:20
      **/
    @Async
    public void notifyEmailFile(String sendTo, String subject, File file) throws MessagingException {
        //使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
        MimeMessage msg = mailSender.createMimeMessage();
        //创建MimeMessageHelper对象，处理MimeMessage的辅助类
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        //使用辅助类MimeMessage设定参数
        helper.setFrom(sendFrom);
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText("您收到一封带附件的邮件");
        //加入附件
        if(file!=null)helper.addAttachment(file.getName(), file);
        //发送邮件
        log.info("正在给{}发送附件邮件", sendTo);
        mailSender.send(msg);
    }

    /**
      * @Author Yang Chunhai
      * @Description  短信消息通知
      * @Date 2021/4/21 17:50
      * @Param [phoneNumber 接收通知的电话号码, message 短消息内容]
      * @return void
      **/
    @Async
    public void notifySms(String phoneNumber, String message) {
        if (smsSender == null)
            return;
        smsSender.send(phoneNumber, message);
    }

    public String getCountSms() {
        return smsSender.querySmsCount();
    }

    public void setMailSender(JavaMailSender  mailSender) {
        this.mailSender = mailSender;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }
}
