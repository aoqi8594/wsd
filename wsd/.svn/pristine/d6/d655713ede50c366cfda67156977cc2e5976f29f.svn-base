package com.qzsoft.tah.task;

import com.qzsoft.tah.dao.wsd.*;
import com.qzsoft.tah.entity.*;
import com.qzsoft.tah.service.BjTaskService;
import com.qzsoft.tah.service.CollectionSynInfoService;
import com.qzsoft.tah.service.WsdDevTahHService;
import org.apache.http.client.utils.DateUtils;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Configuration
@EnableScheduling
public class ScheduleBJJob {

    @Autowired
    private BjTaskService bjTaskService;

    private Logger logger = LoggerFactory.getLogger(ScheduleBJJob.class);

    public void execute()  {
        logger.info(String.format("\n\n【同步lims基础数据】 执行时间: %s \n\n",DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:dd")));
        bjTaskService.updLimsData();
    }

}
