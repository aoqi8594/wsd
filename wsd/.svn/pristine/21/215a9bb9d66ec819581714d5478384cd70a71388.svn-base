package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.dao.wsd.SysTaskDao;
import com.qzsoft.tah.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private SysTaskDao sysTaskDao;

    @Override
    public void initSchedule() {
        // 系统初始化加载需要同步的设备
        /*List<SysTask> list = sysTaskDao.selectEffective();
        list.forEach(lists -> {
            String id = lists.getId().toString();
            ScheduleUtil.reset(new ScheduleTask(id, this, lists.getJobName()), lists.getCronExpression());
        });*/
    }

    @Override
    public void work(String keyword) {
        try {
            logger.info(String.format("【定时任务】 %s",keyword));
            //模拟业务逻辑
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
