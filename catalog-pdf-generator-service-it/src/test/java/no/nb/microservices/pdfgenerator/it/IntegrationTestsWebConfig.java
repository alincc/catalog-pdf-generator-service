package no.nb.microservices.pdfgenerator.it;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "no.nb.microservices.pdfgenerator")
public class IntegrationTestsWebConfig {

}
