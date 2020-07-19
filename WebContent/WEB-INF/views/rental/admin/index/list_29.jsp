<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../taglibs.jsp"%>
<title>${title }-权限管理</title>

<link rel="stylesheet" type="text/css" href="${files }/css/footable/footable.core.css" />
<script type="text/javascript" src="${files }/js/footable/footable.all.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('.footable').footable();
		
		var msg = '${message }';
		if(msg != '') {
			error(msg);
		}
	});
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="${jsp_science }/left.html" />
		
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="${jsp_science }/top.html">
				<jsp:param value="${top_params }" name="top_params" />
			</jsp:include>
			
			<jsp:include page="${jsp_science }/navigation.html" />
			
			<div class="wrapper wrapper-content">
				<div class="row">
					<div class="col-lg-12 animated fadeInRight">
						<form class="form" action="${admin_science }/list/${params.mid }.html?mid=${params.mid }" method="post">
							<input type="hidden" name="id" />
							<input type="hidden" name="pid" />
							<c:if test="${not empty params.parent_id }">
								<input type="hidden" name="parent_id" value="${params.parent_id }" />
							</c:if>
							<div class="ibox">
								<div class="ibox-title">
									<h5>权限管理-条件</h5>
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
												<label class="control-label" for="title">登录手机号码</label>
												<input type="text" id="adminTel" name="adminTel" placeholder="登录手机号码" class="form-control" value="${params.adminTel }" data-search="true" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label" for="title">用户名称</label>
												<input type="text" id="adminName" name="adminName" placeholder="用户名称" class="form-control" value="${params.adminName }" data-search="true" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label" for="adminState">账号状态</label>
												<select name="adminState" id="adminState" class="form-control" data-search="true">
													<option value="">--请选择--</option>
													<c:forEach items="${ADMIN_STATE}" var="m">
														<option value="${m.key }" <c:if test="${params.adminState eq m.key }">selected="selected"</c:if>>${m.value }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-lg-12">
									<div class="ibox">
										<div class="ibox-title">
											<h5>权限管理-列表</h5>
											<div class="ibox-tools">
												<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
													<i class="fa fa-wrench"></i>
												</a>
												<ul class="dropdown-menu dropdown-user">
													<li><a href="javascript:;" data-pid="0" data-action="${admin_science }/edit/${params.mid }.html?mid=${params.mid }">添加</a></li>
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
														<th data-sort-ignore="true">登录手机号码</th>
														<th data-sort-ignore="true">用户姓名</th>
														<th data-sort-ignore="true">账号状态</th>
														<th data-sort-ignore="true">账号属性</th>
														<th data-sort-ignore="true">添加时间</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${models }" var="m">
														<tr>
															<td>
																${m.adminTel }
																<c:if test="${empty m.adminTel }">-</c:if>
															</td>
															<td>
																${m.adminName }
																<c:if test="${empty m.adminName }">-</c:if>
															</td>
															<td>
																${ADMIN_STATE[m.adminState] }
																<c:if test="${empty m.adminState }">-</c:if>
															</td>
															<td>
																<c:if test="${ m.adminAdministrate eq 0}">权限管理-</c:if>
																<c:if test="${empty m.adminAdministrate }"></c:if>
																<c:if test="${ m.adminAdminuser eq 0}">用户管理-</c:if>
																<c:if test="${empty m.adminAdminuser }"></c:if>
																<c:if test="${ m.adminAdmindriver eq 0}">司机管理-</c:if>
																<c:if test="${empty m.adminAdmindriver }"></c:if>
																<c:if test="${ m.adminAdmintj eq 0}">统计管理-</c:if>
																<c:if test="${empty m.adminAdmintj }"></c:if>
																<c:if test="${ m.adminAdminpx eq 0}">培训管理-</c:if>
																<c:if test="${empty m.adminAdminpx }"></c:if>
																<c:if test="${ m.adminAdminuc eq 0}">用户确认管理-</c:if>
																<c:if test="${empty m.adminAdminuc }"></c:if>
																<c:if test="${ m.adminAdmints eq 0}">投诉反馈-</c:if>
																<c:if test="${empty m.adminAdmints }"></c:if>
																<c:if test="${ m.adminAdminbz eq 0}">帮助中心-</c:if>
																<c:if test="${empty m.adminAdminbz }"></c:if>
																<c:if test="${ m.adminAdminarea eq 0}">地域管理</c:if>
																<c:if test="${empty m.adminAdminarea }"></c:if>
															</td>
															<td>
																<fmt:formatDate value="${m.initDate }" pattern="yyyy-MM-dd HH:mm:ss" />
																<c:if test="${empty m.initDate }">-</c:if>
															</td>
															<td class="text-right">
																<a href="javascript:;" data-href="true" data-pid="0" data-id="${m.id }" data-action="${admin_science }/edit/${params.mid }.html?mid=${params.mid }" class="btn btn-white btn-sm">
																	<i class="fa fa-pencil"></i>
																	<span>编辑</span>
																</a>
															</td>
														</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
														<td colspan="7">
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