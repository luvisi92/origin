//-------------------------------------------------------------------------
// Copyright (c) 2015-2017 HF-STAR-V. All Rights Reserved.
//
// This software is the confidential and proprietary information of
// HF-STAR-V
//
//-------------------------------------------------------------------------

package com.zhisou.qqs.portal.website.core;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

/**
 * 去除空格
 * @author            JiangTao
 * @version           1.0
 * @since             2015-6-8
 */

public class TrimParameterFilter implements Filter
{

    @Override
    public void destroy()
    {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        if (request instanceof HttpServletRequest) {
            chain.doFilter(new WrapperHttpServletRequest((HttpServletRequest)request), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {

    }
    
    class WrapperHttpServletRequest extends HttpServletRequestWrapper implements ServletRequest {

        public WrapperHttpServletRequest(HttpServletRequest request)
        {
            super(request);
        }
        
        @Override
        public String getParameter(String name)
        {
            return StringUtils.trim(super.getParameter(name));
        }
        
        @Override
        public String[] getParameterValues(String name)
        {
            String[] values = super.getParameterValues(name);
            for(int i = 0; values != null && i < values.length; i++)
                values[i] = StringUtils.trim(values[i]);
            return values;
        }
        
        @Override
        public Map<String,String[]> getParameterMap()
        {
            Map<String,String[]> map = super.getParameterMap();
            Iterator<String[]> iter = map.values().iterator();
            while(iter.hasNext())
            {
                String[] values = iter.next();
                for(int i = 0; values != null && i < values.length; i++)
                    values[i] = StringUtils.trim(values[i]);
            }
            
            return map;
        }
        
    }

}

