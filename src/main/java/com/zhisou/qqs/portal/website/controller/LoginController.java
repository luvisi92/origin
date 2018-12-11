package com.zhisou.qqs.portal.website.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.likegene.framework.core.QueryFilter;
import com.zhisou.qqs.website.model.QqsBanner;
import com.zhisou.qqs.website.model.QqsKeyword;
import com.zhisou.qqs.website.model.QqsNews;
import com.zhisou.qqs.website.model.QqsProduct;
import com.zhisou.qqs.website.model.QqsShop;
import com.zhisou.qqs.website.model.QqsSolutionCategory;
import com.zhisou.qqs.website.model.SysConfig;
import com.zhisou.qqs.website.service.QqsBannerService;
import com.zhisou.qqs.website.service.QqsKeywordService;
import com.zhisou.qqs.website.service.QqsNewsService;
import com.zhisou.qqs.website.service.QqsProductService;
import com.zhisou.qqs.website.service.QqsShopService;
import com.zhisou.qqs.website.service.QqsSolutionCategoryService;

/**
 * @author JT
 * @version 1.0
 * @since 2017-07-20
 */
@Controller
public class LoginController extends BaseController {	
	
	@Autowired
	private QqsKeywordService qqsKeywordService;
	@Autowired
	private QqsSolutionCategoryService qqsSolutionCategoryService;
	@Autowired
	private QqsNewsService qqsNewsService;
	@Autowired
	private QqsProductService qqsProductService;
	@Autowired
	private QqsShopService qqsShopService;
	@Autowired
	private QqsBannerService qqsBannerService;
	
	/**
	 * 跳转首页
	 * @author JT
	 */
	@RequestMapping(value = "/home.html")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//关键字初始化数据查询
		QueryFilter qfKey = new QueryFilter();
		qfKey.put("module", QqsKeyword.MODULE_SOLUTION);
		List<QqsKeyword> solutionKeyList  = qqsKeywordService.findList(qfKey).getReturnObj();
		qfKey.put("module", QqsKeyword.MODULE_NEWS);
		List<QqsKeyword> newsKeyList  = qqsKeywordService.findList(qfKey).getReturnObj();
		qfKey.put("module", QqsKeyword.MODULE_SHOP);
		List<QqsKeyword> shopKeyList  = qqsKeywordService.findList(qfKey).getReturnObj();
		qfKey.put("module", QqsKeyword.MODULE_PRODUCT);
		List<QqsKeyword> productKeyList  = qqsKeywordService.findList(qfKey).getReturnObj();
		model.put("solutionKeyList", solutionKeyList);		
		model.put("newsKeyList", newsKeyList);		
		model.put("shopKeyList", shopKeyList);		
		model.put("productKeyList", productKeyList);
		
		//解决方案初始数据
		QueryFilter qfSolution = new QueryFilter();
		qfSolution.put("isShow", SysConfig.SHOW_YES);
		qfSolution.put("order", " order_no desc");
		qfSolution.setStart(0);
		qfSolution.setLimit(5);
		List<QqsSolutionCategory> qqsSolutionCategoryList = qqsSolutionCategoryService.findList(qfSolution).getReturnObj();
		//产品中心初始数据
		QueryFilter qfProduct = new QueryFilter();
		qfProduct.setStart(0);
		qfProduct.setLimit(5);
		List<QqsProduct> productList = qqsProductService.findList(qfProduct).getReturnObj();
		//服务机构初始数据
		QueryFilter qfShop = new QueryFilter();
		qfShop.put("publishStatus", QqsProduct.STATUS_PUBLISH);
		qfShop.setStart(0);
		qfShop.setLimit(6);
		List<QqsShop> shopList = qqsShopService.findList(qfShop).getReturnObj();
		//新闻中心初始数据
		QueryFilter qfNews = new QueryFilter();
		qfNews.put("isShow", SysConfig.SHOW_YES);
		qfNews.put("order", " publish_time desc");
		qfNews.put("type", QqsNews.TYPE_NEWS);
		qfNews.setStart(0);
		qfNews.setLimit(5);
		List<QqsNews> newsList = qqsNewsService.findList(qfNews).getReturnObj();
		
		//首页banner图
		QueryFilter qfBanner = new QueryFilter();
		qfBanner.put("page", "home.html");
		List<QqsBanner> bannerList = qqsBannerService.findList(qfBanner).getReturnObj();
		
		model.put("bannerList", bannerList);
		model.put("qqsSolutionCategoryList", qqsSolutionCategoryList);
		model.put("productList", productList);
		model.put("shopList", shopList);
		model.put("newsList", newsList);
		
		return "qqs/index";
	}

	/**
	 * 获取关键词对应方案
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-9上午11:43:55
	 * @JT
	 */
	@RequestMapping(value="/index/getSolutionData.json")
	@ResponseBody
	public ResponseData getSolutionData(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseData data = new ResponseData(true);	
		try{
			//查询关键词
			QueryFilter filter = new QueryFilter(request);
			QqsKeyword keyword = qqsKeywordService.findOne(filter).getReturnObj();
			//查询关联数据
			QueryFilter qfSolution = new QueryFilter();
			qfSolution.put("isShow", SysConfig.SHOW_YES);
			qfSolution.put("ids", keyword.getIds());
			qfSolution.put("order", " order_no desc");
			List<QqsSolutionCategory> qqsSolutionCategoryList = qqsSolutionCategoryService.findList(qfSolution).getReturnObj();
			data.setObj(qqsSolutionCategoryList);
		}catch(Exception e){
			return new ResponseData(false, e.getMessage());
		}
		
		return data;
	}
	
	/**
	 * 获取关键词对应新闻
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-9上午11:43:55
	 * @JT
	 */
	@RequestMapping(value="/index/getNewsData.json")
	@ResponseBody
	public ResponseData getNewsData(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseData data = new ResponseData(true);	
		try{
			//查询关键词
			QueryFilter filter = new QueryFilter(request);
			QqsKeyword keyword = qqsKeywordService.findOne(filter).getReturnObj();
			//查询关联数据
			QueryFilter qfNews = new QueryFilter();
			qfNews.put("isShow", SysConfig.SHOW_YES);
			qfNews.put("order", " publish_time desc");
			qfNews.put("ids", keyword.getIds());
			qfNews.put("type", QqsNews.TYPE_NEWS);
			List<QqsNews> newsList = qqsNewsService.findList(qfNews).getReturnObj();
			data.setObj(newsList);
		}catch(Exception e){
			return new ResponseData(false, e.getMessage());
		}
		
		return data;
	}
	/**
	 * 获取关键词对应产品
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-9上午11:43:55
	 * @JT
	 */
	@RequestMapping(value="/index/getProductData.json")
	@ResponseBody
	public ResponseData getProductData(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseData data = new ResponseData(true);	
		try{
			//查询关键词
			QueryFilter filter = new QueryFilter(request);
			QqsKeyword keyword = qqsKeywordService.findOne(filter).getReturnObj();
			//查询关联数据
			QueryFilter qfProduct = new QueryFilter();
			qfProduct.put("ids", keyword.getIds());
			List<QqsProduct> productList = qqsProductService.findList(qfProduct).getReturnObj();
			data.setObj(productList);
		}catch(Exception e){
			return new ResponseData(false, e.getMessage());
		}
		
		return data;
	}
	/**
	 * 获取关键词对应店铺
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-9上午11:43:55
	 * @JT
	 */
	@RequestMapping(value="/index/getShopData.json")
	@ResponseBody
	public ResponseData getShopData(HttpServletRequest request, HttpServletResponse response)
	{
		ResponseData data = new ResponseData(true);	
		try{
			//查询关键词
			QueryFilter filter = new QueryFilter(request);
			QqsKeyword keyword = qqsKeywordService.findOne(filter).getReturnObj();
			//查询关联数据
			QueryFilter qfShop = new QueryFilter();
			qfShop.put("publishStatus", QqsProduct.STATUS_PUBLISH);
			qfShop.put("ids", keyword.getIds());
			List<QqsShop> shopList = qqsShopService.findList(qfShop).getReturnObj();
			data.setObj(shopList);
		}catch(Exception e){
			return new ResponseData(false, e.getMessage());
		}
		
		return data;
	}

	
}
