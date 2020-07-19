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
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">满意度</h1>
		<a href="../en/satisfied.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<form class="m-t-150 mui-form-group" method="post" novalidate="novalidate">
	<div class="mui-content">
	    <input type="hidden" value="${param.jian}" name="userid"/>
	    
	    <input type="hidden" value="${model.id}" name="username"/>
	    
		<h1 class="f-s-14 ft-999999 t-a-c f-w-n m-t-40">请对您的司机服务做出评价</h1>
		<input type="number" class="rating" min="0" max="5" step="1" />
		<div class="rating-caption m-t-50 ft-ff8a00 f-s-12 t-a-c"></div>
		<div class="p-rl-45 m-t-40">
			<textarea class="mui-star-leave f-s-16 ft-333333 p-rl-40 p-tb-25 bg-transparent" placeholder="请输入您的留言" name="content"></textarea>
			<button class="mui-btn mui-btn-block m-t-60 ft-ffffff f-s-16 bg-2cad6e" type="submit">确认提交</button>
		</div>
	</div>
	</form>
	<script type="text/javascript">
		$(function() {
			$('input.rating').rating({
				showClear: false,
				showCaption: true,
				captionElement: $('.rating-caption'),
				symbol: ' \ue006 ',
				clearCaption: '您的评价会让司机做的更好',
				starCaptions: {
					1: '很不满意，各方面都很差',
					2: '不满意，比较差',
					3: '一般，还需改善',
					4: '较满意，仍有改善空间',
					5: '非常满意，无可挑剔'
				}
			});
			
			// 表单提交点击事件
			$('.mui-form-group').on('tap', '.mui-btn', function() {
				 setTimeout(function() {
		                mui(this).button('reset');
		                var number =$('input[type="number"]').val();
		                var content = $("textarea[name='content']").val();
		                var _username = $('input[name="username"]').val();
		                var _userid = $('input[name="userid"]').val();
		                if(number==null || number=="" || number==0){
		                	mui.toast("内容不允许为空");
		                }else{
						$.post('../zh/satisfied', {utel:_userid,id:_username,leavel: number, beizhu: content}, function(data) {
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
		});
	</script>
</body>
</html>