package no.nb.microservices.pdfgenerator.service;

import java.util.List;

import javax.ws.rs.core.StreamingOutput;

import no.nb.bookgenerator.PageLocation;

public interface BuilderService {
	
	StreamingOutput build(List<PageLocation> pageLocations);
}
