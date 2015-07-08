package no.nb.sesam.bb.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<Object> handleNotFountException(RuntimeException ex, WebRequest request) {
		logger.warn(ex);
        return handleExceptionInternal(ex, ex.getMessage(), 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

	@ExceptionHandler(value = { SecurityException.class })
    protected ResponseEntity<Object> handleSecurityException(RuntimeException ex, WebRequest request) {
		logger.warn(ex);
        return handleExceptionInternal(ex, ex.getMessage(), 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

	
	@ExceptionHandler(value = { Throwable.class })
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
		logger.warn(ex);
        return handleExceptionInternal(ex, null, 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
