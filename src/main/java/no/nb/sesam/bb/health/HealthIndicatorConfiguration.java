package no.nb.sesam.bb.health;

import no.nb.sesam.bb.config.ByggmesterBobProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HealthIndicatorConfiguration {


	public static class PostmanPathHealthIndicatorConfiguration {
		@Autowired
		private RestTemplate restTemplate;

		@Autowired
		ByggmesterBobProperties config;
		
		@Bean
		protected HealthIndicator postmanPatHealthIndicator() {
			return new PostmanPatHealthIndicator(restTemplate, config);
		}	
	}

	public static class TilemapHealthIndicatorConfiguration {
		@Autowired
		private RestTemplate restTemplate;

		@Autowired
		ByggmesterBobProperties config;
		
		@Bean
		protected HealthIndicator tilemapHealthIndicator() {
			return new TilemapHealthIndicator(restTemplate, config);
		}	
	}

	public static class ImageServerHealthIndicatorConfiguration {
		@Autowired
		private RestTemplate restTemplate;
		
		@Autowired
		ByggmesterBobProperties config;
		
		@Bean
		protected HealthIndicator imageHealthIndicator() {
			return new ImageServerHealthIndicator(restTemplate, config);
		}	
	}

}
