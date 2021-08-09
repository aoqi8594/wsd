package com.qzsoft.tah.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.stereotype.Component;

/**
 * @author aq
 * @version 1.0 2021/6/22
 */
@Component
public class AutoCodeGenerator {

    public static  void run() {
        String packageName = "com.qzsoft.tah";
        boolean serviceNameStartWithI = false;//auth -> UserService, 设置成true: auth -> IUserService
        generateByTables(serviceNameStartWithI, packageName, "aq", "humiture",
                new String[] {"wsd_pt_sync_undo_record","wsd_pt_sync_undo_record_info","wsd_dev_b"});
        System.out.println("completed...");
    }

    private static void generateByTables(boolean serviceNameStartWithI, String packageName, String author, String database, String... tableNames) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(MybatisDbConfigProperty.getDbUrl())
                .setUsername(MybatisDbConfigProperty.getDbUsername())
                .setPassword(MybatisDbConfigProperty.getDbPassword())
                .setDriverName(MybatisDbConfigProperty.getDbDriverClassName());
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false)
                .setAuthor(author)
                .setOutputDir("e:\\codeGen")
                .setFileOverride(true)
                .setEnableCache(false);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("model")
                                .setMapper("mapper")
                                .setService("service")
                                .setServiceImpl("service.impl")
                                .setXml("config")
                ).execute();
    }
}
