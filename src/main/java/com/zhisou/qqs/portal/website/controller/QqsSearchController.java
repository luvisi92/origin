package com.zhisou.qqs.portal.website.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.likegene.framework.core.QueryFilter;
import com.zhisou.qqs.website.service.QqsProductService;
import com.zhisou.qqs.website.service.QqsShopService;
import com.zhisou.qqs.website.service.QqsSoftProductService;
import com.zhisou.qqs.website.service.SysConfigService;

/**
 * 搜索服务
 * @author      GuoJun
 * @since       1.0
 * 
 */
@Controller
public class QqsSearchController extends BaseController {

    @Autowired
    private QqsProductService productService;
    
    @Autowired
    private QqsSoftProductService softProductService;
    
    @Autowired
    private QqsShopService shopService;
    
    @Autowired
    private SysConfigService configService;
    
    private static String COOKIE_SEARCH_NAME = "search";
    /**
     * 查询结果页
     * @return
     */
    @RequestMapping(value = "/search.html")
    public String search(ModelMap model, String search, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Set<String> set = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), COOKIE_SEARCH_NAME)) {
                    String value;
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        set = new LinkedHashSet<>(Arrays.asList(value.split(",")));
                        if (set != null && set.size() > 6) {
                            Iterator<String> it = set.iterator();
                            while(it.hasNext()) {
                                it.remove();
                                break;
                            }
                        }
                        set.add(search);
                        cookie = new Cookie(COOKIE_SEARCH_NAME, URLEncoder.encode(StringUtils.join(set.toArray(), ","), "UTF-8"));
                        response.addCookie(cookie);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    break;
                }
            }
        }
        if (set == null) {
            set = new HashSet<>();
            set.add(search);
            try {
                Cookie cookie = new Cookie(COOKIE_SEARCH_NAME, URLEncoder.encode(search, "UTF-8"));
                response.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            
        }
        model.put("search", set);
        if (StringUtils.isNotEmpty(search)) {
            QueryFilter qf = new QueryFilter();
            qf.put("search", search);
            qf.put("publishStatus", 1);
            qf.setStatementKey(QqsProductService.SEARCH_PRODUCT);
            model.put("productList", productService.findList(qf).getReturnObj());
            
            qf.setStatementKey(QqsSoftProductService.SEARCH_SOFT);
            model.put("softProductList", softProductService.findList(qf).getReturnObj());
            
            qf.setStatementKey(QqsShopService.SEARCH_SHOP);
            model.put("shopList", shopService.findList(qf).getReturnObj());
        }
        return "qqs/searchResult";
    }
    
    
    @RequestMapping(value = "/clearSearch.json")
    public void clearSearch(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_SEARCH_NAME, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    
    @RequestMapping(value = "/searchList.json")
    @ResponseBody
    public ResponseData searchList(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        ResponseData data = new ResponseData(true);
        Set<String> set = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), COOKIE_SEARCH_NAME)) {
                    String value;
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        set = new LinkedHashSet<>(Arrays.asList(value.split(",")));
                        data.setObj(set);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return data;
    }
    
    
    @RequestMapping(value = "/hotSearch.json")
    @ResponseBody
    public ResponseData hotSearch() {
        ResponseData data = new ResponseData(true);
        QueryFilter qf = new QueryFilter();
        qf.put("category", "hot_search");
        data.setObj(configService.findList(qf).getReturnObj());
        return data;
    }
}
