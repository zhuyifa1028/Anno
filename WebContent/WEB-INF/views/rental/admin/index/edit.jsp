<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../taglibs.jsp"%>
<script type="text/javascript" src="${files }/js/jquery/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="${files }/js/jquery/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${files }/js/jquery/validate/validate.method.js"></script>
<script type="text/javascript" src="${files }/js/jquery/form/jquery.form.js"></script>

<link rel="stylesheet" type="text/css" href="${files }/css/jasny/jasny-bootstrap.min.css" />
<script type="text/javascript" src="${files }/js/jasny/jasny-bootstrap.min.js"></script>

<script type="text/javascript">
	$(function() {
		$('form.form').validate({
			submitHandler: function(form) {
				$(form).ajaxSubmit({
					type: "post",
					dataType: "json",
					success: function(data) {
						if(data.succeed === true) {
							var form = $('form.navigation');
							if(form.length > 0) {
								var action = form.find('a[data-action]').attr('data-action') || '';
								if(!!action) {
									form.attr("action", action);
									form.find('input[name="pageNum"]').val(1);
									form.submit();
								}
							}
						} else {
							error(data.message);
						}
					}
				});
				return false;
			}
		});
		
		$('form.form').on('click', 'span.icon-search', function() {
			var _this = $(this);
			var mid = '${default_id}' || '${model.areaParentId }';
			$.get(_admin_science + "/search/" + mid + ".html", function(data) {
				var modal = $('#modal-form');
				if(modal.length > 0) {
					modal.remove();
				}
				$('body').append(data);
				_this.siblings('a').trigger('click');
			});
		});
		
		$('form.form').on('click', 'input.input-search', function() {
			$(this).siblings('span.icon-search').trigger('click');
		}).on('focus', 'input.input-search', function() {
			$(this).blur();
		});
		
		//删除购买信息
		$('div.fr-wrapper').next().remove();
	});
</script>
<title>${title }-区域编辑</title>
</head>
<body>
	<div id="wrapper">
	 <jsp:include page="${jsp_science }/left.html" />
		<div id="page-wrapper" class="gray-bg">
		    <jsp:include page="${jsp_science }/top.html">
				<jsp:param value="${top_params }" name="top_params" />
			</jsp:include>
			<jsp:include page="${jsp_science }/navigation.html">
				<jsp:param value="编辑" name="name"/>
			</jsp:include>
			<div class="wrapper wrapper-content">
				<div class="row">
					<div class="col-lg-12 animated fadeInRight">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									区域信息表单
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
									<a class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content">
								<form class="form" action="${admin_science}/edit" method="post" novalidate="novalidate">
									<c:if test="${not empty model }">
										<input type="hidden" name="_id" value="${model.id }" />
									</c:if>
									
									<div class="form-group" role="form">
										<label>上级区域</label>
										<div class="input-group">
											<span class="input-group-addon icon-search"><i class="fa fa-search"></i></span>
											<a data-toggle="modal" href="#modal-form"></a>
											<input type="text" id="area_name" placeholder="请选择上级区域" class="form-control input-search" value="${models.areaName }" data-validates="{required: true, messages: {required: '上级区域不允许为空'}}" />
											<input type="hidden" name="parent_id" value="${model.areaParentId }"  />
										</div>
										<label class="error" for="parent_name"></label>
									</div>
									
									<div class="form-group" role="form">
										<label>区域名称</label>
										<input type="input" name="name" placeholder="请输入区域名称" class="form-control" value="${model.areaName }" data-validates="{required: true, messages: {required: '区域名称不允许为空'}}" />
									</div>
									
									<div class="form-group" role="form">
										<label>英文区域名称</label>
										<input type="input" name="way" placeholder="请输入英文区域名称" class="form-control" value="${model.areaWay }" data-validates="{required: true, messages: {required: '英语区域名称不允许为空'}}" />
									</div>
									
									<div class="form-group" role="form">
										<label>区域序列</label>
										<input type="text" name="sequence" placeholder="请输入区域序列" class="form-control" value="${model.areaSequence }" data-validates="{digits: true, messages: {digits: '区域序列只允许输入整数'}}" />
									</div>
									<div class="form-group" role="form">
										<label>语言环境</label>
										<select class="form-control" name="language">	
											<option value="">--请选择--</option>							 
											 <c:forEach items="${AREA_LANGUAGE }" var="m"> 
												<option value="${m.key }" <c:if test="${m.key eq model.areaLanguage }">selected="selected"</c:if>>${m.value }</option>
											
											</c:forEach> 
										</select>
									</div> 
									<div class="form-group" role="form">
										<label>是否隐藏</label>
										<select class="form-control" name="is_display">
										    <option value="">--请选择--</option>
											<c:forEach items="${AREA_IS_DISPLAY }" var="m">
												<option value="${m.key }" <c:if test="${m.key eq model.areaIsDisplay }">selected="selected"</c:if>>${m.value }</option>
											</c:forEach>
										</select>
									</div>
									<div style="text-align: right;">
										<button class="btn btn-sm btn-primary m-t-n-xs" type="submit"><strong>保存</strong></button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>