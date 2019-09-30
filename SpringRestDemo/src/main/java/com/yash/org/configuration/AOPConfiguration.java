package com.yash.org.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.yash.org.aspect.LogService;

@Configuration
@EnableAspectJAutoProxy
public class AOPConfiguration {
	
	@Bean
	public LogService logService(){
		return new LogService();
	}

}
