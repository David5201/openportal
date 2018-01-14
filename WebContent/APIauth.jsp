<%@page import="com.leeson.core.bean.Portalbas"%>
<%@page import="com.leeson.portal.core.model.Config"%>
<%@page import="com.leeson.common.utils.stringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String web=(String)session.getAttribute("web"); 
String api=(String)session.getAttribute("api"); 
%>
<%
    String basip=(String) session.getAttribute("basip");
    if(stringUtils.isBlank(basip)){
    	response.sendRedirect("http://www.openportal.com.cn");
    }else{
    	Portalbas basConfig=Config.getInstance().getConfigMap().get(basip.trim());
    	if(basConfig==null){
    		response.sendRedirect("http://www.openportal.com.cn");
    	}
    }
%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>"/>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="keywords" content="OpenPortal,portal协议,wifi认证,Web认证,微信认证,华为,H3C,中兴,锐捷,ROS,爱快,Unifi,wifidog">
    <meta name="description" content="中国支持最全面的Portal协议 WIFI WEB认证 系统，支持 华为 H3C 中兴 锐捷 爱快 ROS UniFi OpenWrt wifidog 飞塔等设备，支持标准Portal协议、Portal V1 V2协议、CMCC协议、PAP CHAP认证方式。实现页面广告展示、一键认证、微信认证、公众号关注、短信认证、二维码认证、访客认证、用户密码认证,支持第三方Radius">
	<meta name="author" content="LeeSon,李硕">

	<title>OpenPortal网络接入认证专家</title>

	<!-- Bootstrap core CSS -->
	<link href="<%=web%>dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- Bootstrap theme -->
	<link href="<%=web%>dist/css/bootstrap-theme.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="<%=web%>dist/css/theme.css" rel="stylesheet">
	<script src="<%=web%>dist/js/jquery-1.11.1.min.js"></script>
	<script src="<%=web%>dist/js/bootstrap.min.js"></script>
	<script src="<%=web%>dist/js/APIsite.js"></script>
	<script src="<%=web%>dist/js/APIauth.js"></script>
	<script src="<%=web%>dist/js/encode.js"></script>
	<link rel="shortcut icon" href="customer/favicon.ico">
	<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
	<!--[if lt IE 9]>
	<script src="../../assets/js/ie8-responsive-file-warning.js"></script>
	<![endif]-->
	<!--
	<script src="../assets/js/ie-emulation-modes-warning.js"></script>
-->
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
	
<body role="document">
<form role="form">
<input type="hidden" name="web" id="web" value="<%=web%>" />
<input type="hidden" name="api" id="api" value="<%=api%>" />
</form>
		
<div id="main" class="jumbotron masthead">
	<div class="container">
		<div class="container">
			<a id="logo" href="">
				<img  src="<%=web%>dist/css/img/logo.png" style="height:100px;" alt="Responsive image"></a>
		</div>
		<h1>网络接入认证专家</h1>
		<h2>亲，欢迎您使用OpenPortal网络接入认证系统</h2>
		
		
		
		<div class="row">
			<div class="col-md-8">
			<h3></h3>
				<div id="myCarousel" class="carousel slide">
				   <!-- 轮播（Carousel）指标 -->
				   <ol class="carousel-indicators">
					  <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					  <li data-target="#myCarousel" data-slide-to="1"></li>
					  <li data-target="#myCarousel" data-slide-to="2"></li>
				   </ol>   
				   <!-- 轮播（Carousel）项目 -->
				   <div class="carousel-inner">
					  <div class="item active">
						 <a href="http://www.openportal.com.cn" target="_blank">
						 <img src="<%=web%>dist/css/img/s1.jpg" alt="OpenPortal Portal协议WEB认证WIFI认证系统">
						 </a>
					  </div>
					  <div class="item">
					     <a href="http://www.openportal.com.cn" target="_blank">
						 <img src="<%=web%>dist/css/img/s2.jpg" alt="OpenPortal Portal协议WEB认证WIFI认证系统">
						 </a>
					  </div>
					  <div class="item">
					     <a href="http://www.openportal.com.cn" target="_blank">
						 <img src="<%=web%>dist/css/img/s3.jpg" alt="OpenPortal Portal协议WEB认证WIFI认证系统">
						 </a>
					  </div>
				   </div>
				   <!-- 轮播（Carousel）导航 -->
				   <a class="carousel-control left" href="#myCarousel" 
					  data-slide="prev">&lsaquo;</a>
				   <a class="carousel-control right" href="#myCarousel" 
					  data-slide="next">&rsaquo;</a>
				</div> 
			</div>
			<div id="rz" class="col-md-4">
				<h3>请选择认证方式</h3>
				<c:if test="${fn:contains(auth, 0)}">
				<button id="oneKeyLogin" type="button" class="btn btn-danger btn-lg btn-block">一键认证</button>
				<br>
				</c:if>
				
				<c:if test="${fn:contains(auth, 5)}">
				<button id="weixinLogin" type="button" class="btn btn-lg btn-success btn-block ">微信认证</button>
				<br>
				</c:if>
				
				<c:if test="${fn:contains(auth, 4)}">
				<button id="smsLogin" type="button" class="btn btn-lg btn-warning btn-block">短信认证</button>
				<br>
				</c:if>
				
				<c:if test="${fn:contains(auth, 3)}">
				<button id="appLogin" type="button" class="btn btn-lg btn-success btn-block">APP认证</button>
				<br>
				</c:if>
				
				<c:if test="${fn:contains(auth, 1)}">
				<button id="accountLogin" type="button" class="btn btn-lg btn-info btn-block">用户密码认证</button>
				<br>
				</c:if>
				
				<c:if test="${fn:contains(auth, 2)}">
				<button id="accountLogin" type="button" class="btn btn-lg btn-info btn-block">用户密码认证</button>
				<br>
				</c:if>
				
				<c:if test="${fn:contains(auth, 6)}">
				<button id="gzhLogin" type="button" class="btn btn-lg btn-success btn-block">公众号认证</button>
				<br>
				</c:if>
				
				<c:if test="${fn:contains(auth, 7)}">
				<button id="guestLogin" type="button" class="btn btn-lg btn-primary btn-block">访客认证</button>
				<br>
				</c:if>
				
				<ul class="list-group">
					<li class="list-group-item"><a href="<%=path%>/message_sendUI.action" target="_blank">联系管理员</a></li>
					<li class="list-group-item"><a href="<%=path%>/customerLogin.action" target="_blank">用户自助中心</a></li>
					<li class="list-group-item"><a href="<%=path%>/homeAction/index.action" target="_blank">系统管理入口</a></li>
				</ul>
				
				</div>
		</div>
</div>
</div>

<div class="container theme-showcase" role="main">
	<div class="page-header text-center">
		<h4><a href="http://www.openportal.com.cn" target="_blank">Copyright © www.openportal.com.cn</a></h4>
	</div>
</div>
<c:if test="${msg != null }">
<script type="text/javascript">
window.alert = function(name){
	var iframe = document.createElement("IFRAME");
	iframe.style.display="none";
	iframe.setAttribute("src", 'data:text/plain,');
	document.documentElement.appendChild(iframe);
	window.frames[0].window.alert(name);
	iframe.parentNode.removeChild(iframe);
}
alert("${msg }");
</script>
</c:if>
</body>
</html>