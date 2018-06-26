<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="/css/base.css" />
    <link rel="stylesheet" type="text/css" href="/css/public.css"/>
    <script src="/js/zepto.js" type="text/javascript" charset="utf-8"></script>
    <title>搜索</title>
</head>
<body>
<div class="searchTop">
    <img src="/images/icon/backing.png" class="leftBack" onclick="window.history.go(-1)"/>
    <div class="topSearch"><input type="text" id="searchText" value="<#if searchText??>${searchText}</#if>" placeholder="<#if searchDefault??>${searchDefault}</#if>"/></div>
    <input type="button" value="搜索" onclick="search()" class="clickSearch" />
</div>

<div  style="margin: 28% auto 0;">
    <img src="/images/icon/notIcon.png" alt="" style="width: 30%;display: block;margin: 0 auto;"/>
    <span style="display:block;color: #999999;font-size: 18px;text-align: center;">很遗憾，没有相关的搜索结果</span>
</div>

<#include "../common/common.ftl"/>
<script type="text/javascript">
    //搜索词放入cookie
    var historyWords = ddbase.getCookie("historyWords");
    if(historyWords != null && historyWords != ''){
        if(historyWords.indexOf('${searchText}|') == -1){
            historyWords = '${searchText}|' + historyWords;
        }
    }else{
        historyWords = '${searchText}|';
    }
    ddbase.setCookie("historyWords",historyWords,8760);

    function search(){
        var searchText = $.trim($("#searchText").val());
        if(searchText == null || searchText == ''){
            searchText = $("#searchText").attr("placeholder");
        }
        if(searchText == null || searchText == ''){
            $(".sureDiv p").html("请输入搜索词");
            $('.shadowBox').show();
        }else{
            var url = "/search/search.go?searchText="+ encodeURI(encodeURI(searchText));
            ddbase.toH5Page(url);
        }
    }
</script>
</body>
</html>