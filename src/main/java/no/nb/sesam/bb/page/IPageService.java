package no.nb.sesam.bb.page;

import java.util.List;

import no.nb.bookgenerator.PageLocation;
import no.nb.sesam.bb.ByggmesterBobParams;

/**
 * 
 * @author ronnym
 *
 */
public interface IPageService {

	List<PageLocation> findPageLocations(ByggmesterBobParams params);

}
