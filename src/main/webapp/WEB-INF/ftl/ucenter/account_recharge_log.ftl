<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>充值记录</title>
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
            充值记录
        </h2>
    </div>
<ul class="am-list pageLoad">
</#if>
    <!--缩略图在标题左边-->
<#if pageFinder.data??>
  <#list pageFinder.data as userAccountLog>
        <li class="am-g am-list-item-desced am_list_li">
            <div class=" am-list-main">
                <h3 class="am-list-item-hd am_list_title am_list_title_s">
                ${userAccountLog.unitMoney}
                </h3>
                <div class="am-list-item-text am_list_item_text">${userAccountLog.unitVirtual}</div>
            </div>
        </li>
  </#list>
  <#if syn=='0'>
      <p class="v3_btmload" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/portalIndex.go?&page=${pageFinder.pageNo+1}&syn=1" style="display:none;"></p>
  </#if>
</#if>
<#if syn=='0'>
</ul>
</div>
</div>
</div>
</div>

<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
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
</#if>
