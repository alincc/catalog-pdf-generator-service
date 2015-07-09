package no.nb.microservices.pdfgenerator.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PageUtilsTest {
	
	@Test
	public void arrayTest() {
		String pages = "1,5,9";
		List<Integer> expecteds = Arrays.asList(
				new Integer(1),
				new Integer(5),
				new Integer(9));

		List<Integer> actuals = PageUtils.toPageList(pages);

		assertNotNull(actuals);
		assertTrue(actuals.size() == 3);
		assertEquals(expecteds, actuals);
	}
		
	@Test
	public void rangeTest() {
		String pages = "3-5";		
		List<Integer> expecteds = Arrays.asList(
				new Integer(3), 
				new Integer(4),
				new Integer(5));

		List<Integer> actuals = PageUtils.toPageList(pages);
		
		assertNotNull(actuals);
		assertTrue(actuals.size() == 3);
		assertEquals(expecteds, actuals);
	}

	@Test
	public void combinedTest() {
		String pages = "1-3,5,9,11-13,";
		List<Integer> expecteds = Arrays.asList(
				new Integer(1), 
				new Integer(2), 
				new Integer(3), 
				new Integer(5), 
				new Integer(9),
				new Integer(11),
				new Integer(12),
				new Integer(13));

		List<Integer> actuals = PageUtils.toPageList(pages);
		
		assertNotNull(actuals);
		assertTrue(actuals.size() == 8);
		assertEquals(expecteds, actuals);
		
	}

	@Test
	public void noPagesTest() {
		String pages = null;
		List<Integer> actuals = PageUtils.toPageList(pages);
                
		assertNotNull(actuals);
		assertTrue(actuals.size() == 0);
                
                pages = "";
                actuals = PageUtils.toPageList(pages);
                assertNotNull(actuals);
                assertTrue(actuals.size() == 0);
		
	}
	
}
