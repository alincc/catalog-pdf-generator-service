package no.nb.microservices.pdfgenerator.repository;

import no.nb.microservices.pdfgenerator.domain.Root;

/**
 * 
 * @author ronnym
 *
 */
public interface ITilemapRepository {

	Root findByUrn(String urn);

}
