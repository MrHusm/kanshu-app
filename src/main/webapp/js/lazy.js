var Lazy = {Img: null,getY: function(b) {
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
},getX: function(b) {
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
},scrollY: function() {
    var a = document.documentElement;
    return self.pageYOffset || (a && a.scrollTop) || document.body.scrollTop || 0;
},scrollX: function() {
    var a = document.documentElement;
    return self.pageXOffset || (a && a.scrollLeft) || document.body.scrollLeft || 0;
},windowWidth: function() {
    var a = document.documentElement;
    return self.innerWidth || (a && a.clientWidth) || document.body.clientWidth;
},windowHeight: function() {
    var a = document.documentElement;
    return self.innerHeight || (a && a.clientHeight) || document.body.clientHeight;
},CurrentHeight: function() {
    return Lazy.scrollY() + Lazy.windowHeight();
},CurrentWidth: function() {
    return Lazy.scrollX() + Lazy.windowWidth();
},Load: function(d) {
    Lazy.Init();
    var f = Lazy.CurrentHeight();
    var b = Lazy.CurrentWidth();
    for (_index = 0; _index < Lazy.Img.length; _index++) {
        var a = Lazy.Img[_index];
        alert(a);
        if ($(a).attr("lazy") == undefined) {
            $(a).attr("lazy", "n");
        }
        if ($(a).attr("lazy") == "y") {
            continue;
        }
        $(a).bind("error", function() {
            if (this.id == "subject") {
                $(this).attr("src", "");
            } else {
                $(this).attr("src", "/images/0000000.jpg");
            }
        });
        if (d == undefined || d == "" || d == null) {
            var c = Lazy.getY(a);
            var e = Lazy.getX(a);
            if (e < b) {
                if (c < f) {
                    a.src = a.getAttribute("data-src");
                    $(a).attr("lazy", "y");
                    a.removeAttribute("data-src");
                }
            }
        } else {
            if (d == "x") {
                var c = Lazy.getX(a);
                if (c < b) {
                    a.src = a.getAttribute("data-src");
                    $(a).attr("lazy", "y");
                }
            }
        }
    }
},Init: function() {
    var a = document.querySelectorAll("img[data-src]");
    Lazy.Img = a;
},};