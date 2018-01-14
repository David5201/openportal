function LoginOut(){
var str="/ajax_WISPr_LoginOut.action";
var data_str=encodeToUTF8(str);
$.ajax({
	type: "GET",
	url: data_str,
	success: loginOut_fun,
	error: null,
	timeout: 5000,
	dataType: "json",
	cache: false,
	complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
		if(status=='timeout'){//超时,status还有success,error等值的情况
			var web=$('#web').val();
			weburl="/"+web+"out.jsp";
			window.location.replace(weburl);
		}
		}
});
}
function loginOut_fun(msg){
$("#LoginOut").removeAttr("disabled"); 
if(msg.ret==0){
	var	weburl="/APIGoOut.jsp";
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
	alert(msg.msg);
}
}




function oneKeyLogin(){
var str="/ajax_WISPr_Login.action";
var data_str=encodeToUTF8(str);
$.ajax({
	type: "GET",
	url: data_str,
	success: oneKey_fun,
	error: null,
	timeout: 5000,
	dataType: "json",
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
				$("#oneKeyLogin").removeAttr("disabled"); 
		}
		}
});
}
function oneKey_fun(msg){
$("#oneKeyLogin").removeAttr("disabled"); 
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
	var	weburl="http://www.openportal.com.cn";
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
	alert(msg.msg);
}
}



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
				$("#btnAcpassword").removeAttr("disabled"); 
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
	var t=msg.msg;
	if(t!=null&&t!=""){
		window.alert = function(name){
			var iframe = document.createElement("IFRAME");
			iframe.style.display="none";
			iframe.setAttribute("src", 'data:text/plain,');
			document.documentElement.appendChild(iframe);
			window.frames[0].window.alert(name);
			iframe.parentNode.removeChild(iframe);
		}
		alert(t);
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
	var	weburl="http://www.openportal.com.cn";
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
	}
	$("#InputAccount").focus();
}
}


function passwordLogin(){
var str="/ajax_WISPr_gzh.action";
var data_str=encodeToUTF8(str);
$.ajax({
	type: "GET",
	url: data_str,
	success: password_fun,
	error: null,
	timeout: 5000,
	dataType: "json",
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
				$("#btngdpass").removeAttr("disabled"); 
		}
		}
});
}

function password_fun(msg){
$("#btngdpass").removeAttr("disabled"); 
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
}
else if(msg.ret==10){
	window.alert = function(name){
		var iframe = document.createElement("IFRAME");
		iframe.style.display="none";
		iframe.setAttribute("src", 'data:text/plain,');
		document.documentElement.appendChild(iframe);
		window.frames[0].window.alert(name);
		iframe.parentNode.removeChild(iframe);
	}
	alert("参数获取失败，请重试！！");
	var	weburl="http://www.openportal.com.cn";
	window.location.replace(weburl);
}
else{
	window.alert = function(name){
		var iframe = document.createElement("IFRAME");
		iframe.style.display="none";
		iframe.setAttribute("src", 'data:text/plain,');
		document.documentElement.appendChild(iframe);
		window.frames[0].window.alert(name);
		iframe.parentNode.removeChild(iframe);
	}
alert(msg.msg);   	
}
}






function smsLogin(phone,yzm){
	var str="/ajax_WISPr_sms.action?phone="+phone+"&yzm="+yzm;
	var data_str=encodeToUTF8(str);
	$.ajax({
		type: "GET",
		url: data_str,
		success: sms_fun,
		error: null,
		timeout: 5000,
		dataType: "json",
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
					$("#btnSms").removeAttr("disabled"); 
　　　　		}
　　		}
	});
	}

	function sms_fun(msg){
	$("#btnSms").removeAttr("disabled"); 
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
	}
	else if(msg.ret==10){
		window.alert = function(name){
			var iframe = document.createElement("IFRAME");
			iframe.style.display="none";
			iframe.setAttribute("src", 'data:text/plain,');
			document.documentElement.appendChild(iframe);
			window.frames[0].window.alert(name);
			iframe.parentNode.removeChild(iframe);
		}
		alert("参数获取失败，请重试！！");
		var	weburl="http://www.openportal.com.cn";
		window.location.replace(weburl);
	}
	else{
		window.alert = function(name){
			var iframe = document.createElement("IFRAME");
			iframe.style.display="none";
			iframe.setAttribute("src", 'data:text/plain,');
			document.documentElement.appendChild(iframe);
			window.frames[0].window.alert(name);
			iframe.parentNode.removeChild(iframe);
		}
		alert(msg.msg);
	}
	}






function weixinLogin(){
$("#weixinLogin").removeAttr("disabled"); 
var str="/ajax_WISPr_weixin.action";
var data_str=encodeToUTF8(str);
$.ajax({
	type: "GET",
	url: data_str,
	success: weixin_fun,
	error: null,
	timeout: 5000,
	dataType: "json",
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
				$("#weixinLogin").removeAttr("disabled"); 
		}
		}
});
}

function weixin_fun(msg){
	$("#weixinLogin").removeAttr("disabled"); 
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
}
else if(msg.ret==10){ 
	window.alert = function(name){
		var iframe = document.createElement("IFRAME");
		iframe.style.display="none";
		iframe.setAttribute("src", 'data:text/plain,');
		document.documentElement.appendChild(iframe);
		window.frames[0].window.alert(name);
		iframe.parentNode.removeChild(iframe);
	}
	alert("获取连接参数失败，等待页面刷新！！");
	var	weburl="http://www.openportal.com.cn";
	window.location.replace(weburl);
}
else{ 
	window.alert = function(name){
		var iframe = document.createElement("IFRAME");
		iframe.style.display="none";
		iframe.setAttribute("src", 'data:text/plain,');
		document.documentElement.appendChild(iframe);
		window.frames[0].window.alert(name);
		iframe.parentNode.removeChild(iframe);
	}
	alert(msg.msg);
}
}