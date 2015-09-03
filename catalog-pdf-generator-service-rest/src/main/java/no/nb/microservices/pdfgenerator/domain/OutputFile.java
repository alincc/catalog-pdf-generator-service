package no.nb.microservices.pdfgenerator.domain;

import java.io.ByteArrayOutputStream;

/**
 * Created by andreasb on 02.09.15.
 */
public class OutputFile {
    private String filetype;
    private ByteArrayOutputStream byteArrayOutputStream;

    public OutputFile(String filetype, ByteArrayOutputStream byteArrayOutputStream) {
        this.filetype = filetype;
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return byteArrayOutputStream;
    }

    public void setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }
}
