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
    String authUser=(String)session.getAttribute("username");
    String authPwd=(String)session.getAttribute("password");
%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>"/>
<meta charset="utf-8">
<title>正在认证...</title>
<script src="<%=web%>dist/js/jquery-1.11.1.min.js"></script>
<script src="<%=web%>dist/js/encode.js"></script>
</head>
<body>

<form id="login_form">
<input type="hidden" name="web" id="web" value="<%=web%>">
<input type="hidden" name="api" id="api" value="<%=api%>">
<input type="hidden" id="InputAccount" name="usr" value="<%=authUser%>">
<input type="hidden" id="InputPassword" name="pwd" value="<%=authPwd%>">
</form>

</body>
<script type="text/javascript">
function accountLogin(){
	var str="/ajax_WISPr_Login.action";
	var data_str=encodeToUTF8(str);
	var postData = $('#login_form').serialize();
	$.ajax({
		type: "POST",
		url: data_str,
		success: login_fun,
		error: null,
		timeout: 5000,
		data: postData,
		cache: false,
		complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
			if(status=='timeout'){//超时,status还有success,error等值的情况
				window.alert = function(name){
					var iframe = document.createElement("IFRAME");
					iframe.style.display="none";
					iframe.setAttribute("src", 'data:text/plain,');
					document.documentElement.appendChild(iframe);
					window.frames[0].window.alert(name);
					iframe.parentNode.removeChild(iframe);
				}
					alert("认证超时！！");
					var web=$('#web').val();
					var	weburl="/"+web+"APIauth.jsp";
					window.location.replace(weburl);
			}
			}
	});
	}
	function login_fun(msg){
	$("#btnAcpassword").removeAttr("disabled"); 
	if(msg.ret==0){
		var api=$('#api').val();
		var	weburl="/error.html";
		if(api=='ros'){
			weburl="/APIGoAuthRos.jsp";
		}else if(api=='ruckus'){
			weburl="/APIGoAuthRuckus.jsp";
		}else if(api=='aruba'){
			weburl="/APIGoAuthAruba.jsp";
		}else if(api=='tplink'){
			weburl="/APIGoAuthTplink.jsp";
		}
	    window.location.replace(weburl);
	}else if(msg.ret==10){
		window.alert = function(name){
			var iframe = document.createElement("IFRAME");
			iframe.style.display="none";
			iframe.setAttribute("src", 'data:text/plain,');
			document.documentElement.appendChild(iframe);
			window.frames[0].window.alert(name);
			iframe.parentNode.removeChild(iframe);
		}
		alert("参数获取失败，请重试！！");
		var web=$('#web').val();
		var	weburl="/"+web+"APIauth.jsp";
		window.location.replace(weburl);
	}else{
		var t=msg.msg;
		if(t==null||t==""){
			window.alert = function(name){
				var iframe = document.createElement("IFRAME");
				iframe.style.display="none";
				iframe.setAttribute("src", 'data:text/plain,');
				document.documentElement.appendChild(iframe);
				window.frames[0].window.alert(name);
				iframe.parentNode.removeChild(iframe);
			}
			alert("你操作太快！！");
			var web=$('#web').val();
			var	weburl="/"+web+"APIauth.jsp";
			window.location.replace(weburl);
		}else{
			window.alert = function(name){
				var iframe = document.createElement("IFRAME");
				iframe.style.display="none";
				iframe.setAttribute("src", 'data:text/plain,');
				document.documentElement.appendChild(iframe);
				window.frames[0].window.alert(name);
				iframe.parentNode.removeChild(iframe);
			}
			alert(t); 
			var web=$('#web').val();
			var	weburl="/"+web+"APIauth.jsp";
			window.location.replace(weburl);
		}
	}
	}
	accountLogin();
</script>
</html>