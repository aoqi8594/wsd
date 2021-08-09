package com.qzsoft.tah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时处理数据
 * @date 2021/04/16
 * @author zhw
 */
@SpringBootApplication
@EnableScheduling
public class WsdSynApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsdSynApplication.class, args);
    }

}


