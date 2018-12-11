package com.zhisou.qqs.portal.website.core;

import javax.servlet.ServletContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.likegene.framework.util.SettingUtil;

/**
 * 
 * @author JiangTao
 * @version 1.0
 * @since 2016-5-21
 */
@Component
public class AppContextHolder implements ServletContextAware
{
    public static final String USER_INFO = "USER_INFO";
    public static final String TOKEN_STR = "TOKEN_STR";
    private static String  CONTEXTPATH;

    private static String  FILECONTEXTPATH;

    private static String  FILESAVEPATH;

    private static boolean initialized = false;

    public static void init(ServletContext servletContext)
    {
        FILECONTEXTPATH = SettingUtil.getSetting("files.contextpath", null)
                .toString();
        if (!(FILECONTEXTPATH.startsWith("http://") || FILECONTEXTPATH
                .startsWith("/")))
        {
            FILECONTEXTPATH = servletContext.getContextPath() + "/"
                    + FILECONTEXTPATH;
        }
        CONTEXTPATH = servletContext.getContextPath();

        FILESAVEPATH = SettingUtil.getSetting("uploadfilesavepath", null)
                .toString();
        if (!(FILESAVEPATH.contains(":") || FILESAVEPATH.startsWith("/")))
        {
            FILESAVEPATH = servletContext.getRealPath("/") + FILESAVEPATH;
        }
        
        SettingUtil.putAllToServletContext(servletContext);

        initialized = true;
    }

    public static String getContextPath()
    {
        return CONTEXTPATH;
    }

    public static String getFileContextPath()
    {
        return FILECONTEXTPATH;
    }

    public static String getFileSavePath()
    {
        return FILESAVEPATH;
    }

    public static boolean isInitialized()
    {
        return initialized;
    }

    public static <T> T getCurrentUser()
    {
        try
        {
            if (SecurityUtils.getSubject() == null
                    || SecurityUtils.getSubject().getSession(false) == null)
                return null;
            return (T) SecurityUtils.getSubject().getSession()
                    .getAttribute(USER_INFO);
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public static Session getSession()
    {
        try
        {
            if (SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getSession(false) == null)
                return null;
            return SecurityUtils.getSubject().getSession();
        }
        catch (Exception e)
        {
        }
        return null;
    }

    @Override
    public void setServletContext(ServletContext arg0)
    {
        init(arg0);
    }
}
