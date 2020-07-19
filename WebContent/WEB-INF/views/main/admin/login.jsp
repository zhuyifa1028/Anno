<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="taglibs.jsp" %>
<script type="text/javascript" src="${files }/js/jquery/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="${files }/js/jquery/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${files }/js/jquery/validate/validate.method.js"></script>
<script type="text/javascript" src="${files }/js/jquery/form/jquery.form.js"></script>

<title>${title }-登录</title>
<script type="text/javascript">
	$(function() {
		var publickey = '${publickey}';
		if(!!publickey) {
			$('form.form').validate({
				submitHandler: function(form) {
					var _form = $(form);
					var _tel = _form.find('input[name="tel"]').val() || '';
					var _pwd = _form.find('input[name="pwd"]').val();
					_pwd = encrypt(_pwd, publickey) || '';
					if(!!_pwd && _tel) {
						$.post('${admin }/rental/login', {tel: _tel, pwd: _pwd}, function(data) {
							if(data.succeed == true) {
								window.location.href = '${admin }/rental/index.html';
							} else {
								error(data.message);
							}
						});
					} else {
						error('密码加密失败');
					}
					return false;
				}
			});
		} else {
			error('加载密钥失败');
		}
	});
</script>
</head>
<body class="gray-bg">
	<div class="loginColumns animated fadeInDown">
		<form class="form" method="post" novalidate="novalidate">
			<div class="row">
				<div class="col-md-6">
					<h2 class="font-bold">Welcome to 安诺租车</h2>
					<p>Perfectly designed and precisely prepared admin theme with over 50 pages with extra new web app views.</p>
					<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
					<p>When an unknown printer took a galley of type and scrambled it to make a type specimen book.</p>
					<p>
						<small>It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.</small>
					</p>
				</div>
				<div class="col-md-6">
					<div class="ibox-content">
						<div class="form-group">
							<input type="text" name="tel" class="form-control" placeholder="请输入登录手机号" data-validates="{required: true, messages: {required: '登录手机号不允许为空'}}" />
						</div>
						<div class="form-group">
							<input type="password" name="pwd" class="form-control" placeholder="请输入用户密码" required="required" data-validates="{required: true, messages: {required: '用户密码不允许为空'}}" />
						</div>
						<button type="submit" class="btn btn-primary block full-width m-b">登录</button>
						<a href="javascript:;"><small>忘记密码？</small></a>
						<p class="m-t">
							<small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small>
						</p>
					</div>
				</div>
			</div>
		</form>
		<hr />
		<div class="row">
			<div class="col-md-6">Copyright Example Company</div>
			<div class="col-md-6 text-right">
				<small>© 2014-2015</small>
			</div>
		</div>
	</div>
</body>
</html>