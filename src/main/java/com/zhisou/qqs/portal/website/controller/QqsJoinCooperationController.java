package com.zhisou.qqs.portal.website.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zhisou.qqs.website.service.QqsJoinCooperationService;
import com.zhisou.qqs.website.service.QqsRegionsService;
import com.zhisou.qqs.website.model.QqsJoinCooperation;
import com.zhisou.qqs.website.model.QqsRegions;
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
 * 加盟合作表 Controller
 * @author zcs
 * @since  2018-11-05
 */
@Controller
@RequestMapping("/joinCooperation")
public class QqsJoinCooperationController extends BaseController{
	
	@Autowired
	private QqsJoinCooperationService service;
	
	@Autowired
	private QqsRegionsService regionsService;

	@RequestMapping(value = "/index.html")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		return "qqs/joinCooperation";
	}
	 @RequestMapping(value="/getProvince.json")
	    @ResponseBody
	    //获取省
	    public ResponseData getProvince(HttpServletRequest request , HttpServletResponse response){
		 	ResponseData data = new ResponseData(true);
		 	QueryFilter filter = new QueryFilter();
			filter.put("regionGrade", 1);
	    	List<QqsRegions> provinceList = regionsService.findList(filter).getReturnObj();
	    	data.setObj(provinceList);
			return data;
		 
	 }
	 
	 @RequestMapping(value="/getCity.json")
	    @ResponseBody
	    //获取省code查询省下所有市
	    public ResponseData getCity(HttpServletRequest request , HttpServletResponse response){
		 
		 	ResponseData data = new ResponseData(true);
		 	String code=request.getParameter("code");//获取地域编码code
		 	
		 	QueryFilter filter = new QueryFilter();
			filter.put("code", code);			
			QqsRegions province = regionsService.findOne(filter).getReturnObj();//根据code查询这个省

	    	
			QueryFilter filter2 = new QueryFilter();
	    	filter2.put("regionGrade", 2);
	    	filter2.put("regionPath",province.getRegionPath());
	    	List<QqsRegions> cityList = regionsService.findList(filter2).getReturnObj();//根据等级和region_path查询省下面的市

	    	for(int i=0;i<cityList.size();i++){
	    		if(cityList.get(i).getLocalName().equals("县")){//判断是否为县

	    			cityList.remove(i);
	    			
	    	    	
	    		}
	    	}
	    	
	    	data.setObj(cityList);
			return data;
		 
	 }
	
	@RequestMapping(value = "/add.html")
	public String add(HttpServletRequest request, HttpServletResponse response) {
		
		return "qqs/JoinCooperation/add";
	}
	
	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData save(ModelMap model, @ModelAttribute("entity") QqsJoinCooperation entity, 
						HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession();
	        String validateCode1 =  (String)session.getAttribute("validateCode1");
	        String validateCode2 =  (String)session.getAttribute("validateCode2");
	        String validateCode3 =  (String)session.getAttribute("validateCode3");          
	        String codeCheck=request.getParameter("codeCheck");
	        String code=request.getParameter("code");
	       
	        
		try{
			Map<String,Object> errors = FormValidatorManager.validate("saveQqsJoinCooperationConfig", request);
	        if (errors.size() != 0)
	        {
	            return new ResponseData(false, errors.keySet().toString());
	        }
	        if(codeCheck.equals("0")){
	        	if(validateCode1.equalsIgnoreCase(code)){
		        	
		        	Date date = new Date();
			        date.setTime(System.currentTimeMillis());
			        entity.setCreateTime(date);
			 
				    Result result = service.save(entity);
				    if (!result.isSuccess()) {
				    	return new ResponseData(false, result.getErrormsg());
			        }
		        }else{
		        	return new ResponseData(false, "验证码不正确");
		        }
	        }
	        if(codeCheck.equals("1")){
	        	if(validateCode2.equalsIgnoreCase(code)){
		        	
		        	Date date = new Date();
			        date.setTime(System.currentTimeMillis());
			        entity.setCreateTime(date);
			 
				    Result result = service.save(entity);
				    if (!result.isSuccess()) {
				    	return new ResponseData(false, result.getErrormsg());
			        }
		        }else{
		        	return new ResponseData(false, "验证码不正确");
		        }
	        }
	        if(codeCheck.equals("2")){
	        	if(validateCode3.equalsIgnoreCase(code)){
		        	
		        	Date date = new Date();
			        date.setTime(System.currentTimeMillis());
			        entity.setCreateTime(date);
			 
				    Result result = service.save(entity);
				    if (!result.isSuccess()) {
				    	return new ResponseData(false, result.getErrormsg());
			        }
		        }else{
		        	return new ResponseData(false, "验证码不正确");
		        }
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
        return "qqs/JoinCooperation/detail";
    }

	@RequestMapping(value = "/edit.html")
    public String edit(Long id, HttpServletRequest request, HttpServletResponse response) {
    	QueryFilter filter = new QueryFilter();
        filter.put("id", id);
        request.setAttribute("entity", service.findOne(filter).getReturnObj());
        return "qqs/JoinCooperation/edit";
    }
    
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    @ResponseBody
	public ResponseData update(ModelMap model, @ModelAttribute("entity") QqsJoinCooperation entity, 
						HttpServletRequest request, HttpServletResponse response) {
        try{
			Map<String,Object> errors = FormValidatorManager.validate("saveQqsJoinCooperationConfig", request);
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
