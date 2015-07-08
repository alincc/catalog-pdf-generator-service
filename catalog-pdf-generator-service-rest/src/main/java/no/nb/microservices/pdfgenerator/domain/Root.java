package no.nb.microservices.pdfgenerator.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author ronnym
 *
 */
public class Root {
	private Pages pages;

	public Pages getPages() {
		return pages;
	}

	public void setPages(Pages pages) {
		this.pages = pages;
	}

	public String toString() {
	    return new ToStringBuilder(this)
	      .append("pages", pages)
	      .toString(); 
	}}
