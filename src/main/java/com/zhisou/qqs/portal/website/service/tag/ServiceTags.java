package com.zhisou.qqs.portal.website.service.tag;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import com.jagregory.shiro.freemarker.ShiroTags;
import com.likegene.framework.core.SpringBeanUtil;
import com.likegene.framework.core.formvalidator.FormValidatorManager;
import com.zhisou.qqs.portal.rule.NumberValidRule;
import com.zhisou.qqs.website.model.SysFile;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * 服务标签注册类
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2016-7-28
 */
@Component
public class ServiceTags extends SimpleHash implements InitializingBean
{
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;
    
    @Override
    public TemplateModel get(String arg0) throws TemplateModelException {
    	return SpringBeanUtil.getBean(arg0+"Tag");
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {/*
        BeansWrapper wrapper = (BeansWrapper) freeMarkerConfigurer.getConfiguration().getObjectWrapper();  
        TemplateHashModel staticModels = wrapper.getStaticModels();  
//        try
//        {
//            freeMarkerConfigurer.getConfiguration().setSharedVariable("constants", staticModels.get(Constants.class.getName()));
//        }
//        catch (TemplateModelException e)
//        {
//            e.printStackTrace();
//        }
        
//        SysConfigService esConfigService = SpringBeanUtil.getBean(SysConfigService.class);
//        Result result = esConfigService.findList(new QueryFilter());
//        List<SysConfig> lists = result.getReturnObj();
//        for(SysConfig ec:lists)
//        {
//            freeMarkerConfigurer.getConfiguration().setSharedVariable(ec.getKey(), ec);
//        }
        FormValidatorManager.addRule(new NumberValidRule());
        freeMarkerConfigurer.getConfiguration().setSharedVariable("SysFile", staticModels.get(SysFile.class.getName()));
        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags()); 
        freeMarkerConfigurer.getConfiguration().setSharedVariable("service", this); */
    }
}