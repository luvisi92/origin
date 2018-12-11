<#global base=appPath/>
<#macro header title="" charset="utf-8" lang="zh-CN">
<!DOCTYPE html>
<head>
  <title>${setting["app.name"]} ${title!}</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>全球安官网</title>
    <link rel="stylesheet" href="${base}/assets/fonts/iconfont.css">
    <link rel="stylesheet" href="${base}/assets/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/assets/css/swiper.css">
    <link rel="stylesheet" href="${base}/assets/css/metisMenu.css">
    <link rel="stylesheet" href="${base}/assets/css/css.css">
    <link rel="stylesheet" href="${base}/assets/css/mobile.css">
 
	<script type="text/javascript" src="${base}/assets/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="${base}/assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${base}/assets/js/swiper.js"></script>
	<script type="text/javascript" src="${base}/assets/js/metisMenu.min.js"></script>
	<script type="text/javascript" src="${base}/assets/js/demo.js"></script>
	<script type="text/javascript" src="${base}/assets/js/app.js"></script>
	<script src="${base}/assets/js/noty/jquery.noty.packaged.js"></script>

    <style>
        .productLi {
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
        }
    </style>
    <script>
      var ctxPath = "${base}";
      var appPath = "${appPath}";
      var formValidatorServletPath = "${base}/formValidator";
      $(function(){
    		//产品中心初始化
    		getProductCenter();
    		//解决方案初始化
    		getSolution();
    		//初始化服务
    		getService();
    		//初始化历史搜索
    		initSearchList();
    		//初始化热门搜索
    		initHotSearch();
    		
    		
    		$('#search').bind('keyup', function(event) {
                if (event.keyCode == "13") {
                    //回车执行查询
                    searchFun();
                }
            });
    	});

    	function getSolution(){
    		$.ajax({
    	        type: "get",
    	        url: "${base}/QqsSolution/getSolutionCategory.json",
    	        dataType: "json",
    	        data:{},
    	        success: function(data){
    	        		 	if(data.success){
    	        		 	var categoryArr = data.obj;
    	        		 	getSolutionCategoryHTML(categoryArr);
    	        		 	}
    	                 },
    	        error:function(){
    	        }
    	    });
    	}
    	
    	function getSolutionCategoryHTML(categoryArr){
    		var htmlContentPC = "";
    		var htmlContentMobile = "";
    		$(categoryArr).each(function(){
    			var entity = this;
    			var url = "${base}/QqsSolution/index.html?type="+entity.id
    			htmlContentMobile += '<li><a href="'+url+'">'+entity.tempPic+' '+entity.name+'</a></li>';	
    			htmlContentPC += '<li class="width100 t-left m-b-0"><a href="'+url+'" class="">'+entity.tempPic+' '+entity.name+'<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i></a></li>';
    		});
			$('.solutionPC').append(htmlContentPC);
			$('.solutionMobile').append(htmlContentMobile);
    	}
    	
    	function getService(){
    		$.ajax({
    	        type: "get",
    	        url: "${base}/QqsServiceSupport/getService.json",
    	        dataType: "json",
    	        data:{},
    	        success: function(data){
    	        		 	if(data.success){
    	        		 	var serviceArr = data.obj;
    	        		 	getServiceHTML(serviceArr);
    	        		 	}
    	                 },
    	        error:function(){
    	        }
    	    });
    	}
    	
    	function getServiceHTML(serviceArr){
    		var htmlServicePC = "";
    		var htmlServiceMobile = "";
    		var htmlServiceDB = "";
    		$(serviceArr).each(function(){
    			var entity = this;
    			var url = "${base}/QqsServiceSupport/serviceSupport.html?type="+entity.id;
    			htmlServicePC += '<li class="t-left m-b-0"><a href="'+url+'"><i class="iconfont icon-tuandui"></i>'+entity.name+'<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i></a></li>';
    			htmlServiceMobile += '<li><a href="'+url+'"><i class="iconfont icon-tuandui"></i>'+entity.name+'</a></li>';
    			if(htmlServiceDB==""){
    				htmlServiceDB +='<p class="p-t-10"><a href="'+url+'">'+entity.name+'</a></p>';
    			}else{
    				htmlServiceDB +='<p><a href="'+url+'">'+entity.name+'</a></p>';
    			}
    			
    		});
			$('.servicePC').append(htmlServicePC);
			$('.serviceMobile').append(htmlServiceMobile);
			$('.htmlServiceDB').append(htmlServiceDB);
    	}    	
	
    </script>
<#nested>
</head>
</#macro>

<#macro body>
<body>
<!--PC导航-->
<header class="pc-header">
    <a href="${base}/home.html" class="logo fl">
        <img src="${base}/assets/images/logo.png " alt="">
    </a>
    <nav class="nav-bar flex-one">
        <ul>
            <li class="" navList="want">
                <a href="javascript:void(0);">我要 <i class="icon iconfont icon-icon-xia"></i> </a>
            </li>
            <li class="" navList="product">
                <a href="${base}/QqsProduct/index.html">产品中心 <i class=" icon iconfont icon-icon-xia"></i></a>
            </li>
            <li class="" navList="solution">
                <a href="javascript:void(0);">解决方案 <i class=" icon iconfont icon-icon-xia"></i></a>
            </li>
            <li class="">
                <a href="${base}/shop/showShops.html">服务机构</a>
            </li>
            <li>
                <a href="${base}/QqsNews/news.html?type=1">新闻中心</a>
            </li>
            <li navList="aboutus">
                <a href="javascript:void(0);">关于我们 <i class=" icon iconfont icon-icon-xia"></i></a>
            </li>
        </ul>
        <div class="navigation-down">
            <div id="want" class="nav-down-menu menu-1" style="display: none;" navList="want">
                <div class="title-list-box">
                    <div class="width50 l">
                        <div class="title-t">
                            <ul>
                                <li class="width100 t-left">
                                    <a href="${base}/home.html#join" class="">
                                        <i class="iconfont icon-woshou"></i> 成为代理商<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>
                                <li class="width100 t-left">
                                    <a href="${base}/home.html#join">
                                        <i class="iconfont icon-jingli"></i> 成为服务商<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>
                                <li class="width100 t-left">
                                    <a href="${base}/home.html#join">
                                        <i class="iconfont icon-gongyingshang"></i> 成为供应商<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="width50 bgfff l">
                        <div class="title-text">
                            <img src="${base}/assets/images/wy.jpg"/>
                            <p class="two-text p-t-20 f16">欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网</p>
                            <button type="button" class="btn btn-primary m-t-20" onclick="window.location.href='${base}/home.html';">查看详情 <i class="iconfont icon-icon1"></i></button>
                        </div>

                    </div>
                </div>
            </div>
            <div id="product" class="nav-down-menu menu-1" style="display: none;" navList="product">
                <div class="title-list-box">
                    <div class="width50 l">
                        <div class="title-cpzx">
                            <ul>
                                <li class="t-left">
                                    <h2 id="hardware">
                                        <a href="${base}/QqsProduct/list.html?type=1">
                                            <i class="iconfont icon-yingjian"></i> 全球安硬件产品<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                        </a>
                                    </h2>
                                </li>
                                <li class="t-left">
                                    <h2 id="soft">
                                        <a href="${base}/QqsProduct/list.html?type=2">
                                            <i class="iconfont icon-daima"></i> 全球安软件产品<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                        </a>
                                    </h2>
                                </li>
                                <li class="t-left">
                                    <h2 id="setMeal">
                                        <a href="${base}/QqsProduct/list.html?type=3">
                                            <i class="iconfont icon-cangchu"></i> 全球安套餐产品<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                        </a>
                                    </h2>

                                </li>

                            </ul>
                        </div>
                    </div>
                    <div class="width50 bgfff l">
                        <div class="title-text">
                            <img src="${base}/assets/images/cpzx.jpg"/>
                            <p class="two-text p-t-20 f16">
                                欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网</p>
                            <button type="button" class="btn btn-primary m-t-20" onclick="window.location.href='${base}/QqsProduct/index.html';">查看详情 <i
                                    class="iconfont icon-icon1"></i></button>
                        </div>

                    </div>
                </div>
            </div>
            <div id="solution" class="nav-down-menu menu-3 menu-1" style="display: none;" navList="solution">
                <div class="title-list-box">
                    <div class="width50 l">
                        <div class="title-t">
                            <ul  class="solutionPC">
                            </ul>
                        </div>
                    </div>
                    <div class="width50 bgfff l">
                        <div class="title-text">
                            <img src="${base}/assets/images/wy.jpg"/>
                            <p class="two-text p-t-20 f16">
                                欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网</p>
                            <button type="button" class="btn btn-primary m-t-20" onclick="window.location.href='${base}/QqsSolution/index.html';">查看详情 <i
                                    class="iconfont icon-icon1"></i></button>
                        </div>

                    </div>
                </div>
            </div>
            <div id="aboutus" class="nav-down-menu menu-3 menu-1" style="display: none;" navList="aboutus">
                <div class="title-list-box">
                    <div class="width50 l">
                        <div class="title-aboutus">
                            <ul class="servicePC">
                                <li class="t-left m-b-0">
                                    <a href="${base}/AboutUs/aboutUs.html" class="">
                                        <i class="iconfont icon-diannao1"></i> 关于我们<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>
                                <li class="t-left m-b-0">
                                    <a href="${base}/QqsNews/news.html?type=2">
                                        <i class="iconfont icon-woshou"></i> 企业资讯<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>
                                <li class="t-left m-b-0">
                                    <a href="${base}/AboutUs/teamIntroduction.html">
                                        <i class="iconfont icon-tuandui"></i> 团队介绍<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>                                
                                <li class="t-left m-b-0">
                                    <a href="${base}/AboutUs/developmentHistory.html">
                                        <i class="iconfont icon-cangchu"></i> 发展历程<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>
                                <li class="t-left m-b-0">
                                    <a href="${base}/AboutUs/join.html">
                                        <i class="iconfont icon-weibiaoti1"></i> 加入全球安<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>
                                <li class="t-left m-b-0">
                                    <a href="${base}/AboutUs/aboutUs.html#connectUs">
                                        <i class="iconfont icon-weibiaoti1"></i> 联系我们<i class="iconfont icon-youjiantou r m-r-8 fontf5f5f5"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="width50 bgfff l">
                        <div class="title-text">
                            <img src="${base}/assets/images/wy.jpg"/>
                            <p class="two-text p-t-20 f16">
                                欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网欢迎来到全球安官网</p>
                            <button type="button" class="btn btn-primary m-t-20" onclick="window.location.href='${base}/AboutUs/aboutUs.html';">查看详情 <i
                                    class="iconfont icon-icon1"></i></button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </nav>
    <a class="mobile-nav" id="buttonWith">
        <div class="hamburger" id="hamburger-1">
            <span class="line"></span>
            <span class="line"></span>
            <span class="line"></span>
        </div>
    </a>
    <a class="nav-search" >
        <i class="iconfont icon-sousuo"></i>
    </a>
</header>
<aside id="aside" class="aside">
    <i class="aside-overlay hideAside"></i>
    <div class="aside-content">
        <div class="module module-filter-list">
            <div class="module-main scrollable" style="height:300px;">
                <div class="mobile-nav-list">
                    <nav class="sidebar-nav">
                        <ul class="metismenu" id="metismenu">
                            <li class="">
                                <a class="has-arrow" href="#"><span class="fa fa-fw fa-github fa-lg"></span>我要</a>
                                <ul class="mm-collapse">
                                    <li>
                                        <a href="${base}/home.html#join">
                                            <i class="iconfont icon-woshou"></i> 成为代理商
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${base}/home.html#join">
                                            <i class="iconfont icon-jingli"></i> 成为服务商
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${base}/home.html#join">
                                            <i class="iconfont icon-gongyingshang"></i> 成为供应商
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a class="has-arrow" href="#">产品中心</a>
                                <ul class="mm-collapse">
                                    <li>
                                        <a class="has-arrow" href="${base}/QqsProduct/list.html?type=1" aria-expanded="false">
                                            <i class="iconfont icon-woshou"></i>全球安硬件产品
                                        </a>
                                        <ul class="mm-collapse" id="hardwareMobile">
                                            <!--li><a href="#">服务器</a></li>
                                            <li><a href="#">视频终端</a></li>
                                            <li><a href="#">探测单元</a></li>
                                            <li><a href="#">链接传输</a></li-->
                                        </ul>
                                    </li>
                                    <li>
                                        <a class="has-arrow" href="${base}/QqsProduct/list.html?type=2" aria-expanded="false"><i
                                                class="iconfont icon-daima"></i> 全球安软件产品</a>
                                        <ul class="mm-collapse" id="softMobile">
                                            <!--li><a href="#">安防云服务平台</a></li>
                                            <li><a href="#">物联网信息平台</a></li>
                                            <li><a href="#">O2O商城</a></li>
                                            <li><a href="#">乐店管理</a></li>
                                            <li><a href="#">人工智能</a></li-->
                                        </ul>
                                    </li>
                                    <li>
                                        <a class="has-arrow" href="${base}/QqsProduct/list.html?type=3" aria-expanded="false"><i
                                                class="iconfont icon-fenlei"></i> 全球安套餐产品</a>
                                        <ul class="mm-collapse" id="setMealMobile">
                                            <!--li><a href="#">珠宝店套餐</a></li>
                                            <li><a href="#">汽车店套餐</a></li-->
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                            <li class="">
                                <a class="has-arrow" href="#"><span class="fa fa-fw fa-github fa-lg"></span>解决方案</a>
                                <ul class="mm-collapse solutionMobile">
                                </ul>
                            </li>
                            <li class="">
                                <a class="has-arrow" href="${base}/shop/showShops.html"><span class="fa fa-fw fa-github fa-lg"></span>服务机构</a>
                            </li>
                            <li class="">
                                <a class="has-arrow" href="${base}/QqsNews/news.html?type=1"><span class="fa fa-fw fa-github fa-lg"></span>新闻中心</a>
                            </li>
                            <li class="">
                                <a class="has-arrow" href="#">关于我们</a>
                                <ul class="mm-collapse serviceMobile">
                                    <li>
                                        <a href="${base}/AboutUs/aboutUs.html">
                                            <i class="iconfont icon-fuwu"></i> 关于我们
                                        </a>
                                    </li>
                                    <li>
                                        <a href="https://github.com/onokumus/metisMenu">
                                            <i class="iconfont icon-dingdan1"></i> 企业咨询
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${base}/AboutUs/teamIntroduction.html">
                                            <i class="iconfont icon-tuandui"></i> 团队介绍
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${base}/AboutUs/developmentHistory.html">
                                            <i class="iconfont icon-shuju1"></i> 发展历程
                                        </a>
                                    </li>
                                    <li>
                                        <a href="https://github.com/onokumus/metisMenu/issues">
                                            <i class="iconfont icon-bangong"></i> 加入全球安
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${base}/AboutUs/aboutUs.html#connectUs">
                                            <i class="iconfont icon-dianhua1"></i> 联系我们
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</aside>
<div class="search-box">
    <div class="search flex">
        <div class="search-icon">
            <i class="iconfont icon-cha"></i>
        </div>
        <input type="text" id="search" placeholder="请输入关键字搜索" class="flex-one" value="${(RequestParameters.search)!}">
        <div class="searchicon">
            <a href="###" onclick="searchFun()">
                <i class="iconfont icon-sousuo blue"></i>
            </a>
        </div>
        <div class="search-list shadow">
            <div class="search-lately">
                <ul id="lastestSearches">
                    <h1 id="searchDiv">最近搜索</h1>
                    <li class="search-clear" onclick="clearSearch()"><i class="iconfont icon-cha"></i> 全部清除</li>
                </ul>
            </div>
            <div class="search-hot">
                <ul>
                    <h1 id="hotSearch">热门搜索</h1>
                <#if hotSearchList?? && (hotSearchList?size gt 0)>
                <#list hotSearchList as hotSearch>
                    <li><a href="${base}/search.html?search=${(hotSearch.val)!}">${(hotSearch.val)!}</a></li>
                </#list>
                </#if>
                </ul>
            </div>

        </div>
    </div>

</div>
<!--PC导航结束-->
<#nested>
</#macro>
<#macro footer>
<!--底部-->
<div class="bottom">
    <div class="container">
        <div class="row">
            <div class="col-md-2 col-xs-6">
                <div class="bottom-text">
                    <h4>关于我们</h4>
                    <p class="p-t-10"><a href="${base}/AboutUs/aboutUs.html">企业概况</a></p>
                    <p><a href="${base}/QqsNews/news.html?type=2">企业资讯</a></p>
                    <p><a href="${base}/AboutUs/aboutUs.html#connectUs">联系我们</a></p>
                    <p><a href="${base}/AboutUs/join.html">招聘中心</a></p>
                </div>
            </div>
            <div class="col-md-2 col-xs-6">
                <div class="bottom-text">
                    <h4>供应商平台</h4>
                    <p class="p-t-10"><a href="${base}/QqsSelfRecommendation/qqsSelfRecommendation.html">供应商自荐</a></p>
                    <p><a href="${base}/home.html#join">成为代理商</a></p>
                    <p><a href="${base}/home.html#join">成为服务商</a></p>
                    <p><a href="${base}/home.html#join">成为供应商</a></p>
                </div>
            </div>
            <div class="col-md-2 col-xs-6">
                <div class="bottom-text htmlServiceDB">
                    <h4>服务支持</h4>
                </div>
            </div>
            <div class="col-md-2 col-xs-6">
                <div class="bottom-text">
                    <h4>下载中心</h4>
                    <p class="p-t-10"><a href="http://ledian.xjqqa.com/" target ="_blank">乐店软件下载平台</a></p>
                    <p><a href="http://console.xjqqa.com" target ="_blank">全球安云安防服务平台</a></p>
                    <p><a href="http://app.xjqqa.com/" target ="_blank">全球安云助手</a></p>
                    <p><a href="http://app.xjqqa.com/" target ="_blank"> 全球安云安防接警平台</a></p>
                    <p><a href="http://b2b2c.xjqqa.com" target ="_blank">乐店微信小程序管理平台</a></p>
                    <p><a href="http://face.xjqqa.com" target ="_blank">人脸识别后台管理服务</a></p>
                </div>
            </div>
            <div class="col-md-2 col-xs-6">
                <div class="bottom-text">
                    <h4>联系我们</h4>
                    <p class="p-t-10"><a href="###">0755-83159999</a></p>
                    <p><a href="###">深圳市福田区红荔西路 香蜜湖娱乐城南门A1栋</a></p>
                </div>
            </div>
            <div class="col-md-2 col-xs-6">
                <div class="bottom-text">
                    <h4>官方微信订阅号</h4>
                    <p class="p-t-10"><a href="###">
                        <img src="${base}/assets/images/ewm.jpg"/>
                    </a></p>
                </div>
            </div>
        </div>
    </div>
    
    <div class="bottom-down">
        <div class="container">
            <div class="row">
                <div class="col-md-7">
                    <div><a href="${base}/AboutUs/legalDeclaration.html">法律声明</a>&nbsp&nbsp&nbsp<a href="${base}/AboutUs/webSiteMap.html">网站地图</a></div>
                    <div class=" f666 p-t-10">
                        Copyright © 2017 - 2019 Xjqqa. All Rights Reserved.</div>
                </div>
                <div class="col-md-5 f666 p-t-30">
                    <div class="bot-text v-middle" >
                        <img src="${base}/assets/images/gongan.jpg" />
                        <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=44030502002574" target ="_blank">粤公网安备 44030502002574号</a>
                        <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=44030502002574" target ="_blank">ICP备案号：粤ICP备18095938号</a>
                    </div>
                </div>
            </div>
        </div>
    </div>    
</div>
<!--底部结束-->
</footer>
<#nested>
<script>
    function initSearchList() {
        $.post("${base}/searchList.json", function(result){
            if (result.success) {
                var searchHtml = "";
                if (result.obj != null && result.obj.length > 0) {
                    result.obj.forEach(function(search){
                        searchHtml += "<li><a href='${base}/search.html?search=" + search + "'>" + search + "</a></li>";
                    });
                    $("#searchDiv").after(searchHtml);
                } else {
                    $(".search-lately").hide();
                }
            }
        });
    }
    
    function initHotSearch() {
        $.post("${base}/hotSearch.json", function(result){
            if (result.success) {
                var searchHtml = "";
                if (result.obj != null && result.obj.length > 0) {
                    result.obj.forEach(function(search){
                        searchHtml += "<li><a href='${base}/search.html?search=" + search.val + "'>" + search.val + "</a></li>";
                    });
                    $("#hotSearch").after(searchHtml);
                }
            }
        });
    }

    function getProductCenter() {
        $.post("${base}/QqsProduct/getQqsProductCategory.json", function(result){
            if (result.success) {
                var hardware = "";
                var soft = "";
                var setMeal = "";
                var hardwareMobile = "";
                var softMobile = "";
                var setMealMobile = "";
                result.obj.forEach(function(category){
                    if (category.type == "1") {
                        hardware += "<p class='productLi'><a href='###' onclick='details(1," + category.catId + ")'>"
                            + category.catName + "<i class='iconfont icon-youjiantou r m-r-8 fontf5f5f5'></i></a></p>";
                        hardwareMobile += "<li><a href='###' onclick='details(1," + category.catId + ")'><p class='productLi'>"
                            + category.catName + "</p></a></li>";
                    } else if (category.type == "2") {
                        soft += "<p class='productLi'><a href='###' onclick='details(2," + category.catId + ")'>" + category.catName
                            + "<i class='iconfont icon-youjiantou r m-r-8 fontf5f5f5'></i></a></p>";
                        softMobile += "<li><a href='###' onclick='details(2," + category.catId + ")'><p class='productLi'>"
                            + category.catName + "</p></a></li>";
                    } else if (category.type == "3") {
                        setMeal += "<p class='productLi'><a href='###' onclick='details(3," + category.catId + ")'>"
                            + category.catName + "<i class='iconfont icon-youjiantou r m-r-8 fontf5f5f5'></i></a></p>";
                        setMealMobile += "<li><a href='###' onclick='details(3," + category.catId + ")'><p class='productLi'>"
                            + category.catName + "</p></a></li>";
                    }
                });
                $("#hardware").after(hardware);
                $("#soft").after(soft);
                $("#setMeal").after(setMeal);
                $("#hardwareMobile").append(hardwareMobile);
                $("#softMobile").append(softMobile);
                $("#setMealMobile").append(setMealMobile);
            }
        });
    }

    function details(type, catId) {
        window.location.href = "${base}/QqsProduct/list.html?type=" + type + "&selectCatId=" + catId;
    }
    function searchFun() {
        if ($("#search").val()) {
            window.location.href = "${base}/search.html?search=" + $("#search").val();
        }
    }
    function clearSearch() {
        $.post("${base}/clearSearch.json", function(result) {
            $('.search-lately').remove();
        });
    }
</script>
</body>
</html>
</#macro>

<#macro pagination pager>
	<#if (pager.totalRecords)?default(0) != 0>
		<div class="container">
			<input type="hidden" id="startId" name="start" value="${(RequestParameters.start?default(0))!}"/>	
			<nav style="text-align: center" aria-label="Page navigation" class="secu-page">
		      <ul class="pagination">
		        <li>
		        	<a>${(pager.totalPages)!}页</a>
		        </li>
		        <li>
		          <a aria-label="Previous" href="javascript:void(0)" onclick="toPage(0);">
		            <span aria-hidden="true">首页</span>
		          </a>
		        </li>
		        
		        <#if pager.pageNum gt 2>
		        <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 2)*pager.pageSize!});">${(pager.pageNum - 2)!}</a></li>
		        <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 1)*pager.pageSize!});">${(pager.pageNum - 1)!}</a></li>
		        <#elseif pager.pageNum gt 1>
		        <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 1)*pager.pageSize!});">${(pager.pageNum - 1)!}</a></li>
		        </#if>
		        <li class="active"><a><font color="white">${(pager.pageNum)!}</font></a></li>
		        <#if (pager.totalPages - pager.pageNum) gt 1>
		        <li><a href="javascript:void(0)" onclick="toPage(${(pager.pageNum)*pager.pageSize!});">${(pager.nextPage)!}</a></li>
		        <li><a href="javascript:void(0)" onclick="toPage(${(pager.nextPage)*pager.pageSize!});">${(pager.nextPage + 1)!}</a></li>
		        <#elseif (pager.totalPages - pager.pageNum) gt 0>
		        <li><a href="javascript:void(0)" onclick="toPage(${(pager.pageNum)*pager.pageSize!});">${(pager.nextPage)!}</a></li>
		        </#if>
		        <li>
		          <a aria-label="Next" href="javascript:void(0)" onclick="toPage(${(pager.totalPages-1)*pager.pageSize!});">
		            <span aria-hidden="true">尾页</span>
		          </a>
		        </li>
		      </ul>
		    </nav>
		</div>
	</#if>
</#macro>

<#macro pagination pager>
    <#if (pager.totalRecords)?default(0) != 0>
        <div class="container">
            <input type="hidden" id="startId" name="start" value="${(RequestParameters.start?default(0))!}"/>   
            <nav style="text-align: center" aria-label="Page navigation" class="secu-page">
              <ul class="pagination">
                <li>
                    <a>${(pager.totalPages)!}页</a>
                </li>
                <li>
                  <a aria-label="Previous" href="javascript:void(0)" onclick="toPage(0);">
                    <span aria-hidden="true">首页</span>
                  </a>
                </li>
                
                <#if pager.pageNum gt 2>
                <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 2)*pager.pageSize!});">${(pager.pageNum - 2)!}</a></li>
                <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 1)*pager.pageSize!});">${(pager.pageNum - 1)!}</a></li>
                <#elseif pager.pageNum gt 1>
                <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 1)*pager.pageSize!});">${(pager.pageNum - 1)!}</a></li>
                </#if>
                <li class="active"><a><font color="white">${(pager.pageNum)!}</font></a></li>
                <#if (pager.totalPages - pager.pageNum) gt 1>
                <li><a href="javascript:void(0)" onclick="toPage(${(pager.pageNum)*pager.pageSize!});">${(pager.nextPage)!}</a></li>
                <li><a href="javascript:void(0)" onclick="toPage(${(pager.nextPage)*pager.pageSize!});">${(pager.nextPage + 1)!}</a></li>
                <#elseif (pager.totalPages - pager.pageNum) gt 0>
                <li><a href="javascript:void(0)" onclick="toPage(${(pager.pageNum)*pager.pageSize!});">${(pager.nextPage)!}</a></li>
                </#if>
                <li>
                  <a aria-label="Next" href="javascript:void(0)" onclick="toPage(${(pager.totalPages-1)*pager.pageSize!});">
                    <span aria-hidden="true">尾页</span>
                  </a>
                </li>
              </ul>
            </nav>
        </div>
    </#if>
</#macro>

<#macro websitePager pager>
    <#if (pager.totalRecords)?default(0) != 0>
        <div class="container t-center pagination-box">
            <input type="hidden" id="startId" name="start" value="${(RequestParameters.start?default(0))!}"/>
            <nav aria-label="...">
                <ul class="pagination">
                    <li>
                        <a href="#" aria-label="Previous" onclick="toPage(${(pager.previousPage - 1)*pager.pageSize!});"><span aria-hidden="true">&lt;</span></a>
                    </li>
                    <#if pager.pageNum gt 2>
                        <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 2)*pager.pageSize!});">${(pager.pageNum - 2)!}</a></li>
                        <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 1)*pager.pageSize!});">${(pager.pageNum - 1)!}</a></li>
                        <#elseif pager.pageNum gt 1>
                        <li><a href="javascript:void(0)" onclick="toPage(${(pager.previousPage - 1)*pager.pageSize!});">${(pager.pageNum - 1)!}</a></li>
                    </#if>
                    <li class="active"><a>${(pager.pageNum)!}<span class="sr-only">(current)</span></a></li>
                    <#if (pager.totalPages - pager.pageNum) gt 1>
                        <li><a href="javascript:void(0)" onclick="toPage(${(pager.pageNum)*pager.pageSize!});">${(pager.nextPage)!}</a></li>
                        <li><a href="javascript:void(0)" onclick="toPage(${(pager.nextPage)*pager.pageSize!});">${(pager.nextPage + 1)!}</a></li>
                        <#elseif (pager.totalPages - pager.pageNum) gt 0>
                        <li><a href="javascript:void(0)" onclick="toPage(${(pager.pageNum)*pager.pageSize!});">${(pager.nextPage)!}</a></li>
                    </#if>
                    <li>
                        <a href="#" aria-label="Next"
                            <#if (pager.nextPage gt pager.pageNum)>
                                onclick="toPage(${(pager.pageNum)*pager.pageSize!});"
                                <#else>
                                onclick="toPage(${(pager.pageNum - 1)*pager.pageSize!});"
                            </#if>
                        ><span aria-hidden="true">&gt;</span></a>
                    </li>
              </ul>
            </nav>
        </div>
    </#if>
</#macro>