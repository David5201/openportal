//============按照项目实际情况修改====Start
var def_url="http://www.openportal.com.cn";
var basipQ="basip";
var wlanuseripQ="wlanuserip";
var macQ="mac";
var ssidQ="ssid";
var apmacQ="apmac";
function go_url(){
	var url=get_url();
//	window.location.replace(url);   //认证成功后当前页面跳转
	window.open(url,"_blank","");   //认证成功后新窗口打开（可能会被浏览器拦截）
}
//============按照项目实际情况修改====End

var	basip=GetQueryString(basipQ);
var	ip=GetQueryString(wlanuseripQ);
var	umac=GetQueryString(macQ);
var ssid=GetQuerySSID();
var	apmac=GetQueryString(apmacQ);
var htmlUrl=get_htmlurl();
var	ikenc=GetIkuai();
if(ikenc!=""){
var cs=base64Decode(ikenc);
if(cs!=""){
basip=GetIkuaiQueryString(cs,"basip");
ip=GetIkuaiQueryString(cs,"user_ip");
umac=GetIkuaiQueryString(cs,"mac");
ssid=GetIkuaiQueryString(cs,"ssid");
apmac=GetIkuaiQueryString(cs,"apmac");
basip=trim(basip);
}
}


	function get_url(){
		var url=GetQueryString("w");
		if(url==""){
			url=def_url;
		}
		if(url==null){
			url=def_url;
		}
		if(url=="null"){
			url=def_url;
		}
		if(url=="undefined"){
			url=def_url;
		}
		return url;
	}
	var url = window.location.href;
	if(url.indexOf("ok.html") > 0 )
	{
		go_url();
	}
	if(url.indexOf("wifidogOk.html") > 0 )
	{
		go_url();
	}
	
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return "";
}

function GetIkuai()
{
     var r = window.location.search.substr(1);
	 var bool = r.indexOf("&ikenc=");
	 if(bool>=0){
		rs=r.split("&ikenc=");
		var a=rs[1];
		var booll = a.indexOf("&");
		if(booll>=0){
			as=a.split("&");
			return as[0];
		}else{
			return a;
		}
	 }
	 return "";
}

function GetIkuaiQueryString(key,name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = key.match(reg);
     if(r!=null)return  unescape(r[2]); return "";
}

function GetQuerySSID()
{
     var r = window.location.search.substr(1);
	 var bool = r.indexOf(ssidQ);
	 if(bool>=0){
		rs=r.split("ssid=");
		var a=rs[1];
		var booll = a.indexOf("&");
		if(booll>=0){
			as=a.split("&");
			return as[0];
		}else{
			return a;
		}
	 }
	 return "";
}

function GetQueryUSER()
{
     var r = window.location.search.substr(1);
	 var bool = r.indexOf("u=");
	 if(bool>=0){
		rs=r.split("u=");
		var a=rs[1];
		var booll = a.indexOf("&");
		if(booll>=0){
			as=a.split("&");
			return as[0];
		}else{
			return a;
		}
	 }
	 return "";
}

function get_htmlurl(){
var r = window.location.href;
var bool = r.indexOf("?");
if(bool>=0){
	rs=r.split("?");
	var a=rs[0];
	var booll = a.indexOf(".html");
	if(booll>=0){
		var n=a.lastIndexOf("/");
		return a.substring(0,n+1);
	}else{
		return "";
	}
}
else
{
var booll = r.indexOf(".html");
if(booll>=0){
	var n=r.lastIndexOf("/");
	return r.substring(0,n+1);
}else{
	return "";
}
}
}
        /*
         * Javascript base64encode() base64加密函数
           用于生成字符串对应的base64加密字符串
         * 吴先成  www.51-n.com ohcc@163.com QQ:229256237
         * @param string input 原始字符串
         * @return string 加密后的base64字符串
        */
        function base64Encode(input){
                var rv;
                rv = encodeURIComponent(input);
                rv = unescape(rv);
                rv = window.btoa(rv);
                return rv;
        }
        /*
         * Javascript base64Decode() base64解密函数
           用于解密base64加密的字符串
         * 吴先成  www.51-n.com ohcc@163.com QQ:229256237
         * @param string input base64加密字符串
         * @return string 解密后的字符串
        */
        function base64Decode(input){
                rv = window.atob(input);
                rv = escape(rv);
                rv = decodeURIComponent(rv);
                return rv;
        }
		
		function trim(str){ //删除左右两端的空格
　　    	return str.replace(/(^\s*)|(\s*$)/g, "");
　　 	}