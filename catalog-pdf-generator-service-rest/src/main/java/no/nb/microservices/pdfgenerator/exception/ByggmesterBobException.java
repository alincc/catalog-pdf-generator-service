package no.nb.microservices.pdfgenerator.exception;

/**
 * 
 * @author ronnym
 *
 */
public class ByggmesterBobException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ByggmesterBobException (Exception ex) { 
		super(ex);
	}

	public ByggmesterBobException (String message) { 
		super(message);
	}

	public ByggmesterBobException (String message, Exception ex) { 
		super(message, ex);
	}
	
}
