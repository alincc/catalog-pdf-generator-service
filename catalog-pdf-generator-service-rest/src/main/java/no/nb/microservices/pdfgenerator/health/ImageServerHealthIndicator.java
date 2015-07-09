package no.nb.microservices.pdfgenerator.health;

import no.nb.microservices.pdfgenerator.config.ByggmesterBobProperties;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.client.RestTemplate;

public class ImageServerHealthIndicator extends AbstractHealthIndicator {
	private final RestTemplate restTemplate;
	private final ByggmesterBobProperties config;;

	public ImageServerHealthIndicator(RestTemplate restTemplate, ByggmesterBobProperties config) {
		this.restTemplate = restTemplate;
		this.config = config;
    }

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		
        try {
            String urn = "URN:NBN:no-nb_digibok_2008100803017_C1";
            byte[] result = restTemplate.getForObject(config.getPageUrlTemplate(), byte[].class, urn, 1);
            builder.up().withDetail(urn, result.length);
        } catch(Exception ex) {
        	builder.down(ex);
        }

	}
}
