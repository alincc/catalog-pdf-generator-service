package no.nb.microservices.pdfgenerator.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.StreamingOutput;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.model.ByggmesterBobParams;
import no.nb.microservices.pdfgenerator.service.BuilderService;
import no.nb.microservices.pdfgenerator.exception.ByggmesterBobException;
import no.nb.microservices.pdfgenerator.service.IPageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author ronnym
 *
 */
@RestController
@RequestMapping("/pdf")
public class ByggmesterBobController {
	
	private static final Logger logger = LoggerFactory.getLogger(ByggmesterBobController.class);
	
	private IPageService pageService;
	private BuilderService builderService;

	@Autowired
	public ByggmesterBobController(IPageService pageService, BuilderService builderService) {
		super();
		this.pageService = pageService;
		this.builderService = builderService;
	}

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public void generate(
			@RequestParam("urn") List<String> urns,
			@RequestParam(value = "pages", required=false) String[] pages,
			@RequestParam(value = "type", required = false, defaultValue = "book") String type,
			@RequestParam(value = "text", required = false, defaultValue = "true") boolean text,
			@RequestParam(value = "resolutionlevel", required = false) List<String> resolutionlevel,
			@RequestParam(value = "filename", required = false, defaultValue = "book") String filename,
			@RequestParam(value = "fileType", required = false, defaultValue = "jpg") String filetype,
            HttpServletResponse response) {

		if (pages != null && (urns.size() != pages.length)) {
            throw new ByggmesterBobException("Wrong number of parameters");
        }
		
		ByggmesterBobParams params = new ByggmesterBobParams(urns, pages, type, text, resolutionlevel);
		logger.debug(params.toString());

		List<PageLocation> pageLocations = pageService.findPageLocations(params);
		StreamingOutput streamingOutput = builderService.build(pageLocations); 
				
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".pdf");

		try {
			streamingOutput.write(response.getOutputStream());
		} catch(Exception ex) {
			throw new ByggmesterBobException(ex);
		}
	}

}
