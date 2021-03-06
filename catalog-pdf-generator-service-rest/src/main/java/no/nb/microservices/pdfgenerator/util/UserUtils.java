package no.nb.microservices.pdfgenerator.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class UserUtils {

	private UserUtils () {
		super();
	}
	
	public static String getClientIp() {
		HttpServletRequest request = 
				  ((ServletRequestAttributes) RequestContextHolder.
				    currentRequestAttributes()).
				    getRequest();			
		String clientIp = request.getParameter("clientIp");
		if (clientIp == null || clientIp.isEmpty()) {
			clientIp = request.getHeader("X-Original-IP-Fra-Frontend");
		}

		if(clientIp == null || clientIp.isEmpty()) {
			clientIp = request.getRemoteAddr();
		}
		
		return clientIp;
	}

	public static String getSsoToken() {
		HttpServletRequest request = 
				  ((ServletRequestAttributes) RequestContextHolder.
				    currentRequestAttributes()).
				    getRequest();

		String ssoToken = request.getParameter("ssoToken");
		if (ssoToken == null || ssoToken.isEmpty()) {
			Cookie cookie = WebUtils.getCookie(request, "amsso");
			if (cookie != null) {
				ssoToken = cookie.getValue();
			}
		}
		return ssoToken;
		
	}
}
