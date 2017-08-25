<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>精选</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/reset_5.css">
    <script src="/js/jquery.min.js"></script>
</head>
<body>
    <aside class="newBookBox">
        <ul class="bookPopular" onclick="rankList(1)">
            <li class="newbook">重磅新书</li>
            <li class="newInfo">最新大神著作</li>
            <li class="newbookBox"><img class="newbookImg" src="/img/other/book1.jpg"></li>
        </ul>
        <ul class="bookPopular" onclick="rankList(2)">
            <li class="newbook">男生最爱</li>
            <li class="newInfo">都市玄幻万本爽文</li>
            <li class="newImg"><img class="newbookImg" src="/img/other/book2.jpg"></li>
        </ul>
        <ul class="bookPopular" onclick="rankList(3)">
            <li class="newbook">女生最爱</li>
            <li class="newInfo">言情后宫跌宕起伏</li>
            <li class="newImg"><img class="newbookImg" src="/img/other/book3.jpg"></li>
        </ul>
    </aside>
    <div class="hr"></div>
    <article class="pageLoad">
</#if>
        <#if pageFinder.data??>
            <#list pageFinder.data as driveBook>
                <section class="bookListBox" onclick="bookInfo(${driveBook.book.bookId})">
                    <img class="bookListImg" src="${driveBook.book.coverUrl}" onerror="javascript:this.src='/img/other/book7.jpg';">
                    <div class="bookList">
                        <div class="bookName">${driveBook.book.title}</div>
                        <div class="bookInfo">
                            <#if driveBook.book.intro?length gt 50>
                                ${driveBook.book.intro?substring(0,50)}...
                            <#else>
                                ${driveBook.book.intro}
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
    <div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/portalIndex.go?&page=${pageFinder.pageNo+1}&syn=1" style="display:none;"></div>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script>
    function bookInfo(bookId) {
        var url = "/book/bookDetail.go?bookId="+bookId;
        window.JSHandle.goToHtml(url,"图书详情页"，1,1);
    }

    function rankList(type){
        var url = "/portal/rankList.go?type="+type;
        window.JSHandle.goToHtml(url,"重磅"，0,0);
    }
</script>
</body>
</html>
</#if>