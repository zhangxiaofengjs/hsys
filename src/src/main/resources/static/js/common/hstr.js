// "aaa{0},{1}".format("1","2")
String.prototype.format = function() {
    var str = String(this);
    
    for(var i = 0; i < arguments.length; i++) {
        var re = new RegExp('\\{' + (i) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};

String.prototype.startWith = function(strHead) {
    var str = String(this);
    
    return str.left(strHead.length) == strHead;
};

//"abc".left(2)=>"ab"
String.prototype.left = function(n) {
    var str = String(this);
    
    return str.substr(0, n);
};

String.prototype.padLeft = function (len, charStr) {
    var s = this + '';
    return new Array(len - s.length + 1).join(charStr,  '') + s;
}

String.prototype.padRight = function (len, charStr) {
    var s = this + '';
    return s + new Array(len - s.length + 1).join(charStr,  '');
}

// Jquery中带点中括号的选择器替换 a.b =>  a\.b a[0]=a\[0\]
String.prototype.safeJqueryId = function() {
    var str = String(this);
    str = str.replace(/\./g, "\\\.").replace(/\[/g, "\\\[").replace(/\]/g, "\\\]");
    
    return str;
};

String.prototype.compareNumber = function() {
    var str = String(this);
    var str2 = String(arguments[0]);
    
    return Number(str) - Number(str2);
};