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
    <link rel="stylesheet" type="text/css" href="../css/swiper.min.css"/>
    <script src="/js/zepto.js" type="text/javascript" charset="utf-8"></script>
    <title>书库</title>
</head>
<body>
    <div class="content">
        <#if data??>
            <ul class="topNav">
                <#list data as map>
                    <#list map?keys as key>
                        <li id="${key}" <#if map_index==0>class="active"</#if>>
                            <#if key == '男'>男频
                            <#elseif key == '女'>女频
                            <#elseif key == '其他'>出版
                            <#else>${key}
                            </#if>
                        </li>
                    </#list>
                </#list>
            </ul>
            <ul class="manList clearfix">
                <#list data as map>
                    <#list map?keys as key>
                        <#list map[key] as category>
                            <#if category.categoryImg?? && category.categoryImg.imgUrl??>
                                <li class="${key} <#if map_index==0> current</#if>" onclick="categoryBooks(${category.categoryId?c},'${category.name}')">
                                    <div class="manCon">
                                        <h3>${category.name}</h3>
                                        <p>${category.bookNum?c}册</p>
                                    </div>
                                    <div class="mamImg"><img src="${category.categoryImg.imgUrl}"/></div>
                                </li>
                            </#if>
                        </#list>
                    </#list>
                </#list>
            </ul>
        </#if>
    </div>
<script>
    function categoryBooks(categoryId,title){
        var url = "/portal/categoryBooks.go?categoryId="+categoryId;
        window.JSHandle.goToHtml(url,title,0,0);
    }

    $('.topNav li').click(function(){
        $(".topNav li").removeClass('active');
        $(this).addClass('active');
        $('.manList li').removeClass('current');
        var className = "."+ $(this).attr("id");
        $(className).addClass('current');
    })

</script>
</body>
</html>