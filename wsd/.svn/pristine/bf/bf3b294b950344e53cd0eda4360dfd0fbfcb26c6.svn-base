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

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManageFactorySecondary",
       /* transactionManagerRef = "transactionManagerSecondary",*/
        basePackages = {"com.qzsoft.tah.dao.temp"}
)
public class SecondaryManageConfig {

    @Autowired
    @Qualifier(value = DataSourceNames.SECOND)
    private DataSource secondDataSource;

    @Bean
    LocalContainerEntityManagerFactoryBean entityManageFactorySecondary(EntityManagerFactoryBuilder builder){
        return builder.dataSource(secondDataSource).packages("com.qzsoft.tah.entity").build();
    }

   /* @Bean
    PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = entityManageFactorySecondary(builder);
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }*/

}
