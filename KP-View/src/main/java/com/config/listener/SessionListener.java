package com.config.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {
	
	private Logger logger = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		logger.debug("==== Session is created. ====");
		
		event.getSession().setMaxInactiveInterval(12 * 60 * 60);
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

		logger.debug("==== Session is destroyed ====");
		
	}

}
