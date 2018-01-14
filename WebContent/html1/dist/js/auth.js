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
			weburl="out.html";
			window.location.replace(weburl);
		}
		}
});
}
function loginOut_fun(msg){
$("#LoginOut").removeAttr("disabled"); 
if(msg.ret==0){
	weburl="out.html";
	window.location.replace(weburl);
}else{
	alert(msg.msg);
	weburl="out.html";
	window.location.replace(weburl);
}
}




function oneKeyLogin(){
var str="/ajax_Login.action?basip="+basip+"&ip="+ip+"&umac="+umac+"&apmac="+apmac+"&ssid="+ssid;
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
			$("#portal-login-tip").html("认证超时！！");
			alert("认证超时！！");
			$("#oneKeyLogin").removeAttr("disabled"); 
		}
		}
});
}
function oneKey_fun(msg){
$("#oneKeyLogin").removeAttr("disabled"); 
if(msg.ret==0){
	var	weburl="ok.html?u="+msg.u+"&i="+msg.i+"&w="+msg.w+"&m="+msg.msg+"&l="+msg.l;
	window.location.replace(weburl);
}else if(msg.ret==10){
	$("#portal-login-tip").html("参数获取失败，请重试！！");
	alert("参数获取失败，请重试！！");
	var	weburl=def_url;
	window.location.replace(weburl);
}else if(msg.ret==119){
	$("#portal-login-tip").html(msg.msg);
	alert(msg.msg);
	var	weburl="ok.html";
	window.location.replace(weburl);
}
else{
	$("#portal-login-tip").html(msg.msg);
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
			$("#portal-login-tip").html("认证超时！！");
			alert("认证超时！！");
			$("#btnAcpassword").removeAttr("disabled"); 
		}
		}
});
}
function login_fun(msg){
$("#btnAcpassword").removeAttr("disabled"); 
if(msg.ret==0){
	var	weburl="ok.html?u="+msg.u+"&i="+msg.i+"&w="+msg.w+"&m="+msg.msg+"&l="+msg.l;
	var t=msg.msg;
	if(t!=null&&t!=""){
		$("#portal-login-tip").html(t);
		alert(t);
	}
	window.location.replace(weburl);
}else if(msg.ret==10){
	$("#portal-login-tip").html("参数获取失败，请重试！！");
	alert("参数获取失败，请重试！！");
	var	weburl=def_url;
	window.location.replace(weburl);
}else if(msg.ret==119){
			$("#portal-login-tip").html(msg.msg);
			alert(msg.msg);
			var	weburl="ok.html";
			window.location.replace(weburl);
		}
		else{
	var t=msg.msg;
	if(t==null||t==""){
		$("#portal-login-tip").html("你操作太快！！");
		alert("你操作太快！！");
	}else{
		$("#portal-login-tip").html(t);
		alert(t);
		//window.location.reload();
	}
	$("#InputAccount").focus();
}
}


function passwordLogin(){
var str="/ajax_gzh.action?basip="+basip+"&ip="+ip+"&umac="+umac+"&apmac="+apmac+"&ssid="+ssid;
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
			$("#portal-login-tip").html("认证超时！！");
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
	var	weburl="wx.html?appId="+appId+"&extend="+extend+"&timestamp="+timestamp+"&sign="+sign+"&shop_id="+shop_id+"&authUrl="+authUrl+"&mac="+mac+"&ssid="+ssid+"&bssid="+bssid;
	window.location.replace(weburl);  
}
else if(msg.ret==10){
	$("#portal-login-tip").html("参数获取失败，请重试！！");
	alert("参数获取失败，请重试！！");
	var	weburl=def_url;
	window.location.replace(weburl);
}
else if(msg.ret==119){
	$("#portal-login-tip").html(msg.msg);
	alert(msg.msg);
	var	weburl="ok.html";
	window.location.replace(weburl);
}
else{
$("#portal-login-tip").html(msg.msg);
alert(msg.msg);   	
}
}






function smsLogin(phone,yzm){
	var str="/ajax_sms.action?phone="+phone+"&yzm="+yzm+"&basip="+basip+"&ip="+ip+"&umac="+umac+"&apmac="+apmac+"&ssid="+ssid;
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
　　　　			$("#portal-login-tip").html("认证超时！！");
					alert("认证超时！！");
					$("#btnSms").removeAttr("disabled"); 
　　　　		}
　　		}
	});
	}

	function sms_fun(msg){
	$("#btnSms").removeAttr("disabled"); 
	if(msg.ret==0){ 
		var	weburl="ok.html?u="+msg.u+"&i="+msg.i+"&w="+msg.w+"&m="+msg.msg+"&l="+msg.l;
		window.location.replace(weburl);
	}
	else if(msg.ret==10){
		$("#portal-login-tip").html("参数获取失败，请重试！！");
		alert("参数获取失败，请重试！！");
		var	weburl=def_url;
		window.location.replace(weburl);
	}else if(msg.ret==119){
		$("#portal-login-tip").html(msg.msg);
		alert(msg.msg);
		var	weburl="ok.html";
		window.location.replace(weburl);
	}
	else{
		$("#portal-login-tip").html(msg.msg);
		alert(msg.msg);
	}
	}






function weixinLogin(){
var str="/ajax_weixin.action?basip="+basip+"&ip="+ip+"&umac="+umac+"&apmac="+apmac+"&ssid="+ssid+"&htmlUrl="+htmlUrl;
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
			$("#portal-login-tip").html("认证超时！！");
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
	var	weburl="wx.html?appId="+appId+"&extend="+extend+"&timestamp="+timestamp+"&sign="+sign+"&shop_id="+shop_id+"&authUrl="+authUrl+"&mac="+mac+"&ssid="+ssid+"&bssid="+bssid;
	
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
                weburl="wx.html?appId="+appId+"&extend="+extend+"&timestamp="+timestamp+"&sign="+sign+"&shop_id="+shop_id+"&authUrl="+authUrl+"&mac="+mac+"&ssid="+ssid+"&bssid="+bssid;
            }else{
				weburl="wxpc.html?appId="+appId+"&extend="+extend+"&timestamp="+timestamp+"&sign="+sign+"&shop_id="+shop_id+"&authUrl="+authUrl+"&mac="+mac+"&ssid="+ssid+"&bssid="+bssid;
			}
	window.location.replace(weburl);  
}
else if(msg.ret==10){ 
	$("#portal-login-tip").html("参数获取失败，请重试！！");
	alert("获取连接参数失败，等待页面刷新！！");
	var	weburl=def_url;
	window.location.replace(weburl);
}
else if(msg.ret==119){
	$("#portal-login-tip").html(msg.msg);
	alert(msg.msg);
	var	weburl="ok.html";
	window.location.replace(weburl);
}
else{ 
	$("#portal-login-tip").html(msg.msg);
	alert(msg.msg);
}
}