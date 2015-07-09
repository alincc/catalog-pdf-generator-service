package no.nb.microservices.pdfgenerator.service;

import no.nb.bookgenerator.BookGenerator;
import no.nb.bookgenerator.PageLocation;
import no.nb.bookgenerator.pdf.PdfBookGenerator;

import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author ronnym
 */
public class PdfStreamingOutput implements StreamingOutput {

    private List<PageLocation> pagelocations;

    public PdfStreamingOutput(List<PageLocation> pagelocations) {
        this.pagelocations = pagelocations;
    }

    @Override
    public void write(OutputStream out) throws IOException {
        BookGenerator generator = new PdfBookGenerator();
        generator.generate(pagelocations, out);
    }
}
