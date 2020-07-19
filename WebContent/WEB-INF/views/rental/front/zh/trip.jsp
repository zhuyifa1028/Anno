<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>
    <title>安诺租车</title>
    <style type="text/css">
    .p-a-30{
    padding: 0.8rem;
    }
    </style>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<a class="mui-btn f-s-14 mui-btn-link mui-pull-right"
				style="float: left;" href="index.html">首页</a>
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">信息生成</h1>
		<a href="../en/trip.html?servicedate=${param.servicedate}&servicetime=${param.servicetime}&id=${param.id}&servicecontent=${param.servicecontent}&fucity=${param.fucity}&futrain=${param.futrain}" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<div class="mui-content">
		<div class="p-rl-50">
		   <c:if test="${param.id eq 0}">
			<div class="bg-ffffff m-t-50 ft-333333 f-s-14 p-a-30 l-h-24" >
				<textarea class="f-s-14 ft-666666 l-h-d-50" style="-webkit-user-select:text;">您好，请于${param.servicedate},${param.servicetime}${param.servicecontent}接我，谢谢！</textarea>
			</div>
		   </c:if>
		   <c:if test="${param.id eq 1}">
			<div class="bg-ffffff m-t-50 ft-333333 f-s-14 p-a-30 l-h-24" >
				<textarea class="f-s-14 ft-666666 l-h-d-50" style="-webkit-user-select:text;">您好，请于${param.servicedate},${param.servicetime}${param.fucity}接我，${param.servicecontent}，谢谢！</textarea>
			</div>
		   </c:if>
		</div>
		<div class="m-t-40 f-s-12 ft-999999 t-a-c l-h-17">长按可复制内容<br />粘贴至个人微信</div>
	</div>
</body>
</html>