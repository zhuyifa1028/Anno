<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String jsp = "/";
	String front = path;
	String files = front + "/resources/main/front";
%>
<c:set var="jsp" value="<%=jsp %>" scope="request" />
<c:set var="path" value="<%=path %>" scope="request" />
<c:set var="front" value="<%=front %>" scope="request" />
<c:set var="files" value="<%=files %>" scope="request" />
<c:set var="title" value="安诺租车" scope="request" />