package com.ch.config.mybatis;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@AutoConfigureAfter
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = "com.ch.dao")
public class MybatisPlusConfig {

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis-plus.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis-plus.global-config.id-type}")
    private Integer idType;

    @Value("${mybatis-plus.global-config.field-strategy}")
    private int fieldStrategy;

    @Value("${mybatis-plus.global-config.capital-mode}")
    private boolean capitalMode;

    @Value("${mybatis-plus.global-config.refresh-mapper}")
    private boolean refreshMapper;

    @Autowired
    private DataSource dataSource;

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    // mybatis plus 全局配置
    @Bean(name = "globalConfig")
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration configuration = new GlobalConfiguration();
        // 主键策略
        configuration.setIdType(idType);
        // 字段策略
        configuration.setFieldStrategy(fieldStrategy);
        // 刷新mapper
        configuration.setRefresh(refreshMapper);
        // 数据库大写下划线转换
        configuration.setCapitalMode(capitalMode);

        configuration.setDbType("mysql");

        return configuration;
    }

    //  分页插件
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        //配置mysql数据库的方言
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory createSqlSessionFactoryBean(@Qualifier(value = "globalConfig") GlobalConfiguration configuration) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Interceptor[] interceptor = {new PaginationInterceptor()};
        sqlSessionFactoryBean.setPlugins(interceptor);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setGlobalConfig(configuration);
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
            sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean.getObject();
    }
}
