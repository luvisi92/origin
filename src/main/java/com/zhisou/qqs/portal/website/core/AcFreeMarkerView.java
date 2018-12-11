package com.zhisou.qqs.portal.website.core;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.likegene.framework.util.SettingUtil;

/**
 * 
 * 获取项目服务器的绝对路径地址
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2017-3-8
 */
public class AcFreeMarkerView extends FreeMarkerView{

	private static final String CONTEXT_PATH = "appPath";

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
      //String scheme = request.getScheme();
        //String serverName = request.getServerName();
        //int port = request.getServerPort();
        String path = request.getContextPath();
        String basePath = SettingUtil.getSetting("project.server.url", null).toString() + path;
        
        model.put(CONTEXT_PATH, basePath);
        super.exposeHelpers(model, request);
    }
}
