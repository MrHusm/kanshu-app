<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>春光免费小说-搜索书籍</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/sreach.css">
    <script src="/js/jquery-1.11.1.min.js"></script>
    <script src="/js/common.js"></script>
</head>
<body>
    
    <article>
    
    		<#if searchBooks??>
                    <#list searchBooks as book>
                    	<section class="bookListBox">
            			<img class="bookListImg" src="${book.coverUrl}">
            			<div class="bookList">
				                <div class="bookName">${book.title}</div>
				                <div class="bookInfo">${book.intro}</div>
				                <div class="authorBox">
				                    <div class="authorNmae">${book.authorName}</div>
				                    <div class="bookGenre">
				                        <div class="bookGenrePublic">${book.categorySecName}</div>
				                        <div class="bookGenrePublic bookGenrePublicStyle">${book.categoryThrName}</div>
				                    </div>
				                </div>
				            </div>
				        </section>
                    </#list>
            </#if>
        
    </article>
    <div class="bookLoad">正努力加载更多...</div>
</body>
</html>