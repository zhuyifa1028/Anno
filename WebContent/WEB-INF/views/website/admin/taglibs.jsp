<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../main/admin/taglibs.jsp"%>

<%
	String jsp_website = jsp + "/website";
	String admin_website = path + jsp_website;
	String files_website = admin + "/resources/website/admin";
%>

<c:set var="jsp_website" value="<%=jsp_website %>" scope="request" />
<c:set var="admin_website" value="<%=admin_website %>" scope="request" />
<c:set var="files_website" value="<%=files_website %>" scope="request" />

<script type="text/javascript">
<!--
	var _files_website = '${files_website }';
	var _admin_website = '${admin_website }';
//-->
</script>