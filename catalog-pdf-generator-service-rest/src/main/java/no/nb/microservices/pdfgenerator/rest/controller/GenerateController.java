package no.nb.microservices.pdfgenerator.rest.controller;

import no.nb.microservices.pdfgenerator.domain.OutputFile;
import no.nb.microservices.pdfgenerator.domain.PageLocationWrapper;
import no.nb.microservices.pdfgenerator.model.GeneratorParams;
import no.nb.microservices.pdfgenerator.service.IBuilderService;
import no.nb.microservices.pdfgenerator.service.IPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

/**
 * 
 * @author ronnym
 *
 */
@RestController
public class GenerateController {
	
	private static final Logger logger = LoggerFactory.getLogger(GenerateController.class);
	
	private IPageService pageService;
	private IBuilderService builderService;

	@Autowired
	public GenerateController(IPageService pageService, IBuilderService builderService) {
		super();
		this.pageService = pageService;
		this.builderService = builderService;
	}

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public void generate(
			@RequestParam("urn") List<String> urns,
			@RequestParam(value = "pages", required=false) String[] pages,
            @RequestParam(value = "pageSelections", required = false) List<String> pageSelections,
			@RequestParam(value = "addText", required = false) List<Boolean> addText,
			@RequestParam(value = "resolutionlevel", required = false) List<String> resolutionlevels,
			@RequestParam(value = "filename", required = false, defaultValue = "Nasjonalbiblioteket") String filename,
			@RequestParam(value = "filetype", required = false, defaultValue = "pdf") String filetype,
            HttpServletResponse response) throws IOException {

        // Make parameter object
		GeneratorParams params = new GeneratorParams(urns, pages, pageSelections, addText, resolutionlevels, filetype);

        // Find URL for all images that will be used to build a pdf or zip
		List<PageLocationWrapper> pageLocations = pageService.findPageLocations(params);

        if ("pdf".equalsIgnoreCase(filetype)) { // PDF
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".pdf");
            response.setContentType("application/pdf");
            builderService.buildPdf(pageLocations).write(response.getOutputStream());
        }
        else { // Images
			OutputFile outputFile = builderService.buildImages(pageLocations, filetype);
			String outputFilename = filename + "." + outputFile.getFiletype();
			response.setHeader("Content-Disposition", "attachment; filename=" + outputFilename);
			String contentType = Files.probeContentType(new File(outputFilename).toPath());
			response.setContentType(contentType);
			outputFile.getByteArrayOutputStream().writeTo(response.getOutputStream());
		}
    }

}
