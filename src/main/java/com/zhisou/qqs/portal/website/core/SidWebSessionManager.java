package com.zhisou.qqs.portal.website.core;

import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2016-4-1
 */
public class SidWebSessionManager extends DefaultWebSessionManager{

    private static final Logger log = LoggerFactory.getLogger(SidWebSessionManager.class);
    @Override
    public Serializable getSessionId(SessionKey key) {
        Serializable id = key.getSessionId();
        if (id == null && WebUtils.isWeb(key)) {
            HttpServletRequest httpRequest = (HttpServletRequest) WebUtils.getRequest(key);
            HttpServletResponse httpResponse = (HttpServletResponse) WebUtils.getResponse(key);
            
            String sid = "";
            log.debug("request uri="+httpRequest.getRequestURI());
            String userAgent = httpRequest.getHeader("User-Agent");
            if (StringUtils.isNotBlank(userAgent) && userAgent.indexOf(";token=") != -1)
            {
                sid = userAgent.substring(userAgent.lastIndexOf(";token=")+7);
                if (StringUtils.isNotBlank(sid))
                {
                    log.debug("User-Agent token="+sid);
                    httpRequest.setAttribute("token", sid);
                    return sid;
                }
            }
            
            sid = httpRequest.getParameter("sid");
            if (StringUtils.isNotBlank(sid))
            {
                log.debug("parameter sessionid="+sid);
                Cookie template = super.getSessionIdCookie(); 
                Cookie cookie = new SimpleCookie(template);
                cookie.setValue(sid);
                cookie.saveTo(httpRequest, httpResponse);
                httpRequest.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
                httpRequest.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
                
                return sid;
            }
            sid = httpRequest.getHeader("sid");
            if (StringUtils.isNotBlank(sid))
            {
                log.debug("header sessionid="+sid);
                Cookie template = super.getSessionIdCookie(); 
                Cookie cookie = new SimpleCookie(template);
                cookie.setValue(sid);
                cookie.saveTo(httpRequest, httpResponse);
                httpRequest.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
                httpRequest.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
                
                return sid;
            }
            String referer = httpRequest.getHeader("referer");
            if (referer != null && referer.contains("/store/login.html?forward")) {
                javax.servlet.http.Cookie cookies[] = httpRequest.getCookies();
                if (cookies != null) {
                    ArrayList<String> jsessionids = new ArrayList();
                    for (javax.servlet.http.Cookie c : cookies) {
                        if (c.getName().equals("sid")) {
                            sid = c.getValue();
                            jsessionids.add(sid);
                        }
                    }
                    if (jsessionids.size() >= 1)
                    {
                        String contextPath = httpRequest.getContextPath();
                        String uri = httpRequest.getRequestURI().replace("//", "/");
                        uri = uri.substring(0, uri.lastIndexOf("/"));
                        while(!uri.equals(contextPath) && !uri.equals("/"))
                        {
                            Cookie t = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
                            t.setHttpOnly(true);
                            Cookie c = new SimpleCookie(t);
                            c.setValue(sid);
                            c.setPath(uri);
                            c.setMaxAge(0);
                            c.saveTo(httpRequest, httpResponse);
                            uri = uri.substring(0, uri.lastIndexOf("/"));
                        }
                    }
                }
            }
        }
        return super.getSessionId(key);
    }
   
}
