package no.nb.microservices.pdfgenerator.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneratorParams {
	private List<String> urns;
	private List<String> pages;
    private String pageSelection;
    private boolean text;
    private List<String> resolutionlevel;
    private String fileType;

	public GeneratorParams(List<String> urns, String[] pages, String pageSelection, boolean text, List<String> resolutionlevel, String filetype)
    {
		super();
		this.urns = urns;
		if (pages != null) {
            for (int i = 0; i < pages.length; i++) {
                pages[i] = pages[i].replaceAll(" ","");
            }
            this.pages = Arrays.asList(pages);
		}
        this.pageSelection = pageSelection;
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
        this.fileType = filetype;
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
	      .append("text", text)
	      .append("resolutionlevel", resolutionlevel)
	      .toString();
	}

    public String getPageSelection() {
        return pageSelection;
    }

    public void setPageSelection(String pageSelection) {
        this.pageSelection = pageSelection;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
