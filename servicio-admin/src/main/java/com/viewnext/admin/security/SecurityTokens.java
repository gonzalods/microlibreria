package com.viewnext.admin.security;

public class SecurityTokens {

	private String csrf;
	private String session;
	public String getCsrf() {
		return csrf;
	}
	public void setCsrf(String csrf) {
		this.csrf = csrf;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	
}
