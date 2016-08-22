package com.viewnext.admin.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityTokensInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		
		if(sessionId !=null && sessionId.trim().length() != 0){
			SecurityTokens secTokes = new SecurityTokens();
			secTokes.setSession(sessionId);
			SessionTokens.set(secTokes);
		}
		
		return true;
	}
}
