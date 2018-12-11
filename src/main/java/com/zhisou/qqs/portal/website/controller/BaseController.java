package com.zhisou.qqs.portal.website.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likegene.framework.core.spring.MyCustomDateEditor;
import com.likegene.framework.core.spring.MyCustomNumberEditor;

abstract public class BaseController {

    protected final Logger logger       = LoggerFactory.getLogger(this.getClass());
	
	public final static String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
	
	protected final ObjectMapper mapper = new ObjectMapper();

	//~ Instance fields ================================================================================================
	
	//~ Methods ========================================================================================================
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, new MyCustomDateEditor());

        NumberFormat numberFormat = new DecimalFormat();
        binder.registerCustomEditor(Float.class, new MyCustomNumberEditor(Float.class, numberFormat, true));
        binder.registerCustomEditor(float.class, new MyCustomNumberEditor(Float.class, numberFormat, true));
        binder.registerCustomEditor(Double.class, new MyCustomNumberEditor(Double.class, numberFormat, true));
        binder.registerCustomEditor(double.class, new MyCustomNumberEditor(Double.class, numberFormat, true));
        binder.registerCustomEditor(Integer.class, new MyCustomNumberEditor(Integer.class, numberFormat, true));
        binder.registerCustomEditor(int.class, new MyCustomNumberEditor(Integer.class, numberFormat, true));

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(32);
        df.setMaximumIntegerDigits(32);
        binder.registerCustomEditor(BigDecimal.class, new MyCustomNumberEditor(BigDecimal.class, df, true));
    }
	
	final protected void writeJson(HttpServletResponse response, String json)
    {
        response.setContentType("application/json; charset=utf-8");
        try
        {
            response.getWriter().write(json);
            response.getWriter().close();
        }
        catch (IOException e)
        {
             logger.error("Json返回异常", e);
        }
    }
	
	protected HttpServletRequest getRequest(){
	    return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected HttpServletResponse getResponse(){
	    return ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	final public void addError(String msg)
    {
        Object errormsgs = getRequest().getAttribute("errorMsgs");
        if (errormsgs != null)
        {
            if (errormsgs instanceof String)
            {
                getRequest().setAttribute("errorMsgs", new ArrayList<String>());
            }
            ((ArrayList) getRequest().getAttribute("errorMsgs")).add(msg);
        }
        else
        {
            getRequest().setAttribute("errorMsgs", msg);
        }
    }
	
	@ExceptionHandler()
	public void handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		//服务端处理失败
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		request.setAttribute(EXCEPTION_MESSAGE, exception);
		logger.error("handleException", exception);
		String requestedWith = request.getHeader("X-Requested-With");
		if(requestedWith == null || !"XMLHttpRequest".equals(requestedWith)) {
            ResponseData data = new ResponseData(false, exception.getClass() + ": " + exception.getMessage());
            data.setRequestURI(request.getRequestURI());
            data.setExecptionTrace(ExceptionUtils.getMessage(exception));
            
            try {
                String json = mapper.writeValueAsString(data);
                response.setContentType("application/json;charset=UTF-8");
                response.getOutputStream().print(json);
            } catch (Exception e) {
                logger.error("全局异常处理", e);
            }
		} else {
		    RequestDispatcher rd =  request.getSession().getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp"); 
		    try {
		        rd.forward(request, response);
		    } catch (Exception e) {
		        //
		    }
		}
	}
}
