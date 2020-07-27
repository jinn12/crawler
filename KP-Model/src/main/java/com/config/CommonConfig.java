package com.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.config.handler.EgovComTraceHandler;

import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.cmmn.trace.handler.TraceHandler;
import egovframework.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import egovframework.rte.fdl.cmmn.trace.manager.TraceHandlerService;

@Configuration
@ComponentScan(
		basePackages="com",
		includeFilters={
				@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Service.class),
				@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Repository.class)
		},
		excludeFilters={
				@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Controller.class),
		}
)
public class CommonConfig {
	/**
	 * 프로퍼티 파일 위치 설정
	 *
	 * @return
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(
				"classpath:/egovframework/rte/fdl/idgnr/messages/idgnr",
				"classpath:/egovframework/rte/fdl/property/messages/properties");
		messageSource.setCacheSeconds(60);

		return messageSource;
	}

	/**
	 * Exception 발생시 후처리용 별도작업을 위해 실행환경의 LeveaTrace를 활용하도록 설정
	 *
	 * @param traceHandleService
	 * @return
	 */
	@Bean
	public LeaveaTrace leaveaTrace(DefaultTraceHandleManager traceHandlerService) {

		LeaveaTrace leaveaTrace = new LeaveaTrace();
		leaveaTrace.setTraceHandlerServices(new TraceHandlerService[] {traceHandlerService});
		return leaveaTrace;

	}

	/**
	 * Exception 발생시 후처리용 별도작업을 위해 실행환경의 DefaultTrace Handle Manager 를 활용하도록 설정
	 *
	 * @param antPathMatcher
	 * @param defaultTraceHandler
	 * @return
	 */
	@Bean
	public DefaultTraceHandleManager traceHandlerService(AntPathMatcher antPathMatcher, EgovComTraceHandler defaultTraceHandler) {

		DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
		defaultTraceHandleManager.setReqExpMatcher(antPathMatcher);
		defaultTraceHandleManager.setPatterns(new String[] {"*"});
		defaultTraceHandleManager.setHandlers(new TraceHandler[] {defaultTraceHandler});
		return defaultTraceHandleManager;
	}

	/**
	 * Exception 발생시 후처리용 별도작업을 위해 실행환경의  AntPathMatcher 를 활용하도록 설정
	 */
	@Bean
	public AntPathMatcher antPathMatcher() {

		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher;

	}

	/**
	 * Exception 발생시 후처리용 별도작업을 위해 실행환경의  DefaultTraceHandler 를 활용하도록 설정 egovframework.rte.fdl.cmmn.trace.handler.DefaultTraceHandler
	 *
	 * @return
	 */
	@Bean
	public EgovComTraceHandler defaultTraceHandler() {

		EgovComTraceHandler egovComTraceHandler = new EgovComTraceHandler();
		return egovComTraceHandler;

	}

}
