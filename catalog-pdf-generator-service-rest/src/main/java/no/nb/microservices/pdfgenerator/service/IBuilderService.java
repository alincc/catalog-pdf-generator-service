package no.nb.microservices.pdfgenerator.service;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.domain.PageLocationWrapper;

import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface IBuilderService {
	
	StreamingOutput buildPdf(List<PageLocationWrapper> pageLocations);
    ByteArrayOutputStream buildImages(List<PageLocationWrapper> pageLocations, String filetype) throws IOException;
}
