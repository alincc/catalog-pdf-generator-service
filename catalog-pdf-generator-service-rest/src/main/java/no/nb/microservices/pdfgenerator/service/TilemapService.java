package no.nb.microservices.pdfgenerator.service;

import no.nb.microservices.pdfgenerator.domain.Root;
import no.nb.microservices.pdfgenerator.repository.ITilemapRepository;
import no.nb.microservices.pdfgenerator.rest.controller.ByggmesterBobController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ronnym
 *
 */
@Service
public class TilemapService implements ITilemapService {
	private static final Logger logger = LoggerFactory.getLogger(ByggmesterBobController.class);
	
	private final ITilemapRepository tilemapRepository;
	
	@Autowired
	public TilemapService(ITilemapRepository tilemapRepository) {
		super();
		this.tilemapRepository = tilemapRepository;
	}

	@Override
	public Root findByUrn(String urn) {
		logger.debug("finding tilemap for " + urn);
		return tilemapRepository.findByUrn(urn);
	}

}
