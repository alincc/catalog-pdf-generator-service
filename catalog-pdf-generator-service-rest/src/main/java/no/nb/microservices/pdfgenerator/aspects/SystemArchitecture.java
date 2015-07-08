package no.nb.microservices.pdfgenerator.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SystemArchitecture {

	
	@Pointcut("execution(* no.nb.microservices.pdfgenerator.service.PageService.findPageLocations(..))")
	public void findPageLocations() {}


}
