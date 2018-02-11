<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>精选</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/index.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/baidu-statis.js"></script>
</head>
<body>
    <aside class="newBookBox">
    <#if boyImg??>
        <ul class="bookPopular" onclick="rankList(2,'男生')">
            <li class="newbook">男生最爱</li>
            <li class="newInfo">都市玄幻万本爽文</li>
            <li class="newbookBox">
                <img class="newbookImg" data-echo="${boyImg}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
            </li>
        </ul>
    </#if>
    <#if girlImg??>
        <ul class="bookPopular" onclick="rankList(3,'女生')">
            <li class="newbook">女生频道</li>
            <li class="newInfo">言情后宫跌宕起伏</li>
            <li class="newImg">
                <img class="newbookImg" data-echo="${girlImg}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
            </li>
        </ul>
    </#if>
    <#if secImg??>
        <ul class="bookPopular" onclick="rankList(4,'二次元')">
            <li class="newbook">二次元</li>
            <li class="newInfo">初音未来唯美世界</li>
            <li class="newImg">
                <img class="newbookImg" data-echo="${secImg}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
            </li>
        </ul>
    </#if>
    </aside>
    <div class="hr"></div>
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
                                ${driveBook.book.intro?replace("　","")?replace("　","")}
                            </#if>
                        </div>
                        <div class="authorBox">
                            <div class="authorNmae">${driveBook.book.authorPenname}</div>
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
    <div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/portalIndex.go?page=${pageFinder.pageNo+1}&syn=1&type=${type}" style="display:none;"></div>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script>
    function bookInfo(bookId,title) {
        var url = "/book/bookDetail.go?bookId="+bookId;
        window.JSHandle.goToHtml(url,title,1,1);
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