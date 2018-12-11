//-------------------------------------------------------------------------
// Copyright (c) 2015-2017 HF-STAR-V. All Rights Reserved.
//
// This software is the confidential and proprietary information of
// HF-STAR-V
//
//-------------------------------------------------------------------------

package com.zhisou.qqs.portal.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author            JiangTao
 * @version           1.0
 * @since             2016-4-18
 */
@Controller
public class PowerController extends BaseController
{
    @RequestMapping(value = "/{page}.html")
    public String handle(@PathVariable("page") String page, HttpServletRequest request, HttpServletResponse response) {
        return page;
    }
    
    @RequestMapping(value = "/{page}/")
    public String indexHandle(@PathVariable("page") String page, HttpServletRequest request, HttpServletResponse response) {
        return page+"/index.html";
    }
    
    @RequestMapping(value = "/{path1}/{path2}.html")
    public String handle2(@PathVariable("path1") String path1,@PathVariable("path2") String path2, HttpServletRequest request, HttpServletResponse response) {
    	return path1+"/"+path2;
    }
    
    @RequestMapping(value = "/{path1}/{path2}/")
    public String indexHandle2(@PathVariable("path1") String path1,@PathVariable("path2") String path2, HttpServletRequest request, HttpServletResponse response) {
        return path1+"/"+path2+"/index.html";
    }
    
}

