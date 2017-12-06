<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/include.jsp" %>

    <script type="text/javascript" src="${ctx}/static/nprogress/nprogress.js"></script>
    <script type="text/javascript" src="${ctx}/js/pagination/jqPagination.js"></script>
    <script type="text/javascript" src="${ctx}/js/pagination/jquery.pagination.js"></script>
    <link rel="stylesheet" href="${ctx}/js/pagination/pager.css"/>
    <link rel="stylesheet" href="${ctx}/js/pagination/pagination.css"/>
    <link rel="stylesheet" href="${ctx}/static/nprogress/nprogress.css"/>
    <script type="text/javascript">
        $(function () {
            NProgress.start();
            /**
             * 纯前端分页
             */
            $.ajax({
                url: "${ctx}/page/getPage",
                type: "POST",
                dataType: "JSON",
                data: JSON.stringify({pageIndex: 1, pageSize: 1}),
                contentType: "application/json",
            }).then(function (data) {
                rendHtml(data)
                $('#pager').pagination({
                    pageSize: 1,
                    totalCount: data.totalCount,
                    prevContent: '上页',		//上一页内容
                    nextContent: '下页',
                    jump: true,
                    callback: function (api) {
                        var pageIndex = api.getPageIndex();
                        $.ajax({
                            url: "${ctx}/page/getPage",
                            type: "POST",
                            dataType: "JSON",
                            data: JSON.stringify({pageIndex: pageIndex, pageSize: 1}),
                            contentType: "application/json",
                        }).then(function (data) {
                            rendHtml(data);
                        })
                    }
                });

            });

            /**
             * ajax整合进分页插件
             */
            $('#jqPagination').jqPagination({
                url: "${ctx}/page/getPage",
                pageSize: 1,
                paginationClass: "pager",
                showJump: true,
                param: {
                    a: 1
                },
                success: function (data) {
                    //返回的数据根据自己需要处理
                    var tableStr = "<tr><td>编号</td><td>姓名</td><td>年龄</td><td>地址</td></tr>";
                    $.each(data.data, function (i, v) {
                        tableStr += "<tr><td>" + v.ticket_number + "</td><td>" + v.name + "</td><td>" + v.sex + "</td><td>"+ v.adresss +"</td></tr>";
                    });

                    $('#jqPagination_dataTable').html(tableStr);
                },
                complete: function () {
                    NProgress.done();
                }
            });

        });

        function rendHtml(data) {
            var tableStr = "<tr><td>编号</td><td>姓名</td><td>年龄</td><td>地址</td></tr>";
            $.each(data.data, function (i, v) {
                tableStr += "<tr><td>" + v.ticket_number + "</td><td>" + v.name + "</td><td>" + v.sex + "</td><td>"+ v.adresss +"</td></tr>";
            });

            $('#dataTable').html(tableStr);
        }

    </script>

</head>
<body>
<div class="bg-color-mercury overflow-hidden">
    <div class="wrapper">
        <table id="dataTable" border="1" style="width: 100%"></table>
        <br/>
        <div id="pager"></div>
        <br>
        <br>
        <table id="jqPagination_dataTable" border="1" style="width: 100%"></table>
        <div id="jqPagination"></div>
    </div>
</div>


</body>
</html>
