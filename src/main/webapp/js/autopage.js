var Lazy = {
    Img: null,
    getY: function(b) {
        var a = 0;
        if (b && b.offsetParent) {
            while (b.offsetParent) {
                a += b.offsetTop;
                b = b.offsetParent;
            }
        } else {
            if (b && b.y) {
                a += b.y;
            }
        }
        return a; 
    },
    getX: function(b) {
        var a = 0;
        if (b && b.offsetParent) {
            while (b.offsetParent) {
                a += b.offsetLeft;
                b = b.offsetParent;
            }
        } else {
            if (b && b.x) {
                a += b.X;
            }
        }
        return a;
    },
    scrollY: function() {
        var a = document.documentElement;
        return self.pageYOffset || (a && a.scrollTop) || document.body.scrollTop || 0;
    },
    scrollX: function() {
        var a = document.documentElement;
        return self.pageXOffset || (a && a.scrollLeft) || document.body.scrollLeft || 0;
    },
    windowWidth: function() {
        var a = document.documentElement;
        return self.innerWidth || (a && a.clientWidth) || document.body.clientWidth;
    },
    windowHeight: function() {
        var a = document.documentElement;
        return self.innerHeight || (a && a.clientHeight) || document.body.clientHeight;
    },
    CurrentHeight: function() {
        return Lazy.scrollY() + Lazy.windowHeight();
    },
    CurrentWidth: function() {
        return Lazy.scrollX() + Lazy.windowWidth();
    },
    Load: function() {
        Lazy.Init();
        var d = Lazy.CurrentHeight();
        var a = Lazy.CurrentWidth();
        for (var _index = 0; _index < Lazy.Img.length; _index++) {
            if ($(Lazy.Img[_index]).attr("lazy") == undefined) {
                $(Lazy.Img[_index]).attr("lazy", "n");
            }
            if ($(Lazy.Img[_index]).attr("lazy") == "y") {
                continue;
            }
            $(Lazy.Img[_index]).bind("error",
            function() {
                if (this.id == "subject") {
                    $(this).attr("src", "");
                } else {
                    $(this).attr("src", "http://zwsc.ikanshu.cn/images/0000000.png");
                }
            });
            var b = Lazy.getY(Lazy.Img[_index]);
            var c = Lazy.getX(Lazy.Img[_index]);
            if (c < a) {
                if (b < d) {
                    Lazy.Img[_index].src = Lazy.Img[_index].getAttribute("data-src");
                    $(Lazy.Img[_index]).attr("lazy", "y");
                    Lazy.Img[_index].removeAttribute("data-src");
                }
            }
        }
    },
    LoadLbs: function() {
        Lazy.InitLbs();
        var d = Lazy.CurrentHeight();
        var a = Lazy.CurrentWidth();
        for (var _index = 0; _index < Lazy.Img.length; _index++) {
            if ($(Lazy.Img[_index]).attr("lazy") == undefined) {
                $(Lazy.Img[_index]).attr("lazy", "n");
            }
            if ($(Lazy.Img[_index]).attr("lazy") == "y") {
                continue;
            }
            $(Lazy.Img[_index]).bind("error",
            function() {
                if (this.id == "subject") {
                    $(this).attr("src", "");
                } else {
                    $(this).attr("src", "http://zwsc.ikanshu.cn/images/0000000.png");
                }
            });
            var b = Lazy.getY(Lazy.Img[_index]);
            var c = Lazy.getX(Lazy.Img[_index]);
            if (c < a) {
                if (b < d) {
                    Lazy.Img[_index].src = Lazy.Img[_index].getAttribute("data-src");
                    $(Lazy.Img[_index]).attr("lazy", "y");
                }
            }
        }
    },
    LoadX: function() {
        if (Lazy.Img == null) {
            Lazy.Init();
        }
        var a = Lazy.CurrentWidth();
        for (var _index = 0; _index < Lazy.Img.length; _index++) {
            if ($(Lazy.Img[_index]).attr("lazy") == undefined) {
                $(Lazy.Img[_index]).attr("lazy", "n");
            }
            if ($(Lazy.Img[_index]).attr("lazy") == "y") {
                continue;
            }
            $(Lazy.Img[_index]).bind("error",
            function() {
                if (this.id == "subject") {
                    $(this).attr("src", "");
                } else {
                    $(this).attr("src", "http://zwsc.ikanshu.cn/images/0000000.png");
                }
            });
            var b = Lazy.getX(Lazy.Img[_index]);
            if (b < a) {
                Lazy.Img[_index].src = Lazy.Img[_index].getAttribute("data-src");
                $(Lazy.Img[_index]).attr("lazy", "y");
            }
        }
    },
    Init: function() {
        var a = document.querySelectorAll("img[data-src]");
        Lazy.Img = a;
    },
    InitLbs: function() {
        var d = document.getElementsByTagName("img");
        var a = d.length;
        var b = 0;
        var e = new Array();
        for (var c = 0; c < a; c++) {
            if (d[c].getAttribute("data-src") != null) {
                e.push(d[c]);
            }
        }
        Lazy.Img = e;
    },
    Test: function() {
        Lazy.Init();
        alert(Lazy.CurrentHeight());
    }
};

(function() {
    var c = $("#autopbn");
    var b = c.attr("rel");
    var h = parseInt(c.attr("curpage"));
    var f = parseInt(c.attr("totalpage"));
    var d = 0;
    var a = true;
    var e = false;
    window.onscroll = function() {
        var j = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
        b = b.replace(/&page=\d+/, "&page=" + h);
        var i = "is_first_catelist_" + window.location.href;
        if (j + document.documentElement.clientHeight + 500 >= document.documentElement.scrollHeight && !d) {
            c.show();
            var k = "/zybook3/app/";
            if (typeof imgDomain != "undefined") {
                k = imgDomain;
            }
            var l = '正在载入&nbsp;<img src="/img/loading.gif">';
            l="<p class=\"loading\"><span class=\"relative\"><b></b>正在加载更多</span></p>";
            c.html(l);
            d = 1;
            setTimeout(g,500);
        }
    };
    function g() {
        if (e) {
            if (!a) {
                c.remove();
                window.onscroll = null;
                return;
            }
        } else {
            if (h > f) {
                c.remove();
                window.onscroll = null;
                return;
            }
            h++;
        }
        var i = b + "&t=" + parseInt(( + new Date() / 1000) / (Math.random() * 1000));
        $.ajax({
            type: "GET",
            url: i,
            timeout: 9000,
            success: function(k) {
                var j = k;
                if(j.length<100){
					c.remove();
					window.onscroll = null;
					return;
                }
                innerMoreHtml(j, ".pageLoad");
                hasMore = true;
                if (e) {
                    b = b.replace(/&page=\d+/, "&page=" + h);
                } else {
                    b = b.replace(/&page=\d+/, "&page=" + h);
                }
                d = 0;
                Lazy.Init();
            },
            error: function(k, j) {
                alert("网络超时，点击更多重试");
            },
        });
    }
})();

function innerMoreHtml(b, c) {
    var a = nice(b);
    var c = c ? c: ".book-items";
    $(c).append(a);
}