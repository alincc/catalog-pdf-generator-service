package no.nb.microservices.pdfgenerator.service;

import java.util.List;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.model.ByggmesterBobParams;

/**
 * 
 * @author ronnym
 *
 */
public interface IPageService {

	List<PageLocation> findPageLocations(ByggmesterBobParams params);

}
