package no.nb.microservices.pdfgenerator.service;

import no.nb.bookgenerator.PageLocation;

import javax.ws.rs.core.StreamingOutput;
import java.util.List;

public interface BuilderService {
	
	StreamingOutput build(List<PageLocation> pageLocations);
}
