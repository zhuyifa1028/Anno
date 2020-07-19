<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../main/admin/taglibs.jsp"%>

<%
	String jsp_area = jsp + "/area";
	String admin_area = path + jsp_area;
	String files_area = admin + "/resources/area/admin";
%>

<c:set var="jsp_area" value="<%=jsp_area %>" scope="request" />
<c:set var="admin_area" value="<%=admin_area %>" scope="request" />
<c:set var="files_area" value="<%=files_area %>" scope="request" />

<script type="text/javascript">
<!--
	var _files_area = '${files_area }';
	var _admin_area = '${admin_area }';
//-->
</script>