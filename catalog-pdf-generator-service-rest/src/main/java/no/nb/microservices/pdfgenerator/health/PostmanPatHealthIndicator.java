package no.nb.microservices.pdfgenerator.health;

import no.nb.microservices.pdfgenerator.config.ByggmesterBobProperties;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.client.RestTemplate;

public class PostmanPatHealthIndicator extends AbstractHealthIndicator {
	private final ByggmesterBobProperties config;
	private final RestTemplate restTemplate;

	public PostmanPatHealthIndicator(RestTemplate restTemplate, ByggmesterBobProperties config) {
		this.restTemplate = restTemplate;
		this.config = config;
    }

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            String dsm2 = "digibok_2008100803017";
            restTemplate.getForObject(config.getAltoUrlTemplate(), String.class, dsm2, dsm2);
            String dsm1 = "digavis_morgenbladet_null_null_18531201_35_335_1-1_001_null";
            restTemplate.getForObject(config.getAltoUrlTemplate(), String.class, dsm1, dsm1);
            builder.up().withDetail(dsm1 + "|" + dsm2, 2);
        } catch(Exception ex) {
        	builder.down(ex);
        }
		
	}

}
