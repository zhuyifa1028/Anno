<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/admin/taglib.jsp"%>

<div class="row border-bottom">
	<nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
		<div class="navbar-header">
			<a class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="javascript:;">
				<i class="fa fa-bars"></i>
			</a>
			<form role="search" class="navbar-form-custom">
				<div class="form-group">
					<input type="text" placeholder="搜索" class="form-control" name="top-search" id="top-search" />
				</div>
			</form>
		</div>
		<ul class="nav navbar-top-links navbar-right">
			<li>
				<span class="m-r-sm text-muted welcome-message">欢迎来到 ${title } 后台</span>
			</li>
			<li>
				<a href="${admin}/logout.html">
					<i class="fa fa-sign-out"></i>
					<span>退出</span>
				</a>
			</li>
			<li>
				<a class="right-sidebar-toggle">
					<i class="fa fa-tasks"></i>
				</a>
			</li>
		</ul>
	</nav>
</div>