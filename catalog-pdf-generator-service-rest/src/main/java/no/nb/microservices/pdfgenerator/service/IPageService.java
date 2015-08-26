package no.nb.microservices.pdfgenerator.service;

import no.nb.microservices.pdfgenerator.domain.PageLocationWrapper;
import no.nb.microservices.pdfgenerator.model.GeneratorParams;

import java.util.List;

/**
 * 
 * @author ronnym
 *
 */
public interface IPageService {

	List<PageLocationWrapper> findPageLocations(GeneratorParams params);

}
