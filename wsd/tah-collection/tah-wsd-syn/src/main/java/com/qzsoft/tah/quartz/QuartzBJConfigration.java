package com.qzsoft.tah.quartz;

import com.qzsoft.tah.task.ScheduleBJJob;
import com.qzsoft.tah.task.ScheduledJob;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author aq
 * @version 1.0 2021/6/1
 */

@Configuration
public class QuartzBJConfigration {

    @Bean(name = "bjJobDetial")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(ScheduleBJJob task){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false);
        jobDetail.setName("PL-BJ");
        jobDetail.setGroup("PL");
        jobDetail.setTargetObject(task);
        jobDetail.setTargetMethod("execute");
        return jobDetail;
    }

    @Bean(name = "bjTrigger")
    public CronTriggerFactoryBean  cronTriggerFactoryBean(@Qualifier(value = "bjJobDetial") MethodInvokingJobDetailFactoryBean jobDetail){
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(jobDetail.getObject());
        tigger.setCronExpression("0 0 0/1 * * ?");
        tigger.setName("PL-BJ");
        return tigger;
    }


    @Bean(name = "bjScheduler")
    public SchedulerFactoryBean  schedulerFactoryBean(@Qualifier(value = "bjTrigger") Trigger bjTrigger){
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setOverwriteExistingJobs(true);
        bean.setStartupDelay(1);
        bean.setTriggers(bjTrigger);
        return bean;
    }
}
