package no.nb.microservices.pdfgenerator.service;

import no.nb.microservices.pdfgenerator.domain.Root;

/**
 * 
 * @author ronnym
 *
 */
public interface ITilemapService {

	Root findByUrn(String urn);
	
}
