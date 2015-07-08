package no.nb.sesam.bb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ByggmesterBobParams {
	private List<String> urns;
	private List<String> pages;
	private String type;
	private boolean text;
	private List<String> resolutionlevel;
	
	public ByggmesterBobParams(List<String> urns,
                               String[] pages,
                               String type,
                               boolean text, List<String> resolutionlevel)
    {
		super();
		this.urns = urns;
		if (pages != null) {
            for (int i = 0; i < pages.length; i++) {
                pages[i] = pages[i].replaceAll(" ","");
            }
            this.pages = Arrays.asList(pages);
		}
		this.type = type;
		this.text = text;

        // Set resolution level to 4 if missing
        if (resolutionlevel == null) {
            resolutionlevel = new ArrayList<>();
        }
        if (resolutionlevel.size() < urns.size()) {
            while (resolutionlevel.size() < urns.size()) {
                resolutionlevel.add("4");
            }
        }

		this.resolutionlevel = resolutionlevel;
	}

	public List<String> getUrns() {
		return urns;
	}
	
	public void setUrns(List<String> urns) {
		this.urns = urns;
	}
	
	public List<String> getPages() {
		return pages;
	}
	
	public void setPages(List<String> pages) {
		this.pages = pages;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isText() {
		return text;
	}
	
	public void setText(boolean text) {
		this.text = text;
	}
	
	public List<String> getResolutionlevel() {
		return resolutionlevel;
	}
	
	public void setResolutionlevel(List<String> resolutionlevel) {
		this.resolutionlevel = resolutionlevel;
	}

	public String toString() {
	    return new ToStringBuilder(this)
	      .append("urns", urns)
	      .append("pages", pages)
	      .append("type", type)
	      .append("text", text)
	      .append("resolutionlevel", resolutionlevel)
	      .toString(); 
	}

}
