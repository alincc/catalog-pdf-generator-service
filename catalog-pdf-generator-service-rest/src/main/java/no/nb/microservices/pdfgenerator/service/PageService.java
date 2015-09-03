package no.nb.microservices.pdfgenerator.service;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.config.ByggmesterBobProperties;
import no.nb.microservices.pdfgenerator.domain.Page;
import no.nb.microservices.pdfgenerator.domain.PageLocationWrapper;
import no.nb.microservices.pdfgenerator.domain.Root;
import no.nb.microservices.pdfgenerator.exception.ByggmesterBobException;
import no.nb.microservices.pdfgenerator.model.GeneratorParams;
import no.nb.microservices.pdfgenerator.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

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
    public List<PageLocationWrapper> findPageLocations(GeneratorParams params) {
        List<PageLocationWrapper> pageLocations = new ArrayList<>();
        List<String> urns = params.getUrns();
        List<String> pages = params.getPages();
        List<String> pageSelections = params.getPageSelections();
        List<String> resolutionlevels = params.getResolutionlevel();
        List<Boolean> addTexts = params.getAddText();

        for (int i = 0; i < urns.size(); i++) {
            String page = (pages == null || pages.isEmpty()) ? null : pages.get(i);
            String pageSelection = (pageSelections == null || pageSelections.isEmpty()) ? "id" : pageSelections.get(i);
            Boolean addText = (addTexts == null || addTexts.isEmpty()) ? false : addTexts.get(i);
            String urn = urns.get(i);
            Root tilemap = tilemapService.findByUrn(urn);
            List<Integer> pageList = PageUtils.toPageList(page);

            if (!pageList.isEmpty()) {
                if ("label".equalsIgnoreCase(pageSelection)) {
                    for (Page pageElement : tilemap.getPages().getPages()) {
                        if (pageList.contains(pageElement.getPageLabel())) {
                            PageLocationWrapper pageLocationWrapper = new PageLocationWrapper(pageElement.getUrn(), pageElement.getType(), createPageLocation(pageElement, resolutionlevels.get(i), params, addText));
                            pageLocations.add(pageLocationWrapper);
                        }
                    }
                } else if ("id".equalsIgnoreCase(pageSelection)) {
                    for (int requestPage : pageList) {
                        Page pageElement = tilemap.getPages().getPages().get(requestPage-1);
                        PageLocationWrapper pageLocationWrapper = new PageLocationWrapper(pageElement.getUrn(), pageElement.getType(), createPageLocation(pageElement, resolutionlevels.get(i), params, addText));
                        pageLocations.add(pageLocationWrapper);
                    }
                }
            } else {
                // All pages
                for (Page pageElement : tilemap.getPages().getPages()) {
                    PageLocationWrapper pageLocationWrapper = new PageLocationWrapper(pageElement.getUrn(), pageElement.getType(), createPageLocation(pageElement, resolutionlevels.get(i), params, addText));
                    pageLocations.add(pageLocationWrapper);
                }
            }
        }

        return pageLocations;
    }

    private PageLocation createPageLocation(Page page, String resolutionlevel, GeneratorParams params, Boolean addText) {
        String format = "image/jpeg";
        switch (params.getFileType()) {
            case "jpg":
                format = "image/jpeg";
                break;
            case "jp2":
                format = "image/jp2";
                break;
            case "tif":
                format = "image/tiff";
                break;
        }
        String pageUrl = settings.getPageUrlTemplate().replace("{urn}", page.getUrn()).replace("{svc.level}", resolutionlevel).replace("{format}", format);
        String altoUrl = settings.getAltoUrlTemplate().replace("{urn}", page.getUrn().replace("URN:NBN:no-nb_", ""));
        logger.debug("pageUrl=" + pageUrl);
        logger.debug("altoUrl=" + altoUrl);
        try {
            PageLocation pageLocation = new PageLocation(new URL(pageUrl));
            if (addText) {
                pageLocation.setTextLocation(new URL(altoUrl));
            }
            return pageLocation;
        } catch (Exception ex) {
            throw new ByggmesterBobException(ex);
        }
    }

    private List<Integer> pagesToList(String s, Integer max) {
        Pattern mulptrn = Pattern.compile("^(\\d+\\s*(,|-)\\s*)*\\d+$");
        List<Integer> ret = new ArrayList<Integer>();

            if (!mulptrn.matcher(s.trim()).matches()) {
                return null;
            }
            String[] ints = s.replaceAll("\\s", "").split(",");
            for (String i : ints) {
                if (i.contains("-")) {
                    String[] span = i.split("-");
                    if (Integer.parseInt(span[0]) > Integer
                            .parseInt(span[span.length - 1])) {
                        String tmp = span[0];
                        span[0] = span[span.length - 1];
                        span[span.length - 1] = tmp;
                    }

                    if (Integer.parseInt(span[0]) <= 0) {
                        span[0] = "1";
                    }

                    for (int j = Integer.parseInt(span[0]); j <= Integer
                            .parseInt(span[span.length - 1]); j++) {
                        ret.add(j);
                    }
                } else if (Integer.parseInt(i) > 0) {
                    ret.add(Integer.parseInt(i));
                }
            }

            HashSet h = new HashSet(ret);
            ret.clear();
            ret.addAll(h);
            //return ret.toString().replaceAll("[^,\\d]", "");
            return ret;
    }
}
