<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>
    <title>安诺租车</title>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">Expense Detail</h1>
		<a href="../zh/work_detail.html?work=${param.work }" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">中</a>
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
				<div class="m-t-25">Purpose：<c:if test="${m.driverRmbgoal eq 0 }">Business</c:if><c:if test="${m.driverRmbgoal eq 1 }">Private</c:if></div>
				<div class="m-t-25">Expense type：
				      <c:if test="${m.driverRmbtype eq 0 }">Breakfast</c:if>
				      <c:if test="${m.driverRmbtype eq 1 }">Lunch</c:if>
				      <c:if test="${m.driverRmbtype eq 2 }">Dinner</c:if>
				      <c:if test="${m.driverRmbtype eq 3 }">Toll</c:if>
				      <c:if test="${m.driverRmbtype eq 4 }">Parking</c:if>
				      <c:if test="${m.driverRmbtype eq 5 }">Accommodation</c:if>
				      <c:if test="${m.driverRmbtype eq 6 }">Business trip</c:if>
				      <c:if test="${m.driverRmbtype eq 7 }">Drinks</c:if>
				      <c:if test="${m.driverRmbtype eq 8 }">Communication</c:if>
				      <c:if test="${m.driverRmbtype eq 9 }">Transport</c:if>
				      <c:if test="${m.driverRmbtype eq 10 }">Refueling</c:if>
				      <c:if test="${m.driverRmbtype eq 11 }">Other</c:if>
				</div>
				<div class="m-t-25">Amount：
				    <fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
				    <c:out value="¥${result}"  default="--"/>
				</div>
				<div class="m-t-25">Total Expenses：
				    <fmt:formatNumber value="${m.driverRmb}" var="rmb" pattern="0.00" groupingUsed="false"/>
				    <c:out value="¥${rmb}"  default="--"/>
				</div>
			</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>