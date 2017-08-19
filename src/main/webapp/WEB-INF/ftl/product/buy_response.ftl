<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>充值并购买</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script src="/js/jquery.min.js"></script>

</head>
<body>
<script>
    var interval;

    $(function(){
        if ("${code}" == "0") {
            alert("购买成功");
            //调用客户端阅读方法打开阅读并下载
            //TODO
        }else if("${code}" == "-1") {
            alert("余额不够")
            //调用客户端方法通知客户端刷新账户信息
            //TODO
        }else if("${code}" == "-2") {
            alert("充值还未到账");
            //定时刷充值response表
            interval = window.setInterval(isSuccess, 2000);
        }else if("${code}" == "-3") {
            alert("系统异常");
        }
    });

    var num = 0;
    //成功函数
    function isSuccess() {
        num++;
        var url = '/book/ajaxBuyResponse??userId=${userId}&payType=${payType}&type=${type}&param=${param}&WIDout_trade_no=${WIDout_trade_no}";
        $.ajax({
            url: url,
            type: 'GET',
            timeout: 10000,
            msg:"",
            error: function(){},
            success: function(data){
                var json = JSON.parse(data);
                if (json.result == 0) {
                    alert("购买成功");
                    window.clearInterval(interval);
                    //调用客户端阅读方法打开阅读并下载
                    //TODO
                } else (json.result == -1) {
                    alert("余额不够")
                    window.clearInterval(interval);
                    //调用客户端方法通知客户端刷新账户信息
                    //TODO
                }else if(json.rsult == -2){
                    alert("充值还未到账");
                    //继续刷购买接口
                }else if(json.rsult == -3) {
                    alert("系统异常");
                    //调用客户端方法关闭页面
                    //TODO
                }
            }
        });

        if (num == 10) {
            window.clearInterval(interval);
        }
    }
</script>
</body>
</html>