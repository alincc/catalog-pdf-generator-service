package no.nb.microservices.pdfgenerator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nb.microservices.pdfgenerator.domain.Root;
import no.nb.microservices.pdfgenerator.repository.ITilemapRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author ronnym
 *
 */
public class TilmapServiceTest {
	private ITilemapService tilemapService;
	private ITilemapRepository tilemapRepository;
	
	@Before
	public void setup() {
		tilemapRepository = createNiceMock(ITilemapRepository.class);
		tilemapService = new TilemapService(tilemapRepository);
	}
	
	@Test
	public void findPageUrnsTest() throws Exception {
		String urn = "URN:NBN:no-nb_digibok_2012071608087";
		
	
		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		ObjectMapper mapper = new ObjectMapper();
		Root tilemap = mapper.readValue(jsonFile, Root.class);
		   
		expect(tilemapRepository.findByUrn(urn)).andReturn(tilemap);
		replay(tilemapRepository);
		
		Root actuals = tilemapService.findByUrn(urn);
		verify(tilemapRepository);
	
		assertNotNull(actuals);
		assertEquals(tilemap, actuals);
		
	}


}
