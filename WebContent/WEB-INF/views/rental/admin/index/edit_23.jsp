<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../taglibs.jsp"%>
<script type="text/javascript" src="${files }/js/jquery/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="${files }/js/jquery/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${files }/js/jquery/validate/validate.method.js"></script>
<script type="text/javascript" src="${files }/js/jquery/form/jquery.form.js"></script>

<script type="text/javascript" src="${files }/js/holder/holder.js"></script>

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
		
		// 上传改变事件
		$('input[name="filedata"]').on('change.bs.fileinput', function(data) {
			$(this).siblings('input[name="avatar"]').val('${REPLACE_PLACEHOLDER}');
		});
	});
</script>
<title>${title }-投诉建议编辑</title>
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
									投诉建议信息表单
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
									<a class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content">
								<form class="form" action="${admin }/information/edit" method="post" novalidate="novalidate">
									<c:if test="${not empty model }">
										<input type="hidden" name="_id" value="${model.id }" />
									</c:if>
									<input type="hidden" name="parent_id" value="23" />
									
									<div class="form-group" role="form">
										<label>推荐标题</label>
										<input type="input" name="title" placeholder="请输入推荐标题" class="form-control" value="${model.programaTitle }" data-validates="{required: true, messages: {required: '推荐标题不允许为空'}}" />
									</div>
									<div class="form-group" role="form">
										<label>推荐序列</label>
										<input type="text" name="sequence" placeholder="请输入推荐序列" class="form-control" value="${model.programaSequence }" data-validates="{digits: true, messages: {digits: '推荐序列只允许输入整数'}}" />
									</div>
									<div class="form-group" role="form">
										<label>语言环境</label>
										<select class="form-control" name="language">
											<c:forEach items="${PROGRAMA_LANGUAGE }" var="m">
												<option value="${m.key }" <c:if test="${m.key eq model.programaLanguage }">selected="selected"</c:if>>${m.value }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group" role="form">
										<label>是否隐藏</label>
										<select class="form-control" name="is_display">
											<c:forEach items="${PROGRAMA_DISPLAY_TYPE }" var="m">
												<option value="${m.key }" <c:if test="${m.key eq model.programaIsDisplay }">selected="selected"</c:if>>${m.value }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group" role="form">
										<label>推荐图片</label><br />
										<div class="fileinput <c:if test="${empty avatars[0] or avatars[0] eq SPACE_PLACEHOLDER }">fileinput-new</c:if> <c:if test="${not empty avatars[0] and avatars[0] ne SPACE_PLACEHOLDER }">fileinput-exists</c:if>" data-provides="fileinput">
											<div class="fileinput-new thumbnail" style="width: 300px;">
												<img data-src="holder.js/300x200?text=1~3.373x590 \n 4~5.576x327 \n 6~8.373x590 \n 9~ .576x327" alt="1~3.373x590 4~5.576x327 6~8.373x590 9~ .576x327" />
											</div>
											<div class="fileinput-preview fileinput-exists thumbnail" style="width: 300px;">
												<c:if test="${not empty avatars[0] and avatars[0] ne SPACE_PLACEHOLDER }"><img src="${path }${avatars[0] }" /></c:if>
											</div>
											<div>
												<span class="btn btn-default btn-file">
													<span class="fileinput-new">选择图片</span>
													<span class="fileinput-exists">更改图片</span>
													<input type="file" name="filedata" />
													<c:if test="${not empty avatars[0] and avatars[0] ne SPACE_PLACEHOLDER }">
														<input type="hidden" name="avatar" value="${avatars[0] }" />
													</c:if>
													<c:if test="${empty avatars[0] or avatars[0] eq SPACE_PLACEHOLDER }">
														<input type="hidden" name="avatar" value="${SPACE_PLACEHOLDER }" />
													</c:if>
												</span>
												<a href="javascript:;" class="btn btn-default fileinput-exists" data-dismiss="fileinput">删除图片</a>
											</div>
										</div>
									</div>
									<div class="form-group" role="form">
										<label>微信图片</label><br />
										<div class="fileinput <c:if test="${empty avatars[1] or avatars[1] eq SPACE_PLACEHOLDER }">fileinput-new</c:if> <c:if test="${not empty avatars[1] and avatars[1] ne SPACE_PLACEHOLDER }">fileinput-exists</c:if>" data-provides="fileinput">
											<div class="fileinput-new thumbnail" style="width: 300px;">
												<img data-src="holder.js/300x200?text=750x240" alt="750x240" />
											</div>
											<div class="fileinput-preview fileinput-exists thumbnail" style="width: 300px;">
												<c:if test="${not empty avatars[1] and avatars[1] ne SPACE_PLACEHOLDER }"><img src="${path }${avatars[1] }" /></c:if>
											</div>
											<div>
												<span class="btn btn-default btn-file">
													<span class="fileinput-new">选择图片</span>
													<span class="fileinput-exists">更改图片</span>
													<input type="file" name="filedata" />
													<c:if test="${not empty avatars[1] and avatars[1] ne SPACE_PLACEHOLDER }">
														<input type="hidden" name="avatar" value="${avatars[1] }" />
													</c:if>
													<c:if test="${empty avatars[1] or avatars[1] eq SPACE_PLACEHOLDER }">
														<input type="hidden" name="avatar" value="${SPACE_PLACEHOLDER }" />
													</c:if>
												</span>
												<a href="javascript:;" class="btn btn-default fileinput-exists" data-dismiss="fileinput">删除图片</a>
											</div>
										</div>
									</div>
									<div class="form-group" role="form">
										<label>推荐内容</label>
										<textarea name="details" class="summernote">
											${model.programaDetails }
				                        </textarea>
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