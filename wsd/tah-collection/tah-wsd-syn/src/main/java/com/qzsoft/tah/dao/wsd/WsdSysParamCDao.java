package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdSysParamC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 系统配置
 */
@Repository
public interface WsdSysParamCDao extends JpaRepository<WsdSysParamC,Long> {

    /**
     * 获取系统配置的采集频率
     * @return
     */
    @Query(value = "select * from wsd_sys_param_c where param_type_code = 'PL' and param_code = 'PL_CJ2'",nativeQuery = true)
    WsdSysParamC selectCj2();

    /**
     * 获取实验室配置的采集频率
     * @return
     */
    @Query(value = "select * from wsd_sys_param_c where param_type_code = 'PL' and param_code = 'PL_CJ3'",nativeQuery = true)
    WsdSysParamC selectCj3();

    @Query(value = "select * from wsd_sys_param_c where param_name = :paramName",nativeQuery = true)
    WsdSysParamC selecByParamName(String paramName);

}
