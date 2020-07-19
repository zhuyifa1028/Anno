<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../taglibs.jsp"%>
<title>${title }-用车人确认管理</title>

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
					    <form class="form" action="${admin_science }/list/20_1.html?mid=20" method="post">
					        <input type="hidden" name="id" />
							<input type="hidden" name="pid" />
							
							<input type="hidden" name="tels" value="${tels }">
							<c:if test="${not empty params.parent_id }">
								<input type="hidden" name="parent_id" value="${params.parent_id }" />
							</c:if>
							<div class="row">
								<div class="col-lg-12">
									<div class="ibox">
										<div class="ibox-title">
											<h5>用车人管理-列表</h5>
											<div class="ibox-tools">
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
											</div>
										</div>
										<div class="ibox-content">
											<table class="footable table table-stripped toggle-arrow-tiny">
												<thead>
													<tr>
														<th data-sort-ignore="true">手机号码</th>
														<th data-sort-ignore="true">司机名称</th>
														<th data-sort-ignore="true">车牌号码</th>
														<th data-sort-ignore="true">上车时间</th>
														<th data-sort-ignore="true">下车时间</th>	
														<th data-sort-ignore="true">行驶里程</th>
														<th data-sort-ignore="true">是否确认日常记录</th>												
														<th data-sort-ignore="true">加班时长</th>	
														<th data-sort-ignore="true">加班类型</th>
														<th data-sort-ignore="true">加班费用目的</th>
														<th data-sort-ignore="true">加班费用</th>
														<th data-sort-ignore="true">报销费用类别</th>														
														<th data-sort-ignore="true">报销费用</th>
														<th data-sort-ignore="true">报销费用类型</th>
														<th data-sort-ignore="true">是否确认加班及费用</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${models }" var="m">
														<tr>
														    <td>
																${m.driverTel }
																<c:if test="${empty m.driverTel }">-</c:if>
															</td>
															<td>
																${m.driverName }
																<c:if test="${empty m.driverName }">-</c:if>
															</td>
															<td>
																${m.driverCarnum }
																<c:if test="${empty m.driverCarnum }">-</c:if>
															</td>
															
															<td>
																<fmt:formatDate value="${m.driverLeavedateuser }" pattern="yyyy-MM-dd HH:mm:ss" />
																<c:if test="${empty m.driverLeavedateuser }">-</c:if>
															</td>
															
															<td>
																<fmt:formatDate value="${m.driverBackdate }" pattern="yyyy-MM-dd HH:mm:ss" />
																<c:if test="${empty m.driverBackdate }">-</c:if>
															</td>
															<td>
																${m.driverMilenow }
																<c:if test="${empty m.driverMilenow }">-</c:if>
															</td>
															<td>
																<c:if test="${m.driverUaffirm eq 0 }">否</c:if>
															    <c:if test="${m.driverUaffirm eq 1 }">是</c:if>
																<c:if test="${empty m.driverUaffirm }">-</c:if>
															</td>
															
															<td>
																${m.driverOvertimehours }
																<c:if test="${empty m.driverOvertimehours }">-</c:if>
															</td>
															
															<td>
																<c:if test="${m.driverOvertimetype eq 0 }">工作日</c:if>
															    <c:if test="${m.driverOvertimetype eq 1 }">节假日</c:if>
															    <c:if test="${m.driverOvertimetype eq 2 }">周末</c:if>
																<c:if test="${empty m.driverOvertimetype }">-</c:if>
															</td>
															<td>
																<c:if test="${m.driverOvertimeusecar eq 0 }">个人原因</c:if>
															    <c:if test="${m.driverOvertimeusecar eq 1 }">商务原因</c:if>
																<c:if test="${empty m.driverOvertimeusecar }">-</c:if>
															</td>
															<td>
																${m.driverOvertimermbtotal }
																<c:if test="${empty m.driverOvertimermbtotal }">-</c:if>
															</td>
															<td>
															    <c:if test="${m.driverRmbtype eq 0 }">早餐</c:if>
				                                                <c:if test="${m.driverRmbtype eq 1 }">中餐</c:if>
				                                                <c:if test="${m.driverRmbtype eq 2 }">晚餐</c:if>
				                                                <c:if test="${m.driverRmbtype eq 3 }">通行费</c:if>
				                                                <c:if test="${m.driverRmbtype eq 4 }">停车费</c:if>
				                                                <c:if test="${m.driverRmbtype eq 5 }">住宿费</c:if>
				                                                <c:if test="${m.driverRmbtype eq 6 }">差旅</c:if>
				                                                <c:if test="${m.driverRmbtype eq 7 }">餐饮</c:if>
				                                                <c:if test="${m.driverRmbtype eq 8 }">通讯费</c:if>
				                                                <c:if test="${m.driverRmbtype eq 9 }">交通费</c:if>
				                                                <c:if test="${m.driverRmbtype eq 10 }">加油费</c:if>
				                                                <c:if test="${m.driverRmbtype eq 11 }">其他</c:if>
																<c:if test="${empty m.driverRmbtype }">-</c:if>	
															</td>
															<td>
																${m.driverRmb }
																<c:if test="${empty m.driverRmb }">-</c:if>
															</td>
															
															<td>
																<c:if test="${m.driverRmbtype eq 0 }">个人原因</c:if>
															    <c:if test="${m.driverRmbtype eq 1 }">商务原因</c:if>
																<c:if test="${empty m.driverRmbtype }">-</c:if>
															</td>
															
															<td>
															    <c:if test="${m.driverUaffirm eq 0 }">否</c:if>
															    <c:if test="${m.driverUaffirm eq 1 }">是</c:if>
																<c:if test="${empty m.driverUaffirm }">-</c:if>
															</td>
														</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
														<td colspan="15">
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