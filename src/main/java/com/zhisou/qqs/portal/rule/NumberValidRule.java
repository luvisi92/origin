package com.zhisou.qqs.portal.rule;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import com.likegene.framework.core.formvalidator.DateRule;
import com.likegene.framework.core.formvalidator.Rule;

public class NumberValidRule implements Rule{

	@Override
	public String getRuleName() {
		
		return "numberValid";
	}

	@Override
	public boolean validate(Object config, Map<String, String> requestParams, String name,
			String value) {
		  
		String leftVal = ((Map<String,String>)config).get("leftnum");
		String rightVal = ((Map<String,String>)config).get("rightcount").toString();
        
        if(StringUtils.isBlank(leftVal)||StringUtils.isBlank(rightVal)|| StringUtils.isBlank(value))
            return true; 

        if (NumberUtils.isNumber(value)&&value.contains(".")) {
        	Long ln = Long.valueOf(value.split("\\.")[0].toString());
        	String rn = value.split("\\.")[1];
        	if(ln.longValue()>Long.valueOf(leftVal).longValue()||rn.length()>Integer.parseInt(rightVal)){
        		return false;
        	}else return true;
        } else if(NumberUtils.isNumber(value)&&!value.contains(".")) {
            if(Long.valueOf(value).longValue()>Long.valueOf(leftVal).longValue()){
            	return false;
            }else return true;
        }
		
		return false;
	}


}
