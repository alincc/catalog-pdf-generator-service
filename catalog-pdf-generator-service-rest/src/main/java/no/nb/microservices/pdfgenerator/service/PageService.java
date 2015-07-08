package no.nb.microservices.pdfgenerator.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.model.ByggmesterBobParams;
import no.nb.microservices.pdfgenerator.config.ByggmesterBobProperties;
import no.nb.microservices.pdfgenerator.exception.ByggmesterBobException;
import no.nb.microservices.pdfgenerator.domain.Page;
import no.nb.microservices.pdfgenerator.domain.Root;
import no.nb.microservices.pdfgenerator.util.PageUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ronnym
 *
 */
@Service
public class PageService implements IPageService {
	private static final Logger logger = LoggerFactory.getLogger(PageService.class);
	
	private final ITilemapService tilemapService;
	private final ByggmesterBobProperties settings;
	
	@Autowired
	public PageService(
			ITilemapService tilemapService,
			ByggmesterBobProperties settings) {
		super();
		this.tilemapService = tilemapService;
		this.settings = settings;
	}

	@Override
	public List<PageLocation> findPageLocations(ByggmesterBobParams params) {
		List<PageLocation> pageLocations = new ArrayList<>();
		List<String> urns = params.getUrns();
		List<String> pages = params.getPages();
		List<String> resolutionlevels = params.getResolutionlevel();

		for (int i = 0; i < urns.size(); i++) {
			String page = (pages == null) ? null : pages.get(i);
			String urn = urns.get(i);
			Root tilemap = tilemapService.findByUrn(urn);
			List<Integer> pageList = PageUtils.toPageList(page);

			if (params.getType().equalsIgnoreCase("book")) {
				if (!pageList.isEmpty()) {
					// Selected pages
					for (int pageIndex : pageList) {
						Page pageElement = tilemap.getPages().getPages().get(pageIndex-1);
						if (pageElement != null) {
							pageLocations.add(createPageLocation(pageElement, resolutionlevels.get(i), params));
						}
					}
				} else {
					// All pages
					for(Page pageElement : tilemap.getPages().getPages()) {
						pageLocations.add(createPageLocation(pageElement, resolutionlevels.get(i), params));
					}
				}
			} else if (params.getType().equalsIgnoreCase("cover")) {
				List<Page> covers = tilemap.getPages().findPageByType("COVER.*");
				if (covers.size() > 0) {
					for (Page cover : covers) {
						pageLocations.add(createPageLocation(cover, resolutionlevels.get(i), params));
					}
				}
			} else if (params.getType().equalsIgnoreCase("pages")) {
				List<Page> covers = tilemap.getPages().findPageByType(".*PAGE.*");
				if (covers.size() > 0) {
					for (Page cover : covers) {
						pageLocations.add(createPageLocation(cover, resolutionlevels.get(i), params));
					}
				}
			}
			
		}

		return pageLocations;
	}

	private PageLocation createPageLocation(Page page, String resolutionlevel, ByggmesterBobParams params) {
		String pageUrl = settings.getPageUrlTemplate().replace("{urn}", page.getUrn()).replace("{svc.level}", resolutionlevel);
		String altoUrl = settings.getAltoUrlTemplate().replace("{urn}", page.getUrn().replace("URN:NBN:no-nb_", ""));
		logger.debug("pageUrl=" + pageUrl);
		logger.debug("altoUrl=" + altoUrl);
		try {
			PageLocation pageLocation = new PageLocation(new URL(pageUrl));
			if (params.isText()) {
				pageLocation.setTextLocation(new URL(altoUrl));
			}
			return pageLocation;
		} catch(Exception ex) {
			throw new ByggmesterBobException(ex);
		}
	}
}
