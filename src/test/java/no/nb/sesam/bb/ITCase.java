package no.nb.sesam.bb;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {IntegrationTestsWebConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles(profiles = "integration")
@Ignore
public class ITCase {
	
	final static String LINE_SEPERATOR = System.getProperty("line.separator");
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private MockMvc mockMvc;
	private MockRestServiceServer mockServer;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}
	
	@Test
	public void testAllPages() throws Exception {
		String urn = "URN:NBN:no-nb_digibok_2011051320024";
		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn)))
        	.andExpect(method(HttpMethod.GET))
        	.andRespond(withSuccess(FileUtils.readFileToString(jsonFile), MediaType.APPLICATION_JSON));		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		MvcResult result = mockMvc.perform(get("/pdf/generate")
				.param("urn", "URN:NBN:no-nb_digibok_2011051320024")
				.param("clientIp", "158.39.122.111")
				.accept("application/pdf")
		).andExpect(status().isOk())
		 .andExpect(content().contentType("application/pdf"))
		 .andReturn();
		
		assertEquals(50, result.getResponse().getContentAsString().split(LINE_SEPERATOR).length);
		
		mockServer.verify();
	}

	@Test
	public void testNoText() throws Exception {
		String urn = "URN:NBN:no-nb_digibok_2011051320024";
		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn)))
        	.andExpect(method(HttpMethod.GET))
        	.andRespond(withSuccess(FileUtils.readFileToString(jsonFile), MediaType.APPLICATION_JSON));		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		MvcResult result = mockMvc.perform(get("/pdf/generate")
				.param("urn", "URN:NBN:no-nb_digibok_2011051320024")
				.param("text", "false")
				.param("clientIp", "158.39.122.111")
				.accept("application/pdf")
		).andExpect(status().isOk())
		 .andExpect(content().contentType("application/pdf"))
		 .andReturn();
		
		assertEquals(25, result.getResponse().getContentAsString().split(LINE_SEPERATOR).length);
		
		mockServer.verify();
	}
	
	@Test
	public void testRange() throws Exception {
		String urn = "URN:NBN:no-nb_digibok_2011051320024";
		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn)))
        	.andExpect(method(HttpMethod.GET))
        	.andRespond(withSuccess(FileUtils.readFileToString(jsonFile), MediaType.APPLICATION_JSON));		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		MvcResult result = mockMvc.perform(get("/pdf/generate")
				.param("urn", "URN:NBN:no-nb_digibok_2011051320024")
				.param("pages", "1-4")
				.param("clientIp", "158.39.122.111")
				.accept("application/pdf")
		).andExpect(status().isOk())
		 .andExpect(content().contentType("application/pdf"))
		 .andReturn();
		
		assertEquals(8, result.getResponse().getContentAsString().split(LINE_SEPERATOR).length);
		
		mockServer.verify();
	}

	@Test
	public void testTwoUrns() throws Exception {
		String urn1 = "URN:NBN:no-nb_digibok_2011051320024";
		String urn2 = "URN:NBN:no-nb_digibok_2011051320025";
		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn1)))
        	.andExpect(method(HttpMethod.GET))
        	.andRespond(withSuccess(FileUtils.readFileToString(jsonFile), MediaType.APPLICATION_JSON));		
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn2)))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withSuccess(FileUtils.readFileToString(jsonFile), MediaType.APPLICATION_JSON));		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		MvcResult result = mockMvc.perform(get("/pdf/generate")
				.param("urn", urn1)
				.param("urn", urn2)
				.param("pages", "1-2")
				.param("pages", "3-4")
				.param("clientIp", "158.39.122.111")
				.accept("application/pdf")
		).andExpect(status().isOk())
		 .andExpect(content().contentType("application/pdf"))
		 .andReturn();
		
		assertEquals(8, result.getResponse().getContentAsString().split(LINE_SEPERATOR).length);
		
		mockServer.verify();
	}

	@Test
	public void testCovers() throws Exception {
		String urn = "URN:NBN:no-nb_digibok_2011051320024";
		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn)))
        	.andExpect(method(HttpMethod.GET))
        	.andRespond(withSuccess(FileUtils.readFileToString(jsonFile), MediaType.APPLICATION_JSON));		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		MvcResult result = mockMvc.perform(get("/pdf/generate")
				.param("urn", urn)
				.param("type", "cover")
				.param("clientIp", "158.39.122.111")
				.accept("application/pdf")
		).andExpect(status().isOk())
		 .andExpect(content().contentType("application/pdf"))
		 .andReturn();
		
		assertEquals(10, result.getResponse().getContentAsString().split(LINE_SEPERATOR).length);
		mockServer.verify();
	}

	@Test
	public void testPages() throws Exception {
		String urn = "URN:NBN:no-nb_digibok_2011051320024";
		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn)))
        	.andExpect(method(HttpMethod.GET))
        	.andRespond(withSuccess(FileUtils.readFileToString(jsonFile), MediaType.APPLICATION_JSON));		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		MvcResult result = mockMvc.perform(get("/pdf/generate")
				.param("urn", urn)
				.param("type", "pages")
				.param("clientIp", "158.39.122.111")
				.accept("application/pdf")
		).andExpect(status().isOk())
		 .andExpect(content().contentType("application/pdf"))
		 .andReturn();

		assertEquals(40, result.getResponse().getContentAsString().split(LINE_SEPERATOR).length);

		mockServer.verify();
	}

	@Test
	public void testNotFound() throws Exception {
		String urn = "URN:NBN:no-nb_digibok_2011051320024";
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn)))
        	.andExpect(method(HttpMethod.GET))
        	.andRespond(withStatus(HttpStatus.NOT_FOUND));		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		mockMvc.perform(get("/pdf/generate")
				.param("urn", urn)
				.param("clientIp", "158.39.122.111")
				.accept("application/pdf")
		).andExpect(status().isNotFound());
		
		mockServer.verify();
	}

	@Test
	public void testWrongParameters() throws Exception {
		String urn1 = "URN:NBN:no-nb_digibok_2011051320024";
		String urn2 = "URN:NBN:no-nb_digibok_2011051320025";
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		mockMvc.perform(get("/pdf/generate")
				.param("urn", urn1)
				.param("urn", urn2)
				.param("pages", "1-5")
				.accept("application/pdf")
		).andExpect(status().isInternalServerError());
		
	}

	@Test
	public void testIllegalIp() throws Exception {
		String urn = "URN:NBN:no-nb_digibok_2011051320024";
		File jsonFile = new File(Paths.get(getClass().getResource("/tilemap.json").toURI()).toString());
		mockServer.expect(requestTo("http://www.nb.no/services/tilesv2/tilemap?URN={urn}&format=json".replace("{urn}", urn)))
        	.andExpect(method(HttpMethod.GET))
        	.andRespond(withSuccess(FileUtils.readFileToString(jsonFile), MediaType.APPLICATION_JSON));		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("GET");
		mockMvc.perform(get("/pdf/generate")
				.param("urn", urn)
				.param("clientIp", "localhost")
				.accept("application/pdf")
		).andExpect(status().isBadRequest());

		mockServer.verify();
		
	}
	
}
