package com.zhisou.qqs.portal.website.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.likegene.framework.core.formvalidator.FormValidatorManager;
import com.likegene.framework.core.QueryFilter;
import com.likegene.framework.core.Result;
import com.zhisou.qqs.website.model.SysConfig;
import com.zhisou.qqs.website.service.SysConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 参数配置 Controller
 * @author JiangTao
 * @since  2017-02-23
 */
@Controller
@RequestMapping("/SysConfig")
public class SysConfigController extends BaseController{
	
	@Autowired
	private SysConfigService service;

	@RequestMapping(value = "/index.html")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter(request);
        model.put("pager", service.findPager(filter).getReturnObj());
		return "/SysConfig/index";
	}
	
	@RequestMapping(value = "/add.html")
	public String add(HttpServletRequest request, HttpServletResponse response) {
		return "/SysConfig/add";
	}
	
	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData save(ModelMap model, @ModelAttribute("entity") SysConfig entity, 
						HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> errors = FormValidatorManager.validate("saveSysConfigConfig", request);
        if (errors.size() != 0)
        {
            return new ResponseData(false, errors);
        }
        try{
		    Result result = service.save(entity);
		    if (!result.isSuccess()) {
		    	return new ResponseData(false, result);
	        }
        }catch(DuplicateKeyException e){
        	Result result = new Result();
            if (e.getRootCause().getMessage().toUpperCase().contains("PRIMARY")){
                result.addErrormsg("id  重复");
            }
            return new ResponseData(false, result);
        }
        return ResponseData.SUCCESS_NO_DATA;
	} 
	
	@RequestMapping(value = "/detail.html")
    public String detail(Long id, HttpServletRequest request, HttpServletResponse response) {
    	QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        request.setAttribute("entity", service.findOne(filter).getReturnObj());
        return "/SysConfig/detail";
    }

	@RequestMapping(value = "/edit.html")
    public String edit(Long id, HttpServletRequest request, HttpServletResponse response) {
    	QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        request.setAttribute("entity", service.findOne(filter).getReturnObj());
        return "/SysConfig/edit";
    }
    
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
	public ResponseData update(ModelMap model, @ModelAttribute("entity") SysConfig entity, 
						HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> errors = FormValidatorManager.validate("saveSysConfigConfig", request);
        if (errors.size() != 0)
        {
            return new ResponseData(false, errors);
        }
        try{
		    Result result = service.update(entity);
		    if (!result.isSuccess()) {
		    	return new ResponseData(false, result);
	        }
        }catch(DuplicateKeyException e){
        	Result result = new Result();
            if (e.getRootCause().getMessage().toUpperCase().contains("PRIMARY")){
                result.addErrormsg("id  重复");
            }
            return new ResponseData(false, result);
        }
	    return ResponseData.SUCCESS_NO_DATA;
	}
	
	@RequestMapping(value="/delete.json")
	@ResponseBody
    public ResponseData delete(Long[] ids, HttpServletRequest request, HttpServletResponse response)
    {
    	for (Long id : ids)
      	{
        	service.delete(id);
      	}
    	
      	
      	return ResponseData.SUCCESS_NO_DATA;
    }
	
	//根据分类查询参数配置
	@RequestMapping(value="/getConfig.json")
	@ResponseBody
    public ResponseData getConfig(HttpServletRequest request, HttpServletResponse response)
    {
		ResponseData data = new ResponseData(true);
		
		QueryFilter filter = new QueryFilter(request);
		List<SysConfig> list = service.findList(filter).getReturnObj();
		
        data.setObj(list);       
      	return data;
    }
}
