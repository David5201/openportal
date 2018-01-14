$(document).ready(function() {
	$("#goUserAuth").click(function() {
		var r = window.location.search.substr(1);
		var	weburl="auth.html?"+r;
        window.location.replace(weburl);
	});
	$("#goGuestAuth").click(function() {
		var r = window.location.search.substr(1);
		var	weburl="auth2.html?"+r;
        window.location.replace(weburl);
	});
	$("#goSmsAuth").click(function() {
		var r = window.location.search.substr(1);
		var	weburl="auth2.html?"+r;
        window.location.replace(weburl);
	});
	$("#goWeixinAuth").click(function() {
		var r = window.location.search.substr(1);
		var	weburl="auth1.html?"+r;
        window.location.replace(weburl);
	});
	$("#goAppAuth").click(function() {
		var r = window.location.search.substr(1);
		var	weburl="auth1.html?"+r;
        window.location.replace(weburl);
	});
	$("#goOnekeyAuth").click(function() {
		var r = window.location.search.substr(1);
		var	weburl="auth1.html?"+r;
        window.location.replace(weburl);
	});
	
	//直接认证
	$("#oneKeyLogin").click(function() {
		if (!$('#protocol-checkbox').prop('checked')) {
			$("#portal-login-tip").html("请勾选上网协议");
			return false;
		}
		$("#oneKeyLogin").attr('disabled',"true");
		oneKeyLogin();
	});
	//微信认证
	$("#weixinLogin").click(function() {
		if (!$('#protocol-checkbox').prop('checked')) {
			$("#portal-login-tip").html("请勾选上网协议");
			return false;
		}
		$("#weixinLogin").attr('disabled',"true");
		weixinLogin();
	});
	//APP认证
	$("#appLogin").click(function() {
		if (!$('#protocol-checkbox').prop('checked')) {
			$("#portal-login-tip").html("请勾选上网协议");
			return false;
		}
		window.location.href = '/Portal.apk';
	});
	//联系管理员
	$("#sendMessage").click(function() {
		window.open("/message_sendUI.action","_blank","");
	});
	//自助中心
	$("#userCenter").click(function() {
		window.open("/customerLogin.action","_blank","");
	});
	//返回登录页
	$("#goauth").click(function() {
		var	weburl="auth.html";
		window.location.replace(weburl);
	});
	//访客认证
	$("#guestLogin").click(function() {
		if (!$('#protocol-checkbox').prop('checked')) {
			$("#portal-login-tip").html("请勾选上网协议");
			return false;
		}
		$("#guestLogin").attr('disabled',"true");

			var str="/ajax_guest.action?basip="+basip+"&ip="+ip+"&umac="+umac+"&apmac="+apmac+"&ssid="+ssid;
			var data_str=encodeToUTF8(str);
			$.ajax({
				type: "GET",
				url: data_str,
				success: guest_fun,
				error: null,
				timeout: 5000,
				dataType: "json",
				cache: false,
	complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
		　　　　		if(status=='timeout'){//超时,status还有success,error等值的情况
		　　　　			$("#portal-login-tip").html("获取二维码超时！！");
							alert("获取二维码超时！！");
							$("#guestLogin").removeAttr("disabled"); 
		　　　　		}
		　　		}
			});
	});
	var gip;
	var gbasip;
	function guestHeart() {  
		var str="/ajax_guestHeart.action?ip="+gip+"&basip="+gbasip;
		var data_str=encodeToUTF8(str);
    	$.ajax({ 
    		type : "GET", 
            url : data_str,
            dataType: "json", 
            success : function(msg) { 
            	if(msg.ret==0){ 
            		var web=$('#web').val();
            		var	weburl="ok.html?u="+msg.u+"&i="+msg.i+"&w="+msg.w+"&m="+msg.msg+"&l="+msg.l;
            		window.location.replace(weburl);
        		}
            } 
        }); 
    	
    }
	
	function guest_fun(msg){
		$("#guestLogin").removeAttr("disabled"); 
		if(msg.ret==0){ 
			var url=msg.url;
			var trs ='<div class="portal-code-box"><img src="'+url+'" alt="用户名密码认证用户手机浏览器扫描二维码授权" style="height:128px;width:128px;display:block;margin-left:auto;margin-right:auto;"/></div><p class="attr" style="text-align:center">用户名密码认证用户手机浏览器扫描二维码授权</p><button id="goback" class="button button-highlight button-block">返回</button>';  
			$("#portalContext").empty();
			$("#portalContext").append(trs);
			$("#goback").click(function() {
				window.location.reload();
			});
			gip=msg.ip;
			gbasip=msg.basip;  
			interval = setInterval(guestHeart, 2000);
		}
		else if(msg.ret==10){
			$("#portal-login-tip").html(msg.msg);
			alert(msg.msg);
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
	
	//帐号密码认证
	$("#btnAcpassword").click(function() {
		if (!$('#protocol-checkbox').prop('checked')) {
			$("#portal-login-tip").html("请勾选上网协议");
			return false;
		}
		var account = $("#InputAccount").val();
		var password = $("#InputPassword").val();
		if (!account) {
			$("#InputAccount").focus();
			$("#portal-login-tip").html("请输入用户名");
			alert("请输入用户名");
			return false;
			}
		if (!password) {
			$("#InputPassword").focus();
			$("#portal-login-tip").html("请输入密码");
			alert("请输入密码");
			return false;
			}
		//授权认证
		$("#btnAcpassword").attr('disabled',"true");
		accountLogin();
	});
	
	//公众号认证
	$("#gzhLogin").click(function() {
		if (!$('#protocol-checkbox').prop('checked')) {
			$("#portal-login-tip").html("请勾选上网协议");
			return false;
		}
		var trs ='<div class="tips"><div id="portal-login-tip"></div></div><div class="portal-code-box"><img src="weixin/wx.png" style="height:60px;width:60px;display:block;margin-left:auto;margin-right:auto;"/></div><p class="attr" style="text-align:center">微信连WIFI后，进入公众号，回复"上网"或点击"上网验证"菜单！</p><button id="btngdpass" class="button button-primary button-block">微信连WIFI</button><button id="goback" class="button button-highlight button-block">返回</button>';  
		$("#portalContext").empty();
		$("#portalContext").append(trs);
		$("#goback").click(function() {
			window.location.reload();
		});
		$("#btngdpass").click(function() {
			//授权认证
			$("#btngdpass").attr('disabled',"true");
			passwordLogin();
		});
	});
	//下线
	$("#LoginOut").click(function() {
		$("#LoginOut").attr('disabled',"true");
		LoginOut();
	});
	//继续访问
	$("#goURL").click(function() {
		var url=get_url();
		window.open(url);
	});

	
	//关闭窗口
	$("#closeMe").click(function() {
		window.open('','_self');window.close();
	});
	//短信认证
	var orig_time = 60;
	time   = orig_time;
	$("#getSms").click(function() {
		if (!$('#protocol-checkbox').prop('checked')) {
			$("#portal-login-tip").html("请勾选上网协议");
			return false;
		}
		var phone = $('#InputPhone').val();
		if(phone=="") {
			$("#InputPhone").focus();
			$("#portal-login-tip").html('手机号码不为能空');
			alert('手机号码不为能空');
			return false;
		}
		// 获取短信验证码
		getSMSCode();
		});
		
		// 获取短信验证码
		function getSMSCode() {
			getSms.disabled=true;
			interval = setInterval(countDown, 1000);
			var phone = $('#InputPhone').val();
		 	var url="/ajax_smsAPI.action?phone="+phone;
			 $.ajax({
				 url: url,
				 type: "GET",
				 error: null,
				 timeout: 5000,
				 dataType: "json",
				 cache: false,
				 success:function(data){
					 if(data.ret==0){
						 $("#InputYZM").focus();
						 var info=data.msg;
						 if(info!=null&&info!=""){
							 setTimeout(function(){
								 //$("#portal-login-tip").html(info);
								 alert(info);
						      	},3000);
							}
						}else{
							time=-1;
							$("#portal-login-tip").html(data.msg);
							alert(data.msg);
							$("#InputPhone").focus();
						}
				 }
				
				});
		}
		
		$("#btnSms").click(function() {
			if (!$('#protocol-checkbox').prop('checked')) {
			$("#portal-login-tip").html("请勾选上网协议");
			return false;
			}
			var iyzm=$('#InputYZM').val();
			// 验证短信
			if(iyzm=="" || iyzm==null){
				$(this).val('确认');
				$("#InputYZM").focus();
				$("#portal-login-tip").html('请输入验证码！');
				alert('请输入验证码！');
			}else{
				var phone = $('#InputPhone').val();
				if(phone=="" || phone==null){
				$(this).val('确认');
				$("#InputPhone").focus();
				$("#portal-login-tip").html('请输入手机号！');
				alert('请输入手机号！');
			}else{
				$("#btnSms").attr('disabled',"true");
				smsLogin(phone,iyzm);
			}
			}			
		});
		
		// 倒计时
		function countDown() {
			var btn = $('#getSms');
			time--
			if (time >= 0) {
				btn.html(time);
				getSms.disabled=true;
			} else {
				clearInterval(interval);
				getSms.disabled=false;
				btn.html('重新获取');
				time = orig_time;
			}
		}
	
});