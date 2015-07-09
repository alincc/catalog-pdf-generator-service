package no.nb.microservices.pdfgenerator.repository;

import no.nb.microservices.pdfgenerator.config.ByggmesterBobProperties;
import no.nb.microservices.pdfgenerator.domain.Root;
import no.nb.microservices.pdfgenerator.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author ronnym
 *
 */
@Repository
public class TilemapRepository implements ITilemapRepository {
	private static final Logger logger = LoggerFactory.getLogger(TilemapRepository.class);
	
	private final RestTemplate restTemplate;
	private final ByggmesterBobProperties settings;
	
	@Autowired
	public TilemapRepository(
			ByggmesterBobProperties settings,
			RestTemplate restTemplate) {
		super();
		this.settings = settings;
		this.restTemplate = restTemplate;
	}

	@Override
	public Root findByUrn(String urn) {
		Root tilemap = null;
		try {
			logger.debug(settings.getTilemapUrlTemplate().replace("{urn}", urn));
			tilemap = restTemplate.getForObject(settings.getTilemapUrlTemplate(), Root.class, urn);
		} catch (Exception ex) {
			throw new NotFoundException(urn + " not found", ex);
		}
		logger.debug(tilemap.toString());
		return tilemap;
	}

}
