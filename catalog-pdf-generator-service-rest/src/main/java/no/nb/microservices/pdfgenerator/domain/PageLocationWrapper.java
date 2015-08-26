package no.nb.microservices.pdfgenerator.domain;

import no.nb.bookgenerator.PageLocation;

/**
 * Created by andreasb on 26.08.15.
 */
public class PageLocationWrapper {
    private String urn;
    private String type;
    private PageLocation pageLocation;

    public PageLocationWrapper(String urn, String type, PageLocation pageLocation) {
        this.urn = urn;
        this.type = type;
        this.pageLocation = pageLocation;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PageLocation getPageLocation() {
        return pageLocation;
    }

    public void setPageLocation(PageLocation pageLocation) {
        this.pageLocation = pageLocation;
    }
}
