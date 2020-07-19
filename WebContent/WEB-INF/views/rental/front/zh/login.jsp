<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>
    <title>安诺租车</title>
    <style type="text/css">
    .mui-toast-message {
    font-size:0.55rem;
    width:6.5rem;
    padding: 0px 15px;
    text-align: center;
    color: #fff;
    border-radius: 6px;
    background-color: #323232;
    line-height: 0.7rem;
    }
    </style>
</head>

<body>
<script type="text/javascript">
	$(document).ready(function(){
	    var strName = localStorage.getItem('zh_tel');
	    var strPass = localStorage.getItem('zh_pwd');
	    if(strName){
	        $("input[name='drivertel']").val(strName);
	    }if(strPass){
        	$("input[name='driverpwd']").val(strPass);
	    }
	
	});
</script>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">用车人登录</h1>
		<a href="../en/login.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<div class="mui-content">
		<img class="m-t-110 m-lr-auto d-b w-p-32" src="${files }/images/login.png" alt="" />
		<form class="m-t-150 mui-form-group" method="post" novalidate="novalidate">
			<div class="mui-input-row h-80 p-rl-40">
				<span class="mui-input-icon mui-input-iphone">
					<img class="h-p-100" src="${files }/images/icon_iphone.png" alt="" />
				</span>
				<span class="mui-input-icon mui-input-clear" id="clear">
					<img class="w-p-100" src="${files }/images/icon_clear.png" alt="" />
				</span>
				<div class="mui-input-text">
					<input name="usertel" type="text" class="f-s-16"  placeholder="请输入您的手机号码" />
				</div>
			</div>
			<div class="mui-input-row h-80 p-rl-40">
				<span class="mui-input-icon mui-input-lock">
					<img class="h-p-100" src="${files }/images/icon_lock.png" alt="" />
				</span>
				<span class="mui-input-icon mui-input-eye" id="show">
					<img class="w-p-100" id="eye" src="${files }/images/icon_eye_close.png" alt="" />
				</span>
				<div class="mui-input-text">
					<input name="userpwd" id="pwd" type="password" class="f-s-16" placeholder="请输入您的登录密码" />
				</div>
			</div>
			
			<div class="p-rl-40">
				<button class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e" type="submit">登&nbsp;&nbsp;录</button>
				<a class="ft-aaaaaa mui-pull-right m-t-15 f-s-14" href="forget.html">忘记密码？</a>
			</div>
		</form>
	</div>
	<div class="mui-bar h-74 bg-eceef0" style="bottom: 0px; height:2rem ;">
		<div class="mui-title f-s-14 l-h-d-74" onclick="record();" style="color: #2cad6e;margin-bottom: 2rem;">
		<img src="${files }/images/cop_iocn.png" style="position: relative;vertical-align: middle;" /> 沪ICP备17040677号</div>
		<div class="mui-title f-s-14 l-h-d-74" style="right: 0px;left: 0px;top: 0.5rem;">上海市吴中东路555号天源大厦7楼  021-33385558 </div>
		<div style="width:60%; margin:1.4rem 35%;">
	 		<a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=31010402003593" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;">
	 		<img src="${files }/images/record.png" style="float:left;"/><p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#939393;">沪公网安备 31010402003593号</p></a>
	 	</div>
	</div>
	<script type="text/javascript">
    function record() {
	    window.location.href="http://www.miitbeian.gov.cn/";
    }
		$(function() {
			mui.init();
			
			//清除账号
			$("#clear").click(function(){				
				$("input[name='usertel']").val("")				
			})
			
		    //显示密码		 
		   $("#show").click(function(){				
			   if ($("#pwd").attr("type") == "password") {
				   $("#pwd").attr("type", "text");
				   $("#eye").attr("src", "${files }/images/icon_eye_open.png");
				   }
				   else {
				   $("#pwd").attr("type", "password")
				   $("#eye").attr("src", "${files }/images/icon_eye_close.png");
				   }		
			})	
			// 表单提交点击事件
			$('.mui-form-group').on('tap', '.mui-btn', function() {
	            setTimeout(function() {
	                mui(this).button('reset');
	            	var _tel =$('input[name="usertel"]').val();
					var _pwd =$('input[name="userpwd"]').val();
					$.post('../zh/userlogin', {tel: _tel, pwd: _pwd}, function(data) {
						if(data.succeed == true) {
							localStorage.setItem('zh_tel',_tel);
							localStorage.setItem('zh_pwd',_pwd);
							var reqUrl = "${requestUrl}";
							var openid = data.result.userOpenid;
							if(openid != null && openid != ''){//如果openid为空，则先去获取openid，再做页面跳转
								if(reqUrl != null && reqUrl != ""){
									window.location.href = reqUrl;
								}else{
									window.location.href = '/zh/index.html';
								}
							}else{
								var wxapiurl = "/zh/wxapi.html?redirectUrl="+reqUrl;
								window.location.href = wxapiurl;
							}
						} else {
						   mui.toast(data.message,{duration:5000,type:'div'});
						}
					});
	            }.bind(this), 1000);
	            mui(this).button('loading');
			});
		});
	</script>
</body>
</html>