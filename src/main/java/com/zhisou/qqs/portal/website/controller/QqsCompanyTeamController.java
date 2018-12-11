package com.zhisou.qqs.portal.website.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhisou.qqs.website.service.QqsBannerService;
import com.zhisou.qqs.website.service.QqsCompanyTeamService;
import com.zhisou.qqs.website.model.QqsBanner;
import com.zhisou.qqs.website.model.QqsCompanyTeam;
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
 * 公司团队表 Controller
 * @author JT
 * @since  2017-05-18
 */
@Controller
@RequestMapping("/QqsCompanyTeam")
public class QqsCompanyTeamController extends BaseController{

	@Autowired
	private QqsCompanyTeamService service;

	@Autowired
	private QqsBannerService qqsBannerService;
	
	@RequestMapping(value = "/index.html")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter(request);
        model.put("pager", service.findPager(filter).getReturnObj());
        
        //Banner
		QueryFilter qf = new QueryFilter();
		qf.put("page", "introduction.html");
        QqsBanner banner = qqsBannerService.findOne(qf).getReturnObj();
        model.put("banner", banner);
		return "/qqs/teamIntroduction";
	}
	
	@RequestMapping(value = "/add.html")
	public String add(HttpServletRequest request, HttpServletResponse response) {
		return "/QqsCompanyTeam/add";
	}
	
	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData save(ModelMap model, @ModelAttribute("entity") QqsCompanyTeam entity, 
						HttpServletRequest request, HttpServletResponse response) {
        try{
			Map<String,Object> errors = FormValidatorManager.validate("saveQqsCompanyTeamConfig", request);
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
	                result.addErrormsg("编号  重复");
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
        return "/QqsCompanyTeam/detail";
    }

	@RequestMapping(value = "/edit.html")
    public String edit(Long id, HttpServletRequest request, HttpServletResponse response) {
    	QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        request.setAttribute("entity", service.findOne(filter).getReturnObj());
        return "/QqsCompanyTeam/edit";
    }
    
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
	public ResponseData update(ModelMap model, @ModelAttribute("entity") QqsCompanyTeam entity, 
						HttpServletRequest request, HttpServletResponse response) {
        try{
			Map<String,Object> errors = FormValidatorManager.validate("saveQqsCompanyTeamConfig", request);
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
                result.addErrormsg("编号  重复");
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
