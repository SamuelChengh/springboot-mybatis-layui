package com.ch.config.mybatis;

import com.ch.config.druid.DruidConfig;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@AutoConfigureAfter(DruidConfig.class)
@EnableTransactionManagement
@MapperScan(basePackages = "com.ch.dao")
public class MybatisConfig {

    //  配置类型别名
    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;

    //  配置mapper的扫描，找到所有的mapper.xml映射文件
    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;

    //  mybatis配置文件
    @Value("${mybatis.configLocation}")
    private String configLocation;

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    //  分页插件
    @Bean
    public Interceptor pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "false");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        //  读取配置
        sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

        //  设置mapper.xml文件所在位置
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        sessionFactoryBean.setMapperLocations(resources);

        //  设置mybatis-config.xml配置文件位置
        sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

        //  添加分页插件
        Interceptor[] interceptors = new Interceptor[1];
        interceptors[0] = pageHelper();
        sessionFactoryBean.setPlugins(interceptors);

        return sessionFactoryBean.getObject();
    }
}
