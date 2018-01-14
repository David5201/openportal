$(document).ready(function() {
	
		$("#InputPhone").focus();

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
		 	var url="/ajax_smsAPI_find.action?phone="+phone;
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
				
				alert('请输入验证码！');
			}else{
				var phone = $('#InputPhone').val();
				if(phone=="" || phone==null){
				
				alert('请输入手机号！');
			}else{
				$("#btnSms").attr('disabled',"true");
				document.forms[0].submit();
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