package com.zhisou.qqs.portal.website.service.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.likegene.framework.util.SettingUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2016-8-29
 */
@Component
public class ImageTag implements TemplateDirectiveModel{

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		if (params.get("src") == null||"".equals(params.get("src"))) {
            env.getOut().write(this.getClass()+"变量名src不能为空");
            return;
        }
		String src =params.get("src").toString();
		String setting = SettingUtil.getSetting("files.contextpath", String.class);
		src = src.replace("fs:", setting);		
		
		env.getOut().write(src);
	}
}
