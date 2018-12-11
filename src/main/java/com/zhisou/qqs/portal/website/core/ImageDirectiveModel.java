package com.zhisou.qqs.portal.website.core;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 图片处理指令类
 * @author            JiangTao
 * @version           1.0
 * @since             2017年4月7日
 *
 */
public class ImageDirectiveModel implements TemplateDirectiveModel {
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String src = this.getValue(params, "src");
		String thumbnail= this.getValue(params, "thumbnail");
		String defaultImage= this.getValue(params, "default");
		String imageurl = getImageUrl(src.toString(),defaultImage,thumbnail);
		
		StringBuffer html = new StringBuffer();
		html.append("<img");
		html.append(" src=\""+imageurl+"\"");
		
		Set keySet = params.keySet();
		Iterator<String> itor = keySet.iterator();
		
		//添加image标签中除src、thumbnail以外的属性
		while(itor.hasNext()){
			String name = itor.next();
			if("src".equals(name)||"thumbnail".equals(name)){ continue; }
			if("postfix".equals(name)){ continue; }
			String value =this.getValue(params, name);
			if(!StringUtils.isEmpty(value)){
				html.append(" "+name+"=\""+value+"\"");
			}
		}
		
		html.append(" />");
		env.getOut().write(html.toString());
	}

	private String getImageUrl(String pic,String defaultImage, String thumbnail){
		if (StringUtils.isEmpty(pic) || (pic.lastIndexOf(".") < 0)){
		    if (StringUtils.isNotBlank(defaultImage)){
		        return defaultImage;
		    }
		}
		
	    if (StringUtils.isNotBlank(thumbnail)){ //需要显示缩略图
            String extension = pic.substring(pic.lastIndexOf("."));
            pic = pic.replace(extension, "_" + thumbnail + extension);
        }

		return pic;
	}
	
	private String getValue(Map params, String name) {
		Object value_obj = params.get(name);
		if (value_obj == null) {
			return "";
		}

		return value_obj.toString();
	}
}
