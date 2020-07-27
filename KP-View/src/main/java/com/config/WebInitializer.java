package com.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.config.listener.SessionListener;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;


public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		String profile = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
		profile = null != profile && !"".equals(profile) ? profile : "local";

		servletContext.setInitParameter(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, profile);

		servletContext.addListener(new SessionListener());

		//log listener
		servletContext.addListener(new LogbackConfigListener());
		servletContext.setInitParameter("logbackConfigLocation", "classpath:logback-" + profile + ".xml");

		// Root Context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));

		// Action Servlet
		AnnotationConfigWebApplicationContext actionContext = new AnnotationConfigWebApplicationContext();

		DispatcherServlet actionDispatcherServlet = new DispatcherServlet(actionContext);
		actionDispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

		ServletRegistration.Dynamic actionEntryPoint =
				servletContext.addServlet("action", actionDispatcherServlet);
		actionEntryPoint.setLoadOnStartup(1);
		actionEntryPoint.addMapping("/", "*.do", "*.json");


	}

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[] {
				RootConfig.class,
				AppConfig.class,
				//SqlMapConfig.class,
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		return new Class[] {
				WebMvcConfig.class
		};
	}

	@Override
	protected String[] getServletMappings() {
		return null;

	}

	@Override
	protected boolean isAsyncSupported() {
		return true;
	}

}
