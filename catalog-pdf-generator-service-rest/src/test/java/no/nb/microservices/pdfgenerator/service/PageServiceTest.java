package no.nb.microservices.pdfgenerator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.config.ByggmesterBobProperties;
import no.nb.microservices.pdfgenerator.domain.Root;
import no.nb.microservices.pdfgenerator.model.ByggmesterBobParams;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author ronnym
 *
 */
public class PageServiceTest {
	private PageService pageService;
	private ITilemapService tilemapService;
	
	@Before
	public void setup() {
		tilemapService = createNiceMock(ITilemapService.class);
		ByggmesterBobProperties settings = new ByggmesterBobProperties();
		String pageUrlTemplate="http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id={urn}&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level={svc.level}&svc.rotate=0";
		String altoUrlTemplate="http://appserv3.nb.no:8088/postman-pat/file/{urn}/alto/{urn}.xml";
		settings.setAltoUrlTemplate(altoUrlTemplate);
		settings.setPageUrlTemplate(pageUrlTemplate);
		
		pageService = new PageService(tilemapService, settings);
	}
	
	@Test
	public void findPageLocations() throws Exception {
		List<String> urns = Arrays.asList("URN:NBN:no-nb_digibok_2013021509063");
		String[] pages = new String[]{"1,3,10-12"};
		List<PageLocation> expecteds = Arrays.asList(
				new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_C1&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_C1/alto/digibok_2013021509063_C1.xml")),
				new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_0001&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_0001/alto/digibok_2013021509063_0001.xml")),
				new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_0008&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_0008/alto/digibok_2013021509063_0008.xml")),
				new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_0009&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_0009/alto/digibok_2013021509063_0009.xml")),
				new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_0010&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_0010/alto/digibok_2013021509063_0010.xml"))
			);

		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		ObjectMapper mapper = new ObjectMapper();
		Root tilemap = mapper.readValue(jsonFile, Root.class);
		   
		expect(tilemapService.findByUrn(urns.get(0))).andReturn(tilemap);
		replay(tilemapService);

        List<String> resolutionlevel = new ArrayList<>();
        resolutionlevel.add("1");

		ByggmesterBobParams params = new ByggmesterBobParams(urns, pages, "book", true, resolutionlevel);
		List<PageLocation> actuals = pageService.findPageLocations(params);
		
		verify(tilemapService);

		assertNotNull(actuals);
		assertEquals(5, actuals.size());
		assertEquals(expecteds, actuals);
		
	}

    @Test
    public void findPageLocationsMultipleResolutionlevels() throws Exception {
        List<String> urns = Arrays.asList("URN:NBN:no-nb_digibok_2013021509063", "URN:NBN:no-nb_digibok_2013021509063");
        List<String> resolutionlevel = Arrays.asList("1", "4");
        String[] pages = new String[]{"1,3,10-12", "1"};
        List<PageLocation> expecteds = Arrays.asList(
                new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_C1&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_C1/alto/digibok_2013021509063_C1.xml")),
                new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_0001&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_0001/alto/digibok_2013021509063_0001.xml")),
                new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_0008&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_0008/alto/digibok_2013021509063_0008.xml")),
                new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_0009&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_0009/alto/digibok_2013021509063_0009.xml")),
                new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_0010&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=1&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_0010/alto/digibok_2013021509063_0010.xml")),
                new PageLocation(new URL("http://www.nb.no/services/image/resolver?url_ver=Z39.88-2004&rft_id=URN:NBN:no-nb_digibok_2013021509063_C1&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.format=image/jpeg&svc.level=4&svc.rotate=0"), new URL("http://appserv3.nb.no:8088/postman-pat/file/digibok_2013021509063_C1/alto/digibok_2013021509063_C1.xml"))
        );

        File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
        ObjectMapper mapper = new ObjectMapper();
        Root tilemap = mapper.readValue(jsonFile, Root.class);

        expect(tilemapService.findByUrn(urns.get(0))).andReturn(tilemap);
        expect(tilemapService.findByUrn(urns.get(1))).andReturn(tilemap);
        replay(tilemapService);

        ByggmesterBobParams params = new ByggmesterBobParams(urns, pages, "book", true, resolutionlevel);
        List<PageLocation> actuals = pageService.findPageLocations(params);

        verify(tilemapService);

        assertNotNull(actuals);
        assertEquals(6, actuals.size());

        for (int i = 0; i < actuals.size(); i++ ) {
            assertEquals(true, actuals.get(i).equals(expecteds.get(i)));
        }
    }
}
