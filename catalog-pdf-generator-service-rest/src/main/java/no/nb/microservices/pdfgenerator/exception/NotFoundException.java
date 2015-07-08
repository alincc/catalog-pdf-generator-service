package no.nb.microservices.pdfgenerator.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException (Exception ex) { 
		super(ex);
	}

	public NotFoundException (String message) { 
		super(message);
	}

	public NotFoundException (String message, Exception ex) { 
		super(message, ex);
	}

}
