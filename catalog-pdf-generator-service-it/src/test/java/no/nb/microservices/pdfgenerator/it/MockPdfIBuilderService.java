package no.nb.microservices.pdfgenerator.it;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.domain.PageLocationWrapper;
import no.nb.microservices.pdfgenerator.service.IBuilderService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@Profile({"integration"})
public class MockPdfIBuilderService implements IBuilderService {

	final static String LINE_SEPERATOR = System.getProperty("line.separator");
	
	@Override
    public StreamingOutput buildPdf(List<PageLocationWrapper> pageLocations) {
		return new StreamingOutput() {

			@Override
			public void write(OutputStream os) throws IOException,
					WebApplicationException {
				
				StringBuilder sb = new StringBuilder();
				for(PageLocationWrapper pageLocation : pageLocations) {
					if (pageLocation.getPageLocation().getImageLocation() != null) {
						sb.append(pageLocation.getPageLocation().getImageLocation() + LINE_SEPERATOR);
					}
					if (pageLocation.getPageLocation().getTextLocation() != null) {
						sb.append(pageLocation.getPageLocation().getTextLocation() + LINE_SEPERATOR);
					}
				}
				os.write(sb.toString().getBytes());
			}
			
        };
	}

    @Override
    public ByteArrayOutputStream buildImages(List<PageLocationWrapper> pageLocations, String filetype) throws IOException {
        return null;
    }
}
