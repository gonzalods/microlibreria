package com.viewnext.confserver;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.monitor.PropertyPathNotification;
import org.springframework.cloud.config.monitor.PropertyPathNotificationExtractor;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.MultiValueMap;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	@Bean
	public PropertyPathNotificationExtractor propertyPathNotificationExtractor(){
		return new GogsPropertyPathNotificationExtractor();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
	
	public static class GogsPropertyPathNotificationExtractor implements PropertyPathNotificationExtractor{

		@Override
		public PropertyPathNotification extract(MultiValueMap<String, String> headers, Map<String, Object> payload) {
			if("push".equals(headers.getFirst("X-Gogs-Event"))){
				if(payload.get("commits") instanceof Collection){
					Set<String> paths = new HashSet<>();
					@SuppressWarnings("unchecked")
					Collection<Map<String, Object>> commits = (Collection<Map<String, Object>>)payload.get("commits");
					for(Map<String, Object> commit: commits){
						List<String> filtrados = Arrays.asList(((String)commit.get("message")).split(",")).stream()
								.filter(item -> {
									return item.trim().endsWith(".yml");
								})
								.map(item -> {return item.trim();})
								.collect(Collectors.toList());
						paths.addAll(filtrados);
					}
					return new PropertyPathNotification(paths.toArray(new String[0]));
				}
			}
			return null;
		}
		
	}
}
