<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../taglibs.jsp"%>
<title>${title }-每日例行保养</title>

<link rel="stylesheet" type="text/css"
	href="${files }/css/footable/footable.core.css" />
<script type="text/javascript"
	src="${files }/js/footable/footable.all.min.js"></script>
	
<link rel="stylesheet" href="${files }/css/datepicker/datetimepicker.css" />
<script type="text/javascript" src="${files }/js/datepicker/bootstrap-datetimepicker.js"></script>

<style type="text/css">
.fa-penci:before {
    content: "\f127";
}
</style>
<script type="text/javascript">
	$(function() {
		$('.footable').footable();
		var msg = '${message }';
		if (msg != '') {
			error(msg);
		}
		
		$('.input-group.date').datetimepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : true,
			autoclose : true,
			startView:2,
			minView:2,
			language: 'cn',
			format: 'yyyy-mm-dd '
		});
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
					
						<form class="form"
							action="${admin_science }/list/${params.mid }.html?mid=${params.mid }"
							method="post">
							<input type="hidden" name="id" /> <input type="hidden"
								name="pid" />
							<c:if test="${not empty params.parent_id }">
								<input type="hidden" name="parent_id"
									value="${params.parent_id }" />
							</c:if>
							<div class="ibox">
								<div class="ibox-title">
									<h5>每日例行保养-条件</h5>
									<div class="ibox-tools">
										<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
										<a class="close-link"><i class="fa fa-times"></i></a>
									</div>
								</div>
								<div class="ibox-content m-b-sm border-bottom">
									<script type="text/javascript">
										$('div.ibox-content')
												.on(
														'keydown',
														'input[data-search="true"]',
														function(event) {
															var target = event.which
																	|| event.keyCode;
															if (target == 13) {
																var value = this.value
																		|| '';
																var _value = this.defaultValue
																		|| '';
																if (!!value
																		|| value != _value) {
																	var form = $(
																			this)
																			.closest(
																					'form');
																	if (form.length > 0) {
																		form
																				.submit();
																	}
																}
															}
														})
												.on(
														'change',
														'select[data-search="true"]',
														function() {
															var form = $(this)
																	.closest(
																			'form');
															if (form.length > 0) {
																form.submit();
															}
														});
									</script>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label" for="title">司机名称</label> <input
													type="text" id="driverName" name="driverName" placeholder="司机名称"
													class="form-control" value="${params.driverName }"
													data-search="true" />
											</div>
										</div>
										
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label" for="title">司机工号</label> <input
													type="text" id="driverId" name="driverId" placeholder="司机工号"
													class="form-control" value="${params.driverId }"
													data-search="true" />
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label" for="title">车牌号码</label> <input
													type="text" id="driverCarnum" name="driverCarnum"
													placeholder="车牌号码" class="form-control"
													value="${params.driverCarnum }" data-search="true" />
											</div>
										</div>
										
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label" for="title">例保日期自</label>
												<div class="input-group date">
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span> <input type="text"
														name="ge_date" id="ge_date" placeholder="例保日期自"
														class="form-control"
														value="${params.ge_date }" data-search="true"/>
												</div>
												<label class="error" for="edate"></label>
											</div>
										</div>
										
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label" for="title">至</label>
												<div class="input-group date">
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span> <input type="text"
														name="le_date" id="le_date" placeholder="至"
														class="form-control"
														value="${params.le_date }" data-search="true"/>
												</div>
												<label class="error" for="edate"></label>
											</div>
										</div>

									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-lg-12">
									<div class="ibox">
										<div class="ibox-title">
											<h5>每日例行保养-列表</h5>

											<div class="ibox-tools">
												<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
													<i class="fa fa-wrench"></i>
												</a>

												<ul class="dropdown-menu dropdown-user">
													<li><a href="../list/123.html?mid=12&driverName=${params.driverName }&driverCarnum=${params.driverCarnum }&driverId=${params.driverId }&ge_date=${params.ge_date }&le_date=${params.le_date }">导出</a></li>
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
											<table
												class="footable table table-stripped toggle-arrow-tiny">
												<thead>
													<tr>
													    <th data-sort-ignore="true"></th>
														<th data-sort-ignore="true">手机号码</th>
														<th data-sort-ignore="true">司机名称</th>
														<th data-sort-ignore="true">司机工号</th>
														<th data-sort-ignore="true">出行前维护</th>
														<th data-sort-ignore="true">出行公里数</th>
														<th data-sort-ignore="true">车牌号码</th>
														<th data-sort-ignore="true">例保日期</th>
														<th data-sort-ignore="true">添加时间</th>
														<th data-sort-ignore="true" data-hide="all">上传的图片</th>
														<th class="text-right" data-sort-ignore="true">操作</th>
													</tr>
												</thead>

												<tbody>
													<c:forEach items="${models }" var="m" >
														<tr>
														    <td>
														         <c:if test="${not empty m.driverMilepic }">
																 <i class="fa fa-penci"></i> 
																 </c:if>
																 <c:if test="${empty m.driverMilepic }">-</c:if>
															</td>
															<td>
															    ${m.driverTel }
															    <c:if test="${empty m.driverTel }">-</c:if>
															</td>
															<td>
															     ${m.driverName }
															     <c:if test="${empty m.driverName }">-</c:if>
															</td>
															<td>
															     ${m.driverId }
															     <c:if test="${empty m.driverId }">-</c:if>
															</td>
															<td>
															     ${DRIVER_CLOCK[m.driverClock] }
															     <c:if test="${empty m.driverClock }">-</c:if>
															</td>
															<td>
															    <fmt:formatNumber value="${m.driverStartmile}" var="result" pattern="0" groupingUsed="false"/>
															    <c:out value="${result}"  default="--"/>
															</td>
															<td>
															    ${m.driverCarnum }
															    <c:if test="${empty m.driverCarnum }">-</c:if>
															</td>
															<td>
															    <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" /> 
															    <c:if test="${empty m.driverDate }">-</c:if>
                                                             </td>
                                                             <td>
															    <fmt:formatDate value="${m.initDate }" pattern="yyyy-MM-dd HH:mm:ss" /> 
															    <c:if test="${empty m.initDate }">-</c:if>
                                                             </td>
                                                             <td>
																<c:if test="${not empty m.driverMilepic }">
																	<img alt="动态图片" src="${path }${m.driverMilepic }" style="height: 400px;" /> 
																</c:if>
																<c:if test="${empty m.driverMilepic }">-</c:if>
															</td> 
															<td class="text-right"><a href="javascript:;"
																data-href="true" data-pid="0" data-id="${m.id }"
																data-action="${admin_science }/edit/${params.mid }.html?mid=${params.mid }"
																class="btn btn-white btn-sm"> <i
																	class="fa fa-pencil"></i> <span>编辑</span>
															</a></td>
														</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
														<td colspan="10"><jsp:include
																page="${jsp }/page.html">
																<jsp:param value="${modelsPaginator.page }"
																	name="pageNum" />
																<jsp:param value="${modelsPaginator.limit }"
																	name="pageSize" />
																<jsp:param value="${modelsPaginator.totalPages }"
																	name="pageTotal" />
															</jsp:include></td>
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