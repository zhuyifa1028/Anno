<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String jsp = "/web";
	String web = path + jsp;
	String files = web + "/resources/main/web";
%>
<c:set var="jsp" value="<%=jsp %>" scope="request" />
<c:set var="path" value="<%=path %>" scope="request" />
<c:set var="web" value="<%=web %>" scope="request" />
<c:set var="files" value="<%=files %>" scope="request" />
<c:set var="title" value="${website.websiteName }" scope="request" />