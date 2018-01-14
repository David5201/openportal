<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String web=(String)session.getAttribute("web"); 

String appId=(String)session.getAttribute("appId");
String extend=(String)session.getAttribute("extend"); 
String timestamp=(String)session.getAttribute("timestamp"); 
String sign=(String)session.getAttribute("sign"); 
String shop_id=(String)session.getAttribute("shop_id"); 
String authUrl=(String)session.getAttribute("authUrl"); 
String mac=(String)session.getAttribute("mac"); 
String ssid=(String)session.getAttribute("ssid"); 
String bssid=(String)session.getAttribute("bssid"); 
%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>"/>
	<meta charset="utf-8">
    <title>OpenPortal微信认证</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script type="text/javascript" src="<%=web%>weixin/pcauth.js" ></script>
    <!-- <link rel="stylesheet" href="https://wifi.weixin.qq.com/resources/css/style-simple-follow.css"/>  -->
    
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--  meta name="renderer" content="webkit"-->
    <!--  meta charset="UTF-8"-->
    <link rel="stylesheet" href="<%=web%>weixin/style-pcdemo.css">
</head>
<!-- body class="mod-simple-follow">
<div class="mod-simple-follow-page">
    <div class="mod-simple-follow-page__banner">
        <img class="mod-simple-follow-page__banner-bg" src="https://wifi.weixin.qq.com/resources/images/background.jpg" alt=""/>
        <div class="mod-simple-follow-page__img-shadow"></div>
        <div class="mod-simple-follow-page__logo">
            <img class="mod-simple-follow-page__logo-img" src="https://wifi.weixin.qq.com/resources/images/t.weixin.logo.png" alt=""/>
            <p class="mod-simple-follow-page__logo-name"></p>
            <p class="mod-simple-follow-page__logo-welcome">欢迎您</p>
        </div>
    </div>
    <div class="mod-simple-follow-page__attention">
        <p class="mod-simple-follow-page__attention-txt">欢迎使用微信连Wi-Fi</p>
    </div>
</div>
</body -->
<body>
	<div class="container">
	    <div class="header">
	        <img src="<%=web%>weixin/image-logo.png" class="header__logo" alt="OpenPortal微信认证">
	        <!--建议图片大小为570x140 或 287x70-->
	    </div>
	
	    <div class="main" style="background-image: url(<%=web%>weixin/image-bg.jpg);">
	        <!--建议图片大小为 1920x1200 或 1920x1080-->
	        <div class="main__content">
	            <h2 class="main__content-title">欢迎使用<em>免费Wi-Fi</em></h2>
	            <div class="main__content-qrcode" id='qrcode_zone' style="text-align:center;margin:20px auto;width:250px;"></div>
	            <div class="main__content-info">使用微信扫描二维码</div>
	        </div>
	
	        <!--<div class="main__wifi-logo">-->
	            <!--<i class="main__wifi-icon"></i>-->
	        <!--</div>-->
	    </div>
	
	    <div class="footer">
	        <div class="footer_copyright"><a href="http://www.openportal.com.cn">OpenPortalServer</a> Copyright © 2015-2016 OpenPortal. All Rights Reserved.</div>
	    </div>
	</div>
</body>
<script type="text/javascript">

	    JSAPI.auth({
	    	target : document.getElementById('qrcode_zone'),
	        appId : '<%=appId%>',
	        shopId : <%=shop_id%>,
	        extend : '<%=extend%>',
	        authUrl : '<%=authUrl%>'
	    });

</script>
</html>