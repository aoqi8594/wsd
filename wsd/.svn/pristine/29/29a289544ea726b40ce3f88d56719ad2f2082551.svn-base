package com.qzsoft.tah.config.manage;

import com.qzsoft.tah.config.DataSourceNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManageFactoryPLRS",basePackages = {"com.qzsoft.tah.dao.plrs"})
public class PLRSManageConfig {
    @Autowired
    @Qualifier(value = DataSourceNames.PLRS)
    private DataSource plrsDataSource;

    @Bean
    LocalContainerEntityManagerFactoryBean entityManageFactoryPLRS(EntityManagerFactoryBuilder builder){
        return builder.dataSource(plrsDataSource).packages("com.qzsoft.tah.entity.plrs").build();
    }
}
