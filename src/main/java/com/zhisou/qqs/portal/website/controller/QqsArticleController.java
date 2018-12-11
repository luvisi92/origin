package com.zhisou.qqs.portal.website.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhisou.qqs.portal.website.controller.BaseController;
import com.zhisou.qqs.portal.website.controller.ResponseData;
import com.zhisou.qqs.website.service.QqsArticleService;
import com.zhisou.qqs.website.model.QqsArticle;
import com.likegene.framework.core.formvalidator.FormValidatorManager;
import com.likegene.framework.core.QueryFilter;
import com.likegene.framework.core.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文章表 Controller
 * @author JT
 * @since  2017-05-17
 */
@Controller
@RequestMapping("/QqsArticle")
public class QqsArticleController extends BaseController{
	
	@Autowired
	private QqsArticleService service;

	@RequestMapping(value = "/index.html")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter(request);
        model.put("pager", service.findPager(filter).getReturnObj());
		return "/QqsArticle/index";
	}
	
	@RequestMapping(value = "/add.html")
	public String add(HttpServletRequest request, HttpServletResponse response) {
		return "/QqsArticle/add";
	}
	
	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData save(ModelMap model, @ModelAttribute("entity") QqsArticle entity, 
						HttpServletRequest request, HttpServletResponse response) {
        try{
			Map<String,Object> errors = FormValidatorManager.validate("saveQqsArticleConfig", request);
	        if (errors.size() != 0)
	        {
	            return new ResponseData(false, errors.keySet().toString());
	        }
		    Result result = service.save(entity);
		    if (!result.isSuccess()) {
		    	return new ResponseData(false, result.getErrormsg());
	        }
	    }catch(DuplicateKeyException e){
	        	Result result = new Result();
	            if (e.getRootCause().getMessage().toUpperCase().contains("PRIMARY")){
	                result.addErrormsg("主键  重复");
	            }
	            return new ResponseData(false, result.getErrormsg());
        } catch(Exception e)
        {
            return new ResponseData(false, e.getMessage());
        }
        return ResponseData.SUCCESS_NO_DATA;
	} 
	
	@RequestMapping(value = "/detail.html")
    public String detail(Long id, HttpServletRequest request, HttpServletResponse response) {
    	QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        request.setAttribute("entity", service.findOne(filter).getReturnObj());
        return "/qqs/info-details";
    }

	@RequestMapping(value = "/edit.html")
    public String edit(Long id, HttpServletRequest request, HttpServletResponse response) {
    	QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        request.setAttribute("entity", service.findOne(filter).getReturnObj());
        return "/QqsArticle/edit";
    }
    
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
	public ResponseData update(ModelMap model, @ModelAttribute("entity") QqsArticle entity, 
						HttpServletRequest request, HttpServletResponse response) {
        try{
			Map<String,Object> errors = FormValidatorManager.validate("saveQqsArticleConfig", request);
	        if (errors.size() != 0)
	        {
	            return new ResponseData(false, errors.keySet().toString());
	        }
		    Result result = service.update(entity);
		    if (!result.isSuccess()) {
		    	return new ResponseData(false, result.getErrormsg());
	        }
        }catch(DuplicateKeyException e){
        	Result result = new Result();
            if (e.getRootCause().getMessage().toUpperCase().contains("PRIMARY")){
                result.addErrormsg("主键  重复");
            }
            return new ResponseData(false, result.getErrormsg());
        } catch(Exception e)
        {
            return new ResponseData(false, e.getMessage());
        }
	    return ResponseData.SUCCESS_NO_DATA;
	}
	
	@RequestMapping(value="/delete.json")
	@ResponseBody
    public ResponseData delete(Long id, HttpServletRequest request, HttpServletResponse response)
    {
    	try{
	    	if (id != null)
	      	{
	        	service.delete(id);
	      	}
      	}catch(Exception e){
            return new ResponseData(false, e.getMessage());
        }
      	
      	return ResponseData.SUCCESS_NO_DATA;
    }
	
	//首页的全球安简介
	@RequestMapping(value = "/indexIntroduction.json")
	@ResponseBody
	public ResponseData indexIntroduction(HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter(request);
        filter = new QueryFilter();
        filter.put("title", "全球安简介");
        filter.put("type",5);
        QqsArticle qqsArticle= service.findOne(filter).getReturnObj();
        ResponseData data = new ResponseData(true);
        data.setObj(qqsArticle);
		return data;
	}
	
	
}
