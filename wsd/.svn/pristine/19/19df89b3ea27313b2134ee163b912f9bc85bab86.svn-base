package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.SysTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysTaskDao extends JpaRepository<SysTask,Long> {

    /**
     * 查询有效的定时任务
     * 任务启用且表达式不能为空
     * @return
     */
    @Query(value = "select * from sys_task where job_status = 'QY' and cron_expression is not null",nativeQuery = true)
    List<SysTask> selectEffective();

}
