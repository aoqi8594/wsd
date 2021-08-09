package com.qzsoft.tah;


//import com.qzsoft.tah.config.Generator;
import com.qzsoft.tah.generator.AutoCodeGenerator;
import com.qzsoft.tah.model.WsdPtSyncUndoRecord;
import com.qzsoft.tah.model.WsdPtSyncUndoRecordInfo;
import com.qzsoft.tah.service.WsdDevBService;
import com.qzsoft.tah.service.WsdPtSyncUndoRecordInfoService;
import com.qzsoft.tah.service.WsdPtSyncUndoRecordService;
import com.qzsoft.tah.tools.RedisUtil;
import com.qzsoft.tah.tools.UIDGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TahPlatFormApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestJunit {

	@Autowired
	private  WsdDevBService wsdDevBService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private WsdPtSyncUndoRecordService wsdPtSyncUndoRecordService;

	@Autowired
	private WsdPtSyncUndoRecordInfoService wsdPtSyncUndoRecordInfoService;

	@Autowired
	private RedisUtil redisUtil;

	@Test
	public void testmethod01() throws Exception {
//		AutoCodeGenerator.run();
//		System.out.println(wsdDevBService.selectDevByNo("SB-1788"));
//		stringRedisTemplate.opsForValue().set("aa1","bb1");
//		System.out.println(stringRedisTemplate.opsForValue().get("aa1"));
//		redisTemplate.opsForValue().set("aa2","bb2");
//		System.out.println(redisTemplate.opsForValue().get("aa2"));
//		ValueOperations valueOperations = stringRedisTemplate.opsForValue();
//		wsdPtSyncUndoRecordService.getone("0726001");
//		wsdPtSyncUndoRecordService.getone("0726001");
		redisUtil.set("0727001","001",7);
		for(int i=0 ; i<10 ; i++){
			Thread.sleep(1000);
			System.out.println(redisUtil.getExpire("0727001"));
		}

	}



	@Test
	public void testmethod02() throws Exception {
		WsdPtSyncUndoRecord wsdPtSyncUndoRecord = new WsdPtSyncUndoRecord();
		String recordId = UIDGenerator.getUID()+"";
		wsdPtSyncUndoRecord.setId(recordId);
		wsdPtSyncUndoRecord.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		wsdPtSyncUndoRecord.setDevNo("cs001");
		wsdPtSyncUndoRecordService.saveRecord(wsdPtSyncUndoRecord);
	}

	@Test
	public void testmethod03() throws Exception {
		List<WsdPtSyncUndoRecordInfo> listInfos = new ArrayList<>();
		WsdPtSyncUndoRecordInfo wsdPtSyncUndoRecordInfo = new WsdPtSyncUndoRecordInfo();
		wsdPtSyncUndoRecordInfo.setId(UIDGenerator.getUID()+"");
		wsdPtSyncUndoRecordInfo.setRecordId("931606593376");
		wsdPtSyncUndoRecordInfo.setShebeibianhao("cs001");
		wsdPtSyncUndoRecordInfo.setTemperature01("100");
		wsdPtSyncUndoRecordInfo.setHumidity("101");
		listInfos.add(wsdPtSyncUndoRecordInfo);
		wsdPtSyncUndoRecordInfoService.saveRecordInfo(listInfos);
	}


}
