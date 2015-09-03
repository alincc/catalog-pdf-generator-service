package no.nb.microservices.pdfgenerator.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorParams {
	private List<String> urns;
	private List<String> pages;
    private List<String> pageSelections;
    private List<Boolean> addText;
    private List<String> resolutionlevel;
    private String fileType;

	public GeneratorParams(List<String> urns, String[] pages, List<String> pageSelections, List<Boolean> addText, List<String> resolutionlevel, String filetype)
    {
		super();
		this.urns = urns;
		if (pages != null) {
            for (int i = 0; i < pages.length; i++) {
                pages[i] = pages[i].replaceAll(" ","");
            }
            this.pages = Arrays.asList(pages);
		}
        this.pageSelections = pageSelections;
        this.addText = addText;

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

	public List<Boolean> getAddText() {
		return addText;
	}

	public void setAddText(List<Boolean> addText) {
		this.addText = addText;
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
	      .append("text", addText)
	      .append("resolutionlevel", resolutionlevel)
	      .toString();
	}


    public List<String> getPageSelections() {
        return pageSelections;
    }

    public void setPageSelections(List<String> pageSelections) {
        this.pageSelections = pageSelections;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
