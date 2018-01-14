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
    String ip=(String)session.getAttribute("ip");
    String mac=(String)session.getAttribute("pmac");
    String apip=(String)session.getAttribute("apip");
    String apmac=(String)session.getAttribute("apmac");
    String vlan=(String)session.getAttribute("vlan");
    String loginUrl=(String)session.getAttribute("loginUrl");
    
    String AC=(String)session.getAttribute("AC");
    String url=(String)session.getAttribute("url");
    String gw_address=(String)session.getAttribute("gw_address");
    String gw_id=(String)session.getAttribute("gw_id");
    String gw_port=(String)session.getAttribute("gw_port");
%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>"/>
	<meta charset="utf-8">

	<title>正在认证...</title>
<%if(stringUtils.isNotBlank(AC)&&"yes".equals(AC)){%>
<script type="text/javascript">
window.onload =function(){
	location.href="<%=loginUrl%>?username=<%=authUser%>&password=<%=authPwd%>&apIp=<%=apip%>&apMac=<%=apmac%>&staIp=<%=ip%>&staMac=<%=mac%>&vlan=<%=vlan%>";
}
</script>
<%}else if(stringUtils.isNotBlank(AC)&&"no".equals(AC)){%>
<script type="text/javascript">
window.onload =function(){
	location.href="<%=loginUrl%>?user=<%=authUser%>&pwd=<%=authPwd%>&authtype=web&gw_address=<%=gw_address%>&gw_port=<%=gw_port%>&gw_id=<%=gw_id%>&ip=<%=ip%>&mac=<%=mac%>&url=<%=url%>";
}
</script>
<%}else{%>
<script type="text/javascript">
window.onload =function(){
	location.href="http://www.openportal.com.cn";
}
</script>
<%}%>
</head>

<body>

</body>
</html>