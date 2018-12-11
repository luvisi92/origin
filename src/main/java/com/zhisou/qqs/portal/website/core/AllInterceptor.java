package com.zhisou.qqs.portal.website.core;
import java.util.Iterator;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

/**
 * 
 * Description of the class
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2017-3-22
 */
public class AllInterceptor implements WebRequestInterceptor {  
    private Logger logger = LoggerFactory.getLogger(AllInterceptor.class);
      
    /** 
     * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中 
     */  
    @Override  
    public void preHandle(WebRequest request) throws Exception {  
        String deviceType = request.getHeader("deviceType");
        request.setAttribute("deviceType", deviceType, WebRequest.SCOPE_REQUEST);
        DispatcherServletWebRequest dispatcherServletWebRequest = (DispatcherServletWebRequest)request;
        dispatcherServletWebRequest.setAttribute("deviceType", deviceType, WebRequest.SCOPE_REQUEST);
        
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.startsWith("android="))
        {
            dispatcherServletWebRequest.setAttribute("deviceType", "android", WebRequest.SCOPE_REQUEST);
        }
        else if (userAgent.startsWith("ios="))
        {
            dispatcherServletWebRequest.setAttribute("deviceType", "ios", WebRequest.SCOPE_REQUEST);
        } else {
            dispatcherServletWebRequest.setAttribute("deviceType", "browser", WebRequest.SCOPE_REQUEST);
        }
        
        
        Iterator<String> names = request.getHeaderNames();
        logger.debug("===============================================================");
        logger.debug("请求地址："+dispatcherServletWebRequest.getRequest().getRequestURI());
        logger.debug("Header信息：");
        while(names.hasNext())
        {
            String name = names.next();
            logger.debug(name+ ":"+request.getHeader(name));
        }
        if (AppContextHolder.getSession() != null) 
        	dispatcherServletWebRequest.setAttribute("sid", AppContextHolder.getSession().getId(), WebRequest.SCOPE_REQUEST);
    }  

    /** 
     * 该方法将在Controller执行之后，返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在 
     * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。 
     */  
    @Override  
    public void postHandle(WebRequest request, ModelMap map) throws Exception {  
    }  

    /** 
     * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用，主要用于进行一些资源的释放 
     */  
    @Override  
    public void afterCompletion(WebRequest request, Exception exception)  
    throws Exception {  
    }  
      
}  