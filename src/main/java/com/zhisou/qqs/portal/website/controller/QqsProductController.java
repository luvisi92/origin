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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.likegene.framework.core.QueryFilter;
import com.zhisou.qqs.website.model.QqsBanner;
import com.zhisou.qqs.website.model.QqsProduct;
import com.zhisou.qqs.website.model.QqsProductCategory;
import com.zhisou.qqs.website.model.QqsSoftProduct;
import com.zhisou.qqs.website.service.QqsArticleService;
import com.zhisou.qqs.website.service.QqsBannerService;
import com.zhisou.qqs.website.service.QqsProductCategoryService;
import com.zhisou.qqs.website.service.QqsProductService;
import com.zhisou.qqs.website.service.QqsSoftProductService;

/**
 * 产品 Controller
 * 
 * @author Guojun Zhang
 * @since 2018-11-07
 */
@Controller
@RequestMapping("/QqsProduct")
public class QqsProductController extends BaseController {

    @Autowired
    private QqsProductCategoryService categoryService;

    @Autowired
    private QqsProductService productService;

    @Autowired
    private QqsSoftProductService softProductService;

    @Autowired
    private QqsArticleService articleService;
    @Autowired
    private QqsBannerService qqsBannerService;
    
    /**
     * 产品中心页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.html")
    public String index(ModelMap model) {
        QueryFilter qf = new QueryFilter();
        qf.put("disable", QqsProductCategory.DISABLE_TRUE);
        qf.put("order", "`type`, sort");
        List<QqsProductCategory> categoryList = categoryService.findList(qf).getReturnObj();
        
        splitByType(model, categoryList);
        
        qf = new QueryFilter();
        qf.put("publishStatus", QqsProduct.STATUS_PUBLISH);
        qf.setStatementKey(QqsProductService.LIST_WEBSITE_PRODUCT);
        List<QqsProduct> list = productService.findList(qf).getReturnObj();
        Map<String, List<QqsProduct>> productMap = producList2Map(list);
        //Banner
        QueryFilter filter = new QueryFilter();
        filter.put("page", "product.html");
        QqsBanner banner = qqsBannerService.findOne(filter).getReturnObj();
        model.put("banner", banner);
        model.put("productMap", productMap);
        return "/qqs/product";
    }

    /**
     * 产品列表页
     * @param model
     * @param type
     * @param selectCatId
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String list(ModelMap model, @ModelAttribute("type")Integer type, Integer selectCatId,
            HttpServletRequest request) {
        QueryFilter qf = new QueryFilter(request);
        //Banner
        QueryFilter filter = new QueryFilter();
        filter.put("page", "list.html");
        QqsBanner banner = qqsBannerService.findOne(filter).getReturnObj();
        model.put("banner", banner);
        
        qf.put("publishStatus", QqsProduct.STATUS_PUBLISH);
        if (QqsProductCategory.TYPE_SET_MEAL == type.intValue() ||
                QqsProductCategory.TYPE_SOFT == type.intValue()) {
            qf.put("type", type);
            List<QqsProductCategory> categoryList = categoryService.findList(qf).getReturnObj();
            model.put("categoryList", categoryList);
            if (categoryList != null && !categoryList.isEmpty()) {
                if (selectCatId == null || selectCatId.intValue() == 0) {
                    QqsProductCategory first = categoryList.get(0);
                    Integer catId = first.getCatId();
                    selectCatId = catId;
                }
                qf.put("catId", selectCatId);
                model.put("selectCatId", selectCatId);
                model.put("softProductList", softProductService.findList(qf).getReturnObj());
            }
            return "/qqs/soft";
        } else {
            qf.setLimit(9);
            if (selectCatId != null && selectCatId.intValue() != 0) {
                qf.put("catId", selectCatId);
            }
            model.put("pager", productService.findPager(qf).getReturnObj());
            return "/qqs/hardware";
        }

    }
    
    /**
     * 硬件产品详情页
     * @param model
     * @param productId
     * @return
     */
    @RequestMapping(value = "/hardwareDetail.html")
    public String hardwareDetail(ModelMap model, Integer productId, @ModelAttribute("type")Integer type) {
        QueryFilter qf = new QueryFilter();
        qf.put("productId", productId);
        QqsProduct product = productService.findOne(qf).getReturnObj();
        model.put("product", product);
        
        qf = new QueryFilter();
        qf.put("type", QqsProductCategory.TYPE_HARDWARE);
        model.put("categoryList", categoryService.findList(qf).getReturnObj());
        
        return "/qqs/hardwareDetail";
    }

    /**
     * 软件产品详情页
     * @param model
     * @param productId
     * @return
     */
    @RequestMapping(value = "/softDetail.html")
    public String softDetail(ModelMap model, Integer productId, @ModelAttribute("type")Integer type, Integer selectCatId) {
        QueryFilter qf = new QueryFilter();
        qf.put("productId", productId);
        qf.put("disable", QqsProductCategory.DISABLE_TRUE);
        QqsSoftProduct product = softProductService.findOne(qf).getReturnObj();
        if (product != null) {
            QqsSoftProduct softProduct = new QqsSoftProduct();
            softProduct.setProductId(productId);
            int reading = 1;
            if (product.getReading() != null) {
                reading = product.getReading().intValue() + 1;
            }
            softProduct.setReading(reading);
            softProductService.update(softProduct);
            product.setReading(reading);
            qf = new QueryFilter();
            qf.put("id", product.getArticleId());
            model.put("article", articleService.findOne(qf).getReturnObj());
            model.put("entity", product);
        }
        qf = new QueryFilter();
        qf.put("type", type);
        model.put("categoryList", categoryService.findList(qf).getReturnObj());
        
        qf.put("catId", selectCatId);
        qf.put("publishStatus", 1);
        qf.put("order", "create_time desc");
        qf.setLimit(4);
        qf.put("removed", productId);
        model.put("pager", softProductService.findPager(qf).getReturnObj());

        qf.put("productId", productId);
        qf.setStatementKey(QqsSoftProductService.SELECT_PREVIOUS_PRODUCT);
        model.put("previousProduct", softProductService.findOne(qf).getReturnObj());
        
        qf.setStatementKey(QqsSoftProductService.SELECT_NEXT_PRODUCT);
        model.put("nextProduct", softProductService.findOne(qf).getReturnObj());

        return "/qqs/softDetail";
    }

    /**
     * 获得产品分类列表
     * @return
     */
    @RequestMapping(value = "/getQqsProductCategory.json")
    @ResponseBody
    public ResponseData getQqsProtectCategory() {
        QueryFilter qf = new QueryFilter();
        qf.put("disable", QqsProductCategory.DISABLE_TRUE);
        qf.put("order", "`type`, sort");
        List<QqsProductCategory> list = categoryService.findList(qf).getReturnObj();
        ResponseData response = new ResponseData(true);
        response.setObj(list);
        return response;
    }

    private Map<String, List<QqsProduct>> producList2Map(List<QqsProduct> list) {
        Map<String, List<QqsProduct>> map = new HashMap<>();
        for (QqsProduct product : list) {
            List<QqsProduct> tempList = map.get(String.valueOf(product.getCatId()));
            if (tempList == null) {
                tempList = new ArrayList<>();
            }
            tempList.add(product);
            map.put(String.valueOf(product.getCatId()), tempList);
        }
        return map;
    }

    private void splitByType(ModelMap model, List<QqsProductCategory> categoryList) {
        List<QqsProductCategory> hardware = new ArrayList<>();
        List<QqsProductCategory> soft = new ArrayList<>();
        List<QqsProductCategory> setMeal = new ArrayList<>();
        for (QqsProductCategory category : categoryList) {
            if (QqsProductCategory.TYPE_HARDWARE == category.getType().intValue()) {
                hardware.add(category);
            } else if (QqsProductCategory.TYPE_SOFT == category.getType().intValue()) {
                soft.add(category);
            } else if (QqsProductCategory.TYPE_SET_MEAL == category.getType().intValue()) {
                setMeal.add(category);
            }
        }
        model.put("hardwareList", hardware);
        model.put("softList", soft);
        model.put("setMealList", setMeal);
    }
}