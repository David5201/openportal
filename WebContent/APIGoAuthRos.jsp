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
    String authUser=(String)session.getAttribute("authUser");
    String authPwd=(String)session.getAttribute("authPwd");
    String loginUrl=(String)session.getAttribute("loginUrl");
    String callbackPath=(String)session.getAttribute("callbackPath");
%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>"/>
	<meta charset="utf-8">

	<title>正在认证...</title>
	
<script type="text/javascript">
window.onload =function(){
	location.href="<%=loginUrl%>?username=<%=authUser%>&password=<%=authPwd%>&dst=<%=callbackPath%>&popup=true";
}
</script>
</head>

<body>

</body>
</html>