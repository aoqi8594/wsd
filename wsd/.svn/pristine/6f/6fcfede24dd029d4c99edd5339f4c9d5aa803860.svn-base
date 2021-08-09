package com.qzsoft.tah.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author aq
 * @version 1.0 2021/6/22
 */

@Component
public class MybatisDbConfigProperty {

    private   static  String dbDir;

    private  static  String dbUrl;

    private  static  String dbDriverClassName;

    private  static  String dbUsername;

    private  static  String dbPassword;

    @Value("${user.dir}")
    public void setDbDir(String dir) {
        this.dbDir = dir;
    }

    @Value("${spring.datasource.url}")
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Value("${spring.datasource.driverClassName}")
    public void setDbDriverClassName(String dbDriverClassName) {
        this.dbDriverClassName = dbDriverClassName;
    }

    @Value("${spring.datasource.username}")
    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    @Value("${spring.datasource.password}")
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public static String getDbDir() {
        return dbDir;
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public  static String getDbDriverClassName() {
        return dbDriverClassName;
    }

    public  static String getDbUsername() {
        return dbUsername;
    }

    public  static String getDbPassword() {
        return dbPassword;
    }

}
