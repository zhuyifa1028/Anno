<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="taglibs.jsp"%>
<title>${title }-区域列表</title>

<link rel="stylesheet" type="text/css" href="${files }/css/footable/footable.core.css" />
<script type="text/javascript" src="${files }/js/footable/footable.all.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('.footable').footable();
	});
</script>
</head>
<body>
	<div id="wrapper">
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
											<strong class="font-bold">admin</strong>
										</span>
										<span class="text-muted text-xs block">Art Director<b class="caret"></b></span>
									</span>
								</a>
								<ul class="dropdown-menu animated fadeInRight m-t-xs">
									<li><a href="javascript:;">Profile</a></li>
									<li><a href="javascript:;">Contacts</a></li>
									<li><a href="javascript:;">Mailbox</a></li>
									<li class="divider"></li>
									<li><a href="javascript:;">Logout</a></li>
								</ul>
							</div>
							<div class="logo-element">admin</div>
						</li>
						<li class="active">
							<a href="${admin_area }/list.html?mid=0">
								<i class="fa fa-sitemap"></i>
								<span class="nav-label">区域管理</span>
							</a>
						</li>
					</ul>
				</form>
			</div>
		</nav>
		
		<div id="page-wrapper" class="gray-bg">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="javascript:;">
							<i class="fa fa-bars"></i>
						</a>
						<form role="search" class="navbar-form-custom">
							<div class="form-group">
								<input type="text" placeholder="搜索" class="form-control" name="top-search" />
							</div>
						</form>
					</div>
					<ul class="nav navbar-top-links navbar-right">
						<li>
							<span class="m-r-sm text-muted welcome-message">欢迎来到 IN+ 后台</span>
						</li>
						<li>
							<a href="javascript:;">
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
			
			<div class="row wrapper border-bottom white-bg page-heading">
				<form class="navigation" method="post" novalidate="novalidate">
					<div class="col-lg-10">
						<h2>
							区域管理-列表
						</h2>
						<ol class="breadcrumb">
							<li class="active"><strong>列表</strong></li>
						</ol>
					</div>
					<div class="col-lg-2"></div>
				</form>
			</div>
			
			<div class="wrapper wrapper-content">
				<div class="row">
					<jsp:include page="${jsp_area }/tree.html" />
					
					<div class="col-lg-9 animated fadeInRight">
						<form class="form" action="${admin_area }/list.html?mid=0" method="post">
							<input type="hidden" name="id" />
							<input type="hidden" name="pid" />
							<c:if test="${not empty params.parent_id }">
								<input type="hidden" name="parent_id" value="${params.parent_id }" />
							</c:if>
							<div class="ibox">
								<div class="ibox-title">
									<h5>区域管理-条件</h5>
									<div class="ibox-tools">
										<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
										<a class="close-link"><i class="fa fa-times"></i></a>
									</div>
								</div>
								<div class="ibox-content m-b-sm border-bottom">
									<script type="text/javascript">
										$('div.ibox-content').on('keydown', 'input[data-search="true"]', function(event) {
											var target = event.which || event.keyCode;
											if(target == 13) {
												var value = this.value || '';
												var _value = this.defaultValue || '';
												if(!!value || value != _value) {
													var form = $(this).closest('form');
													if(form.length > 0) {
														form.submit();
													}
												}
											}
										}).on('change', 'select[data-search="true"]', function() {
											var form = $(this).closest('form');
											if(form.length > 0) {
												form.submit();
											}
										});
									</script>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label" for="name">区域名称</label>
												<input type="text" id="name" name="name" placeholder="区域名称" class="form-control" value="${params.name }" data-search="true" />
											</div>
										</div>
										
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-lg-12">
									<div class="ibox">
										<div class="ibox-title">
											<h5>区域管理-列表</h5>
											<div class="ibox-tools">
												<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
													<i class="fa fa-wrench"></i>
												</a>
												<ul class="dropdown-menu dropdown-user">
													<li><a href="javascript:;" data-pid="0" data-action="${admin_area }/edit.html?mid=0">添加</a></li>
												</ul>
												<script type="text/javascript">
													$(function() {
														$('ul.dropdown-menu').on('click', 'a[data-action]', function() {
															var _this = $(this);
															var action = _this.attr('data-action') || '';
															var pid = _this.attr('data-pid') || '';
															var form = _this.closest('form');
															if(form.length > 0 && !!action && !!pid) {
																form.find('input[name="pid"]').val(pid);
																form.attr("action", action);
																form.submit();
															}
														});
														
														$('table.footable').on('click', 'a[data-href]', function() {
															var _this = $(this);
															var _id = _this.attr("data-id") || '';
															var _pid = _this.attr("data-pid") || '';
															var action = _this.attr("data-action") || '';
															var form = _this.closest('form');
															if(!!_id && !!action && !!_pid && form.length > 0) {
																form.attr('action', action);
																form.find('input[name="id"]').val(_id);
																form.find('input[name="pid"]').val(_pid);
																form.submit();
															}
														}).on('click', 'a[data-model]', function() {
															var _this = $(this);
															var _id = _this.attr('data-id') || '';
															var action = _this.attr('data-action') || '';
															if(!!_id && !!action) {
																$.post(action, {id: _id}, function(data) {
																	var modal = $('#modal-form');
																	if(modal.length > 0) {
																		modal.remove();
																	}
																	$('body').append(data);
																	_this.siblings('a[data-toggle="modal"]').trigger('click');
																});
															}
														}).on('click', 'a[data-btn]', function() {
															var _this = $(this);
															var _id = _this.attr('data-id') || '';
															var action = _this.attr('data-action') || '';
															if(!!_id && !!action) {
																$.post(action, {id: _id}, function(data) {
																	if(data.succeed == true) {
																		_this.closest('form').submit();
																	} else {
																		error(data.message);
																	}
																});
															}
														});
													});
												</script>
												<a class="close-link"> <i class="fa fa-times"></i>
												</a>
											</div>
										</div>
										<div class="ibox-content">
											<table class="footable table table-stripped toggle-arrow-tiny">
												<thead>
													<tr>
														<th data-sort-ignore="true">区域名称</th>
														
														<th data-sort-ignore="true">区域顺序</th>
														<th data-sort-ignore="true">添加时间</th>
														
														<th data-hide="all" data-sort-ignore="true">添加地址</th>
														<th class="text-right" data-sort-ignore="true">操作</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${models }" var="m">
														<tr>
															<td>
																${m.areaName }
																<c:if test="${empty m.areaName }">-</c:if>
															</td>
															
															<td>
																${m.areaSequence }
																<c:if test="${empty m.areaSequence }">-</c:if>
															</td>
															<td>
																<fmt:formatDate value="${m.initDate }" pattern="yyyy-MM-dd HH:mm:ss" />
																<c:if test="${empty m.initDate }">-</c:if>
															</td>
															
															<td>
																${m.initAddr }
																<c:if test="${empty m.initAddr }">-</c:if>
															</td>
															<td class="text-right">
																<a href="javascript:;" data-href="true" data-pid="0" data-id="${m.id }" data-action="${admin_area }/edit.html?mid=0" class="btn btn-white btn-sm">
																	<i class="fa fa-pencil"></i>
																	<span>编辑</span>
																</a>
															</td>
														</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
														<td colspan="8">
															<jsp:include page="${jsp }/page.html">
																<jsp:param value="${modelsPaginator.page }" name="pageNum" />
																<jsp:param value="${modelsPaginator.limit }" name="pageSize" />
																<jsp:param value="${modelsPaginator.totalPages }" name="pageTotal" />
															</jsp:include>
														</td>
													</tr>
												</tfoot>
											</table>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<jsp:include page="${jsp }/foot.html"></jsp:include>
		</div>
	</div>
</body>
</html>