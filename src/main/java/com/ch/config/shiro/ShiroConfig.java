package com.ch.config.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Value("${shiro.loginUrl}")
    private String loginUrl;

    @Value("${shiro.successUrl}")
    private String successUrl;

    @Value("${shiro.unauthorizedUrl}")
    private String unauthorizedUrl;

    @Value("${shiro.filterChainDefinitions}")
    private String filterChainDefinitions;

    @Value("${shiro.timeOut}")
    private Integer timeOut;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 配置shiro安全管理器 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 登录时的链接
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        // 未授权时跳转的界面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        // filterChainDefinitions拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap();
        // authc:所有url都必须【认证】通过才可以访问
        // anon: 所有url都可以匿名访问【放行】
        String[] filterStrs = filterChainDefinitions.split(";");
        if(filterStrs != null && filterStrs.length > 0){
            for(String str : filterStrs){
                String[] s = str.split("=");
                filterChainDefinitionMap.put(s[0], s[1]);
            }
        }

        // 配置退出过滤器
        filterChainDefinitionMap.put("/logout", "logout");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        logger.debug("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * Shiro安全管理器设置realm认证
     *
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        // 设置realm.
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookie(sessionIdCookie());
        // 设置全局session过期时间
        sessionManager.setGlobalSessionTimeout(timeOut * 60 * 60 * 1000);
        return sessionManager;
    }

    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("EDUCATION_SESSION_ID");
        cookie.setHttpOnly(true);
        return cookie;
    }

    /**
     * 身份认证realm(账号密码校验；权限等)
     *
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }
}
