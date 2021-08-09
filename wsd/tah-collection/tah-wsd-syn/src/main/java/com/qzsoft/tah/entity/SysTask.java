package com.qzsoft.tah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "sys_task")
public class SysTask {

    @Id
    @Column(name = "id")
    private Long id;
    // 任务名
    @Column(name = "job_name")
    private String jobName;
    // 任务描述
    @Column(name = "description")
    private String description;
    // cron表达式
    @Column(name = "cron_expression")
    private String cronExpression;
    // 任务执行时调用哪个类的方法 包名+类名
    @Column(name = "bean_class")
    private String beanClass;
    // 任务状态
    @Column(name = "job_status")
    private String jobStatus;
    // 任务分组
    @Column(name = "job_group")
    private String jobGroup;

    @Column(name = "create_user")
    private String createUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private String updateUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

}
