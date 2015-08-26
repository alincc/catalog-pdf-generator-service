package no.nb.microservices.pdfgenerator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author ronnym
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Page {
	private String urn;
	private String type;
    private String pageId;
    private String pageLabel;

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	@JsonProperty(value="pg_type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    @JsonProperty(value="pg_id")
    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    @JsonProperty(value="pg_label")
    public String getPageLabel() {
        return pageLabel;
    }

    public void setPageLabel(String pageLabel) {
        this.pageLabel = pageLabel;
    }

    public String toString() {
	    return new ToStringBuilder(this)
	      .append("urn", urn)
	      .append("type", type)
	      .toString(); 
	}

}
