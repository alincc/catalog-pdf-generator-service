package no.nb.microservices.pdfgenerator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ronnym
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Pages {
	private List<Page> pages;

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
	
	public List<Page> findPageByType(String regex) {
		List<Page> result = new ArrayList<>();
		for(Page page : pages) {
			if (page.getType() != null && page.getType().matches(regex)) {
				result.add(page);
			}
		}
		return result;
	}
	
	public String toString() {
	    return new ToStringBuilder(this)
	      .append("pages", pages)
	      .toString(); 
	}
	
}
