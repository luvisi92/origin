package com.zhisou.qqs.portal.website.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.likegene.framework.core.QueryFilter;
import com.zhisou.qqs.website.model.QqsBanner;
import com.zhisou.qqs.website.model.QqsCompanyTeam;
import com.zhisou.qqs.website.model.QqsMonthProcess;
import com.zhisou.qqs.website.model.QqsOrg;
import com.zhisou.qqs.website.model.QqsProductCategory;
import com.zhisou.qqs.website.model.QqsSolutionCategory;
import com.zhisou.qqs.website.model.QqsYearProcess;
import com.zhisou.qqs.website.service.QqsBannerService;
import com.zhisou.qqs.website.service.QqsCompanyTeamService;
import com.zhisou.qqs.website.service.QqsMonthProcessService;
import com.zhisou.qqs.website.service.QqsOrgService;
import com.zhisou.qqs.website.service.QqsProductCategoryService;
import com.zhisou.qqs.website.service.QqsRecruitService;
import com.zhisou.qqs.website.service.QqsShopService;
import com.zhisou.qqs.website.service.QqsSolutionCategoryService;
import com.zhisou.qqs.website.service.QqsYearProcessService;


/**
 * 关于我们Controller
 * 
 * @author JT
 * @since 2017-05-17
 */
@Controller
@RequestMapping("/AboutUs")
public class QqsAboutUsController extends BaseController {

    @Autowired
    private QqsShopService service;
    
    @Autowired
    private QqsYearProcessService yearService;
    
    @Autowired
    private QqsMonthProcessService monthService;
    
    @Autowired
    private QqsRecruitService qqsRecruitService;
    
    @Autowired
    private QqsCompanyTeamService companyTeamservice;
    
    @Autowired
    private QqsOrgService qqsOrgService;

    @Autowired
    private QqsBannerService qqsBannerService;
    
    @Autowired
    private QqsSolutionCategoryService qqsSolutionCategoryService;
    
    @Autowired
    private QqsProductCategoryService qqsProductCategoryService;
    /**
     * 关于我们页面跳转
     * @param model
     * @param request
     * @param response
     * @return
     * @2018-11-7上午9:59:23
     * @JT
     */
    @RequestMapping(value = "/aboutUs.html")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	  //banner
        QueryFilter qf = new QueryFilter();
		qf.put("page", "aboutUs.html");
        QqsBanner banner = qqsBannerService.findOne(qf).getReturnObj();
        model.put("banner", banner);
        return "/qqs/aboutUs";
    }
    
    /**
     * 发展历程页面跳转
     * @param model
     * @param request
     * @param response
     * @return
     * @2018-11-7上午9:59:23
     * @JT
     */
    @RequestMapping(value = "/developmentHistory.html")
    public String developmentHistory(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter(request);
        filter.put("isShow", 1);
        List<QqsYearProcess> yearList = yearService.findList(filter).getReturnObj();
        model.put("yearList", yearList);
        Map<String, Object> map = new HashMap<String,Object>();
        for (QqsYearProcess qqsYearProcess : yearList) {
            filter = new QueryFilter();
            filter.put("yearId",qqsYearProcess.getYear());
            filter.put("isShow", 1);
            List<QqsMonthProcess> monthList = monthService.findList(filter).getReturnObj();
            map.put(qqsYearProcess.getYear(), monthList);
            model.put("map", map);
        }
        //banner
        QueryFilter qf = new QueryFilter();
		qf.put("page", "history.html");
        QqsBanner banner = qqsBannerService.findOne(qf).getReturnObj();
        model.put("banner", banner);
        
    	return "/qqs/developmentHistory";
    }
    
    /**
     * 团队介绍页面跳转
     * @param model
     * @param request
     * @param response
     * @return
     * @2018-11-7上午9:59:23
     * @JT
     */
    @RequestMapping(value = "/teamIntroduction.html")
    public String teamIntroduction(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	
    	 //手机端和电脑端数据封装	
    	 QueryFilter filter = new QueryFilter();
    	 Map<String,Object> map = new HashMap<String,Object>();
    	 Map<String,Object> mapMobile = new HashMap<String,Object>();
    	 //查询所有机构
    	 List<QqsOrg> orgList = qqsOrgService.findList(filter).getReturnObj();
    	 if(null!=orgList&&orgList.size()>0){
        	 for(QqsOrg org:orgList){
        		 //查询当前机构下的所有成员
        		 filter.put("orgId", org.getId());
        		 List<QqsCompanyTeam> teamList = companyTeamservice.findList(filter).getReturnObj();
        		 List<List<QqsCompanyTeam>> teamList1 = new ArrayList<List<QqsCompanyTeam>>();
        		 if(null!=teamList&&teamList.size()>0){
        			 for(int i=0;i<teamList.size();i+=2){
        				 List<QqsCompanyTeam> teamList2 = new ArrayList<QqsCompanyTeam>();
        				 teamList2.add(teamList.get(i));
        				 if(i<teamList.size()-1){
        					 teamList2.add(teamList.get(i+1));
        				 }
        				 teamList1.add(teamList2);
        			 }
        		 }
        		 map.put(org.getId().toString(), teamList1);
        		 mapMobile.put(org.getId().toString(), teamList);
        	 }
    	 }
    	 //banner
         QueryFilter qf = new QueryFilter();
 		qf.put("page", "introduction.html");
         QqsBanner banner = qqsBannerService.findOne(qf).getReturnObj();
         model.put("banner", banner);
		 //查询无机构的成员
    	 filter.clear();
		 filter.put("noOrg", "noOrg");
		 List<QqsCompanyTeam> noOrgTeamList = companyTeamservice.findList(filter).getReturnObj();
		 List<List<QqsCompanyTeam>> noOrgTeamList1 = new ArrayList<List<QqsCompanyTeam>>();
		 if(null!=noOrgTeamList&&noOrgTeamList.size()>0){
			 for(int i=0;i<noOrgTeamList.size();i+=2){
				 List<QqsCompanyTeam> noOrgTeamList2 = new ArrayList<QqsCompanyTeam>();
				 noOrgTeamList2.add(noOrgTeamList.get(i));
				 if(i<noOrgTeamList.size()-1){
					 noOrgTeamList2.add(noOrgTeamList.get(i+1));
				 }
				 noOrgTeamList1.add(noOrgTeamList2);
			 }
		 }

		 model.put("noOrgTeamList1", noOrgTeamList1);
		 model.put("noOrgTeamList", noOrgTeamList);
         model.put("map", map);       
         model.put("mapMobile", mapMobile);       
         model.put("orgList", orgList);
         
         
         //手机端数据封装

     	return "/qqs/teamIntroduction";
    }
    
    
    /**
     * 法律声明页面跳转
     * @param model
     * @param request
     * @param response
     * @return
     * @2018-11-7上午9:59:23
     * @JT
     */
    @RequestMapping(value = "/legalDeclaration.html")
    public String  legalDeclaration(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	
    	return "/qqs/legalDeclaration";
    }
    
    /**
     * 网站地图页面跳转
     * @param model
     * @param request
     * @param response
     * @return
     * @2018-11-7上午9:59:23
     * @JT
     */
    @RequestMapping(value = "/webSiteMap.html")
    public String webSiteMap(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	//查询解决方案类型
		QueryFilter filter = new QueryFilter();
		List<QqsSolutionCategory> categoryList = qqsSolutionCategoryService.findList(filter).getReturnObj();
		model.put("categoryList", categoryList);	
		
		//查询硬件分类
		filter.clear();
		filter.put("disable", QqsProductCategory.DISABLE_TRUE);
		filter.put("tyep", QqsProductCategory.TYPE_HARDWARE);
        List<QqsProductCategory> pCategoryList = qqsProductCategoryService.findList(filter).getReturnObj();
        model.put("pCategoryList", pCategoryList);	
        
		//查询软件分类
        filter.put("tyep", QqsProductCategory.TYPE_SOFT);
        List<QqsProductCategory> sCategoryList = qqsProductCategoryService.findList(filter).getReturnObj();
        model.put("sCategoryList", sCategoryList);	
        
    	return "/qqs/webSiteMap";
    }

    /**
     * 招聘中心页面跳转
     * @param model
     * @param request
     * @param response
     * @return
     * @2018-11-7上午9:59:23
     * @JT
     */
    @RequestMapping(value = "/join.html")
    public String join(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	
        QueryFilter filter = new QueryFilter(request);
        filter.setLimit(9);
        model.put("pager", qqsRecruitService.findPager(filter).getReturnObj());  
        //banner
        QueryFilter qf = new QueryFilter();
		qf.put("page", "join.html");
        QqsBanner banner = qqsBannerService.findOne(qf).getReturnObj();
        model.put("banner", banner);
    	return "/qqs/join";
    }    

}
