package com.qzsoft.tah;

import com.qzsoft.tah.udp.FreshShieldServer;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.Resource;

/**
 * 从设备平台定时采集数据至数据库中
 * @date 2021/04/15
 * @author zhw
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.qzsoft.tah.mapper")
public class TahPlatFormApplication implements CommandLineRunner {

	@Resource
	FreshShieldServer freshShieldServer;
	@Value("${wsd.server.port}")
	private String port;

	public static void main(String[] args) {
		SpringApplication.run(TahPlatFormApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		freshShieldServer.run(Integer.parseInt(port));
	}

}
