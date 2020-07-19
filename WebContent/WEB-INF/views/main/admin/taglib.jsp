<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String jsp = "/admin";
	String admin = path + jsp;
	String files = admin + "/resources/main/admin";
%>
<c:set var="jsp" value="<%=jsp %>" scope="request" />
<c:set var="path" value="<%=path %>" scope="request" />
<c:set var="admin" value="<%=admin %>" scope="request" />
<c:set var="files" value="<%=files %>" scope="request" />
<c:set var="title" value="${website.websiteName }" scope="request" />