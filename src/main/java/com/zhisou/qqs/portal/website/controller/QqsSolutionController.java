package com.zhisou.qqs.portal.website.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zhisou.qqs.portal.website.controller.BaseController;
import com.zhisou.qqs.website.service.QqsBannerService;
import com.zhisou.qqs.website.service.QqsSolutionCategoryService;
import com.zhisou.qqs.website.service.QqsSolutionService;
import com.zhisou.qqs.website.model.QqsBanner;
import com.zhisou.qqs.website.model.QqsSolution;
import com.zhisou.qqs.website.model.QqsSolutionCategory;
import com.likegene.framework.core.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 解决方案表 Controller
 * @author JT
 * @since  2017-05-17
 */
@Controller
@RequestMapping("/QqsSolution")
public class QqsSolutionController extends BaseController{
	
	@Autowired
	private QqsSolutionService service;
	@Autowired
	private QqsSolutionCategoryService qqsSolutionCategoryService;
	@Autowired
	private QqsBannerService qqsBannerService;

	@RequestMapping(value = "/index.html")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
			String type = request.getParameter("type");
			if(null==type||"".equals(type)){
				type = "1";
			}
			//查询方案分类
			QueryFilter filter = new QueryFilter();
			List<QqsSolutionCategory> categoryList = qqsSolutionCategoryService.findList(filter).getReturnObj();
			model.put("categoryList", categoryList);	
			
			//查询当前请求分类下的解决方案
			filter.clear();
			filter.put("categoryId", type);
			filter.setStatementKey("selectAllbyCondition");
			List<Map<String,Object>> solutionList = service.findList(filter).getReturnObj();
			model.put("solutionList", solutionList);
			model.put("type", type);
			
			//Banner
			QueryFilter qf = new QueryFilter();
			qf.put("page", "solution.html");
	        QqsBanner banner = qqsBannerService.findOne(qf).getReturnObj();
	        model.put("banner", banner);
        	return "/qqs/solution";
	}
	
	/**
	 * 查询解决方案
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-7上午11:36:26
	 * @JT
	 */
	@RequestMapping(value = "/getSolution.json")
	@ResponseBody
	public ResponseData getSolution(HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter(request);
        filter.put("isShow", 1);
        filter.put("order", "order_no");
        List<QqsSolution> solutionList = service.findList(filter).getReturnObj(); 
        ResponseData data = new ResponseData(true);
        data.setObj(solutionList);
		return data;
	}
	
	/**
	 * 解决方案详情
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-7上午11:39:12
	 * @JT
	 */
	@RequestMapping(value = "/detail.html")
	public String detail(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
			String id = request.getParameter("id");
			QueryFilter filter = new QueryFilter();
			if(null!=id&&!"".equals(id)){
				filter.put("id", id);
				filter.setStatementKey("selectAllbyCondition");
				Map<String,Object> solution = service.findOne(filter).getReturnObj();
				model.put("solution", solution);
				
				//查询相同类别解决方案
				filter.clear();
				filter.put("categoryId", solution.get("categoryId"));
				filter.put("exceptId", solution.get("id"));
				List<QqsSolution> solutionList = service.findList(filter).getReturnObj();
				model.put("solutionList", solutionList);
			}   	
        	return "/qqs/solutionDetail";
	}	
	
	
	/**
	 * 查询解决方案类别
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-7上午11:36:26
	 * @JT
	 */
	@RequestMapping(value = "/getSolutionCategory.json")
	@ResponseBody
	public ResponseData getSolutionCategory(HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter();
        filter.put("isShow", 1);
        filter.put("order", "order_no");
        List<QqsSolutionCategory> categoryList = qqsSolutionCategoryService.findList(filter).getReturnObj(); 
        ResponseData data = new ResponseData(true);
        data.setObj(categoryList);
		return data;
	}
	
}
