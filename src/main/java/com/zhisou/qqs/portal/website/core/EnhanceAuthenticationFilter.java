package com.zhisou.qqs.portal.website.core;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;

/**
 * 
 * 增加ajax请求时返回json信息
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2015-7-28
 */
public class EnhanceAuthenticationFilter extends AuthenticationFilter {

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            //------------------------------------------------------------
            //add by xiantao
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            HttpServletResponse httpServletResponse = (HttpServletResponse)response;
            if (httpServletRequest.getRequestURL().toString().endsWith(".json"))
            {
                httpServletResponse.setContentType("application/json; charset=utf-8");
                StringBuffer result = new StringBuffer();
                result.append("{\"code\":401,\"message\":\"");
                result.append(httpServletRequest.getContextPath());
                result.append("/login.html\"}");
                httpServletResponse.getOutputStream().write(result.toString().getBytes());
                httpServletResponse.getOutputStream().close();
            }
            else {
                if (httpServletRequest.getParameter("forward") != null){
                    httpServletResponse.sendRedirect(getLoginUrl()+"?forward="+httpServletRequest.getParameter("forward"));
                }else{
                    String url = httpServletRequest.getRequestURL().toString();
                    String params=httpServletRequest.getQueryString();//传递参数
                    if(params!=null && !"".equals(params)){
                        params=params.replace("&", "!");
                        url=url+"?"+params;
                    }
                    
                    String requestedWith = httpServletRequest.getHeader("X-Requested-With");
                    
                    if(requestedWith != null && "XMLHttpRequest".equals(requestedWith)) {
                        httpServletResponse.setContentType("application/json; charset=utf-8");
                        StringBuffer result = new StringBuffer();
                        result.append("{\"code\":401,\"message\":\"");
                        result.append(httpServletRequest.getContextPath());
                        result.append("/login.html\"}");
                        httpServletResponse.getOutputStream().write(result.toString().getBytes());
                        httpServletResponse.getOutputStream().close();
                    } else {
                        if (getLoginUrl().startsWith("http://")) {
                            httpServletResponse.sendRedirect(getLoginUrl());
                        } else {
                            if(url.indexOf("sidValidator")==-1){
                                StringBuffer sb = new StringBuffer();
                                sb.append("http://");
                                sb.append(httpServletRequest.getServerName());
                                if (httpServletRequest.getServerPort() != 80) {
                                    sb.append(":").append(httpServletRequest.getServerPort());
                                }
                                sb.append(httpServletRequest.getContextPath()).append("/");
                                sb.append(getLoginUrl());
                                sb.append("?forward=");
    
                                sb.append(url);
                                httpServletResponse.sendRedirect(sb.toString());
                            }else{
                                httpServletResponse.sendRedirect(getLoginUrl());
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

}
