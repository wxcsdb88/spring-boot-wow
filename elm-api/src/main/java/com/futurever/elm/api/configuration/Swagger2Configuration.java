package com.futurever.elm.api.configuration;

import com.futurever.elm.api.constants.RequestField;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wxcsdb88 on 2017/8/13 21:35.
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    // 如果某个接口不想暴露,可以使用以下注解
    // @ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下

    @Bean
    public SecurityScheme apiKey() {
        return new ApiKey("access_token", "accessToken", "header");
    }

    @Bean
    public Docket buildDocket() {
//        String defultToken = "token";
//        ParameterBuilder parameterBuilder = new ParameterBuilder();
//        parameterBuilder
//                .parameterType("header")
//                .name(RequestField.TOKEN)
//                .defaultValue(defultToken)
//                .description("auth token")
//                .modelRef(new ModelRef("string"))
//                .required(true).build();
//        List<Parameter> parameters = new ArrayList<Parameter>();
//        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("controller")
                .apiInfo(buildApiInfo())
                .useDefaultResponseMessages(false)
//                .globalOperationParameters(parameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.futurever.elm.api.controller"))//要扫描的API(Controller)基础包
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey()));

    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("futurever API")
                .description("spring boot for fururever-elm API")
                .version("1.0")
                .build();
    }


}
