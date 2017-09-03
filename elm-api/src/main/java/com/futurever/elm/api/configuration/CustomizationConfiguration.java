package com.futurever.elm.api.configuration;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Compression;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by wxcsdb88 on 2017/8/13 18:30.
 * 此处配置优先于 配置文件中配置！
 * 也可以注释掉此处的@Component ,使此处配置失效!
 */
@Component
public class CustomizationConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory server) {
//        server.setPort(8012);
        Compression compression = new Compression();
        compression.setEnabled(true);
        server.setCompression(compression);
    }

//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.setPort(9000);
//        factory.setSessionTimeout(10, TimeUnit.MINUTES);
//        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
//        return factory;
//    }

    //     单独配置 http
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHTTPConnector());
        return tomcat;
    }

    private Connector createHTTPConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8081);
        connector.setRedirectPort(8443);
        return connector;
    }
//
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addAdditionalTomcatConnectors(createSslConnector());
//        return tomcat;
//    }
//
//    private Connector createSslConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//        try {
////            InputStream inputStream = this.getClass().getResourceAsStream("keystore/keystore.p12");
//            // jar 中文件读取需要使用流！！！
//            File keystore = new ClassPathResource("keystore/keystore.p12").getFile();
//            connector.setScheme("https");
//            connector.setSecure(true);
//            connector.setPort(8443);
//            protocol.setSSLEnabled(true);
////            protocol.setKeystoreFile(keystore.getPath());
//            protocol.setKeystoreFile(keystore.getAbsolutePath());
//            protocol.setKeystorePass("uniapp");
//            protocol.setKeyAlias("tomcat");
//            return connector;
//        } catch (IOException ex) {
//            throw new IllegalStateException("can't access keystore: [" + "keystore"
//                    + "] or truststore: [" + "keystore" + "]", ex);
//        }
//    }
}
