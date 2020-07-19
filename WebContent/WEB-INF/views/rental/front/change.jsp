<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html class="bg-eceef0">
<head>
    <%@ include file="taglibs.jsp"%>
    <title>安诺租车</title>   
</head>
<style>
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


<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">修改密码</h1>
	</header>
	<div class="mui-content">
		<div class="p-rl-45 m-t-50">
			<form class="mui-form-group">
			    <c:forEach items="${models }" var="m">
				<input type="hidden" name="parent_id" value="${m.id }" />
				</c:forEach>
				
				<div class="m-t-30 mui-input">
					<input class="f-s-16" type="number" name="opwd" placeholder="请输入原密码" />
				</div>
				<div class="m-t-30 mui-input">
					<input class="f-s-16" type="number" name="npwd" placeholder="请输入新密码" />
				</div>
				<div class="m-t-30 mui-input">
					<input class="f-s-16" type="number" name="pwd" placeholder="请再次输入新密码" />
				</div>
				<button class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e">确认修改</button>
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
	            	var _opwd =$('input[name="opwd"]').val();
					var _pwd =$('input[name="npwd"]').val();
					var _upwd =$('input[name="pwd"]').val();
					//var _id=$('input[name="parent_id"]').val();
					$.post('change', {opwd:_opwd, npwd:_pwd,pwd:_upwd}, function(data) {
						if(data.succeed == true) {
							window.location.href = 'login.html';
						} else {
						   mui.toast(data.message,{ duration:'long', type:'div' });
						}
					});
	            }.bind(this), 1000);
	            mui(this).button('loading');
			});
		});
	</script>
</body>
</html>