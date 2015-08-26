package no.nb.microservices.pdfgenerator.service;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.domain.PageLocationWrapper;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Profile({"prod", "dev"})
public class BuilderService implements IBuilderService {

	@Override
	public StreamingOutput buildPdf(List<PageLocationWrapper> pageLocations) {
        List<PageLocation> pageLocationList = pageLocations.stream().map(q -> q.getPageLocation()).collect(Collectors.toList());
		return new PdfStreamingOutput(pageLocationList);
	}

    @Override
    public ByteArrayOutputStream buildImages(List<PageLocationWrapper> pageLocations, String filetype) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        for (PageLocationWrapper pageLocation : pageLocations) {
            InputStream pageStream = pageLocation.getPageLocation().getImageLocation().openStream();

            ZipEntry ze = new ZipEntry(pageLocation.getUrn() + "." + filetype);
            zos.putNextEntry(ze);
            zos.write(IOUtils.toByteArray(pageStream));
            zos.closeEntry();
        }

        zos.close();
        OutputStream os = new ByteArrayOutputStream();;
        baos.writeTo(os);

        return baos;
    }

}
