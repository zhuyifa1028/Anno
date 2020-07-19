<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
<%@ include file="../taglibs.jsp"%>
<title>安诺租车</title>
<script type="text/javascript">
		$(document).ready(function(){			
			    $("p").attr("class","f-s-14 ft-666666 l-h-d-50");			 
		});	
</script>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
	<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">帮助中心</h1>
	<a href="../en/help.html"
		class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a> </header>
	<div class="mui-content">
	  <div class="m-t-45 p-rl-45">
		<c:forEach items="${models }" var="m">
		 <c:if test="${m.recordIsDisplay eq 0}">
		  <c:if test="${m.recordLanguage eq 0}">
				<c:set var="title" value="${fn:split(m.recordCarshow, '。') }" />
				<h1 class="f-s-16 ft-333333 f-w-n t-a-c m-t-60">${m.recordCartitle }</h1>
				<div class="m-t-45 p-rl-45">
				    <c:forEach items="${title }" var="t">
					${t}
					</c:forEach>
				</div>
			</c:if>
		  </c:if>
		</c:forEach>
	  </div>
	</div>
</body>
</html>