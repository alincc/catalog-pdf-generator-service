package no.nb.sesam.bb.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SystemArchitecture {

	
	@Pointcut("execution(* no.nb.sesam.bb.page.PageService.findPageLocations(..))")
	public void findPageLocations() {}


}
