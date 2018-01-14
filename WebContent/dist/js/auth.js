function LoginOut(){
var str="/ajax_LoginOut.action";
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
	var web=$('#web').val();
	weburl="/"+web+"out.jsp";
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
var str="/openportal/ajax_Login.action";
var data_str=encodeToUTF8(str);
console.log(data_str);
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
	var web=$('#web').val();
	var	weburl="/"+web+"ok.jsp?u="+msg.u+"&i="+msg.i+"&m="+msg.msg+"&l="+msg.l;
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
var str="/ajax_Login.action";
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
	var web=$('#web').val();
	var	weburl="/"+web+"ok.jsp?u="+msg.u+"&i="+msg.i+"&m="+msg.msg+"&l="+msg.l;
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
var str="/ajax_gzh.action";
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
	appId=msg.appId;    
	extend=msg.extend;    
	timestamp=msg.timestamp; 
	sign=msg.sign;    
	shop_id=msg.shop_id;  
	authUrl=msg.authUrl;  
	mac=msg.mac;     
	ssid=msg.ssid;      
	bssid=msg.bssid;
	var web=$('#web').val();
	var	weburl="/"+web+"wx.jsp?appId="+appId+"&extend="+extend+"&timestamp="+timestamp+"&sign="+sign+"&shop_id="+shop_id+"&authUrl="+authUrl+"&mac="+mac+"&ssid="+ssid+"&bssid="+bssid;
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
	var str="/ajax_sms.action?phone="+phone+"&yzm="+yzm;
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
		var web=$('#web').val();
		var	weburl="/"+web+"ok.jsp?u="+msg.u+"&i="+msg.i+"&m="+msg.msg+"&l="+msg.l;
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
var str="/ajax_weixin.action";
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
	appId=msg.appId;    
	extend=msg.extend;    
	timestamp=msg.timestamp; 
	sign=msg.sign;    
	shop_id=msg.shop_id;  
	authUrl=msg.authUrl;  
	mac=msg.mac;     
	ssid=msg.ssid;      
	bssid=msg.bssid;
	var web=$('#web').val();
	var	weburl="/"+web+"wx.jsp?appId="+appId+"&extend="+extend+"&timestamp="+timestamp+"&sign="+sign+"&shop_id="+shop_id+"&authUrl="+authUrl+"&mac="+mac+"&ssid="+ssid+"&bssid="+bssid;
	
	var browser = {
                versions: function () {
                    var u = navigator.userAgent, app = navigator.appVersion;
                    return {
                        trident: u.indexOf('Trident') > -1, //IE内核
                        presto: u.indexOf('Presto') > -1, //opera内核
                        webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                        mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
                        ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                        iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                        iPad: u.indexOf('iPad') > -1, //是否iPad
                        webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
                        isNo: u.indexOf('Touch') > -1 || u.indexOf('PlayBook') > -1//自定义扩展
                    };
                }()
            }
            if (browser.versions.mobile == true && ((browser.versions.ios == true && browser.versions.iPhone == true && browser.versions.iPad == false) || browser.versions.isNo == true || browser.versions.android == true)) {
                weburl="/"+web+"wx.jsp?appId="+appId+"&extend="+extend+"&timestamp="+timestamp+"&sign="+sign+"&shop_id="+shop_id+"&authUrl="+authUrl+"&mac="+mac+"&ssid="+ssid+"&bssid="+bssid;
            }else{
				weburl="/"+web+"wxpc.jsp?appId="+appId+"&extend="+extend+"&timestamp="+timestamp+"&sign="+sign+"&shop_id="+shop_id+"&authUrl="+authUrl+"&mac="+mac+"&ssid="+ssid+"&bssid="+bssid;
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