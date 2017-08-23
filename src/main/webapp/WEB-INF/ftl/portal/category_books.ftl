<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <title>春光免费小说-分类2</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/free.css">
</head>
<body class="newBg">
<div class="bookHeader notBh">
    <img class="bookHeaderImg" src="/img/icon/return.png" alt="" />
    <div>${category.name}</div>
</div>
<div class="classifyBox2 newClear">
    <div class="newClear">
        <div class="classifyBoxTit">${category.name}</div>
        <ul class="classifyBoxUl newClear">
            <#if childCategorys??>
                <#list childCategorys as childCategory>
                    <li>${childCategory.name}</li>
                </#list>
            </#if>
        </ul>
    </div>
    <div class="newClear">
        <div class="classifyBoxTit">不限</div>
        <ul class="classifyBoxUl newClear">
            <li>完结</li>
            <li>连载</li>
        </ul>
    </div>
</div>
<div class="hr"></div>
<article class="article pageLoad">
</#if>
    <#if pageFinder??>
        <#list pageFinder.data as book>
            <section class="bookListBox">
                <img class="bookListImg" src="${book.coverUrl}">
                <div class="bookList">
                    <div class="bookName">${book.title}</div>
                    <div class="bookInfo">
                        <#if book.intro?length gt 50>
                            ${book.intro?substring(0,50)}...
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
    </#if>
<#if syn=='0'>
</article>
<div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/categoryBooks.go?&page=${pageFinder.pageNo+1}&syn=1&categoryId=${categoryId}&childCategoryId=${childCategoryId}&isFull=${isFull}" style="display:none;"></div>
</body>
</html>
</#if>