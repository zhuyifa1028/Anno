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
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">Change Password</h1>
		<a href="../zh/change.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">中</a>
	</header>
	<div class="mui-content">
		<div class="p-rl-45 m-t-50">
			<form class="mui-form-group" method="post" novalidate="novalidate">
				<input type="hidden" name="parent_id" value="${model.id }" />
				<div class="m-t-30 mui-input">
					<input class="f-s-16" type="number" name="userpwd" placeholder="Please enter your current password" />
				</div>
				<div class="m-t-30 mui-input">
					<input class="f-s-16" type="number" name="pwd" placeholder="Please enter a new password" />
				</div>
				<div class="m-t-30 mui-input">
					<input class="f-s-16" type="number" name="upwd" placeholder="Please confirm the new password" />
				</div>
				<button class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e">Confirm</button>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			mui.init();
			// 表单提交点击事件
			$('.mui-form-group').on('tap', '.mui-btn', function() {
	            setTimeout(function() {
	                mui(this).button('reset');
	            	var _userpwd =$('input[name="userpwd"]').val();
					var _pwd =$('input[name="pwd"]').val();
					var _upwd =$('input[name="upwd"]').val();
					var _id=$('input[name="parent_id"]').val();
					$.post('../en/change', {id:_id,userpwd: _userpwd, pwd: _pwd,upwd:_upwd}, function(data) {
						if(data.succeed == true) {
							window.location.href = '../en/login.html';
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