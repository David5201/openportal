$(document).ready(function() {
	//随机背景
	var random_bg = Math.floor(Math.random() * 6 + 1);
	//var bg='url(./dist/css/img/bg'+random_bg+'.jpg) no-repeat center top fixed)';
	$("#main").css({
		"background": "url(dist/css/img/bg" + random_bg + ".jpg) no-repeat center top fixed",
		"-webkit-background-size": "cover",
		"-moz-background-size": "cover",
		"-o-background-size": "cover",
		"background-size": "cover"
	});
	
	$('.carousel').carousel({
		interval: 5000 // in milliseconds
	});
	$('.carousel').carousel('cycle');

	//直接认证
	$("#oneKeyLogin").click(function() {
		$("#oneKeyLogin").attr('disabled',"true");
		oneKeyLogin();
	});
	//微信认证
	$("#weixinLogin").click(function() {
		$("#weixinLogin").attr('disabled',"true");
		weixinLogin();
	});
	//APP认证
	$("#appLogin").click(function() {
		window.location.href = '/Portal.apk';
	});
	//访客认证授权返回登录页
	$("#gobackAuth").click(function() {
		window.location.reload();
	});
	//返回登录页
	$("#goauth").click(function() {
		var	weburl="auth.html";
		window.location.replace(weburl);
	});
	//访客认证
	$("#guestLogin").click(function() {
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
			$(".row").replaceWith('<div class="col-sm-4 col-md-offset-4">\n' + '<div id="wxthum" class="thumbnail">\n' + '<img src="'+url+'" alt="" style="height:160px;width:160px;">\n' + '<div class="caption">\n' + '<h3>访客认证方法:</h3>\n' + '<p>请已认证员工，扫描二维码</p>\n' + '<p>授权</p>\n' + '<p>通过后,您就可以上网了。</p>\n' + '</div>\n' + '</div>\n' + '<br>\n' + '<button id="goback" type="button" class="btn btn-lg btn-default btn-block">返回</button>\n' + '</div>\n');
			$("#goback").click(function() {
				window.location.reload();
			});
			$("h2").replaceWith('');
			gip=msg.ip;
			gbasip=msg.basip;  
			interval = setInterval(guestHeart, 2000);
		}
		else if(msg.ret==10){
			
			alert(msg.msg);
			var	weburl=def_url;
			window.location.replace(weburl);
		}
		else if(msg.ret==1){
			
			alert(msg.msg);
		}
		else if(msg.ret==2){
			
			alert(msg.msg);
		}
		else if(msg.ret==119){
			alert(msg.msg);
			var	weburl="ok.html";
			window.location.replace(weburl);
		}
		else{
			
		alert(msg.msg);
		}
	}
	//访客授期
	$("#guestAuth").click(function() {
			var ip = $("#InputIP").val();
			var basip = $("#InputBasIP").val();
			var mac = $("#InputMAC").val();
			var site = $("#InputSite").val();
			if (!ip) {
				
				alert("参数获取错误！！");
				return false;
			}
			if (!basip) {
				
				alert("参数获取错误！！");
				return false;
			}
			//授权认证
			window.location.href = '/ajax_guestAuth.action?ip='+ip+'&basip='+basip+'&mac='+mac+'&site='+site;
	});
	//帐号密码认证
	$("#accountLogin").click(function() {
		$("h2").replaceWith('');
		$(".row").replaceWith('<div class="col-sm-4 col-md-offset-4"><h3>请输入帐号和密码</h3>\n' + '<form id="login_form" role="form">\n' + '<div class="form-group"><input type="text" class="form-control" id="InputAccount" name="usr" placeholder="帐号"></div>\n' + '<div class="form-group"><input type="password" class="form-control" id="InputPassword" name="pwd"  placeholder="密码"></div>\n' + '<button id="btnAcpassword" type="button" class="btn btn-lg btn-info btn-block">登录</button><button id="goback" type="button" class="btn btn-lg btn-default btn-block">返回</button><input type="hidden" name="basip" value='+basip+'><input type="hidden" name="ip" value='+ip+'><input type="hidden" name="umac" value='+umac+'><input type="hidden" name="apmac" value='+apmac+'><input type="hidden" name="ssid" value='+ssid+'></form></div>\n');
		$("#InputAccount").focus();
		$("#goback").click(function() {
			window.location.reload();
		});
		$("#btnAcpassword").click(function() {
			var account = $("#InputAccount").val();
			var password = $("#InputPassword").val();
			if (!account) {
				
				alert("请输入用户名");
				return false;
			}
			if (!password) {
				
				alert("请输入密码");
				return false;
			}
			//授权认证
			$("#btnAcpassword").attr('disabled',"true");
			accountLogin();
		});
	});
	//公众号认证
	$("#gzhLogin").click(function() {
		$(".row").replaceWith('<div class="col-sm-4 col-md-offset-4">\n' + '<div id="wxthum" class="thumbnail">\n' + '<img src="./dist/css/img/wx.png" alt="" style="height:128px;width:128px;">\n' + '<div class="caption">\n' + '<h3>公众号认证方法</h3>\n' + '<p>微信连WIFI后，关注公众号</p>\n' + '<p>查看公众号</p>\n' + '<p>回复"上网"或点击"上网验证"菜单，获取联网权限！！</p>\n' + '</div>\n' + '</div>\n' + '<button id="btngdpass" type="button" class="btn btn-lg btn-success btn-block">微信连WIFI</button>\n<button id="goback" type="button" class="btn btn-lg btn-default btn-block">返回</button>\n' + '</div>\n');
		$("#goback").click(function() {
			window.location.reload();
		});
		$("h2").replaceWith('');
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
	$("#smsLogin").click(function() {
		$("h2").replaceWith('');
		$(".row").replaceWith('<div class="col-sm-4 col-md-offset-4"><h3>短信认证</h3>\n' + '<form role="form">\n' + '<div class="form-group"><input type="text" class="form-control" id="InputPhone" placeholder="手机号"><button type="button" id="getSms" class="btn btn-lg btn-default btn-block">获取验证码</button></div>\n' + '<div class="form-group"><input type="text" class="form-control" id="InputYZM" placeholder="验证码"></div>\n' + '<button id="btnSms" type="button" class="btn btn-lg btn-info btn-block">提交</button><button id="goback" type="button" class="btn btn-lg btn-default btn-block">返回</button></form></div>\n');
		$("#InputPhone").focus();
	
		$("#goback").click(function() {
			window.location.reload();
		});
	
	
		var orig_time = 60;
		var isLock = false; // 获取验证码按钮锁定
			okLock = false; // 确定按钮锁定
			time   = orig_time;
			
		$("#getSms").click(function() {
			var phone = $('#InputPhone').val();
			if(phone=="") {
				
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
								 
								 alert(info);
						      	},3000);
							}
						}else{
							time=-1;
							
							alert(data.msg);
							$("#InputPhone").focus();
						}
				 }
				
				});
		}
		
		$("#btnSms").click(function() {
			var iyzm=$('#InputYZM').val();
			// 验证短信
			if(iyzm=="" || iyzm==null){
				$(this).val('确认');
				
				alert('请输入验证码！');
			}else{
				var phone = $('#InputPhone').val();
				if(phone=="" || phone==null){
				$(this).val('确认');
				
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
				btn.html('重新获取验证码');
				time = orig_time;
			}
		}
	});
	
});