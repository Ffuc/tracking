package com.ruixun.tracking.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Program: Swagger_demo1
 * <p>
 * Description:
 *
 * @Author: 1246263205@qq.com
 * @Date: 2020-03-16 16:56
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 生成多个组通过配置多个docket对象即可
     */
    @Bean
    public Docket docket(Environment environment) {
//        //判断是否处在自己设定的环境中
//        Profiles profiles = Profiles.of("dev");
//        boolean flag = environment.acceptsProfiles(profiles); //让environment判断是否处在对应的环境中
        //判断是否处在自己设定的环境中
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true) //是否启动swagger
                .groupName("奖池") //设置自己的组
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                /**指定扫描的方式
                 * 1.withClassAnnotation 含有指定注解的类
                 * 2.withMethodAnnotation 与一同理
                 * 3.basePackage 指定包
                 *
                 * */
                /**指定过滤的方式
                 * 1.ant 匹配路径
                 * 2.regex 正则表达式
                 *
                 * */
                .build();//创建实例
    }

    private ApiInfo apiInfo() {

        return new ApiInfo("奖池",
                "有志者事竟成",
                "1.0",
                "none",
                null,
                "",

                "",
                new ArrayList<VendorExtension>()

        );
    }

}
