/*
 * jqPagination
 * @author
 * 参照 https://github.com/sujing910206/ajax-pager
 *
 */
/**
 * jqPagination
 * @author zhangcheng
 * @参照 https://github.com/sujing910206/ajax-pager
 */
;(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // Node / CommonJS
        factory(require('jquery'));
    } else {
        // Globals
        factory(jQuery);
    }
}(function ($) {
    "use strict";
    var defaults = {
        pageSize: 10,
        pageIndex: 0,
        totalCount: 0,
        totalPage: 0,
        displayCount: 4,		//连续分页主体部分显示的分页条目数
        edgeCount: 1,           //省略号边缘分页个数
        preText: "上页",
        nextText: "下页",
        firstText: "First",
        lastText: "Last",
        showFLBtn: true,
        showJump: true,
        paginationClass: "pagination",
        activeClass: "active",
        disableClass: "disabled",

        /*ajax配置*/
        url: "",
        type: "POST",
        dataType: "JSON",
        contentType: "application/json",
        params: {},
        pageParams: null,
        beforeSend: null,
        success: null,
        complete: null,
        error: function () {
            alert("抱歉,请求出错,请重新请求！");
        },
        totalName: 'totalCount',
        traditional: false

    };
    var Pagination = function () {
        this.pagerElement = null,
            this.commonHtmlText = {
                spanHtml: "<span class='{0}'>{1}</span>",
                pageIndexHtml: "<a href='javascript:void(0)' class='page-btn' data-page='{0}'>{1}</a>",
                rightHtml: "<span class='page-info'>&nbsp;共{0}页&nbsp;</span> <div>" +
                "<input class='jump-ipt' value={1} /><a href='javascript:void(0)' class='jump-btn'>跳转</a>" +
                "</div>",
                clearFloatHtml: "<div style='clear:both;'></div>",
                stringEmpty: ""
            }
    }
    Pagination.prototype = {
        init: function (obj, option) {
            var _self = this;
            _self.opts = $.extend({}, defaults, option);
            _self.pagerElement = obj;
            $(_self.pagerElement).addClass(_self.opts.paginationClass);
            _self.doPage(_self.opts.pageIndex);

            return _self.opts;
        },
        doPage: function (index) {
            var _self = this;

            _self.opts.pageIndex = index;
            /*
             * 参数形式:
             * {
             *   pageIndex:,
             *   pageSize:,
             *   params:,
             * }
             */
            $.extend(_self.opts.params || {}, {
                pageIndex: _self.opts.pageIndex,
                pageSize: _self.opts.pageSize || 10
            });

            $.ajax({
                url: _self.opts.url,
                type: _self.opts.type,
                data: JSON.stringify(_self.opts.params),
                dataType: _self.opts.dataType,
                contentType: _self.opts.contentType
            }).done(function (data) {
                // 调用success方法进行html渲染
                _self.opts.success(data);

                // 后台返回数据格式需要保持一致
                _self.opts.totalCount = data[_self.opts.totalName];
                _self.getTotalPage();
                if (_self.opts.totalCount > 0) {
                    var pageTextArr = new Array;
                    _self.createPreAndFirstBtn(pageTextArr);
                    _self.createIndexBtn(pageTextArr);
                    _self.createNextAndLastBtn(pageTextArr);
                    _self.renderHtml(pageTextArr);
                    _self.eventBind();
                }
            }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
                // 异常统一处理
                // if (_self.opts.error) {
                //     _self.opts.error(XMLHttpRequest, textStatus, errorThrown)
                // }
            }).always(function () {
                _self.opts.complete();
            })
        },
        getTotalPage: function () {
            var _self = this;
            _self.opts.totalPage = Math.ceil(_self.opts.totalCount / _self.opts.pageSize);
        },
        /**
         * 极端分页的起始和结束点，这取决于pageIndex 和 displayCount.
         * @返回 {数组(Array)}
         */
        getInterval: function () {
            var displayCountHalf = Math.floor(this.opts.displayCount / 2);
            var upperLimit = this.opts.totalPage - this.opts.displayCount;
            var start = this.opts.pageIndex > displayCountHalf ? Math.max(Math.min(this.opts.pageIndex - displayCountHalf, upperLimit), 0) : 0;
            var end = this.opts.pageIndex > displayCountHalf ? Math.min(this.opts.pageIndex + displayCountHalf + (this.opts.displayCount % 2), this.opts.totalPage)
                : Math.min(this.opts.displayCount, this.opts.totalPage);
            return {start: start, end: end};
        },
        createPreAndFirstBtn: function (pageTextArr) {
            var _self = this;

            if (_self.opts.pageIndex == 0) {
                if (_self.opts.showFLBtn)
                    pageTextArr.push(_self.createSpan(_self.opts.firstText, _self.opts.disableClass));

                pageTextArr.push(_self.createSpan(_self.opts.preText, _self.opts.disableClass));
            } else {
                if (_self.opts.showFLBtn) {
                    pageTextArr.push(_self.createIndexText(0, _self.opts.firstText));
                }
                pageTextArr.push(_self.createIndexText(_self.opts.pageIndex - 1, _self.opts.preText));
            }
        },
        createNextAndLastBtn: function (pageTextArr) {
            var _self = this;
            if (_self.opts.pageIndex == _self.opts.totalPage - 1) {
                pageTextArr.push(_self.createSpan(_self.opts.nextText, _self.opts.disableClass));

                if (_self.opts.showFLBtn)
                    pageTextArr.push(_self.createSpan(_self.opts.lastText, _self.opts.disableClass));
            } else {
                pageTextArr.push(_self.createIndexText(_self.opts.pageIndex + 1, _self.opts.nextText));
                if (_self.opts.showFLBtn)
                    pageTextArr.push(_self.createIndexText(_self.opts.totalPage - 1, _self.opts.lastText));
            }
        },
        createIndexBtn: function (pageTextArr) {
            var interval = this.getInterval();
            if (interval.start > 0 && this.opts.edgeCount > 0) {
                var end = Math.min(this.opts.edgeCount, interval.start);
                for (var i = 0; i < end; i++) {
                    pageTextArr.push(this.createIndexText(i, i + 1));
                }
                pageTextArr.push(this.createSpan('...'));
            }
            for (var i = interval.start; i < interval.end; i++) {
                if (i == this.opts.pageIndex) {
                    pageTextArr.push(this.createSpan(i + 1, this.opts.activeClass));
                } else {
                    pageTextArr.push(this.createIndexText(i, i + 1));

                }
            }
            if (interval.end < this.opts.totalPage && this.opts.edgeCount > 0) {
                if (this.opts.totalPage - this.opts.edgeCount > interval.end) {
                    pageTextArr.push(this.createSpan('...'));
                }
                var start = Math.max(this.opts.totalPage - this.opts.edgeCount, interval.end);
                for (var i = start; i < this.opts.totalPage; i++) {
                    pageTextArr.push(this.createIndexText(i, i + 1));
                }
            }
        },
        renderHtml: function (pageTextArr) {
            var _self = this;

            var pageText = _self.commonHtmlText.stringEmpty;

            for (var i = 0; i < pageTextArr.length; i++) {
                pageText += pageTextArr[i];
            }

            _self.pagerElement.html(pageText);
            if (_self.opts.showJump) {
                _self.pagerElement.append(_self.stringFormat(_self.commonHtmlText.rightHtml, _self.opts.totalPage, _self.opts.pageIndex + 1));
            }
            _self.pagerElement.append(_self.commonHtmlText.clearFloatHtml);
        },
        createSpan: function (text, className) {
            var _self = this;

            return _self.stringFormat(_self.commonHtmlText.spanHtml, className ? className : _self.commonHtmlText.stringEmpty, text);
        },
        createIndexText: function (index, text) {
            var _self = this;
            return _self.stringFormat(_self.commonHtmlText.pageIndexHtml, index, text);
        },
        //绑定事件
        eventBind: function () {
            var _self = this;
            var index = 1;
            var pageCount = _self.getTotalPage()//总页数
            var $element = $(_self.pagerElement);
            $element.off().on('click', 'a.page-btn', function () {
                index = parseInt($(this).data('page'));
                _self.doPage(index);
            });
            //输入跳转的页码
            $element.on('input propertychange', '.jump-ipt', function () {
                var $this = $(this);
                var val = $this.val();
                var reg = /[^\d]/g;
                if (reg.test(val)) {
                    $this.val(val.replace(reg, ''));
                }
                (parseInt(val) > pageCount) && $this.val(pageCount);
                if (parseInt(val) === 0) {//最小值为1
                    $this.val(1);
                }
            });
            $element.on("click", ".jump-btn", function () {
                var $jumpInput = $element.find(".jump-ipt");
                var index = parseInt($jumpInput.val()) - 1;

                if (!isNaN(index) && index <= _self.opts.totalPage) {
                    _self.doPage(index);
                } else {
                    $jumpInput.focus();
                }
            })
        },

        //辅助方法:格式化字符串
        stringFormat: function () {
            if (arguments.length == 0)
                return null;

            var str = arguments[0];

            for (var i = 1; i < arguments.length; i++) {
                var reg = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
                str = str.replace(reg, arguments[i]);
            }

            return str;
        }
    };

    $.fn.jqPagination = function (option) {
        return new Pagination().init($(this), option);
    };

}));