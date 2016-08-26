package com.viewnext.admin.client;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.viewnext.admin.security.SecurityTokens;
import com.viewnext.admin.security.SessionTokens;

import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;

@Configuration
public class CatalogoClientConfig {
	
	@Bean
	Collection<RequestInterceptor> requestInterceptors(){
		Collection<RequestInterceptor> interceptors = new HashSet<>();
		interceptors.add(new SecurityHeadersInterceptor());
		return interceptors;
	}
	
	//@Bean
	ErrorDecoder errorDecoder(){
		return new NotFoundErrorDecoder();
	}
	
	@Bean
	CatalogoClienteFallback catalogoClienteFallback(){
		return new CatalogoClienteFallback();
	}
	
	@Bean
	BusquedaClienteFallback busquedaClenteFallback(){
		return new BusquedaClienteFallback();
	}
	
	static class SecurityHeadersInterceptor implements RequestInterceptor{

		private static final String COOKIE_HEADER = "Cookie";
		private static final String XSRF_TOKEN_HEADER = "X-XSRF-TOKEN";
		private static final String SESSION_COOKIE = "SESSION=";
		//private static final String XSRF_TOKEN_COOKIE = "XSRF-TOKEN=";
		
		@Override
		public void apply(RequestTemplate template) {
			System.out.println("se lanza interceptor");
			if(template.method() != "GET"){
				SecurityTokens securityTokens = SessionTokens.get(); 
				System.out.println("secutiryTotens:" + securityTokens);
				template.header(COOKIE_HEADER, SESSION_COOKIE + securityTokens.getSession()/*,
						XSRF_TOKEN_COOKIE + securityTokens.getCsrf()*/);
				template.header(XSRF_TOKEN_HEADER, securityTokens.getCsrf());
			}
		}
	}
	
	static class NotFoundErrorDecoder implements ErrorDecoder{

		@Override
		public Exception decode(String methodKey, Response response) {
			if(response.status() == 404){
				return FeignException.errorStatus(methodKey, response);
			}
			return FeignException.errorStatus(methodKey, response);
		}
		
	}
	
	public static class LibNotFoundException extends RuntimeException{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String reason;
		int status;
		public LibNotFoundException(String reason, int status) {
			super();
			this.reason = reason;
			this.status = status;
		}
		
	}
}
