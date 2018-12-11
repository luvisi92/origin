package com.zhisou.qqs.portal.website.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zhisou.qqs.website.service.QqsKeywordService;
import com.zhisou.qqs.website.service.QqsNewsService;
import com.zhisou.qqs.website.service.QqsProductService;
import com.zhisou.qqs.website.service.QqsShopService;
import com.zhisou.qqs.website.service.QqsSolutionService;
import com.zhisou.qqs.website.model.QqsKeyword;
import com.zhisou.qqs.website.model.QqsNews;
import com.likegene.framework.core.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 关键词表 Controller
 * @author JT
 * @since  2018-11-05
 */
@Controller
@RequestMapping("/QqsKeyword")
public class QqsKeywordController extends BaseController{
	
	@Autowired
	private QqsKeywordService service;
	@Autowired
	private QqsSolutionService qqsSolutionService;
	@Autowired
	private QqsProductService qqsProductService;
	@Autowired
	private QqsNewsService qqsNewsService;
	@Autowired
	private QqsShopService qqsShopService;

    /**
     * 请求关键词关联数据
     * @param model
     * @param entity
     * @param request
     * @param response
     * @return
     * @2018-11-5下午7:12:19
     * @JT
     */
    @RequestMapping(value = "/getData.json", method = RequestMethod.POST)
    @ResponseBody
	public ResponseData getData(HttpServletRequest request, HttpServletResponse response) {
    	
    	ResponseData data = new ResponseData(true);
    	//关键词ID
    	String id = request.getParameter("id");
    	
        QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        QqsKeyword qqsKeyword = service.findOne(filter).getReturnObj();
        
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        switch(qqsKeyword.getModule()){
	        case 0:
	        	//查询方案
	        	filter.put("ids", qqsKeyword.getIds());
	        	filter.setStatementKey("selectSolutionForKeyword");
	        	list = qqsSolutionService.findList(filter).getReturnObj();
	        	break;
	        case 1:
	        	//查询硬件产品
	        	filter.put("ids", qqsKeyword.getIds());
	        	filter.setStatementKey("selectProductForKeyword");
	        	list = qqsProductService.findList(filter).getReturnObj();
	        	break;
	        case 2:
	        	//查询机构
	        	filter.put("ids", qqsKeyword.getIds());
	        	filter.setStatementKey("selectShopForKeyword");
	        	list = qqsShopService.findList(filter).getReturnObj();
	        	break;
	        case 3:
	        	//查询新闻
	        	filter.put("ids", qqsKeyword.getIds());
	        	filter.setStatementKey("selectNewsForKeyword");
	        	list = qqsNewsService.findList(filter).getReturnObj();
	        	break;
	        default: break;
        }
        
        data.setObj(list);
	    return data;
	}
    
    
	/**首页搜索界面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-6下午8:29:56
	 * @JT
	 */
	@RequestMapping(value = "/searchResult.html")
	public String searchResult(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//搜索关键字
		String searchInput = request.getParameter("serachInput");
		if(searchInput!=null&&!"".equals(searchInput)){
			//搜索硬件产品
			model.put("productPager", "");
			//搜索软件产品
			model.put("softProductPager", "");
			//搜索服务机构
			model.put("shopPager", "");
		}

		return "/qqs/searchResult";
	} 
}
