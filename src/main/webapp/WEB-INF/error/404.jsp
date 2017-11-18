<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>error page</title>
    <%@ include file="/common/include.jsp" %>
</head>
<body style="margin: 0;padding: 0;background-color: #f5f5f5;">
<div id="center-div" style="text-align: center">
                <img width="30%" height="auto" src="${ctx}/images/404.png"/>
                <p> <%=exception.getMessage()%></p>
                <p>错误信息:${ex}</p>
                <p>
                    <a href="javascript:history.go(-1);">返回</a>!!!
                </p>
            </td>
        </tr>
    </table>
</div>
</body>
</html>