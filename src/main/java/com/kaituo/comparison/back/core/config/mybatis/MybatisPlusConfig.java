package com.kaituo.comparison.back.core.config.mybatis;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * mybatis-plus配置
 */
@Configuration
public class MybatisPlusConfig {


    /**
     * mybatis-plus 性能分析拦截器<br>
     * 只在dev和test环境下生效
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }


    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
//        page.setDialectType("mysql");
        return page;
    }
}
