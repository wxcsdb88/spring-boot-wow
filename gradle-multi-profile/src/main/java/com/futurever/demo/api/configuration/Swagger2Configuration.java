package com.futurever.demo.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * description:
 *
 * @author : wxcsdb88
 * @since : 2017/10/10 15:20
 */

@Configuration
@EnableSwagger2
//@ComponentScan(basePackages = {"com.futurever.demo.api.controller"})
public class Swagger2Configuration {

    // 如果某个接口不想暴露,可以使用以下注解
    // @ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下

    @Bean
    public Docket buildDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInfo()).useDefaultResponseMessages(false);
        docket
                .groupName("futurever-demo-gradle")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.futurever"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("futurever-demo-gradle API")
                .description("spring boot for futurever-demo-gradle API")
                .version("1.0")
                .build();
    }


}
