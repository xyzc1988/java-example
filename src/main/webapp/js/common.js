// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 采用正则表达式获取地址栏参数
 * @param name
 * @returns {*}
 * @constructor
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}


/**
 * JS生成随机字符串
 * @param len
 * @returns {string}
 */
function randomString(len) {
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

/**
 *  JS生成 UUID
 * @param len
 * @param radix
 * @returns {string}
 */
function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;

    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
        // rfc4122, version 4 form
        var r;

        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random()*16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }

    return uuid.join('');
}

/**
 * 扩展ajax,设置默认contentType
 */
jQuery.extend({
    postJson: function (url,data,contentType) {
        return $.ajax({
            url: url,
            type: "POST",
            data: "object" === typeof data ? JSON.stringify(data) : data,
            dataType: "json",
            contentType: contentType || "application/json"
        });
    }
});

/**
 * 将form表单序列化为JSON对象
 * @returns {{}}
 */
$.fn.serializeJson = function() {
    var inputs = $(this).find("input,textarea,select");
    var o = {};
    $.each(inputs, function(i, n) {
        switch (n.nodeName.toUpperCase()) {
            case "INPUT":
                if ($(n).is(":checkbox")) {
                    if ($(n).is(":checked")) {
                        o[n.name] = true;
                    } else {
                        o[n.name] = false;
                    }
                } else if ($(n).is(":radio")) {
                    if ($(n).is(":checked")) {
                        o[n.name] = n.value;
                    }
                } else if ($(n).is(":file")) {
                    return true;
                } else {
                    if (n.name) {
                        o[n.name] = n.value;
                    }
                }
                break;
            case "TEXTAREA":
                o[n.name] = $(n).val();
                break;
            case "SELECT":
                o[n.name] = n.value;
                break;
        }
    });
    return o;
}

/*function serializeData(formId, names) {
    var ta = [];
    if (names && Object.prototype.toString.call(names) == "[object Array]") {
        var sa = $('#' + formId).serializeArray();
        for (var i = 0; i < sa.length; i++) {
            for (var j = 0, len = names.length; j < len; j++) {
                if (sa[i].name == names[j]) {
                    // statement
                    ta.push(sa[i].name + "=" + sa[i].value);
                }
            }
        };
    };
    var str = sa ? ta.join("&") : decodeURIComponent($('#' + formId).serialize(), true);
    return str ? str.replace(/(\w*)=([^\&]*)/g, '"$1":"$2"').replace(/&/g, ',') : str;
}*/


function paginationFun(className,opt,ck){    //className 支持class,id , 属性[pag-show=pagination]读取样式
    var params={
        coping:true,
        homePage:'首页',
        endPage:'末页',
        prevContent:'上页',
        nextContent:'下页',
        totalshow:true,
        isHide:true,
        callback:function(api){
            ck && ck(api);
        }
    };
    $.extend(params,opt)
    $(className).pagination(params);

/*    参数配置说明：
    pageCount:	9,   //	总页数
    totalData:	0,   // 数据总条数
    totalshow:false,         //显示总页数条数 true  --------------new add 2017/7/31
    current:1,      //	当前第几页
    showData:0,     //	每页显示的条数
    prevCls:'prev', //	上一页class
    nextCls:'next', //下一页class
    prevContent:'<', //上一页节点内容
    nextContent:'>',  //下一页节点内容
    activeCls:	'active', //当前页选中状态class名
    count:	3,   //当前选中页前后页数
    coping:false, //是否开启首页和末页，值为boolean
    isHide:false, //总页数为0或1时隐藏分页控件
    keepShowPN:false, //是否一直显示上一页下一页
    homePage:'', //	首页节点内容，默认为空
    endPage:'',  //尾页节点内容，默认为空
    jump:false, //	是否开启跳转到指定页数，值为boolean类型
    jumpIptCls:'jump-ipt', //	文本框内容
    jumpBtnCls:'jump-btn', //	跳转按钮class
    jumpBtn:'跳转', //跳转按钮文本内容
    callback:function(){}  //回调函数，参数"index"为当前页*/

    /*api接口:	getPageCount()	无	获取总页数
                setPageCount(page)	page：页数	设置总页数
                getCurrent()	无	获取当前页
                filling()	无	填充数据，参数为页数*/
}