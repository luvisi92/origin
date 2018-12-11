//-------------------------------------------------------------------------
// Copyright (c) 2015-2017 HF-STAR-V. All Rights Reserved.
//
// This software is the confidential and proprietary information of
// HF-STAR-V
//
//-------------------------------------------------------------------------

package com.zhisou.qqs.portal.website.core;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.likegene.framework.core.QueryFilter;
import com.zhisou.qqs.portal.util.DecodeUtil;

/**
 * @author            JiangTao
 * @version           1.0
 * @since             2016-8-2
 */

public class AutoLoginAuthenticationFilter extends AuthenticationFilter
{
    private Logger logger = LoggerFactory.getLogger(AutoLoginAuthenticationFilter.class);
    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception
    {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
    	String tokenStr = (String) httpRequest.getAttribute("token");
        logger.debug(tokenStr);
    	if (tokenStr != null) {
    	        String[] usernamepassword = DecodeUtil.decrypt(tokenStr).split(",");
    	        if (usernamepassword.length == 3)
    	        {
    	            try{
    	            String username = usernamepassword[0];
                    String password = usernamepassword[1];
                   
                    
                    //根据登录名查询用户
                    QueryFilter filter = new QueryFilter();
                    filter.setStatementKey("login");
                    filter.put("username", username);
                    filter.put("status", 1);
                    filter.put("userType", Constants.BUYER);
                    
                        httpRequest.setAttribute("sid", tokenStr);
                        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                        SecurityUtils.getSubject().login(token);
                        return true;
                }catch(Exception e)
                {
                    e.printStackTrace();
                } 
	        }
    	}
        return true;
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
            ServletResponse response, Object mappedValue)
    {
        HttpServletRequest httpRequest = ((HttpServletRequest)request);
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String sid = httpRequest.getHeader("sid");
        
        logger.debug(httpRequest.getRequestURI());
        logger.debug(sid);
        if (sid != null) {
            SimpleCookie simpleCookie = new SimpleCookie();
            simpleCookie.setHttpOnly(true);
            simpleCookie.setPath(httpRequest.getContextPath());
            simpleCookie.setName("sid");
            simpleCookie.setMaxAge(-1);
            simpleCookie.setValue(sid);
            simpleCookie.saveTo(httpRequest, httpResponse);
        }
        
        return super.isAccessAllowed(request, response, mappedValue);
    }
}

