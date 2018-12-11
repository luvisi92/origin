$(function () {
    //首页头部导航
    var qcloud = {};
    $('[navList]').hover(function () {
        $(this).find(".icon").addClass('icon-icon-shang');
        var _nav = $(this).attr('navList');
        clearTimeout(qcloud[_nav + '_timer']);
        qcloud[_nav + '_timer'] = setTimeout(function () {
            $('[navList]').each(function () {
                $(this)[_nav == $(this).attr('navList') ? 'addClass' : 'removeClass']('nav-up-selected');
            });
            $('#' + _nav).stop(true, true).show();
        });
    }, function () {
        $(this).find(".icon").removeClass('icon-icon-shang');
        var _nav = $(this).attr('navList');
        clearTimeout(qcloud[_nav + '_timer']);
        qcloud[_nav + '_timer'] = setTimeout(function () {
            $('[navList]').removeClass('nav-up-selected');
            $('#' + _nav).stop(true, true).hide();
        });
    });
    //导航面包屑点击
    var elButtonWith = $('#buttonWith');
    var elAside = $('#aside');
    elButtonWith.on('click', function () {
        if ($('.hamburger').hasClass("is-active")) {
            $('.hamburger').toggleClass("is-active");
            $('.mobile-nav').removeClass('hideAside');
            elAside.removeClass('active');
            $.smartScroll(elAside, '.scrollable');
            $('html').removeClass('noscroll');
        } else {
            $('.hamburger').toggleClass("is-active");
            $('.mobile-nav').addClass('hideAside');
            // $('.mobile-nav').attr('id','myDivId_new');
            elAside.addClass('active');
            $.smartScroll(elAside, '.scrollable');
            $('html').addClass('noscroll');
        }
    });

    $('.hideAside').on('click', function () {
        $('html').removeClass('noscroll');
        elAside.removeClass('active');
    });
    //面包屑展开
    // $('.mobile-nav').click(function () {
    //     if($('.mobile-nav-list').is(':hidden')){
    //         $('.mobile-nav-list').show();
    //     }else{
    //         $('.mobile-nav-list').hide();
    //     }
    // });
    /*面包屑初始化*/
    $('#metismenu').metisMenu({});
    $('.module-main').height($(window).height() - 50);
    //搜索点击
    $('.nav-search').click(function () {
        if ($('.search-box').is(':hidden')) {
            $('.search-box').show();
        } else {
            $('.search-box').hide();
        }
    });
    //搜索清空
    /*$('.search-clear').click(function () {
        $('.search-lately').remove()
    });*/
    //关闭搜索
    $('.search-icon').click(function () {
        $('.search-box').hide()
    });
    //解决方案
    //软件产品中心切换
    var $tab_li = $('#tab ul li');
    $tab_li.click(function () {
        $(this).addClass('selected').siblings().removeClass('selected');
        var index = $tab_li.index(this);
        $('div.tab_box > div').eq(index).show().siblings().hide();
    });
    //新闻中心
    // $('.swiper-slide').mousemove(function () {
    //     $(this).width($('.swiper-slide').width() +100)
    //     $(this).height($('.swiper-slide').height())
    //
    //
    // })
    // $('.swiper-slide').mouseleave(function () {
    //     $(this).width($('.swiper-slide').width())
    //     $(this).height($('.swiper-slide').height())
    // })


    //团队介绍点击
    $('#a1').click(function () {
        if($(this).hasClass('demo_cur')){
            $(this).html('<i class="iconfont icon-hao"></i>');
            $('.a1-more').slideUp();
            $(this).removeClass('demo_cur')
        }else{
            $(this).html('<i class="iconfont icon-cha"></i>');
            $('.a1-more').slideDown();
            $(this).addClass('demo_cur')
        }

    });

    $('#a111').click(function () {
        if($(this).hasClass('demo_cur')){
            $(this).html('<i class="iconfont icon-hao"></i>');
            $('.a2-more').slideUp();
            $(this).removeClass('demo_cur')
        }else{
            $(this).html('<i class="iconfont icon-cha"></i>');
            $('.a2-more').slideDown();
            $(this).addClass('demo_cur')
        }

    });

    $('#a3').click(function () {
        if($(this).hasClass('demo_cur')){
            $(this).html('<i class="iconfont icon-hao"></i>');
            $('.a3-more').slideUp();
            $(this).removeClass('demo_cur')
        }else{
            $(this).html('<i class="iconfont icon-cha"></i>');
            $('.a3-more').slideDown();
            $(this).addClass('demo_cur')
        }

    });
    $('#a4').click(function () {
        if($(this).hasClass('demo_cur')){
            $(this).html('<i class="iconfont icon-hao"></i>');
            $('.a4-more').slideUp();
            $(this).removeClass('demo_cur')
        }else{
            $(this).html('<i class="iconfont icon-cha"></i>');
            $('.a4-more').slideDown();
            $(this).addClass('demo_cur')
        }

    });

    //服务右侧点击
    $('.fw-list ul li').on('click',function () {
        $(this).addClass('fw-cur').siblings().removeClass('fw-cur')
    });











    //导航弹出固定
    $.smartScroll = function (container, selectorScrollable) {
        // 如果没有滚动容器选择器，或者已经绑定了滚动时间，忽略
        if (!selectorScrollable || container.data('isBindScroll')) {
            return;
        }

        // 是否是搓浏览器
        // 自己在这里添加判断和筛选
        var isSBBrowser;

        var data = {
            posY: 0,
            maxscroll: 0
        };

        // 事件处理
        container.on({
            touchstart: function (event) {
                var events = event.touches[0] || event;

                // 先求得是不是滚动元素或者滚动元素的子元素
                var elTarget = $(event.target);

                if (!elTarget.length) {
                    return;
                }

                var elScroll;

                // 获取标记的滚动元素，自身或子元素皆可
                if (elTarget.is(selectorScrollable)) {
                    elScroll = elTarget;
                } else if ((elScroll = elTarget.parents(selectorScrollable)).length == 0) {
                    elScroll = null;
                }

                if (!elScroll) {
                    return;
                }

                // 当前滚动元素标记
                data.elScroll = elScroll;

                // 垂直位置标记
                data.posY = events.pageY;
                data.scrollY = elScroll.scrollTop();
                // 是否可以滚动
                data.maxscroll = elScroll[0].scrollHeight - elScroll[0].clientHeight;
            },
            touchmove: function (event) {
                // 如果不足于滚动，则禁止触发整个窗体元素的滚动
                if (data.maxscroll <= 0 || isSBBrowser) {
                    // 禁止滚动
                    event.preventDefault();
                }
                // 滚动元素
                var elScroll = data.elScroll;
                // 当前的滚动高度
                var scrollTop = elScroll.scrollTop();

                // 现在移动的垂直位置，用来判断是往上移动还是往下
                var events = event.touches[0] || event;
                // 移动距离
                var distanceY = events.pageY - data.posY;

                if (isSBBrowser) {
                    elScroll.scrollTop(data.scrollY - distanceY);
                    elScroll.trigger('scroll');
                    return;
                }

                // 上下边缘检测
                if (distanceY > 0 && scrollTop == 0) {
                    // 往上滑，并且到头
                    // 禁止滚动的默认行为
                    event.preventDefault();
                    return;
                }

                // 下边缘检测
                if (distanceY < 0 && (scrollTop + 1 >= data.maxscroll)) {
                    // 往下滑，并且到头
                    // 禁止滚动的默认行为
                    event.preventDefault();
                    return;
                }
            },
            touchend: function () {
                data.maxscroll = 0;
            }
        });

        // 防止多次重复绑定
        container.data('isBindScroll', true);
    };

});