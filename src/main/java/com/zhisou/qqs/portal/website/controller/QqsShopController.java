package com.zhisou.qqs.portal.website.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.likegene.framework.core.QueryFilter;
import com.zhisou.qqs.website.model.QqsBanner;
import com.zhisou.qqs.website.model.QqsRegions;
import com.zhisou.qqs.website.model.QqsShop;
import com.zhisou.qqs.website.service.QqsBannerService;
import com.zhisou.qqs.website.service.QqsRegionsService;
import com.zhisou.qqs.website.service.QqsServiceTypeService;
import com.zhisou.qqs.website.service.QqsShopService;

/**
 * qqs_shop Controller
 * 
 * @author JT
 * @since 2017-05-17
 */
@Controller
@RequestMapping("/shop")
public class QqsShopController extends BaseController {

    @Autowired
    private QqsShopService qqsShopService;
    @Autowired
    private QqsRegionsService qqsRegionsService;
    @Autowired
    private QqsServiceTypeService qqsServiceTypeService;
    @Autowired
    private QqsBannerService qqsBannerService;
    /**
     * 服务机构主页跳转
     * @param model
     * @param request
     * @param response
     * @return
     * @2018-11-7上午9:59:23
     * @JT
     */
    @RequestMapping(value = "/showShops.html")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter(request);
        model.put("pager", qqsShopService.findPager(filter).getReturnObj());
        filter.setStatementKey(QqsRegionsService.SELECT_PROVINCE);
        List<QqsRegions>list = qqsShopService.findList(filter).getReturnObj();
        model.put("list", list);
        //查询推荐公司
        filter = new QueryFilter();
        filter.put("dispaly", 1);
        filter.setStatementKey(QqsShopService.SELECT_CITY_COMPANY);
        List<QqsShop>recommendList = qqsShopService.findList(filter).getReturnObj();
        model.put("recommendList", recommendList);
        //banner
        QueryFilter qf = new QueryFilter();
		qf.put("page", "showShops.html");
        QqsBanner banner = qqsBannerService.findOne(qf).getReturnObj();
        model.put("banner", banner);
        return "/qqs/showShops";
    }
    
    @RequestMapping(value="/getCity.json")
    @ResponseBody
    //获取省code查询省下所有市
    public ResponseData getCity(HttpServletRequest request , HttpServletResponse response){
    	ResponseData data = new ResponseData(true);
    	QueryFilter filter = new QueryFilter();
    	String code = request.getParameter("code");
    	filter.put("code", code);
    	filter.setStatementKey(QqsRegionsService.SELECT_CODE_CITY);
    	List<QqsRegions>list = qqsRegionsService.findList(filter).getReturnObj();
    	List<QqsRegions>cityList = new ArrayList<QqsRegions>();
    	for(QqsRegions cityStr:list){
    		if(!"县".equals(cityStr.getLocalName())){
    			cityList.add(cityStr);
    		}
    	}
    	data.setObj(cityList);
		return data;
    }
    
    @RequestMapping(value="/selectCity.json")
    @ResponseBody
    //获取省名称查询省下所有市
    public ResponseData selectCity(String province){
    	ResponseData data = new ResponseData(true);
    	QueryFilter filter = new QueryFilter();
    	filter.put("localName", province);
    	filter.setStatementKey(QqsRegionsService.SELECT_CITY_NAMES);
    	List<QqsRegions>list = qqsRegionsService.findList(filter).getReturnObj();
    	List<QqsRegions>cityList = new ArrayList<QqsRegions>();
    	for(QqsRegions cityStr:list){
    		if(!"县".equals(cityStr.getLocalName())){
    			cityList.add(cityStr);
    		}
    	}
    	data.setObj(cityList);
		return data;
    }
    
    
    
    
    
    //获取市code查询城市公司
    @RequestMapping(value="/getCityCompany.json")
    @ResponseBody
    public ResponseData getCityCompany(HttpServletRequest request , HttpServletResponse response){
    	ResponseData data = new ResponseData(true);
    	QueryFilter filter = new QueryFilter();
    	String code = request.getParameter("code");
    	filter.put("city", code);
    	filter.setStatementKey(QqsShopService.SELECT_CITY_COMPANY);
    	List<QqsShop>shopList = qqsShopService.findList(filter).getReturnObj();
    	data.setObj(shopList);
    	return data;
    }
    
    
    //根据id查询公司经纬度
    @RequestMapping(value="/getLatCity.json")
    @ResponseBody
    public ResponseData getLatCity(Integer id){
    	ResponseData data = new ResponseData(true);
    	QueryFilter filter = new QueryFilter();
    	filter.put("id", id);
    	filter.setStatementKey(QqsShopService.SELECT_CITY_COMPANY);
        List<QqsShop>latCityList = qqsShopService.findList(filter).getReturnObj();
        data.setObj(latCityList);
    	return data;
    }
    
    
    
    @RequestMapping(value = "/shopDetail.html")
    public String detail(ModelMap model, @ModelAttribute("id")Integer id) {
        QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        filter.setStatementKey(QqsShopService.SELECT_CITY_COMPANY);
        List<QqsShop>shopsList = qqsShopService.findList(filter).getReturnObj();
        model.put("shopsList", shopsList);
        
        filter = new QueryFilter();
        filter.put("id", id);
        filter.setStatementKey("selectSupportId");
        model.put("supportList", qqsServiceTypeService.findList(filter).getReturnObj());
        return "/qqs/shopDetail";
    }
    
    @RequestMapping(value="/locationMap.json")
    @ResponseBody
    public ResponseData getLocationMap(String city){
    	ResponseData data = new ResponseData(true);
    	QueryFilter filter = new QueryFilter();
    	filter.put("localName", city);
    	filter.setStatementKey(QqsShopService.SELECT_LOCATION_CITY_SHOP);
        List<QqsShop>cityShopList = qqsShopService.findList(filter).getReturnObj();
        data.setObj(cityShopList);
		return data;
    	
    }

}
