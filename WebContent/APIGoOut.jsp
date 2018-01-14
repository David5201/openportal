<%@page import="com.leeson.common.utils.stringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    String logoutUrl=(String)session.getAttribute("logoutUrl");
%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>"/>
	<meta charset="utf-8">

	<title>正在退出...</title>
	
<script type="text/javascript">
window.onload =function(){
	location.href="<%=logoutUrl%>";
}
</script>
</head>

<body>

</body>
</html>