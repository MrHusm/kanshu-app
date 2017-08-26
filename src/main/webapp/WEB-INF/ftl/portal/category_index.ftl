<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>书库</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/bookStack.css">
    <script src="/js/jquery.min.js"></script>
</head>
<body>
    <aside class="newBookBox">
        <ul class="bookPopular" onclick="rankList(1,'全站畅销')">
            <li class="newbook">全站畅销</li>
            <li class="newbookBox"><img class="newbookImg" src="/img/other/book1.jpg"></li>
        </ul>
        <ul class="bookPopular" onclick="rankList(1,'完结精选')">
            <li class="newbook">完结精选</li>
            <li class="newImg"><img class="newbookImg" src="/img/other/book2.jpg"></li>
        </ul>
        <ul class="bookPopular" onclick="rankList(1,'重磅新书')">
            <li class="newbook">重磅新书</li>
            <li class="newImg"><img class="newbookImg" src="/img/other/book3.jpg"></li>
        </ul>
    </aside>

    <div class="hr"></div>

    <article>
        <#if data??>
            <#list data as map>
            <#list map?keys as key>
                <section class="freeDivision newClear">
                    <div class="freeDivisionNmae bdlColor">
                        <i class="freeDivisionNmaeIcon freeDivisionNmaeColor1"></i>${key}
                    </div>
                    <div class="tag newClear">
                        <#list map[key] as category>
                            <div class="tagPub"><span class="tagCont tagPubStyle1" onclick="categoryBooks(${category.categoryId?c},'${category.name}')">${category.name}</span></div>
                        </#list>
                    </div>
                </section>
            <div class="hr"></div>
           </#list>
            </#list>
        </#if>
    </article>


<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script>
    function categoryBooks(categoryId,title){
        var url = "/portal/categoryBooks.go?categoryId="+categoryId;
        window.JSHandle.goToHtml(url,title,0,0);
        //window.location.href = url;
    }

    function rankList(type,title){
        var url = "/portal/rankList.go?type="+type;
        window.JSHandle.goToHtml(url,title,0,0);
    }
</script>
</body>
</html>