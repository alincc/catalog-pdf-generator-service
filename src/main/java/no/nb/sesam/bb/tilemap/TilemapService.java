package no.nb.sesam.bb.tilemap;

import no.nb.sesam.bb.ByggmesterBobController;
import no.nb.sesam.bb.tilemap.domain.Root;

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
