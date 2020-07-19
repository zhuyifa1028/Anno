<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>
    <title>安诺租车</title>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">费用详情</h1>
		<a href="../en/work_detail.html?work=${param.work }" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<div class="mui-content">
		<div class="p-rl-50">
			<div class="mui-history-item bg-ffffff m-t-20 p-t-15 p-b-40 p-rl-80 ft-999999 f-s-14">
			 <c:forEach items="${models2}" var="m">
				<div class="m-t-25">
				   <b>
				   <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
				   </b>
				   <c:if test="${empty m.driverDate }">-</c:if>
				</div>
				<div class="m-t-25">费用目的：<c:if test="${m.driverRmbgoal eq 0 }">商务</c:if><c:if test="${m.driverRmbgoal eq 1 }">个人</c:if></div>
				<div class="m-t-25">费用类型：
				      <c:if test="${m.driverRmbtype eq 0 }">早餐</c:if>
				      <c:if test="${m.driverRmbtype eq 1 }">中餐</c:if>
				      <c:if test="${m.driverRmbtype eq 2 }">晚餐</c:if>
				      <c:if test="${m.driverRmbtype eq 3 }">通行费</c:if>
				      <c:if test="${m.driverRmbtype eq 4 }">停车费</c:if>
				      <c:if test="${m.driverRmbtype eq 5 }">住宿费</c:if>
				      <c:if test="${m.driverRmbtype eq 6 }">差旅</c:if>
				      <c:if test="${m.driverRmbtype eq 7 }">餐饮</c:if>
				      <c:if test="${m.driverRmbtype eq 8 }">通讯费</c:if>
				      <c:if test="${m.driverRmbtype eq 9 }">交通费</c:if>
				      <c:if test="${m.driverRmbtype eq 10 }">加油费</c:if>
				      <c:if test="${m.driverRmbtype eq 11 }">其他</c:if>
				</div>
				<div class="m-t-25">费用：
				    <fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
				    <c:out value="¥${result}"  default="--"/>
				</div>
				<div class="m-t-25">代支费用：
				    <fmt:formatNumber value="${m.driverRmb}" var="rmb" pattern="0.00" groupingUsed="false"/>
				    <c:out value="¥${rmb}"  default="--"/>
				</div>
			 </c:forEach>
			</div>
		</div>
	</div>
</body>
</html>