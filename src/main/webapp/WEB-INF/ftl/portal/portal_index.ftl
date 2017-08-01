<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>历史的今天</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="/css/amazeui.min.css">
    <link rel="stylesheet" href="/css/petshow.css?6">
    <link rel="stylesheet" href="/css/animate.min.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/amazeui.min.js"></script>
    <script src="/js/amazeui.lazyload.min.js"></script>
</head>
<body>
<div data-am-widget="tabs" class="am-tabs am-tabs-d2 am_news_tab">

    <div class="am-tabs-bd">
        <div data-tab-panel-0 class="am-tab-panel am-active">
            <div class="am-list-news-bd am_news_list_all">
                <div data-am-widget="titlebar" class="am-titlebar am-titlebar-default" style="border-bottom: 0px; margin-bottom: -10px">
                    <h2 class="am-titlebar-title ">
                        历史的今天
                    </h2>
                </div>
                <ul class="am-list">
                    <!--缩略图在标题左边-->
                    <#if pageFinder.data??>
                        <#list pageFinder.data as historyToday>
                            <#if historyToday.imgs?? && (historyToday.imgs?size> 0)>
                                <li onclick="showDetail('${historyToday.id?c}')" class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left am_list_li">
                                    <#list historyToday.imgs as img>
                                        <#if img_index == 0>
                                            <div class="am-u-sm-3 am-list-thumb am_list_thumb">
                                                <img src="http://120.25.125.138:8082/media/${img.fileName}" class="am_news_list_img" alt="${img.title}"/>
                                            </div>
                                        </#if>
                                    </#list>
                                    <div class=" am-u-sm-9 am-list-main am_list_main">
                                        <h3 class="am-list-item-hd am_list_title">
                                            ${historyToday.title}
                                        </h3>
                                        <div class="am-list-item-text am_list_item_text ">${historyToday.content}</div>
                                    </div>
                                </li>
                            <#else>
                                <li onclick="showDetail('${historyToday.id?c}')" class="am-g am-list-item-desced am_list_li">
                                    <div class=" am-list-main">
                                        <h3 class="am-list-item-hd am_list_title am_list_title_s">
                                            ${historyToday.title}
                                        </h3>
                                        <div class="am-list-item-text am_list_item_text">${historyToday.content}</div>
                                    </div>
                                </li>
                            </#if>
                        </#list>
                    </#if>
                </ul>
            </div>
        </div>
    </div>

</div>


<script>
    $(function(){
        $('.am_news_tab').css('min-height',$(window).height() - 52 - 220);
        if ($(window).width() < 600 ) {
            $('.am_list_item_text').each(
                    function(){
                        if($(this).text().length >= 26){
                            $(this).html($(this).text().substr(0,26)+'...');
                        }});}

    });

    function showDetail(id){
        window.location.href = "/historyToday/historyTodayDetail.go?id="+id;
    }
</script>
</body>
</html>
