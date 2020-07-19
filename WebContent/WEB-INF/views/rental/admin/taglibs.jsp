<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../main/admin/taglibs.jsp"%>

<%
	String jsp_science = jsp + "/rental";
	String admin_science = path + jsp_science;
	String files_science = admin + "/resources/rental/admin";
%>

<c:set var="jsp_science" value="<%=jsp_science %>" scope="request" />
<c:set var="admin_science" value="<%=admin_science %>" scope="request" />
<c:set var="files_science" value="<%=files_science %>" scope="request" />

<script type="text/javascript">
<!--
	var _files_science = '${files_science }';
	var _admin_science = '${admin_science }';
//-->
</script>