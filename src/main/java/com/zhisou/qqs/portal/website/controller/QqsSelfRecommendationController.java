package com.zhisou.qqs.portal.website.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zhisou.qqs.website.service.QqsSelfRecommendationService;
import com.zhisou.qqs.website.model.QqsSelfRecommendation;
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
 * 供应商自荐表 Controller
 * @author JT
 * @since  2018-11-05
 */
@Controller
@RequestMapping("/QqsSelfRecommendation")
public class QqsSelfRecommendationController extends BaseController{
	
	@Autowired
	private QqsSelfRecommendationService service;

	@RequestMapping(value = "/qqsSelfRecommendation.html")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
/*        QueryFilter filter = new QueryFilter(request);
        model.put("pager", service.findPager(filter).getReturnObj());*/
		return "/qqs/selfRecommendation";
	}
	
	@RequestMapping(value = "/add.html")
	public String add(HttpServletRequest request, HttpServletResponse response) {
		return "/qqs/QqsSelfRecommendation/add";
	}
	
	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData save(ModelMap model, @ModelAttribute("entity") QqsSelfRecommendation entity, 
						HttpServletRequest request, HttpServletResponse response) {
        try{
			Map<String,Object> errors = FormValidatorManager.validate("saveQqsSelfRecommendationConfig", request);
			 if (errors.size() != 0)
		        {
		            return new ResponseData(false, errors.keySet().toString());
		        }
		  	 //从session取回验证码
		      HttpSession session = request.getSession();
		      String validateCode =  (String)session.getAttribute("validateCode");
		      String code=request.getParameter("code");
		      //验证码判断输入是否正确
		      if(code.equalsIgnoreCase(validateCode)){
		      	
		      	  Date date = new Date();
		          entity.setCreateTime(date);
		  	    Result result = service.save(entity);
		  	    if (!result.isSuccess()) {
		  	    	return new ResponseData(false, result.getErrormsg());
		          }
		      }else{
		      	return new ResponseData(false, "验证码不正确");
		      }
  
	    }catch(DuplicateKeyException e){
	        	Result result = new Result();
	            if (e.getRootCause().getMessage().toUpperCase().contains("PRIMARY")){
	                result.addErrormsg("id  重复");
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
        return "/QqsSelfRecommendation/detail";
    }

	@RequestMapping(value = "/edit.html")
    public String edit(Long id, HttpServletRequest request, HttpServletResponse response) {
    	QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        request.setAttribute("entity", service.findOne(filter).getReturnObj());
        return "/QqsSelfRecommendation/edit";
    }
    
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
	public ResponseData update(ModelMap model, @ModelAttribute("entity") QqsSelfRecommendation entity, 
						HttpServletRequest request, HttpServletResponse response) {
        try{
			Map<String,Object> errors = FormValidatorManager.validate("saveQqsSelfRecommendationConfig", request);
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
                result.addErrormsg("id  重复");
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
}
