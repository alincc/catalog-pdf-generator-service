package no.nb.microservices.pdfgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.hateoas.config.EnableHypermediaSupport;

/**
 * 
 * @author ronnym
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableOAuth2Resource
@RefreshScope
@EnableHypermediaSupport(type= {EnableHypermediaSupport.HypermediaType.HAL})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
