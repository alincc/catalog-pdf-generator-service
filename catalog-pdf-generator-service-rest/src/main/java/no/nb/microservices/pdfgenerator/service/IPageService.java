package no.nb.microservices.pdfgenerator.service;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.model.ByggmesterBobParams;

import java.util.List;

/**
 * 
 * @author ronnym
 *
 */
public interface IPageService {

	List<PageLocation> findPageLocations(ByggmesterBobParams params);

}
