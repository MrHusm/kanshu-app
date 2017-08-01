<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>历史的今天</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="/css/amazeui.min.css">
    <link rel="stylesheet" href="/css/petshow.css">
    <link rel="stylesheet" href="/css/animate.min.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/amazeui.min.js"></script>
    <script src="/js/amazeui.lazyload.min.js"></script>
</head>
<body>

<article data-am-widget="paragraph" class="am-paragraph am-paragraph-default article_nr"
         data-am-paragraph="{ tableScrollable: true, pureview: true }">
    <h1 class="article_nr_title">${historyToday.title}</h1>
    <div class="article_nr_content">
        <#if (historyToday.imgs?size >0)>
            <p>
            <#list historyToday.imgs as img>
                <#if  (historyToday.content?length >200*(img_index+1))>
                    <div class="am-gallery-item">
                        <img src="http://120.25.125.138:8082/media/${img.fileName}">
                        <font size="2" class="am-gallery-title am-center" style="text-align: center;padding-bottom: 5px">${img.title}</font>
                    </div>
                    ${historyToday.content?substring(200 * img_index,200 * (img_index+1))}
                </#if>
            </#list>
            <#if (historyToday.content?length > (200 * historyToday.imgs?size))>
                ${historyToday.content?substring(200 * (historyToday.imgs?size))}
            </#if>
            </p>
        <#else>
            <p>${historyToday.content}</p>
        </#if>

    </div>
</article>

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
</script>
</body>
</html>