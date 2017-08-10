<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>充值</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script src="/js/jquery.min.js"></script>

</head>
<body>
<form id="form" action="/user/updateNickNameByUid.go" method="post">
    <input type="hidden" name="userId" id="userId" value="${user.userId}"/>
    <input type="hidden" name="oldName" id="oldName" value="${user.nickName}"/>

    <input type="text" name="nickName" id="nickName" value="${user.nickName}"/>
    <span id="errorTxt"></span>
    <input type="button" value="修改" onclick="update()">
</form>
<script>
    function update(){
        var nickName = $("#nickName").val();
        var oldName = $("#oldName").val();
        var userId = $("#userId").val();
        if(nickName == null || nickName == ""){
            alert("昵称不能为空");
        }
        if(nickName.length > 8){
            alert("昵称不能超过8个字");
        }
        $.ajax({
            type:"POST",
            url:"/user/findUserByCondition.go",
            data: {"nickName":nickName},
            dataType:"json",
            success: function(msg) {
                if(msg.status.code == 10007){
                    $("#form").submit();
                }else{
                    if(msg.data.user.nickName == oldName){
                        $("#form").submit();
                    }else{
                        alert("“昵称不能重复，再起一个吧");
                    }
                }

            }
        });
    }
</script>
</body>
</html>