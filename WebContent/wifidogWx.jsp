<%@page import="com.leeson.common.utils.stringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String appId=(String)session.getAttribute("appId");
String extend=(String)session.getAttribute("extend"); 
String timestamp=(String)session.getAttribute("timestamp"); 
String sign=(String)session.getAttribute("sign"); 
String shop_id=(String)session.getAttribute("shop_id"); 
String authUrl=(String)session.getAttribute("authUrl"); 
String mac=(String)session.getAttribute("mac"); 
String ssid=(String)session.getAttribute("ssid"); 
String bssid=(String)session.getAttribute("bssid"); 
String url = (String) session.getAttribute("url");
if(stringUtils.isBlank(url)){
	url="http://www.openportal.com.cn";
}
String web=(String)session.getAttribute("web"); 
%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>"/>
	<meta charset="utf-8">
    <title>OpenPortal微信认证</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="shortcut icon" href="customer/favicon.ico">
    
    <script src="indexjs/jquery.js" type="text/javascript"></script>
	<script src="indexjs/encode.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	function checkWX(){
		 var str="/wifidog_check_weixin.action";
			$.ajax({
				type: "GET",
				url: str,
				success: checkWX_fun,
				error: null,
				timeout: 2000,
				dataType: "json",
				cache: false
			});
	}

	function checkWX_fun(msg){
			if(msg.ret==0){
				var weburl="/<%=web%>wifidogOk.jsp?l=<%=url%>";
				window.location.replace(weburl);
			}
	}

		 
		function putNoResponse(ev){
			 clearTimeout(noResponse);
		}	
		
		 function errorJump()
		 {
			 window.alert = function(name){
　				var iframe = document.createElement("IFRAME");
　				iframe.style.display="none";
　				iframe.setAttribute("src", 'data:text/plain,');
　				document.documentElement.appendChild(iframe);
　				window.frames[0].window.alert(name);
　				iframe.parentNode.removeChild(iframe);
　			}
			 alert('跳转到微信进行认证\n如果已跳转请忽略此提示!');
		 }
		 
		 myHandler = function(error) {
			 errorJump();
		 };
		 
		 function createIframe(){
			 var iframe = document.createElement("iframe");
		     iframe.style.cssText = "display:none;width:0px;height:0px;";
		     document.body.appendChild(iframe);
		     loadIframe = iframe;
		 }

		
		
			/**
		  * 微信连Wi-Fi协议3.1供运营商portal呼起微信浏览器使用
		  */
 var loadIframe = null;
 var noResponse = null;
 function createIframe(){
	 var iframe = document.createElement("iframe");
     iframe.style.cssText = "display:none;width:0px;height:0px;";
     document.body.appendChild(iframe);
     loadIframe = iframe;
 }
//注册回调函数
function jsonpCallback(result){  
	re=1;
	if(result && result.success){
		interval = setInterval(checkWX, 3000);
		var ua=navigator.userAgent;              
		if (ua.indexOf("iPhone") != -1 ||ua.indexOf("iPod")!=-1||ua.indexOf("iPad") != -1) {   //iPhone
			document.location = result.data;
		}else{
			createIframe();
		    loadIframe.src=result.data;
			noResponse = setTimeout(function(){
				errorJump();
	      	},3000);
		}
	}else if(result && !result.success){
		LoginOut();
		window.alert = function(name){
			var iframe = document.createElement("IFRAME");
			iframe.style.display="none";
			iframe.setAttribute("src", 'data:text/plain,');
			document.documentElement.appendChild(iframe);
			window.frames[0].window.alert(name);
			iframe.parentNode.removeChild(iframe);
		}
		alert("请联系管理员，错误代码: "+result.data);
		var weburl="/<%=web%>wifidogOut.jsp";
		window.location.replace(weburl);
	}else{
		LoginOut();
		window.alert = function(name){
			var iframe = document.createElement("IFRAME");
			iframe.style.display="none";
			iframe.setAttribute("src", 'data:text/plain,');
			document.documentElement.appendChild(iframe);
			window.frames[0].window.alert(name);
			iframe.parentNode.removeChild(iframe);
		}
		alert("网络暂时不可用，请联系管理员！！");
		var weburl="/<%=web%>wifidogOut.jsp";
		window.location.replace(weburl);
	}
}


function LoginOut(){
var str="/ajax_wifidog_LoginOut.action";
var data_str=encodeToUTF8(str);
$.ajax({
	type: "GET",
	url: data_str,
	success: loginOut_fun,
	error: null,
	timeout: 1000,
	dataType: "json",
	cache: false
});
}
function loginOut_fun(msg){
}


function Wechat_GotoRedirect(appId, extend, timestamp, sign, shopId, authUrl, mac, ssid, bssid){
	
	//如果sign后面的参数有值，则是新3.1发起的流程
	var url = "https://wifi.weixin.qq.com/operator/callWechat.xhtml?appId=" + appId 
																		+ "&extend=" + extend 
																		+ "&timestamp=" + timestamp 
																		+ "&sign=" + sign
																		+ "&shopId=" + shopId
																		+ "&authUrl=" + encodeURIComponent(authUrl)
																		+ "&mac=" + mac
																		+ "&ssid=" + ssid
																		+ "&bssid=" + bssid;			
	
	//通过dom操作创建script节点实现异步请求  
	var script = document.createElement('script');  
	script.setAttribute('src', url);  
	document.getElementsByTagName('head')[0].appendChild(script);
}
	</script>
    <link rel="stylesheet" href="weixin/style-simple-follow.css" type="text/css" >
    <style type="text/css">object,embed{                -webkit-animation-duration:.001s;-webkit-animation-name:playerInserted;                -ms-animation-duration:.001s;-ms-animation-name:playerInserted;                -o-animation-duration:.001s;-o-animation-name:playerInserted;                animation-duration:.001s;animation-name:playerInserted;}                @-webkit-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}                @-ms-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}                @-o-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}                @keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}</style></head>
<body class="mod-simple-follow">
<div class="mod-simple-follow-page">
    <div class="mod-simple-follow-page__banner">
        <img class="mod-simple-follow-page__banner-bg" src="weixin/background.jpg" alt="">
        <div class="mod-simple-follow-page__img-shadow"></div>
        <div class="mod-simple-follow-page__logo">
            <img class="mod-simple-follow-page__logo-img" src="weixin/logo.jpg" alt="">
            <p class="mod-simple-follow-page__logo-name"></p>
             <p class="mod-simple-follow-page__logo-welcome">欢迎您</p>
        </div>
    </div>
    <div id="toWeixin" class="mod-simple-follow-page__attention">
        <p class="mod-simple-follow-page__attention-txt">欢迎使用微信认证</p>
        <p class="mod-simple-follow-page__attention-txt">请等待网络初始化...</p>
    </div>
</div>

<script type="text/javascript">
$(function(){
post_fun();
});
	
var appId;      
var extend;    
var timestamp; 
var sign;     
var shop_id;   
var authUrl;   
var mac;     
var ssid;      
var bssid;  
var re=0;

function post_fun(){
	appId='<%=appId%>';      
	extend='<%=extend%>';
	timestamp='<%=timestamp%>';
	sign='<%=sign%>';    
	shop_id='<%=shop_id%>';
	authUrl='<%=authUrl%>';
	mac='<%=mac%>';     
	ssid='<%=ssid%>';
	bssid='<%=bssid%>';
	
	//alert(appId+"==="+extend+"==="+timestamp+"==="+sign+"==="+shop_id+"==="+authUrl+"==="+mac+"==="+ssid+"==="+bssid);
	//var trs = "<p class='mod-simple-follow-page__attention-txt'>欢迎使用微信认证</p><a class='mod-simple-follow-page__attention-btn' onclick='callWechatBrowser()'>打开微信连Wi-Fi</a>";  
	var trs = "<p class='mod-simple-follow-page__attention-txt'>跳转到微信进行认证</p><a class='mod-simple-follow-page__attention-txt'>如果未跳转到微信,请更换浏览器重试!!</a>";  
	callWechatBrowser();
	setTimeout(function(){
				$("#toWeixin").empty();
				$("#toWeixin").append(trs);
				//alert("haha");
	      	},3000);
	
	setTimeout(function(){
		if(re==0){
		window.location.reload();
		}
    	},4000);
	
}

function callWechatBrowser(){
//alert('appId:' + appId + ' extend:' + extend + ' ssid:' + ssid + ' shop_id:' + shop_id + ' bssid:' + bssid + ' authUrl:' + authUrl + ' mac:' + mac + ' timestamp:' + timestamp + ' sign:' + sign);	
Wechat_GotoRedirect(appId , extend , timestamp , sign , shop_id , authUrl , mac , ssid , bssid );
}
</script>

<script type="text/javascript">
	document.addEventListener('visibilitychange', putNoResponse, false);
</script>
</body></html>