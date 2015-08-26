package no.nb.microservices.pdfgenerator.rest.controller;

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
            @RequestParam(value = "pageSelection", required = false, defaultValue = "id") String pageSelection,
			@RequestParam(value = "text", required = false, defaultValue = "true") boolean text,
			@RequestParam(value = "resolutionlevel", required = false) List<String> resolutionlevel,
			@RequestParam(value = "filename", required = false, defaultValue = "Nasjonalbiblioteket") String filename,
			@RequestParam(value = "filetype", required = false, defaultValue = "pdf") String filetype,
            HttpServletResponse response) throws IOException {

        // Make parameter object
		GeneratorParams params = new GeneratorParams(urns, pages, pageSelection, text, resolutionlevel, filetype);

        // Find URL for all images that will be used to build a pdf or zip
		List<PageLocationWrapper> pageLocations = pageService.findPageLocations(params);

        if ("pdf".equalsIgnoreCase(filetype)) { // PDF
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".pdf");
            response.setContentType("application/pdf");
            builderService.buildPdf(pageLocations).write(response.getOutputStream());
        }
        else { // Images
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".zip");
            response.setContentType("application/zip, application/octet-stream");
            builderService.buildImages(pageLocations,filetype).writeTo(response.getOutputStream());
        }
    }

}
