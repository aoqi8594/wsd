package com.qzsoft.tah.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = DataSourceNames.PRIMARY)
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource(){
        return new DruidDataSource();
    }

    @Bean(name = DataSourceNames.SECOND)
    @ConfigurationProperties(prefix="spring.datasource.secondary")
    public DataSource secondDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = DataSourceNames.PLRS)
    @ConfigurationProperties(prefix="spring.datasource.plrs")
    public DataSource PLRSDataSource() {
        return new DruidDataSource();
    }
}
