package com.qzsoft.tah.task;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.qzsoft.tah.dao.WsdCarBDao;
import com.qzsoft.tah.entity.WsdCarB;
import com.qzsoft.tah.entity.WsdExportCatB;
import com.qzsoft.tah.service.WsdCarGpsLService;
import com.qzsoft.tah.service.WsdDevAlarmLService;
import com.qzsoft.tah.service.WsdExportCatBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TaskStartupRunner implements ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private WsdDevAlarmLService wsdDevAlarmLService;

    @Resource
    private WsdExportCatBService wsdExportCatBService;

    @Resource
    private WsdCarGpsLService wsdCarGpsLService;

    @Resource
    private WsdCarBDao wsdCarBDao;

    @Resource
    private TaskService taskService;

    @Resource
    private Environment environment;

    // TODO 定时任务后面重新建立模块
    @Override
    public void run(ApplicationArguments args) {
        CronUtil.schedule("0 */1 * * * ?", (Task) () -> {
            log.info("===============》》》开始执行报警任务《《《=============");
            wsdDevAlarmLService.sendDevBJ();
        });


        CronUtil.schedule("*/10 * * * * ?", (Task) () -> {
            log.info("===============》》》开始GPS同步任务《《《=============");
            // 查询任务进行中的车辆
            List<WsdCarB> list = wsdCarBDao.findBySt("ZXZ");
            if (!CollectionUtils.isEmpty(list)) {
                log.info("===============》》》共有{}辆车需要同步《《《=============", list.size());
                wsdCarGpsLService.syncDeviceGps(list);
            }
        });


        CronUtil.schedule("0 */1 * * * ?", (Task) () -> {
            log.info("===============》》》开始执行导出任务《《《=============");
            //导出设置
            //获取配置文件设置的凌晨几点
            Integer hour = environment.getProperty("tah.time.lc-hour", Integer.class);
            hour = hour == null ? 2 : hour;
            // 查询还未执行的导出任务
            List<WsdExportCatB> wsdExportCatBS = wsdExportCatBService.queryByExportTime(LocalDateTime.now().plusMinutes(-hour*60L-1));
            if (!CollectionUtils.isEmpty(wsdExportCatBS)) {
                log.info("===============》》》共查询到{}条导出任务《《《=============", wsdExportCatBS.size());
                for (WsdExportCatB b : wsdExportCatBS) {
                    LocalDateTime exportTime = LocalDateTime.parse(b.getExportTime()+ " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    exportTime = exportTime.plusHours(hour);
                    Duration.between(exportTime, LocalDateTime.now());
                    if (Duration.between(LocalDateTime.now(), exportTime).toMinutes() < 2) {
                        taskService.addTask(new ExportTask(b.getId(), Duration.between(LocalDateTime.now(), exportTime).toMillis()));
                    }
                }
            }
        });

        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }

    public static void main(String[] args) {
        LocalDateTime exportTime = LocalDateTime.parse("2021-04-29", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(exportTime.isAfter(LocalDateTime.now()));
    }
}
