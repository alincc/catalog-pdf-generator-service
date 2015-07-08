package no.nb.sesam.bb.aspects;

import java.net.URL;
import java.util.List;

import no.nb.bookgenerator.PageLocation;
import no.nb.sesam.bb.exception.ByggmesterBobException;
import no.nb.sesam.bb.util.UserUtils;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class SsoTokenSecurityAspect {
	private static final Logger logger = LoggerFactory.getLogger(SsoTokenSecurityAspect.class);
	
	@AfterReturning(
		    pointcut="no.nb.sesam.bb.aspects.SystemArchitecture.findPageLocations()",
		    returning="retVal")
	public Object findPageLocations(Object retVal) {
		List<PageLocation> pageLocations = (List<PageLocation>)retVal;
		String ssoToken = UserUtils.getSsoToken();
		String clientIp = UserUtils.getClientIp();
		
		if (!InetAddressValidator.getInstance().isValidInet4Address(clientIp) || "127.0.0.1".equals(clientIp)) {
			throw new SecurityException("localhost or IPv6 is not supported");
		}

		logger.debug("clientIp = " + clientIp);
		logger.debug("ssoToken = " + ssoToken);
		
		for (PageLocation pageLocation : pageLocations) {
			URL imageLocation = pageLocation.getImageLocation();
			try {
				pageLocation.setImageLocation(new URL(imageLocation.toString() + "&clientIp="+clientIp+"&ssoToken="+ssoToken));
			} catch (Exception ex) {
				throw new ByggmesterBobException(ex);
			}
		}
		
        return retVal;
    }
	
}
