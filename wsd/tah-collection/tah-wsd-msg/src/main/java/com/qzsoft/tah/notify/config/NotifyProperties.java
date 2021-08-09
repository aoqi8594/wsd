package com.qzsoft.tah.notify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tah.notify")
public class NotifyProperties {
    private Mail mail;
    private Sms sms;

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public static class Mail {
        private boolean enable;
        private String host;
        private String username;
        private String password;
        private String sendfrom;
        private String sendto;
        private Integer port;
        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSendfrom() {
            return sendfrom;
        }

        public void setSendfrom(String sendfrom) {
            this.sendfrom = sendfrom;
        }

        public String getSendto() {
            return sendto;
        }

        public void setSendto(String sendto) {
            this.sendto = sendto;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }

    public static class Sms {
        private boolean enable;
        private String account;
        private String password;
        private String userid;
        private String sign;
        private String sendUrl;
        private String countUrl;

        public boolean isEnable() {
            return enable;
        }
        public void setEnable(boolean enable) {
            this.enable = enable;
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
}
