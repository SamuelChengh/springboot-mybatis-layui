package com.ch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
*
* @类名称：MyWebMvcConfig
* @类描述：自定义静态资源映射路径和静态资源存放路径
*
* */
@Configuration
public class MyWebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/images/**").addResourceLocations("/images/");

        super.addResourceHandlers(registry);
    }
}
