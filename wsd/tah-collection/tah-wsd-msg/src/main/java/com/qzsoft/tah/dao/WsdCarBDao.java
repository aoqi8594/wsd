package com.qzsoft.tah.dao;


import com.qzsoft.tah.entity.WsdCarB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 车辆表(WsdCarB)表数据库访问层
 *
 * @author makejava
 * @since 2021-05-10 17:25:54
 */
public interface WsdCarBDao extends JpaRepository<WsdCarB, Long> {

    List<WsdCarB> findBySt(String st);
}