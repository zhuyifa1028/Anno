<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>
    <title>安诺租车</title>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">日志确认</h1>
		<a href="../en/daily.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<div class="mui-content">
		<div class="m-t-120 w-288 h-288 bg-2cad6e m-lr-auto b-r-p-50 p-a-15 table">
			<a href="daily_history.html" class="t-a-c w-258 h-258 table-cell v-a-m mui-daily-border b-r-p-50">
				<c:if test="${hasNew_rcjl }">
					<span class="mui-badge mui-badge-danger f-s-9">new</span>
			    </c:if>
				<span class="f-s-22 ft-ffffff">日常记录</span>
			</a>
		</div>
		<div class="m-t-180 w-288 h-288 bg-2cad6e m-lr-auto b-r-p-50 p-a-15 table">
			<a href="work_history.html" class="t-a-c w-258 h-258 table-cell v-a-m mui-daily-border b-r-p-50">
			    <c:if test="${hasNew_fyjlAndJbjl }">
					<span class="mui-badge mui-badge-danger f-s-12">new</span>
			    </c:if>
				<span class="f-s-22 ft-ffffff">加班和费用</span>
			</a>
		</div>
	</div>
</body>
</html>