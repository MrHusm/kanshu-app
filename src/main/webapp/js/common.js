$(function(){
    var width = $(window).width();
    if (width >= 640){
    	width=640;
    }
    var rem = width / 7.2;
    var rem2 = rem.toFixed(4);
    $("html").css("font-size", rem2+"px");
})