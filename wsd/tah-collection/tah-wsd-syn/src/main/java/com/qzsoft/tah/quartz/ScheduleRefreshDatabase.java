package com.qzsoft.tah.quartz;

import com.qzsoft.tah.dao.wsd.WsdSysParamCDao;
import com.qzsoft.tah.entity.WsdSysParamC;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 动态刷库获取数据库中的表达式 动态赋值新数据
 */
@Configuration
@EnableScheduling
@Component
public class ScheduleRefreshDatabase {

    private Logger logger = LoggerFactory.getLogger(ScheduleRefreshDatabase.class);

    @Autowired
    private WsdSysParamCDao wsdSysParamCDao;

    @Resource(name = "jobTrigger")
    private CronTrigger cronTrigger;

    @Resource(name = "schedulerTwo")
    private Scheduler scheduler;

    @Scheduled(fixedRate = 5000*6) // 每隔30s查库，并根据查询结果决定是否重新设置定时任务
    public void scheduleUpdateCronTrigger() throws SchedulerException {
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
        String currentCron = trigger.getCronExpression();// 当前Trigger使用的
        // 从数据库中拿去cron表达式 并重新设置定时任务
        String cron = "";
        WsdSysParamC wsdSysParamC = wsdSysParamCDao.selectCj2();
        if (wsdSysParamC!=null){
            String systemCron = wsdSysParamC.getParamVal();
            if (!StringUtils.isEmpty(systemCron)){
                // 赋值新的配置表达式
                cron = systemCron;
            }
        }
        logger.info(String.format("\n\n【数据库 平台 cron表达式】 %s \n\n",cron));
        if (currentCron.equals(cron)) {
            // 如果当前使用的cron表达式和从数据库中查询出来的cron表达式一致，则不刷新任务
        } else {
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            // 按新的cronExpression表达式重新构建trigger
            trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
            trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey())
                    .withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(cronTrigger.getKey(), trigger);
            currentCron = cron;
        }
    }

}
