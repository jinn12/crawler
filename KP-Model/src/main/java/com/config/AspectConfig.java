package com.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.AntPathMatcher;

import com.config.aop.AopExceptionTransfer;
import com.config.handler.EgovComExcepHndlr;
import com.config.handler.EgovComOthersExcepHndlr;

import egovframework.rte.fdl.cmmn.aspect.ExceptionTransfer;
import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import egovframework.rte.fdl.cmmn.exception.manager.DefaultExceptionHandleManager;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

	/**
	 * 포털사이트의 기능 작동과정에서 Exception 발생시 후처리를 위한 AOP설정
	 *
	 * @param exceptionTransfer
	 * @return
	 */
	@Bean
	public AopExceptionTransfer aopExceptionTransfer(ExceptionTransfer exceptionTransfer) {

		AopExceptionTransfer aopExceptionTransfer = new AopExceptionTransfer();
		aopExceptionTransfer.setExceptionTransfer(exceptionTransfer);
		return aopExceptionTransfer;

	}

	/**
	 * Exception 발생시 후처리를 위해 표준프레임워크 실행환경의 ExceptionTransfer를 활용하도록  설정
	 *
	 * @param defaultExceptionHandleManager
	 * @param otherExceptionHandleManager
	 * @return
	 */
	@Bean
	public ExceptionTransfer exceptionTransfer(
			@Qualifier("defaultExceptionHandleManager") DefaultExceptionHandleManager defaultExceptionHandleManager,
			@Qualifier("otherExceptionHandleManager") DefaultExceptionHandleManager otherExceptionHandleManager) {

		ExceptionTransfer exceptionTransfer = new ExceptionTransfer();
		exceptionTransfer.setExceptionHandlerService(new DefaultExceptionHandleManager[] {
				defaultExceptionHandleManager,
				otherExceptionHandleManager
		});

		return exceptionTransfer;

	}

	/**
	 * Exception 발생에 대한 기본Excpton처리를 위해 표준프레임워크 실행환경의 DefaultExceptionTransfer를 활용하도록 설정
	 *
	 * @param antPathMatcher
	 * @param egovHandler
	 * @return
	 */
	@Bean
	public DefaultExceptionHandleManager defaultExceptionHandleManager(
			AntPathMatcher antPathMatcher, EgovComExcepHndlr egovHandler) {

		DefaultExceptionHandleManager defaultExceptionHandleManager = new DefaultExceptionHandleManager();
		defaultExceptionHandleManager.setReqExpMatcher(antPathMatcher);
		defaultExceptionHandleManager.setPatterns(new String[] {"**service.impl.*"});
		defaultExceptionHandleManager.setHandlers(new ExceptionHandler[] {egovHandler});

		return defaultExceptionHandleManager;

	}

	/**
	 * Exception 발생에 대한 기타Excpton처리를 위해 표준프레임워크 실행환경의 DefaultExceptionTransfer를 활용하도록 설정
	 *
	 * @param antPathMatcher
	 * @param otherHandler
	 * @return
	 */
	@Bean
	public DefaultExceptionHandleManager otherExceptionHandleManager(
			AntPathMatcher antPathMatcher, EgovComOthersExcepHndlr otherHandler) {

		DefaultExceptionHandleManager otherExceptionHandleManager = new DefaultExceptionHandleManager();
		otherExceptionHandleManager.setReqExpMatcher(antPathMatcher);
		otherExceptionHandleManager.setPatterns(new String[] {"**service.impl.*"});
		otherExceptionHandleManager.setHandlers(new ExceptionHandler[] {otherHandler});

		return otherExceptionHandleManager;

	}

	/**
	 * 템플릿 내에서 Exception 발생시 실제 처리를 위한 클래스 설정
	 *
	 * @return
	 */
	@Bean
	public EgovComExcepHndlr egovHandler() {
		EgovComExcepHndlr egovComExcepHndlr = new EgovComExcepHndlr();
		return egovComExcepHndlr;
	}

	/**
	 * 템플릿 내에서 Exception 발생시 실제 처리를 위한 클래스 설정
	 *
	 * @return
	 */
	@Bean
	public EgovComOthersExcepHndlr otherHandler() {
		EgovComOthersExcepHndlr egovComOthersExcepHndlr = new EgovComOthersExcepHndlr();
		return egovComOthersExcepHndlr;
	}
}
