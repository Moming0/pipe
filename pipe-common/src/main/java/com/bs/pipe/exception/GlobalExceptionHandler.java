package com.bs.pipe.exception;

import com.bs.pipe.utils.ResObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
 
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler(value = BusinessException.class)
    public ResObject jsonExceptionHandler(HttpServletRequest request, BusinessException e) {
    	return ResObject.bind(400, e.getMessage());
    }
    
    @ExceptionHandler(value = DataAccessException.class)
    public ResObject dataAccessExceptionHandler(HttpServletRequest request, DataAccessException e) {
    	LOGGER.error(e.getMessage(), e);
    	return ResObject.bind(400, "数据库异常！");
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResObject jsonExceptionHandler3(HttpServletRequest request, Exception e) {
    	LOGGER.error(e.getMessage(), e);
		return ResObject.bind(400, "未知异常，详细请查看相关日志");
    }
}