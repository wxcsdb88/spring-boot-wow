package com.futurever.elm.api.configuration;


import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.futurever.elm.api.AppApplication;
import com.futurever.elm.api.interceptor.AuthInterceptor;
import com.futurever.elm.api.interceptor.LoginInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxcsdb88 on 2017/8/13 23:04.
 */
@Configuration
@ComponentScan(basePackageClasses = AppApplication.class, useDefaultFilters = true)
public class MvcConfiguration extends WebMvcConfigurationSupport {

    public static String[] includeUrlsArray;
    public static String[] excludeUrlsArray;

    static {
        ArrayList<String> includeUrls = new ArrayList<>();
        includeUrls.add("/orders/**");
//        includeUrls.add("/users/**");
        includeUrlsArray = includeUrls.toArray(new String[0]);

        ArrayList<String> excludeUrls = new ArrayList<>();
        excludeUrls.add("/users");
        excludeUrls.add("/login");
        excludeUrls.add("/logout");
        excludeUrls.add("/api-docs");
        excludeUrlsArray = excludeUrls.toArray(new String[0]);

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypeList = new ArrayList<MediaType>();
        mediaTypeList.add(new MediaType("application", "json"));
        mediaTypeList.add(new MediaType("application", "json", Charset.forName("UTF-8")));
        mediaTypeList.add(new MediaType("text", "html", Charset.forName("UTF-8")));
        fastConverter.setSupportedMediaTypes(mediaTypeList);

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setFeatures(
                Feature.AllowArbitraryCommas,
                Feature.AllowUnQuotedFieldNames,
                Feature.DisableCircularReferenceDetect
        );
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(excludeUrlsArray)
        ;
//        registry.addInterceptor(new ParameterInterceptor()).order(2)
//                .addPathPatterns(includeUrlsArray)
//                .excludePathPatterns("/register")
//                .addPathPatterns((String[]) includeUrls.toArray())
//                .addPathPatterns("/login")
        ;

        registry.addInterceptor(new LoginInterceptor()).order(1)
                .addPathPatterns(includeUrlsArray)
//                .addPathPatterns((String[]) includeUrls.toArray())
                .excludePathPatterns(excludeUrlsArray)
//                .excludePathPatterns((String[]) excludeUrls.toArray())
        ;
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
        ;
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
        ;
    }
}
