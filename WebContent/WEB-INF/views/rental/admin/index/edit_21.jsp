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

<link rel="stylesheet" type="text/css" href="${files }/css/footable/footable.core.css" />
<script type="text/javascript" src="${files }/js/footable/footable.all.min.js"></script>

<link rel="stylesheet" type="text/css" href="${files }/css/froala/froala_editor.css" />
<link rel="stylesheet" type="text/css" href="${files }/css/froala/froala_style.css" />
<link rel="stylesheet" type="text/css" href="${files }/css/froala/themes/gray.css" />
<script type="text/javascript" src="${files }/js/froala/froala_editor.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/languages/zh_cn.js"></script>

<script type="text/javascript" src="${files }/js/froala/plugins/font_size.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/font_family.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/paragraph_format.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/align.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/lists.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/link.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/image.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/image_manager.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/table.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/code_view.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/code_beautifier.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/entities.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/colors.min.js"></script>
<script type="text/javascript" src="${files }/js/froala/plugins/save.min.js"></script>

<link rel="stylesheet" href="${files }/css/froala/plugins/colors.css" />
<link rel="stylesheet" href="${files }/css/froala/plugins/emoticons.css" />
<link rel="stylesheet" href="${files }/css/froala/plugins/image_manager.css" />
<link rel="stylesheet" href="${files }/css/froala/plugins/image.css" />
<link rel="stylesheet" href="${files }/css/froala/plugins/line_breaker.css" />
<link rel="stylesheet" href="${files }/css/froala/plugins/table.css" />
<link rel="stylesheet" href="${files }/css/froala/plugins/char_counter.css" />
<link rel="stylesheet" href="${files }/css/froala/plugins/fullscreen.css" />
<link rel="stylesheet" href="${files }/css/froala/plugins/code_view.css" />

<link rel="stylesheet" href="${files }/css/codemirror/codemirror.css" />
<script type="text/javascript" src="${files }/js/codemirror/codemirror.js"></script>
<script type="text/javascript" src="${files }/js/codemirror/mode/xml/xml.js"></script>
<script type="text/javascript" src="${files }/js/codemirror/mode/javascript/javascript.js"></script>

<link rel="stylesheet" href="${files }/css/datepicker/datetimepicker.css" />
<script type="text/javascript" src="${files }/js/datepicker/bootstrap-datetimepicker.js"></script>

<script type="text/javascript">
	$(function() {
		$('.summernote').froalaEditor({
			theme: 'gray',
			language: 'zh_cn',
			toolbarButtons: [
				'bold', 'italic', 'underline', 'fontSize', 'fontFamily', 'color', '|',
				'paragraphFormat', 'align', 'formatOL', 'formatUL', 'indent', 'outdent', '|',
				'insertLink', 'insertImage', 'insertTable', 'clearFormatting', '|',
				'undo', 'redo', 'html'
			],
			toolbarButtonsMD: [
				'bold', 'italic', 'underline', 'fontSize', '|',
				'align', 'formatOL', 'formatUL', 'indent', 'outdent', '|',
				'insertLink', 'insertImage', 'insertTable', 'clearFormatting', 'html'
			],
			toolbarButtonsSM: [
				'bold', 'italic', 'underline', '|',
				'align',  'indent', 'outdent', '|',
				'insertLink', 'insertImage', '|',
				'clearFormatting', 'html'
			],
			toolbarButtonsXS: [
				'bold', 'italic', 'underline', '|',
				'align',  'indent', 'outdent', '|',
				'insertLink', 'insertImage', '|',
				'clearFormatting', 'html'
			],
			linkList: [{
				text: 'Google',
				href: 'http://google.com',
				target: '_blank',
				rel: 'nofollow'
			}, {
				text: 'Froala',
				href: 'https://froala.com',
				target: '_blank'
			}],
			zIndex: 2016,
			imageAllowedTypes: ['jpeg', 'jpg', 'png', 'gif'],
			imageUploadURL: '${admin }/froala/upload',
			imageUploadParam: 'filedata'
		});
		
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
		
		$('form.form').on('click', 'input.input-search', function() {
			$(this).siblings('span.icon-search').trigger('click');
		}).on('focus', 'input.input-search', function() {
			$(this).blur();
		}).on('focus', 'input.input-date', function() {
			$(this).blur();
		});
		
		$('.input-group.date').datetimepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : true,
			autoclose : true,
			language: 'cn',
			format: 'yyyy-mm-dd hh:ii:ss'
		});
		
		//删除购买信息
		$('div.fr-wrapper').next().remove();
	});
</script>
<title>${title }-行车日志编辑</title>
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
									行车日志信息表单
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
									<a class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content">
								<form class="form" action="${admin }/rental/edit/21" method="post" novalidate="novalidate">
									<c:if test="${not empty model }">
										<input type="hidden" name="_id" value="${model.id }" />
									</c:if>							
									
									<div class="form-group" role="form">
										<label>开始时间</label>
										<div class="input-group date">
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											<input type="text" name="leavedate" id="edate" placeholder="请输入开始时间" class="form-control input-date" value="<fmt:formatDate value="${model.driverLeavedate  }" pattern="yyyy-MM-dd HH:mm:ss" />" />
										</div>										
									</div>
									
									 <div class="form-group" role="form">
										<label>结束时间</label>
										<div class="input-group date">
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											<input type="text" name="backdate" id="edate" placeholder="请输入结束时间" class="form-control input-date" value="<fmt:formatDate value="${model.driverBackdate  }" pattern="yyyy-MM-dd HH:mm:ss" />" />
										</div>										
									</div>
									
									<div class="form-group" role="form">
										<label>公里数</label>
										<input type="input" name="milenow" class="form-control" value="${model.driverMilenow }"/>
									</div>
									
									<div class="form-group" role="form">
										<label>是否确认</label>
										<select class="form-control" name="uaffirm">	
											 <option value="">--请选择--</option>					 
											 <c:forEach items="${DRIVER_UAFFIRM }" var="m"> 											   
												<option value="${m.key }" <c:if test="${m.key eq model.driverUaffirm }">selected="selected"</c:if> >${m.value }</option>
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