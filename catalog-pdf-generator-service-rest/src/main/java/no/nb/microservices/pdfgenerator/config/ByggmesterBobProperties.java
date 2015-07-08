package no.nb.microservices.pdfgenerator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("byggmester-bob")
public class ByggmesterBobProperties {
	private String tilemapUrlTemplate;
	private String pageUrlTemplate;
	private String altoUrlTemplate;

	public String getTilemapUrlTemplate() {
		return tilemapUrlTemplate;
	}

	public void setTilemapUrlTemplate(String tilemapUrlTemplate) {
		this.tilemapUrlTemplate = tilemapUrlTemplate;
	}

	public String getPageUrlTemplate() {
		return pageUrlTemplate;
	}

	public void setPageUrlTemplate(String pageUrlTemplate) {
		this.pageUrlTemplate = pageUrlTemplate;
	}

	public String getAltoUrlTemplate() {
		return altoUrlTemplate;
	}

	public void setAltoUrlTemplate(String altoUrlTemplate) {
		this.altoUrlTemplate = altoUrlTemplate;
	}

}
