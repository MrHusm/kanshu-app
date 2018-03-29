<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>
    <script src="/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <title>搜索</title>
</head>
<body>
<div class="content">
    <div class="bookList">
        <ul class="clearfix pageLoad">
</#if>
	<#if searchBooks??>
		<#list searchBooks as book>
            <li onclick="bookInfo(${book.bookId?c},'${book.title}')" <#if book_index == 0 && book.title == searchText>style="padding-bottom: 60px;" </#if>>
                <div class="bookImg">
                    <img data-echo="${book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                </div>
                <div class="bookCon">
                    <h3>${book.title}</h3>
                    <p><#if book.intro??>${book.intro?replace("　","")?replace("　","")}</#if>
                    </p>
                    <div class="bookInstr">
                        <span>${book.authorPenname}</span>
                        <div class="bookKey">
                            <b>${book.categorySecName}</b>
                            <b>${book.categoryThrName}</b>
                        </div>
                    </div>
                </div>
                <#if book_index == 0 && book.title == searchText>
                    <div class="searchbtn">
                        <input type="button" onclick="window.JSHandle.openRead(${book.bookId?c},'${book.title}','${book.coverUrl}',${maxChapterIndex?c})" value="开始阅读" />
                        <input type="button" onclick="window.JSHandle.openBookDir(${book.bookId?c},'${book.title}')" value="目录" />
                        <input type="button" onclick="window.JSHandle.addToShelf(${book.bookId?c},'${book.title}','${book.coverUrl}',${maxChapterIndex?c})" value="加入书架" />
                    </div>
                </#if>
            </li>
        </#list>
    </#if>
<#if syn=='0'>
    </ul>
    </div>
</div>
    <div class="bookLoad" id="autopbn" curpage="${page+1}" totalpage="100" rel="/search/search.go?page=${page+1}&searchText=${searchText}&syn=1" style="display:none;"></div>
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