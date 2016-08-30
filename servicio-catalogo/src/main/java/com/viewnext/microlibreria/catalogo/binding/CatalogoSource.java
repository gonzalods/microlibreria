package com.viewnext.microlibreria.catalogo.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CatalogoSource {

	public static String CATALOGO_UPDATE = "catalogoUpdate";
	public static String CATALOGO_CREATE = "catalogoCreate";
	public static String CATALOGO_DELETE = "catalogoDelete";
	
	@Output(CATALOGO_CREATE)
	public MessageChannel create();
	
	@Output(CATALOGO_UPDATE)
	public MessageChannel update();
	
	@Output(CATALOGO_DELETE)
	public MessageChannel delete();
}
