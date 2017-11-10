<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- jquery插件 必须第一引入 -->
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<script src="${ctx}/js/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
<!--jQuery2.0+不再支持IE8-->
<!--[if lte IE 8]>
<script src="${ctx}/js/jquery/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="https://cdn.bootcss.com/es5-shim/4.5.9/es5-shim.min.js"></script>
<script src="https://cdn.bootcss.com/es5-shim/4.5.9/es5-sham.min.js"></script>
<script src="https://cdn.bootcss.com/json3/3.3.2/json3.min.js"></script>
<![endif]-->

<script src="${ctx}/js/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/js/template-web.js"></script>

<link href="${ctx}/css/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/css/normalize.css" rel="stylesheet"/>
<link href="${ctx}/css/style.css" rel="stylesheet"/>
<link href="${ctx}/js/pagination/pagination.css" rel="stylesheet"/>

<%--<link rel="shortcut icon" href="${ctx}/favicon.ico">--%>

<script type="text/javascript">
	//把el表达式获取工程名赋值给js变量
	var contextPath = '${ctx }';
</script>







