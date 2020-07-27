package com.config.aop;
import org.aspectj.lang.annotation.Aspect;

import egovframework.rte.fdl.cmmn.aspect.ExceptionTransfer;

/**
 * 포털사이트의 기능 작동과정에서 Exception 발생시 후처리를 위한 AOP설정
 *
 * @author hakjoonjang
 *
 */
@Aspect
public class AopExceptionTransfer {

	private ExceptionTransfer exceptionTransfer;

	public void setExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		this.exceptionTransfer = exceptionTransfer;
	}

}
