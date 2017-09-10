<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>搜索书籍</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/sreach.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/common.js"></script>
</head>
<body>
    <#if type?? && type = '1'>
        <div class="notResult">
            <img src="/img/icon/notIcon.png" alt=""  class="notResultIcon"/>
            <span class="notResultTxt">很遗憾，没有相关的搜索结果</span>
        </div>
    <#else>
        <div class="tag newClear">
            <div class="tagPub"><span class="tagCont tagPubStyle1" onclick="search('都市')">都市</span></div>
            <div class="tagPub"><span class="tagCont tagPubStyle2" onclick="search('玄幻')">玄幻</span></div>
            <div class="tagPub"><span class="tagCont tagPubStyle3" onclick="search('言情')">言情</span></div>
            <div class="tagPub"><span class="tagCont tagPubStyle4" onclick="search('体育')">体育</span></div>
            <div class="tagPub"><span class="tagCont tagPubStyle1" onclick="search('军事')">军事</span></div>
            <div class="tagPub"><span class="tagCont tagPubStyle2" onclick="search('武侠')">武侠</span></div>
        </div>
    </#if>
    <div class="hr"></div>
    <article class="pageLoad">
    <div class="h6"><i class="h6Icon"></i>大家都在搜</div>
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
    <div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/search/searchIndex.go?page=${pageFinder.pageNo+1}&syn=1" style="display:none;"></div>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script>
    function bookInfo(bookId,title) {
        var url = "/book/bookDetail.go?bookId="+bookId;
        window.JSHandle.goToHtml(url,title,1,1);
    }

    function search(text){
        var url = "/search/search.go?searchText="+text;
        window.location.href=url;
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