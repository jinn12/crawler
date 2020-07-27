package com.cmn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {
	private Logger logger = LoggerFactory.getLogger(ApplicationContextProvider.class);
	//private static final Log LOG = LogFactory.getLog(SpringContextUtil.class);
	private static ApplicationContext ctx;

	public ApplicationContextProvider() {
		logger.info("init SpringApplicationContext");
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
	    ctx = context;
	}

	public static ApplicationContext getApplicationContext() {
        return ctx;
    }

	/**
	 * 스프링 빈 조회
	 *
	 * @param beanName 스프링빈 이름
	 * @return
	 */
	public static Object getBean(String beanName) {
	    return ctx.getBean(beanName);
	}

	/**
	 * 스프링 빈 조회
	 *
	 * @param beanName 스프링빈 이름
	 * @param requiredType 클래스 타입
	 * @return
	 */
	public static <T> T getBean(String beanName, Class<T> requiredType) {
	    return ctx.getBean(beanName, requiredType);
	}

	public static <T> T getBean(Class<T> requiredType) {
	    return ctx.getBean(requiredType);
	}

}
