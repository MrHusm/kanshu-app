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
    <article class="pageLoad">
</#if>
	<#if searchBooks??>
		<#list searchBooks as book>
            <section class="bookListBox" onclick="bookInfo(${book.bookId},'${book.title}')">
                <div class="bookListImg">
                    <img data-echo="${book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                </div>
                <div class="bookList">
                    <div class="bookName">${book.title}</div>
                    <div class="bookInfo">
						<#if book.intro?length gt 40>
							${book.intro?substring(0,40)}...
						<#else>
							${book.intro}
						</#if>
                    </div>
                    <div class="authorBox">
                        <div class="authorNmae">${book.authorPenname}</div>
                        <div class="bookGenre">
                            <div class="bookGenrePublic">${book.categorySecName}</div>
                            <div class="bookGenrePublic bookGenrePublicStyle">${book.categoryThrName}</div>
                        </div>
                    </div>
                </div>
            </section>
		</#list>
    <#else>
        <script type="application/javascript">
            $("#autopbn").remove();
            window.onscroll = null;
        </script>
	</#if>
<#if syn=='0'>
    </article>
    <div class="bookLoad" id="autopbn" curpage="${pageNo+1}" totalpage="100" rel="/search/search.go?pageNo=${pageNo+1}&searchText=${searchText}&syn=1" style="display:none;"></div>
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