package no.nb.microservices.pdfgenerator.health;

import no.nb.microservices.pdfgenerator.config.ByggmesterBobProperties;
import no.nb.microservices.pdfgenerator.domain.Root;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.client.RestTemplate;

public class TilemapHealthIndicator extends AbstractHealthIndicator {
	private final ByggmesterBobProperties config;
	private final RestTemplate restTemplate;

	public TilemapHealthIndicator(RestTemplate restTemplate, ByggmesterBobProperties config) {
		this.restTemplate = restTemplate;
		this.config = config;
    }

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            String urn = "URN:NBN:no-nb_digibok_2008100803017";
            Root result = restTemplate.getForObject(config.getTilemapUrlTemplate(), Root.class, urn);

            builder.up().withDetail(urn, result.getPages().getPages().size());
        } catch(Exception ex) {
        	builder.down(ex);
        }
		
	}

}
