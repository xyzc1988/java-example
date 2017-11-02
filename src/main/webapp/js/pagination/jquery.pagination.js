/**
 * pagination分页插件
 * @参照github https://github.com/Maxiaoxiang/jQuery-plugins
 * @参照github https://github.com/gbirke/jquery_pagination
 *
 *
 * @调用方法
 * $(selector).pagination(option, callback);
 * -此处callback是初始化调用，option里的callback才是点击页码后调用
 *
 * -- example --
 * $(selector).pagination({
 *     ...
 *     callback: function(api){
 *         console.log('点击页码调用该回调'); //把请求接口函数放在这儿，每次点击请求一次
 *     }
 * }, function(){
 *     console.log('初始化'); //插件初始化时调用该回调，比如请求第一次接口来初始化分页配置
 * });
 */
;(function (factory) {
    if (typeof define === "function" && (define.amd || define.cmd) && !jQuery) {
        // AMD或CMD
        define(["jquery"], factory);
    } else if (typeof module === 'object' && module.exports) {
        // Node/CommonJS
        module.exports = function (root, jQuery) {
            if (jQuery === undefined) {
                if (typeof window !== 'undefined') {
                    jQuery = require('jquery');
                } else {
                    jQuery = require('jquery')(root);
                }
            }
            factory(jQuery);
            return jQuery;
        };
    } else {
        //Browser globals
        factory(jQuery);
    }
}(function ($) {

    //配置参数
    var defaults = {
        pageIndex: 0,			//当前第几页
        pageSize: 0,			//每页显示的条数
        totalPage: 0,			//总页数,默认为9
        totalCount: 0,			//数据总条数
        paginationCls: "pagination", //分页容器class
        activeCls: 'active',	//当前页选中状态
        isHide: false,			//当前页数为0页或者1页时不显示分页
        showFLPage: false,	    //显示首页和尾页
        firstPage: '首页',		//首页节点内容
        lastPage: '末页',		//尾页节点内容
        showPNPage: true,		//是否一直显示上一页下一页
        prevContent: '上页',		//上一页内容
        nextContent: '下页',		//下一页内容
        displayCount: 5,		//连续分页主体部分显示的分页条目数
        edgeCount: 1,           //省略号边缘分页个数
        jump: true,			    //跳转到指定页数
        jumpIptCls: 'jump-ipt',	//文本框内容
        jumpBtnCls: 'jump-btn',	//跳转按钮
        jumpBtn: '跳转',		    //跳转按钮文本
        callback: function () {
        }	//回调
    };

    var Pagination = function (element, options) {
        var opts = $.extend({}, defaults, options);
        var $element = $(element)//容器
        var pageIndex;

        /**
         * 初始化
         * @returns {Pagination}
         */
        this.init = function () {
            opts.totalPage = this.getTotalPage();
            this.filling(opts.pageIndex);
            this.eventBind();
            if (opts.isHide && opts.totalPage == '1' || opts.totalPage == '0') {
                $element.hide();
            } else {
                $element.show();
            }
            return this;
        };
        /**
         * 设置总页数
         * @param int page 页码
         * @return opts.totalPage 总页数配置
         */
        this.setTotalPage = function (page) {
            return opts.totalPage = page;
        };

        /**
         * 获取总页数
         * 如果配置了总条数和每页显示条数，将会自动计算总页数并略过总页数配置，反之
         * @return int p 总页数
         */
        this.getTotalPage = function () {
            return opts.totalCount || opts.pageSize ? Math.ceil(parseInt(opts.totalCount) / opts.pageSize) : opts.totalPage;
        };

        /**
         * 获取当前页
         * @return int pageIndex 当前第几页
         */
        this.getPageIndex = function () {
            return pageIndex;
        };

        /**
         * 极端分页的起始和结束点，这取决于pageIndex 和 displayCount.
         * @返回 {数组(Array)}
         */
        this.getInterval = function () {
            var displayCountHalf = Math.floor(opts.displayCount / 2);
            var upperLimit = opts.totalPage - opts.displayCount;
            var start = pageIndex > displayCountHalf ? Math.max(Math.min(pageIndex - displayCountHalf, upperLimit), 0) : 0;
            var end = pageIndex > displayCountHalf ? Math.min(pageIndex + displayCountHalf + (opts.displayCount % 2), opts.totalPage)
                : Math.min(opts.displayCount, opts.totalPage);
            return [start, end];
        };

        /**
         * 填充数据
         * @param int index 页码
         */
        this.filling = function (index) {
            var fragment = $('<div class="'+ opts.paginationCls +'"></div>');

            pageIndex = index || opts.pageIndex;//当前页码

            var interval = this.getInterval();
            //首页
            if (opts.showFLPage ) {
                fragment.append(this.renderPageBtn(0, {text: opts.firstPage, classes: "disabled"}));
            }
            //上一页
            if (opts.showPNPage) {
                fragment.append(this.renderPageBtn(pageIndex - 1, {text: opts.prevContent, classes: "disabled"}));
            }
            // 产生起始点
            if (interval[0] > 0 && opts.edgeCount > 0) {
                var end = Math.min(opts.edgeCount, interval[0]);
                for (var i = 0; i < end; i++) {
                    fragment.append(this.renderPageBtn(i));
                }
                if (opts.edgeCount < interval[0]) {
                    fragment.append($('<span>...</span>'));
                }
            }
            // 产生内部链接
            for (var i = interval[0]; i < interval[1]; i++) {
                fragment.append(this.renderPageBtn(i));
            }
            //产生结束点
            if (interval[1] < opts.totalPage && opts.edgeCount > 0) {
                if (opts.totalPage - opts.edgeCount > interval[1]) {
                    fragment.append($('<span>...</span>'));
                }
                var begin = Math.max(opts.totalPage - opts.edgeCount, interval[1]);
                for (var i = begin; i < opts.totalPage; i++) {
                    fragment.append(this.renderPageBtn(i));
                }
            }
            //下一页
            if (opts.showPNPage) {
                fragment.append(this.renderPageBtn(pageIndex + 1, {text: opts.nextContent, classes: "disabled"}));
            }
            //末页
            if (opts.showFLPage) {
                fragment.append(this.renderPageBtn(opts.totalPage, {text: opts.lastPage, classes: "disabled"}));
            }
            if (opts.jump) {
                fragment.append($('<span class="page-info">&nbsp;共&nbsp;' + opts.totalPage + '&nbsp;页&nbsp;</span>' +
                    '<div><input type="text" class="' + opts.jumpIptCls + '" value="' + (pageIndex + 1) + '">' +
                    '<a href="javascript:;" class="' + opts.jumpBtnCls + '">' + opts.jumpBtn + '</a></div>'));
            }
            $element.empty().append(fragment);

        };

        this.renderPageBtn = function (pageId, appendopts) {
            var link;
            pageId = pageId < 0 ? 0 : (pageId < opts.totalPage ? pageId : opts.totalPage - 1); // 规范page id值
            appendopts = $.extend({text: pageId + 1, classes: ""}, appendopts || {});
            if (pageIndex == pageId) {
                link = $('<span class="' + opts.activeCls + '">' + appendopts.text + '</span>');
                if (appendopts.classes) {
                    link.attr("class", appendopts.classes);
                }
            } else {
                link = $('<a href="javascript:void(0);" data-page-index="' + pageId + '">' + appendopts.text + '</a>');
            }
            return link;
        };

        //绑定事件
        this.eventBind = function () {
            var that = this;
            var index = 0;
            $element.off().on('click', 'a', function (event) {
                if ($(this).hasClass(opts.jumpBtnCls)) {
                    if ($element.find('.' + opts.jumpIptCls).val() !== '') {
                        index = parseInt($element.find('.' + opts.jumpIptCls).val()) - 1;
                    } else {
                        return;
                    }
                } else {
                    index = parseInt($(this).data('page-index'));
                }
                event.preventDefault();
                that.filling(index);
                typeof opts.callback === 'function' && opts.callback(that);
            });
            //输入跳转的页码
            $element.on('input propertychange', '.' + opts.jumpIptCls, function () {
                var pageId = this.value
                var reg = /[^\d]/g;
                if (reg.test(pageId)) {
                    this.value =  pageId.replace(reg, '');
                }
                pageId = pageId < 1 ? 1 : (pageId < opts.totalPage ? pageId : opts.totalPage);
                this.value = pageId;
            });
            //回车跳转指定页码
            $(document).off().keydown(function (e) {
                if (e.keyCode == 13 && $element.find('.' + opts.jumpIptCls).val()) {
                    var index = parseInt($element.find('.' + opts.jumpIptCls).val());
                    that.filling(index);
                    typeof opts.callback === 'function' && opts.callback(that);
                }
            });
        }
    };

    $.fn.pagination = function (parameter, callback) {
        if (typeof parameter == 'function') {//重载
            callback = parameter;
            parameter = {};
        } else {
            parameter = parameter || {};
            callback = callback || function () {
            };
        }
        return this.each(function () {
            var pagination = new Pagination(this, parameter).init();
            callback(pagination);
        });
    };

}));