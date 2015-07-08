package no.nb.sesam.bb.tilemap.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ronnym
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Page {
	private String urn;
	private String type;

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

	public String toString() {
	    return new ToStringBuilder(this)
	      .append("urn", urn)
	      .append("type", type)
	      .toString(); 
	}

}
