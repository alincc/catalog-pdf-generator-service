package no.nb.sesam.bb.tilemap;

import no.nb.sesam.bb.tilemap.domain.Root;

/**
 * 
 * @author ronnym
 *
 */
public interface ITilemapRepository {

	Root findByUrn(String urn);

}
