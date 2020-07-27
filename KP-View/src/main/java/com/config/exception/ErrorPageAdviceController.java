package com.config.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorPageAdviceController {

	@ExceptionHandler(NoHandlerFoundException.class)
	public String dealWithNoHandleFoundException(NoHandlerFoundException e, HttpServletRequest httpServletRequest) {
		
		return "/common/error.jsp";
				
	}
	
	@ExceptionHandler(ServletException.class)
	public void handleServletException(ServletException e, HttpServletRequest request) {
		e.printStackTrace();
	}
	
	@ExceptionHandler(IOException.class)
	public void handleIOException(IOException e, HttpServletRequest request) {
		e.printStackTrace();
	}
	
	@ExceptionHandler(Exception.class)
	public void handleException(Exception e, HttpServletRequest request) {
		e.printStackTrace();
	}
}
