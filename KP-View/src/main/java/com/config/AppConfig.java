package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmn.utils.ApplicationContextProvider;


@Configuration
public class AppConfig {

	@Bean
	public ApplicationContextProvider applicationContextProvder(){
		ApplicationContextProvider appContextProvider = new ApplicationContextProvider();
		return appContextProvider;
	}

}
