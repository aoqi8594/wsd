package com.qzsoft.tah.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;


@Component
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private TaskService taskService;
    private DelayQueue<Task> delayQueue =  new DelayQueue<>();

    @PostConstruct
    private void init() {
        taskService = this;

        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                try {
                    Task task = delayQueue.take();
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addTask(Task task){
        if(delayQueue.contains(task)){
            return;
        }
        log.info("===============》》》导出任务ID={}添加到延迟队列《《《=============", task.getId());
        delayQueue.add(task);
    }

    public void removeTask(Task task){
        delayQueue.remove(task);
    }
}
