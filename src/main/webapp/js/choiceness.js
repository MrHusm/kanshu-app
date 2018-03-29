// $(function(){
//     // 自动轮播
//     var c = 0;
//     function run(){
//         c++;
//         c = (c==3)?0:c;
//         // 让c号图片显示，其他图片隐藏  让c号的li变色，其他的默认
//         $('#carouselCenter img').eq(c).show().siblings('img').hide();
//         // $('.carousel-box ul li').eq(c).addClass('red').siblings('li').removeClass('red');
//     }
//     timer = setInterval(run,5000);
    
//     // 鼠标移入li的效果
//     $('.carousel-box ul li').mouseover(function() {
//         clearInterval(timer);
//         // 获得当前li的序号
//         var c = $(this).index();
//         // 显示c号图片，其他隐藏  让c号li变色，其他默认
//         $('.carousel-box img').eq(c).fadeIn(2000).siblings('img').hide();
//         $('.carousel-box ul li').eq(c).addClass('red').siblings('li').removeClass('red');
//     });
//     // 鼠标移出事件
//     $('.carousel-box ul li,.carousel-box .arrow-right,.carousel-box .arrow-left').mouseout(function(){
//         timer = setInterval(run,5000);
//     });
// })

window.onload = function(){
    var startPos = {};//开始位置
    var endPos = {};//结束位置
    var scrollDirection;//滚动方向
    var timr;//定时器，后面控制速度会使用
    var touch;//记录触碰节点
    var index = 0;//记录滑动到第几张图片
    var oImg = document.getElementById("carouselCenter");
    var oCircle = document.getElementById("circle");
    var aCircle = oCircle.getElementsByTagName("li");
    var ImgWidth;//图片宽度
    var speed; //滑动速度
    var target;//目标
    function slide(index){
        for(var i=0;i<aCircle.length;i++){
            aCircle[i].className = '';
        }
        aCircle[index].className = 'active';
        ImgWidth = parseInt(oImg.offsetWidth / aCircle.length);
        target = -ImgWidth * index;
        timer = setInterval(function(){
            speed = speed > 0 ? Math.floor((target-oImg.offsetLeft) / 5) : Math.ceil((target-oImg.offsetLeft) / 5);
            if(target == oImg.offsetLeft){
                clearInterval(timer);
            }else{
                oImg.style.left = oImg.offsetLeft + speed +'px';
            }
        },30);
    }
    oImg.ontouchstart = function(event){
        touch = event.targetTouches[0];//取得第一个touch的坐标值
        startPos = {x:touch.pageX,y:touch.pageY}
        scrollDirection = 0;
    }
    oImg.ontouchmove = function(event){
        // 如果有多个地方滑动，我们就不发生这个事件
        if(event.targetTouches.length > 1){
            return
        }
        touch = event.targetTouches[0];
        endPos = {x:touch.pageX,y:touch.pageY}
        // 判断出滑动方向，向右为1，向左为-1
        scrollDirection = touch.pageX-startPos.x > 0 ? 1 : -1;
    }
    oImg.ontouchend = function(){
        if(scrollDirection == 1){
            if(index >= 1 && index <= aCircle.length-1){
                index--;
                slide(index);
            }else{
                return
            }
        }else if(scrollDirection == -1){
            if(index >=0 && index <= aCircle.length-2){
                index++;
                slide(index);
            }else{
                return
            }
        }
    }
    for(var i = 0;i < aCircle.length; i++){
        aCircle[i].index = i;
        aCircle[i].onclick = function(){
            slide(this.index);
        }
    }
}
