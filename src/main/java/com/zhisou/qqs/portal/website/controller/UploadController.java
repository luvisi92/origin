package com.zhisou.qqs.portal.website.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.zhisou.qqs.portal.website.core.AppContextHolder;
import com.zhisou.qqs.portal.util.DateUtils;
import com.zhisou.qqs.portal.util.DateUtils.DateStyle;
import com.zhisou.qqs.website.model.SysFile;

/**
 * 用于文件上传controller
 * 
 * @author JiangTao
 * @since 2017-04-19
 */
@Controller
@RequestMapping("/uploadFile")
public class UploadController extends BaseController {

	/**
	 * 图片上传
	 * 
	 * @Desc
	 * @author JiangTao
	 * @date 2017-4-19
	 */
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData uploadImg(HttpServletRequest request) {
		return null;
/*		// 获取当前登陆用户
		QqsMember member = (QqsMember) AppContextHolder.getCurrentUser();
		if (member == null) {
			return new ResponseData(false, "用户未登录");
		}
		try {
			StringBuffer result = new StringBuffer();
			String folder = request.getParameter("folder");
			if(null == folder||"".equals(folder)){
				folder = "uploadFile";
			}
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			int i=0;
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				
				if (file == null) {
					return new ResponseData(false, "文件不能为空");
				}
				if (file.getSize() > SysFile.PIC_SIZE.longValue()) {
					return new ResponseData(false, "图片过大");
				}
				if (file != null) {
					i++;
					String fname = file.getOriginalFilename();
					String ext = fname.substring(fname.lastIndexOf(".")+1,fname.length());
					String filename = member.getUsername() + DateUtils.formatDatetime(new Date(), DateStyle.YYYYMMDDHHMMSS) + i + "." + ext;
					String curPath = folder + File.separator + DateUtils.formatDatetime(new Date(), DateStyle.YYYY_MM) + File.separator + filename;
					String path = AppContextHolder.getFileSavePath() + curPath ;
					
					File newFile = new File(path);
					if (!newFile.getParentFile().exists()){
						newFile.getParentFile().mkdirs();
					}
					
					file.transferTo(new File(path));
					
					if(result.length() > 0){
						result.append(",");
					}
					result.append(curPath);
				}
			}
			
			if(result.length()>0){
				ResponseData responseData = new ResponseData(true, null);
				responseData.setMessage("图片上传成功");
				responseData.setObj(result.toString());
				return responseData;
			}else{
				return ResponseData.SUCCESS_NO_DATA;
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new ResponseData(false, "文件上传错误");
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseData(false, "文件上传错误");
		}*/
	}

}
