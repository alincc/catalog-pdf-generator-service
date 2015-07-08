package no.nb.microservices.pdfgenerator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.removeConvertible(String.class, Object[].class);
    }
}
