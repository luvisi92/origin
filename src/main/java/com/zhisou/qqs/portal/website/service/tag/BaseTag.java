package com.zhisou.qqs.portal.website.service.tag;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import freemarker.core.Environment;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * Description of the class
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2016-8-28
 */
public abstract class BaseTag implements TemplateDirectiveModel{

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
	    Iterator<String> keyIter = params.keySet().iterator();
	    while(keyIter.hasNext())
	    {
	        String key = keyIter.next();
	        Object val = params.get(key);
	        if (val instanceof SimpleScalar){
	            params.put(key, val.toString());
	        }
	    }
	    String var = (String) params.get("var");
	    String method = (String) params.get("method");
		if (StringUtils.isBlank(var)) {
		    throw new RuntimeException("变量名var不能为空");
		}
		
		SimpleHash result = new SimpleHash();
		TemplateModel model = process(method, params, result, loopVars);
		env.setVariable(var, model);
		if (body != null) {
            try {
                body.render(env.getOut());
            } catch (TemplateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public abstract TemplateModel process(String method, Map params, SimpleHash result,TemplateModel[] loopVars);
	

}
