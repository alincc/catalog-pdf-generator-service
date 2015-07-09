package no.nb.microservices.pdfgenerator.service;

import no.nb.bookgenerator.PageLocation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.StreamingOutput;
import java.util.List;

@Service
@Profile({"prod", "dev"})
public class PdfBuilderService implements BuilderService {

	@Override
	public StreamingOutput build(List<PageLocation> pageLocations) {
		return new PdfStreamingOutput(pageLocations);
	}

}
