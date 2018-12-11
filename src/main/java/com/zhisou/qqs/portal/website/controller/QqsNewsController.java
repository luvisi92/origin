package com.zhisou.qqs.portal.website.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zhisou.qqs.portal.website.controller.BaseController;
import com.zhisou.qqs.website.service.QqsArticleService;
import com.zhisou.qqs.website.service.QqsNewsService;
import com.zhisou.qqs.website.service.SysFileService;
import com.zhisou.qqs.website.model.QqsNews;
import com.likegene.framework.core.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 新闻中心 Controller
 * @author JT
 * @since  2018-11-07
 */
@Controller
@RequestMapping("/QqsNews")
public class QqsNewsController extends BaseController{
	
	@Autowired
	private QqsNewsService service;
	
	@Autowired
	private QqsArticleService qqsArticleService;
	
	@Autowired
	private SysFileService sysFileService;

	/**
	 * 新闻中心
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-8下午3:26:08
	 * @JT
	 */
	@RequestMapping(value = "/news.html")
	public String news(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String type = request.getParameter("type");
		if(null==type||"".equals(type)){
			type="1";
		}
		//查询新闻
        QueryFilter filter = new QueryFilter(request);
        filter.setStatementKey("selectAllbyCondition");
        filter.put("type", type);
        filter.put("isShow", 1);
        filter.put("order", " publish_time desc ");
        filter.setLimit(9);
        model.put("pager", service.findPager(filter).getReturnObj());
        if(type.equals("1")){
            //查询推荐轮播
            QueryFilter qf = new QueryFilter();
            qf.setStatementKey("selectAllbyCondition");
            qf.put("type", request.getParameter("type"));
            qf.put("isShow", 1);
            qf.put("order", " publish_time desc ");
            qf.setLimit(6);     
            qf.put("carousel", QqsNews.CAROUSEL_YES);
            model.put("carouselList", service.findList(qf).getReturnObj());
        }

        
        model.put("type", type);
        
		return "/qqs/news";
	}	
	
	
	/**
	 * 新闻详情
	 * @param newsId
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-8下午3:38:20
	 * @JT
	 */
	@RequestMapping(value = "/detail.html")
    public String detail(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//新闻详情
		String newsId = request.getParameter("id");
    	QueryFilter filter = new QueryFilter();
    	filter.put("newsId", newsId);
    	//更新当前新闻阅读量
    	QqsNews qqsNews = service.findOne(filter).getReturnObj();
    	qqsNews.setReading(qqsNews.getReading()+1);
    	service.update(qqsNews);
    	//获取当前新闻上一条和下一条
    	filter.setStatementKey("selectPreviousNewsById");
    	QqsNews previousNews = service.findOne(filter).getReturnObj();
    	model.put("previousNews", previousNews);
    	filter.setStatementKey("selectNextNewsById");
    	QqsNews nextNews = service.findOne(filter).getReturnObj();
    	model.put("nextNews", nextNews);
    	//查询新闻详情
    	filter.setStatementKey("selectAllbyCondition");
        model.put("entity", service.findOne(filter).getReturnObj());
        
    	//查询相同类别新闻
        filter.clear();
        filter.put("exceptId", newsId);
        filter.put("type", qqsNews.getType());
        filter.put("order", "publish_time desc");
    	List<QqsNews> qqsNewsList = service.findList(filter).getReturnObj();
    	model.put("qqsNewsList", qqsNewsList);
        
        return "/qqs/newsDetail";
    }
    
}
