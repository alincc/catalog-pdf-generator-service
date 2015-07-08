package no.nb.sesam.bb.tilemap;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Paths;

import no.nb.sesam.bb.tilemap.domain.Root;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

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
