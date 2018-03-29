<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>榜单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="/css/main.css" />
    <script src="/js/jquery.min.js"></script>
</head>
<body>
<div class="content">
    <#if type == '9'>
        <div class="topAd"><img src="/img/free.jpg"/></div>
    </#if>
    <div class="bookList">
        <ul class="clearfix pageLoad">
</#if>
    <#if pageFinder??>
        <#list pageFinder.data as driveBook>
            <li onclick="bookInfo(${driveBook.book.bookId?c},'${driveBook.book.title}')" <#if pageFinder.pageNo != 1 && driveBook_index ==0>style="border-top: 1px solid #ddd" </#if> <#if !driveBook_has_next>style="border-bottom: none;"</#if>>
                <div class="bookImg">
                    <img data-echo="${driveBook.book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                </div>
                <div class="bookCon">
                    <h3>${driveBook.book.title}</h3>
                    <p><#if driveBook.book.intro??>${driveBook.book.intro?replace("　","")?replace("　","")}</#if>
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
    </#if>
<#if syn=='0'>
        </ul>
    </div>
</div>
    <div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/rankList.go?page=${pageFinder.pageNo+1}&syn=1&type=${type}" style="display:none;"></div>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script>
    function bookInfo(bookId,title) {
        var version = <#if version??>${version}<#else>null</#if>;
        if(version != null && version >= 120){
            window.JSHandle.openBookIntroduction(bookId);
        }else{
            var url = "/book/bookDetail.go?bookId="+bookId;
            window.JSHandle.goToHtml(url,title,1,1);
        }
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