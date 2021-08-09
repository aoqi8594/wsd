package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdDevB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface WsdDevBDao extends JpaRepository<WsdDevB,Long> {

    /**
     * 获取还在使用的设备
     * @return
     */
    @Query(value = "select * from wsd_dev_b where st = 'QY' and end_number > 0 and dev_no is not null ",nativeQuery = true)
    List<WsdDevB> findEffectiveList();

    /**
     * 获取实验室还在使用的设备
     * 实验室mac地址是唯一的
     * @return
     */
    @Query(value = "select * from wsd_dev_b where st = 'QY' and end_number > 0 and dev_mac is not null",nativeQuery = true)
    List<WsdDevB> findEffectiveMacList();

    /**
     * 通过Id更新温湿度
     * @param id
     * @param avghumidity 湿度
     * @param avgtemperature01 温度
     * @param alarmYn 是否报警
     */
    @Transactional
    @Modifying
    @Query(value = "update wsd_dev_b set humidity = :avghumidity,temperature = :avgtemperature01,alarm_yn = :alarmYn,net_st=:netSt where id = :id ",nativeQuery = true)
    void updateTemperatureAndHumidity(Long id, double avghumidity, double avgtemperature01,String alarmYn,String netSt);

    /**
     * 更新设备的序列号
     * @param id
     * @param startNumber
     */
    @Transactional
    @Modifying
    @Query(value = "update wsd_dev_b set start_number = :startNumber where id = :id ",nativeQuery = true)
    void updateStartNumber(Long id,int startNumber);

    /**
     * 获取设备归属实验室冷链车以及位置
     * @return
     */
    @Query(value = "SELECT CONCAT( b.build_name, '栋', a.floor_cd, '层', a.room_cd, '房间' ) AS location FROM wsd_room_b a " +
            "INNER JOIN wsd_build_b b ON b.id = a.build_id WHERE a.dev_id =?1 " +
            "UNION " +
            "SELECT CONCAT( d.build_name, '栋', c.floor_cd, '层', C.room_cd, '房间' ) AS location FROM wsd_icebox_dev_f a " +
            "INNER JOIN wsd_carroom_icebox_f b ON a.icebox_id = b.icebox_id " +
            "INNER JOIN wsd_room_b c ON c.id = b.carroom_id " +
            "INNER JOIN wsd_build_b d ON d.id = c.build_id WHERE a.dev_id =?1",nativeQuery = true)
    Map<String,Object>  findDevBelongTypeAndLocation1(long devId);

    @Query(value = "SELECT car_no AS location FROM wsd_car_b WHERE dev_id =?1 " +
            "UNION " +
            "SELECT c.car_no AS location FROM wsd_icebox_dev_f a INNER JOIN wsd_carroom_icebox_f b ON a.icebox_id = b.icebox_id " +
            "INNER JOIN wsd_car_b c ON c.id = b.carroom_id WHERE a.dev_id =?1",nativeQuery = true)
    Map<String,Object> findDevBelongTypeAndLocation2(long devId);


    @Query(value = "select * from wsd_dev_b where dev_no = :devNo limit 1",nativeQuery = true)
    WsdDevB selDev(String devNo);

}
