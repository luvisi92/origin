package com.zhisou.qqs.portal.website.service.test;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.likegene.framework.core.formvalidator.FormValidatorManager;
import com.zhisou.qqs.portal.website.core.AppContextHolder;

/**
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2016-5-26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:spring/*.xml" })
public class SpringTestSupport
{
    private HandlerMapping        handlerMapping;

    private HandlerAdapter        handlerAdapter;

    public FreeMarkerViewResolver viewResolver;

    public ShiroFilter            shiroFilter;

    @Autowired
    private WebApplicationContext wac;

    /**
     * 读取配置文件
     */
    @BeforeClass
    public static void setUp()
    {
        FormValidatorManager.init("src/main/webapp/assets/validatejs/", true);
    }

    /**
     * 执行request请求的action
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String excuteAction(MockHttpServletRequest request,
            MockHttpServletResponse response) throws Exception
    {
        MockServletContext servletContext = new MockServletContext("/fb");
        AppContextHolder.init(servletContext);

        handlerMapping = (HandlerMapping) wac.getBean(wac
                .getBeanNamesForType(RequestMappingHandlerMapping.class)[0]);
        handlerAdapter = (HandlerAdapter) wac.getBean(wac
                .getBeanNamesForType(RequestMappingHandlerAdapter.class)[0]);
        viewResolver = (FreeMarkerViewResolver) wac.getBean("viewResolver");

        // for(String n:wac.getBeanDefinitionNames()){
        // System.out.println(n+"="+wac.getBean(n).getClass());
        // }
        //
        // 这里需要声明request的实际类型，否则会报错
        request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
        HandlerExecutionChain chain = handlerMapping.getHandler(request);
        ModelAndView model = null;
        request.addHeader("user-agent", "unit test");
        if (chain == null)
        {
            model = handlerAdapter.handle(request, response, null);
        }
        else
        {
            model = handlerAdapter
                    .handle(request, response, chain.getHandler());
        }
        if (model != null && model.getViewName() != null)
        {
            View view = viewResolver.resolveViewName(model.getViewName(),
                    Locale.getDefault());
            view.render(new HashMap(), (HttpServletRequest) request, response);
        }
        return response.getContentAsString();
    }

    public void login(String username, String password)
    {
        org.apache.shiro.mgt.SecurityManager securityManager = (org.apache.shiro.mgt.SecurityManager) wac
                .getBean("securityManager");
        SecurityUtils.setSecurityManager(securityManager);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        SecurityUtils.getSubject().login(token);
    }
}
