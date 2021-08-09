//package com.qzsoft.tah.dao.temp;
//
//import com.qzsoft.tah.config.DataSourceNames;
//import com.qzsoft.tah.entity.TemphumReport;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.List;
//
//@Repository
//public interface TemphumReportDao extends JpaRepository<TemphumReport,Long> {
//
//    /**
//     * 查询该设备某一个段时间内的信息
//     * @param devMac mac地址
//     * @param sysInfoCreateTime 上次同步时间
//     * @return
//     */
//    @Transactional(value = "transactionManagerSecondary")
//    @Query(value = "select * from temphum_report where thr_mac = :devMac and thr_reporttime > :sysInfoCreateTime",nativeQuery = true)
//    List<TemphumReport> selectByDevMacAndTime(String devMac, Date sysInfoCreateTime);
//
//    /**
//     * 查询该设备的信息
//     * @param devMac mac地址
//     * @return
//     */
//    @Transactional(value = "transactionManagerSecondary")
//    @Query(value = "select * from temphum_report where thr_mac = :devMac ",nativeQuery = true)
//    List<TemphumReport> selectByDevMac(String devMac);
//
//}
