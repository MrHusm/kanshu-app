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
<#if records??>
  <#list records as record>
    ${record.title}-${record.charge}-${record.createDate}-${record.bookId}


  </#list>
</#if>
</body>
</html>
