<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/admin/taglib.jsp"%>

<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="sidebar-collapse">
		<form class="metismenu" method="post" novalidate="novalidate">
			<ul class="nav metismenu" id="side-menu">
				<li class="nav-header">
					<div class="dropdown profile-element">
						<span>
							<img alt="image" class="img-circle" src="${files }/images/default_avatar.jpg" />
						</span>
						<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
							<span class="clear">
								<span class="block m-t-xs">
									<strong class="font-bold">${admin_user_session.adminName }</strong>
								</span>
							</span>
						</a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
							<li><a href="${admin}/logout.html">退出</a></li>
						</ul>
					</div>
					<div class="logo-element">admin</div>
				</li>
				
				<li <c:if test="${empty mid }">class="active"</c:if>>
					<a href="${admin }/rental/index.html">
						<i class="fa"></i>
						<span class="nav-label">欢迎首页</span>
						<span class="pull-right label label-primary">WELCOME</span>
					</a>
				</li>
				
				<c:forEach items="${json }" var="j">
					<li <c:if test="${j.checked eq true }">class="active"</c:if> <c:if test="${j.open eq true }">class="active"</c:if>>
						<a <c:if test="${not empty j.children or empty j.url }">href="javascript:;"</c:if> <c:if test="${empty j.children and not empty j.url }">href="${admin }${j.url }?mid=${j.id }"</c:if>>
							<i class="fa fa-${fn:substringAfter(j.code, 'menu_') }"></i>
							<span class="nav-label">${j.name }</span>
							<span class="fa arrow"></span>
						</a>
						<c:if test="${not empty j.children }">
							<ul class="nav nav-second-level collapse">
								<c:forEach items="${j.children }" var="c">
									<li <c:if test="${c.checked eq true }">class="active"</c:if> <c:if test="${c.open eq true }">class="active"</c:if>>
										<a <c:if test="${not empty c.url }">href="${admin }${c.url }?mid=${c.id }"</c:if><c:if test="${empty c.url }">href="javascript:;"</c:if>>${c.name }</a>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</form>
	</div>
</nav>