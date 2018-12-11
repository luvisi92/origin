package com.zhisou.qqs.portal.website.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zhisou.qqs.website.service.QqsServiceSupportService;
import com.zhisou.qqs.website.model.QqsServiceSupport;
import com.likegene.framework.core.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 服务支持 Controller
 * @author JT
 * @since  2017-05-19
 */
@Controller
@RequestMapping("/QqsServiceSupport")
public class QqsServiceSupportController extends BaseController{
	
	@Autowired
	private QqsServiceSupportService service;

	@RequestMapping(value = "/serviceSupport.html")
	public String alarmServiceIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter();
        String type = request.getParameter("type");
        //所有服务
        model.put("supportList", service.findList(filter).getReturnObj());
        //当前请求服务
        filter.put("id", type);
        model.put("support", service.findOne(filter).getReturnObj());
		return "/qqs/serviceSupport";
	}

	/**
	 * 查询服务
	 * @param request
	 * @param response
	 * @return
	 * @2018-11-7上午11:36:26
	 * @JT
	 */
	@RequestMapping(value = "/getService.json")
	@ResponseBody
	public ResponseData getService(HttpServletRequest request, HttpServletResponse response) {
        QueryFilter filter = new QueryFilter();
        List<QqsServiceSupport> serviceList = service.findList(filter).getReturnObj(); 
        ResponseData data = new ResponseData(true);
        data.setObj(serviceList);
		return data;
	}
	
}
