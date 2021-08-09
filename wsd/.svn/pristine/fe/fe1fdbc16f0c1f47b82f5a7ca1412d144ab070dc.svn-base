package com.qzsoft.tah.dao;

import com.qzsoft.tah.entity.WsdDevB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author aq
 * @version 1.0 2021/7/31
 */
public interface WsdDevBDao extends JpaRepository<WsdDevB, Long> {

    @Query(value = "select * from wsd_dev_b where id in ( " +
            "select dev_id from wsd_car_b where  id = :carId " +
            "union " +
            "select a.id from wsd_dev_b a " +
            "inner join wsd_icebox_dev_f b on a.id=b.dev_id " +
            "inner join wsd_carroom_icebox_f c on b.icebox_id=c.icebox_id " +
            "where c.carroom_id = :carId )",nativeQuery = true)
    List<WsdDevB> selDevsByCar(@Param("carId") Long carId);

}
