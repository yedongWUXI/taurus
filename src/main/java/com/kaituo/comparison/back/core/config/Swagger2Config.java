package com.kaituo.comparison.back.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 *
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kaituo.comparison.back.core"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        StringBuilder description = new StringBuilder();
        description.append("查看json格式:<a target='_blank' href='./v2/api-docs'>api-docs</a> <br>")
                .append("接口文档下载:<a href='./swagger/markdown'>markdown</a>  ")
                .append("<a href='./swagger/confluence'>confluence</a>  ")
                .append("<a href='./swagger/html'>html</a> ")
                .append("<a href='./swagger/json'>json</a> ");
        return new ApiInfoBuilder()
                .title("后台管理 API文档")
                .description(description.toString())
                .version("1.0")
                .build();
    }
}
