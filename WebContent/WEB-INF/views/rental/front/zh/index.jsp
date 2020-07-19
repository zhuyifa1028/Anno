<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>
    <title>安诺租车</title>
    <style type="text/css">
    	.mui-navigate-right:after {
    		right: 0.533333rem;
    	}
    	.mui-table-view-cell:after {
    		height: 0px;
    	}
    </style>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">首页</h1>
		<a href="../en/index.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<div class="mui-content">
		<ul class="mui-table-view bg-transparent p-rl-45" style="margin-top: 0.666667rem;">
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="daily.html" class="mui-navigate-right p-r-40 bg-ffffff">
					<c:if test="${hasNew }">
						<span class="mui-badge mui-badge-danger f-s-9" style="right: 1.12rem;">new</span>
					</c:if>
					<span class="v-a-m m-l-5 f-s-16">日志确认</span>
				</a>
			</li>
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="transport.html" class="mui-navigate-right p-r-40 bg-ffffff">
					<span class="v-a-m m-l-5 f-s-16">用车信息翻译</span>
				</a>
			</li>
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="selectdriver.html" class="mui-navigate-right p-r-40 bg-ffffff">
					<span class="v-a-m m-l-5 f-s-16">满意度</span>
				</a>
			</li>
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="selectdrivers.html" class="mui-navigate-right p-r-40 bg-ffffff">
					<span class="v-a-m m-l-5 f-s-16">投诉建议</span>
				</a>
			</li>
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="share.html" class="mui-navigate-right p-r-40 bg-ffffff">
					<span class="v-a-m m-l-5 f-s-16">我要分享</span>
				</a>
			</li>
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="help.html" class="mui-navigate-right p-r-40 bg-ffffff">
					<span class="v-a-m m-l-5 f-s-16">帮助中心</span>
				</a>
			</li>
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="change.html" class="mui-navigate-right p-r-40 bg-ffffff">
					<span class="v-a-m m-l-5 f-s-16">修改密码</span>
				</a>
			</li>
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="../zh/logout.html" class="p-r-40 bg-ffffff t-a-c">
					<span class="v-a-m m-l-5 f-s-16 ft-e32929">退出登录</span>
				</a>
			</li>
		</ul>
		<div class="p-rl-45 m-t-80">
		   <a href="../zh/phone.html">
			<button class="mui-btn mui-btn-block ft-ffffff f-s-16 bg-2cad6e">一键呼叫司机</button>
		   </a>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			mui.init();
			// 表单提交点击事件
			$('.mui-input-group').on('tap', '.mui-btn', function() {
				mui(this).button('loading');
	            setTimeout(function() {
	                mui(this).button('reset');
	            }.bind(this), 2000);
			});
		});
		
	</script>
</body>
</html>