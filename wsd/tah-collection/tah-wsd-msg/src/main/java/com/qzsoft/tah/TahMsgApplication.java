package com.qzsoft.tah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 设备报警消息
 *
 */
@SpringBootApplication
@EnableCaching
public class TahMsgApplication {
    public static void main(String[] args) {
        SpringApplication.run(TahMsgApplication.class, args);
    }
}
