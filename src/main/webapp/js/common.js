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

var common = (function ($) {
    var _self = {}

    _self.getSessionStorage = function (key) {
        var value = sessionStorage.getItem(key);
        return value && JSON.parse(value);
    }

    _self.setSessionStorage = function (key, value) {
        if (typeof value === "object") {
            value = JSON.stringify(value);
        }
        sessionStorage.setItem(key, value);
    }

    _self.removeSessionStorage = function (key) {
        sessionStorage.removeItem(key);
    }

    /**
     * 采用正则表达式获取地址栏参数
     * @param name
     * @returns {*}
     * @constructor
     */
    _self.getQueryString = function (name) {
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
    _self.randomString = function (len) {
        len = len || 32;
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
        /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
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
    _self.uuid = function (len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;

        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
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
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }

        return uuid.join('');
    };

    _self.ajax = function (opts) {
        var loadingOfAjaxIndex = layer.load(0, {shade: false});
        var defaults = {
            type: "POST",
            dataType: "json",
            contentType: "application/json"
        };

        if (opts.data && "object" === typeof opts.data) {
            opts.data = JSON.stringify(data);
        }

        return $.ajax($.extend({}, defaults, opts)).then(function (data, textStatus, jqXHR) {
            if (data.status === 'notlogin') {
                //ajax请求，发现session过期，重新刷新页面，跳转到登录页面
                $.reLogin();
            }
            if (data.status === 'unauthorized') {
                layer.alert(data.msg, {icon: 2});
                return $.Deferred().reject().promise();
            }

            return $.Deferred().resolve(data).promise();
        }, function () {
            layer.alert("数据加载失败，请您稍后刷新页面，如仍然有问题请联系管理员。", {icon: 2});
            return $.Deferred().reject().promise();
        }).always(function () {
            layer.close(loadingOfAjaxIndex);
        });
    };

    return _self;
})(jQuery);


/**
 * 扩展ajax,设置默认contentType
 */
jQuery.extend({
    postJson: function (url, data, contentType) {
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
$.fn.serializeJson = function () {
    var inputs = $(this).find("input,textarea,select");
    var o = {};
    $.each(inputs, function (i, n) {
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
};

// $.ajaxSetup({
//     dataFilter: function (data, type) {
//         var json = null;
//         try {
//             json = JSON.parse(data);
//             type = 'json';
//         } catch (e) {
//             console.log(e);
//         }
//         if (type === 'json' && json !== null) {
//             if (json.status === 'notlogin') {//ajax请求，发现session过期，重新刷新页面，跳转到登录页面
//                 $.reLogin();
//                 return null;
//             } else if (json.status === 'unauthorized') {
//                 //信息框-例1
//                 layer.alert(json.msg, {icon: 2});
//                 return null;
//             } else {
//                 return data;
//             }
//         } else {
//             return data;
//         }
//     },
//     cache: false,
//     error: function (XMLHttpRequest, textStatus, errorThrown) {
//         console.log(errorThrown);
//         console.log(XMLHttpRequest);
//         layer.alert("数据加载失败，请您稍后刷新页面，如仍然有问题请联系管理员。");
//     }
// });

$(function () {
    var _ajax = $.ajax;
    var loadingOfAjaxIndex;
    $.ajax = function (opt) {
        var _opt = $.extend(opt, {
            beforeSend: function (jqXHR) {
                loadingOfAjaxIndex = layer.load(0, {shade: false});
            }
        });
        return _ajax(_opt).then(function (data, textStatus, jqXHR) {
            try {
                if (data.status === 'notlogin') {//ajax请求，发现session过期，重新刷新页面，跳转到登录页面
                    $.reLogin();
                    return $.Deferred().reject().promise();
                } else if (data.status === 'unauthorized') {
                    //信息框-例1
                    layer.alert(data.msg, {icon: 2});
                    return $.Deferred().reject().promise();
                } else {
                    return $.Deferred().resolve(data).promise();
                }
            } catch (e) {
                console.log(e);
                return $.Deferred().reject().promise();
            }
        }, function (jqXHR, textStatus, errorThrown) {
            layer.alert("数据加载失败，请您稍后刷新页面，如仍然有问题请联系管理员。", {icon: 2});
            return $.Deferred().reject().promise();
        }).always(function () {
            layer.close(loadingOfAjaxIndex);
        });
    }
});

