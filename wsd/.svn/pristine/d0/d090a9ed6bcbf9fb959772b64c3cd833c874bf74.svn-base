package com.qzsoft.tah.config.manage;

import com.qzsoft.tah.config.DataSourceNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManageFactoryPrimary",
        /*transactionManagerRef = "transactionManagerPrimary",*/
        basePackages = {"com.qzsoft.tah.dao.wsd"}
)
public class PrimaryManageConfig {

    @Autowired
    @Qualifier(value = DataSourceNames.PRIMARY)
    private DataSource primaryDataSource;

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean entityManageFactoryPrimary(EntityManagerFactoryBuilder builder){
        return builder.dataSource(primaryDataSource).packages("com.qzsoft.tah.entity").build();
    }

    /*@Bean
    @Primary
    PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = entityManageFactoryPrimary(builder);
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }*/

}
