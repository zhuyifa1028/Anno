<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">选择评价司机</h1>
		<a href="../en/selectdriver.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<div class="mui-content">
		<ul class="mui-table-view bg-transparent p-rl-45" style="margin-top: 0.666667rem;">
		  <c:forEach items="${models }" var="m">
			<li class="mui-table-view-cell h-80 l-h-d-80 b-r-12 m-t-20">
				<a href="satisfied.html?jian=${m.id}" class="mui-navigate-right p-r-40 bg-ffffff">
					<span class="v-a-m m-l-5 f-s-16">${m.driverName}</span>
				</a>
			</li>
		  </c:forEach>
		</ul>
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