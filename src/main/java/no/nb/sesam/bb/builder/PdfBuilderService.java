package no.nb.sesam.bb.builder;

import java.util.List;

import javax.ws.rs.core.StreamingOutput;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import no.nb.bookgenerator.PageLocation;

@Service
@Profile({"prod", "dev"})
public class PdfBuilderService implements BuilderService {

	@Override
	public StreamingOutput build(List<PageLocation> pageLocations) {
		return new PdfStreamingOutput(pageLocations);
	}

}
