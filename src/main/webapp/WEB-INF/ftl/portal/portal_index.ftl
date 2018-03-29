<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/css/swiper.min.css"/>
    <title>精选</title>
</head>
<body>
<div class="content pageLoad">
</#if>
<#if pageFinder.data??>
    <#if pageFinder.pageNo == 1>
        <#assign type=0>
        <#list result as map>
            <#if map.type == 1>
                <#if map_index == 0>
                    <ul class="indexNav">
                        <li onclick="rankList(6,'热销')"><img src="/img/icon.png" alt=""  class="navImg1"/><p>热销</p></li>
                        <li onclick="rankList(2,'男生')"><img src="/img/icon2.png" alt="" class="navImg2"/><p>男生</p></li>
                        <li onclick="rankList(3,'女生')"><img src="/img/icon3.png" alt="" class="navImg3"/><p>女生</p></li>
                        <li onclick="rankList(4,'二次元')"><img src="/img/icon4.png" alt="" class="navImg4"/><p>二次元</p></li>
                        <li onclick="rankList(7,'完本')"><img src="/img/icon5.png" alt="" class="navImg5"/><p>完本</p></li>
                    </ul>
                </#if>
            <#--图书-->
                <#if map.type != type>
                <div class="bookList">
                <ul class="clearfix">
                    <#assign type=1>
                </#if>
                <li onclick="bookInfo(${map.data.book.bookId?c},'${map.data.book.title}')">
                    <div class="bookImg">
                        <img data-echo="${map.data.book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                    </div>
                    <div class="bookCon">
                        <h3>${map.data.book.title}</h3>
                        <p>
                            <#if map.data.book.intro??>
                                        ${map.data.book.intro?replace("　","")?replace("　","")}
                                    </#if>
                        </p>
                        <div class="bookInstr">
                            <span>${map.data.book.authorPenname}</span>
                            <div class="bookKey">
                                <b>${map.data.book.categorySecName}</b>
                                <b>${map.data.book.categoryThrName}</b>
                            </div>
                        </div>
                    </div>
                </li>
            <#else>
            <#--专题-->
                <#if map.data.type == 1>
                    <!--轮播图-->
                    <div class="swiper-container">
                        <div class="swiper-wrapper bannerImg">
                            <#list map.data.specialImgs as img>
                                <div class="swiper-slide">
                                    <#if img.type==1>
                                    <#--跳转图书详情页-->
                                        <img class="imgz" src="${img.imgUrl}" onclick="bookInfo(${img.target},${img.title})" alt="">
                                    <#elseif img.type==2>
                                    <#--跳转链接-->
                                        <img class="imgz" src="${img.imgUrl}" onclick="window.JSHandle.goToHtml(${img.target},${img.title},1,0);" alt="">
                                    <#elseif img.type==3>
                                    <#--跳转充值-->
                                        <#if version?? && version gte 120>
                                            <img class="imgz" src="${img.imgUrl}" onclick="window.JSHandle.goRecharge(false)" alt="">
                                        </#if>
                                    </#if>
                                </div>
                            </#list>
                        </div>
                        <!-- 如果需要分页器 -->
                        <div class="swiper-pagination"></div>
                    </div>
                    <#if map_index == 0>
                        <ul class="indexNav">
                            <li onclick="rankList(6,'热销')"><img src="/img/icon.png" alt=""  class="navImg1"/><p>热销</p></li>
                            <li onclick="rankList(2,'男生')"><img src="/img/icon2.png" alt="" class="navImg2"/><p>男生</p></li>
                            <li onclick="rankList(3,'女生')"><img src="/img/icon3.png" alt="" class="navImg3"/><p>女生</p></li>
                            <li onclick="rankList(4,'二次元')"><img src="/img/icon4.png" alt="" class="navImg4"/><p>二次元</p></li>
                            <li onclick="rankList(7,'完本')"><img src="/img/icon5.png" alt="" class="navImg5"/><p>完本</p></li>
                        </ul>
                    </#if>
                <#elseif map.data.type == 2>
                    <#if map.type != type>
                    </ul>
                    </div>
                        <#assign type=2>
                    </#if>
                    <div class="special">
                        <h3>${map.data.name}</h3>
                        <p>${map.data.content}</p>
                        <#list map.data.specialImgs as img>
                            <#if img.type==1>
                            <#--跳转图书详情页-->
                                <img class="specialImg" src="${img.imgUrl}" onclick="bookInfo(${img.target},${img.title})" alt="">
                            <#elseif img.type==2>
                            <#--跳转链接-->
                                <img class="specialImg" src="${img.imgUrl}" onclick="window.JSHandle.goToHtml(${img.target},${img.title},1,0);" alt="">
                            <#elseif img.type==3>
                            <#--跳转充值-->
                                <#if version?? && version gte 120>
                                    <img class="specialImg" src="${img.imgUrl}" onclick="window.JSHandle.goRecharge(false)" alt="">
                                </#if>
                            </#if>
                        </#list>
                    </div>
                <#elseif map.data.type == 3>
                    <#if map.type != type>
                        </ul>
                    </div>
                        <#assign type=2>
                    </#if>
                <div class="ad">
                    <#list map.data.specialImgs as img>
                        <div class="adImg">
                            <#if img.type==1>
                            <#--跳转图书详情页-->
                                <img src="${img.imgUrl}" onclick="bookInfo(${img.target},${img.title})" alt="">
                            <#elseif img.type==2>
                            <#--跳转链接-->
                                <img src="${img.imgUrl}" onclick="window.JSHandle.goToHtml(${img.target},${img.title},1,0);" alt="">
                            <#elseif img.type==3>
                            <#--跳转充值-->
                                <#if version?? && version gte 120>
                                    <img src="${img.imgUrl}" onclick="window.JSHandle.goRecharge(false)" alt="">
                                </#if>
                            </#if>
                        </div>
                    </#list>
                </div>
                </#if>
            </#if>
        </#list>
    </ul>
    </div>
    <#else>
    <#--图书-->
    <div class="bookList">
        <ul class="clearfix">
            <#list pageFinder.data as driveBook>
                <li <#if driveBook_index == 0>style="border-top: 1px solid #ddd" </#if> onclick="bookInfo(${driveBook.book.bookId?c},'${driveBook.book.title}')">
                    <div class="bookImg">
                        <img data-echo="${driveBook.book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                    </div>
                    <div class="bookCon">
                        <h3>${driveBook.book.title}</h3>
                        <p>
                            <#if driveBook.book.intro??>
                                ${driveBook.book.intro?replace("　","")?replace("　","")}
                            </#if>
                        </p>
                        <div class="bookInstr">
                            <span>${driveBook.book.authorPenname}</span>
                            <div class="bookKey">
                                <b>${driveBook.book.categorySecName}</b>
                                <b>${driveBook.book.categoryThrName}</b>
                            </div>
                        </div>
                    </div>
                </li>
            </#list>
        </ul>
    </div>
    </#if>
</#if>
<#if syn=='0'>
</div>
<div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/portalIndex.go?page=${pageFinder.pageNo+1}&syn=1&type=${type}" style="display:none;"></div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/swiper.min.js" ></script>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script>
    $(document).ready(function () {
        var swiper = new Swiper('.swiper-container', {
            pagination: {
                el: '.swiper-pagination',
                dynamicBullets: true,
            },
            disableOnInteraction: false,
            autoplay:true,
        });
    })

    function bookInfo(bookId,title) {
        var version = <#if version??>${version}<#else>null</#if>;
        if(version != null && version >= 120){
            window.JSHandle.openBookIntroduction(bookId);
        }else{
            var url = "/book/bookDetail.go?bookId="+bookId;
            window.JSHandle.goToHtml(url,title,1,1);
        }
    }

    function rankList(type,title){
        var url = "/portal/rankList.go?type="+type;
        window.JSHandle.goToHtml(url,title,0,0);
    }
</script>
</#if>
<script type="application/javascript">
    Echo.init({
        offset: 0,
        throttle: 0
    });
</script>
<#if syn=='0'>
</body>
</html>
</#if>