package com.viewnext.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="admin")
public class AdminProperties {

	private String catalogoUrl;
	private String busquedaUrl;
	public String getCatalogoUrl() {
		return catalogoUrl;
	}
	public void setCatalogoUrl(String catalogoUrl) {
		this.catalogoUrl = catalogoUrl;
	}
	public String getBusquedaUrl() {
		return busquedaUrl;
	}
	public void setBusquedaUrl(String busquedaUrl) {
		this.busquedaUrl = busquedaUrl;
	}
	
}
