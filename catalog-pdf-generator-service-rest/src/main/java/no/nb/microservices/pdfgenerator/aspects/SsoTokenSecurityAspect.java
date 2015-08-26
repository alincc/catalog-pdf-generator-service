package no.nb.microservices.pdfgenerator.aspects;

import no.nb.bookgenerator.PageLocation;
import no.nb.microservices.pdfgenerator.domain.PageLocationWrapper;
import no.nb.microservices.pdfgenerator.exception.ByggmesterBobException;
import no.nb.microservices.pdfgenerator.util.UserUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;


@Aspect
@Component
public class SsoTokenSecurityAspect {
	private static final Logger logger = LoggerFactory.getLogger(SsoTokenSecurityAspect.class);
	
	@AfterReturning(
		    pointcut="no.nb.microservices.pdfgenerator.aspects.SystemArchitecture.findPageLocations()",
		    returning="retVal")
	public Object findPageLocations(Object retVal) {
		List<PageLocationWrapper> pageLocations = (List<PageLocationWrapper>)retVal;
		String ssoToken = UserUtils.getSsoToken();
		String clientIp = UserUtils.getClientIp();

		if (!InetAddressValidator.getInstance().isValidInet4Address(clientIp) || "127.0.0.1".equals(clientIp)) {
			throw new SecurityException("localhost or IPv6 is not supported");
		}

		logger.debug("clientIp = " + clientIp);
		logger.debug("ssoToken = " + ssoToken);
		
		for (PageLocationWrapper pageLocation : pageLocations) {
			URL imageLocation = pageLocation.getPageLocation().getImageLocation();
			try {
				pageLocation.getPageLocation().setImageLocation(new URL(imageLocation.toString() + "&clientIp="+clientIp+"&ssoToken="+ssoToken));
			} catch (Exception ex) {
				throw new ByggmesterBobException(ex);
			}
		}
		
        return retVal;
    }
	
}
