<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>榜单</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/free.css">
    <link rel="stylesheet" href="/css/channel.css">
    <script src="/js/jquery.min.js"></script>
</head>
<body>
    <#if type == '9'>
        <div class="freeTxt">百本图书限时免费看！每日持续更新</div>
    </#if>
    <article class="pageLoad">
</#if>
        <#if pageFinder.data??>
            <#list pageFinder.data as driveBook>
                <section class="bookListBox" onclick="bookInfo(${driveBook.book.bookId?c},'${driveBook.book.title}')">
                    <img class="bookListImg" data-echo="${driveBook.book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                    <div class="bookList">
                        <div class="bookName">${driveBook.book.title}</div>
                        <div class="bookInfo">
                            <#if driveBook.book.intro??>
                                <#if driveBook.book.intro?replace("　","")?replace("　","")?length gt 40>
                                    ${driveBook.book.intro?replace("　","")?replace("　","")?substring(0,40)}...
                                <#else>
                                    ${driveBook.book.intro?replace("　","")?replace("　","")}
                                </#if>
                            </#if>
                        </div>
                        <div class="authorBox">
                            <div class="authorNmae">作者：${driveBook.book.authorPenname}</div>
                            <div class="bookGenre">
                                <div class="bookGenrePublic">${driveBook.book.categorySecName}</div>
                                <div class="bookGenrePublic bookGenrePublicStyle">${driveBook.book.categoryThrName}</div>
                            </div>
                        </div>
                    </div>
                </section>
            </#list>

        </#if>
<#if syn=='0'>
    </article>
    <div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/rankList.go?page=${pageFinder.pageNo+1}&syn=1&type=${type}" style="display:none;"></div>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script>
    function bookInfo(bookId,title) {
        var url = "/book/bookDetail.go?bookId="+bookId;
        window.JSHandle.goToHtml(url,title,1,1);
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