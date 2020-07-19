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
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">投诉建议</h1>
		<a href="../en/advice.html?jian=${param.jian }" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<div class="mui-content">
		<div class="p-t-40 p-rl-45">
			<div class="ft-999999 f-s-14">亲爱的用户:</div>
			<p class="ft-999999 f-s-14">为了提高我们的客户满意度，请您发送宝贵的建议给我们。</p>
			<div class="ft-999999 f-s-14">谢谢!</div>
			<div class="m-t-40">
				<form class="mui-form-group" method="post">
				    <input type="hidden" value="${param.jian }" name="drivertel"/>
				    <input type="hidden" value="${model.userName}" name="username"/>
				    <input type="hidden" value="${model.userTel}" name="usertel"/>
					<div class="m-t-30 mui-select mui-select-boult mui-select-bear">
						<span class="ft-999999 f-s-16" name="leixing">请选择您要投诉的类别</span>
						<input type="hidden" name="category" />
					</div>
					<div class="m-t-30 mui-select mui-select-boult mui-select-business">
						<span class="ft-999999 f-s-16" name="neirong">请选择您要投诉的内容</span>
						<input type="hidden" name="content" />
					</div>
					<textarea class="mui-star-leave m-t-30 f-s-16 ft-333333 p-rl-40 p-tb-25 bg-transparent" placeholder="请输入您要投诉的内容" name="tecontent"></textarea>
					<button class="mui-btn mui-btn-block m-t-60 ft-ffffff f-s-16 bg-2cad6e" type="submit">确认提交</button>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${files }/js/index_new.js"></script>
	<script type="text/javascript">
		$(function() {
			mui.init();
			// 表单提交点击事件
			var lid;
			var nid;
			$('.mui-form-group').on('tap', '.mui-btn', function() {
				setTimeout(function() {
	                mui(this).button('reset');
	              
	                var _complsort=$('span[name="leixing"]').html();
	                var _complsorts=$('span[name="neirong"]').html();
	                
	                var _compldetails = $('textarea[name="tecontent"]').val();
	                var _drivertel = $('input[name="drivertel"]').val();
	                var _username = $('input[name="username"]').val();
	                var _usertel = $('input[name="usertel"]').val();
	                if(_complsort==null || _complsort=="" || _complsort.length==0){
	                	mui.toast("内容不允许为空");
	                }else if(_complsorts==null || _complsorts=="" || _complsorts.length==0){
	                	mui.toast("内容不允许为空");
	                }else if(_compldetails==null || _compldetails=="" || _compldetails.length==0){
	                	mui.toast("内容不允许为空");
	                }else{
	                	 $.post('../zh/advice', {tel:_usertel,name:_username,driverid:_drivertel,complsort: _complsort, complsorts: _complsorts,compldetails:_compldetails}, function(data) {
		 						if(data.succeed == true) {
		 							window.location.href = '../zh/index.html';
		 						} else {
		 						   mui.toast(data.message,{duration:5000,type:'div'});
		 						}
		 					});
		                }
				}.bind(this), 1000);
		         mui(this).button('loading');
			});
			
			// 选择投诉类型
			$('.mui-form-group').on('tap', '.mui-select-bear', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');
				bear.show(function(rs) {
					_span.html(rs[0].text);
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs[0].value);
					lid=rs[0].value;
				});
				return false;
			});
			

			// 选择投诉内容
			$('.mui-form-group').on('tap', '.mui-select-business', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');
				if(lid==0){
				business.show(function(rs) {
					_span.html(rs[0].text);
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs[0].value);
				});
				}
				if(lid==1){
					a.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==2){
					chao.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==3){
					c.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==4){
					d.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==5){
					e.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==6){
					f.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==7){
					g.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==8){
					h.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==9){
					i.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==10){
					j.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==11){
					k.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==12){
					l.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==13){
					m.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==14){
					n.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==15){
					o.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==16){
					p.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==17){
					q.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==18){
					r.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==19){
					s.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==20){
					t.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==21){
					u.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==22){
					v.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				if(lid==23){
					x.show(function(rs) {
						_span.html(rs[0].text);
						if(_span.hasClass('ft-999999')) {
							_span.removeClass('ft-999999').addClass('ft-333333');
						}
						_input.val(rs[0].value);
					});
				}
				return false;
			});
		});
	</script>
</body>
</html>