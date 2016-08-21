package com.viewnext.admin.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityTokensInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		
		HttpSession session = request.getSession();
		String csrf = null;
		if(request.getMethod()=="POST"){
			csrf = request.getParameter("_csrf");
		}else{
			csrf = getCsrfFromCookie(request.getCookies());
		}
		String sessionId = session.getId();
		
		if(sessionId !=null && sessionId.trim().length() != 0 && 
				csrf != null && csrf.trim().length() != 0){
			SecurityTokens secTokes = new SecurityTokens();
			secTokes.setCsrf(csrf);
			secTokes.setSession(sessionId);
			SessionTokens.set(secTokes);
		}
		
		return true;
	}

	private String getCsrfFromCookie(Cookie[] cookies){
		String csrf = null;
		for(Cookie cookie: cookies){
			if(cookie.getName().equals("XSRF-TOKEN")){
				csrf = cookie.getValue();
				break;
			}
		}
		return csrf;
	}
	
}
