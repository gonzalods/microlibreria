package com.viewnext.admin.security;

public class SessionTokens {

	private static ThreadLocal<SecurityTokens> sessionTokens = new ThreadLocal<>();
	
	public static void set(SecurityTokens securityTokens){
		sessionTokens.set(securityTokens);
	}
	
	public static SecurityTokens get(){
		return sessionTokens.get();
	}
	
	public static void unset(){
		sessionTokens.remove();
	}
}
