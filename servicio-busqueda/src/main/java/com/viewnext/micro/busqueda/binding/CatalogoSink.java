package com.viewnext.micro.busqueda.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface CatalogoSink {

	public static String CATALOGO_UPDATE_CREATE = "catalogoUpdateCreate";
	public static String CATALOGO_DELETE = "catalogoDelete";
	
	@Input(CATALOGO_UPDATE_CREATE)
	public MessageChannel updateCreate();
	
	@Input(CATALOGO_DELETE)
	public MessageChannel delete();
}
