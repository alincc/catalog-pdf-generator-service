package no.nb.microservices.pdfgenerator.it;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import no.nb.microservices.pdfgenerator.service.BuilderService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import no.nb.bookgenerator.PageLocation;

@Service
@Profile({"integration"})
public class MockPdfBuilderService implements BuilderService {

	final static String LINE_SEPERATOR = System.getProperty("line.separator");
	
	@Override
	public StreamingOutput build(final List<PageLocation> pageLocations) {
		return new StreamingOutput() {

			@Override
			public void write(OutputStream os) throws IOException,
					WebApplicationException {
				
				StringBuilder sb = new StringBuilder();
				for(PageLocation pageLocation : pageLocations) {
					if (pageLocation.getImageLocation() != null) {
						sb.append(pageLocation.getImageLocation() + LINE_SEPERATOR);
					}
					if (pageLocation.getTextLocation() != null) {
						sb.append(pageLocation.getTextLocation() + LINE_SEPERATOR);
					}
				}
				os.write(sb.toString().getBytes());
			}
			
        };
	}


}
