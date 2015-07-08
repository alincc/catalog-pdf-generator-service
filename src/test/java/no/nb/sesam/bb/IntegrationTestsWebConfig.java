package no.nb.sesam.bb;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "no.nb.sesam.bb")
public class IntegrationTestsWebConfig {

}
