<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="taglibs.jsp"%>
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

<script type="text/javascript">
	$(function() {
		$('.summernote').froalaEditor({
			theme: 'gray',
			language: 'zh_cn',
			toolbarButtons: [
				'bold', 'italic', 'underline', 'fontSize', 'fontFamily', '|',
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
							success("网站编辑成功");
						} else {
							error("网站编辑失败");
						}
					}
				});
				return false;
			}
		});
		
		//删除购买信息
		$('div.fr-wrapper').next().remove();
	});
</script>
<title>${title }-网站编辑</title>
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
							<a href="${admin_website }/edit.html?mid=0">
								<i class="fa fa-sitemap"></i>
								<span class="nav-label">网站信息管理</span>
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
							网站信息管理-编辑
						</h2>
						<ol class="breadcrumb">
							<li class="active"><strong>编辑</strong></li>
						</ol>
					</div>
					<div class="col-lg-2"></div>
					<c:forEach items="${params }" var="p">
						<input type="hidden" name="${p.key }" value="${p.value }" />
					</c:forEach>
					<script type="text/javascript">
						$(function() {
							$('div.page-heading').on('click', 'a[data-action]', function() {
								var form = $(this).closest('form.navigation');
								var action = $(this).attr('data-action') || '';
								if(form.length > 0 && !!action) {
									form.attr("action", action);
									form.submit();
								}
							});
						});
					</script>
				</form>
			</div>
			
			<div class="wrapper wrapper-content">
				<div class="row">
					<div class="col-lg-12 animated fadeInRight">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									网站信息表单
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
									<a class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content">
								<form class="form" action="${admin }/website/edit" method="post" novalidate="novalidate">
									<c:if test="${not empty model }">
										<input type="hidden" name="_id" value="${model.id }" />
									</c:if>
									<div class="form-group" role="form">
										<label>网站名称</label>
										<input type="input" name="name" placeholder="请输入网站名称" class="form-control" value="${model.websiteName }" data-validates="{required: true, messages: {required: '网站名称不允许为空'}}" />
									</div>
									<div class="form-group" role="form">
										<label>网站网址</label>
										<input type="text" name="domain" placeholder="请输入网站网址" class="form-control" value="${model.websiteDomain }" data-validates="{required: true, messages: {required: '网站网址不允许为空'}}" />
									</div>
									<div class="form-group" role="form">
										<label>关键字词</label>
										<input type="text" name="keywords" placeholder="请输入关键字词" class="form-control" value="${model.websiteKeywords }" data-validates="{required: true, messages: {required: '关键字词不允许为空'}}" />
									</div>
									<div class="form-group" role="form">
										<label>网站描述</label>
										<input type="text" name="description" placeholder="请输入网站描述" class="form-control" value="${model.websiteDescription }" data-validates="{required: true, messages: {required: '网站描述不允许为空'}}" />
									</div>
									<div class="form-group" role="form">
										<label>网站备案</label>
										<input type="text" name="icp" placeholder="请输入网站备案" class="form-control" value="${model.websiteIcp }" data-validates="{required: true, messages: {required: '网站备案不允许为空'}}" />
									</div>
									<div class="form-group" role="form">
										<label>网站传真</label>
										<input type="text" name="faxes" placeholder="请输入网站传真" class="form-control" value="${model.websiteFaxes }" />
									</div>
									<div class="form-group" role="form">
										<label>网站电话</label>
										<input type="text" name="tel" placeholder="请输入网站电话" class="form-control" value="${model.websiteTel }" />
									</div>
									<div class="form-group" role="form">
										<label>咨询热线</label>
										<input type="text" name="hotline" placeholder="请输入咨询热线" class="form-control" value="${model.websiteHotline }" />
									</div>
									<div class="form-group" role="form">
										<label>网站地址</label>
										<input type="text" name="address" placeholder="请输入网站地址" class="form-control" value="${model.websiteAddress }" />
									</div>
									<div class="form-group" role="form">
										<label>网站图片</label><br />
										<div class="fileinput <c:if test="${empty model.websiteLogo }">fileinput-new</c:if> <c:if test="${not empty model.websiteLogo }">fileinput-exists</c:if>" data-provides="fileinput">
											<c:if test="${empty model.websiteLogo }">
												<div class="fileinput-new thumbnail" style="width: 167px; min-height: 100px;">
													<img src="${path }/admin/resources/main/web/images/login_icon.png" alt="默认图片" />
													<input type="hidden" name="logo" value="/admin/resources/main/web/images/login_icon.png" />
												</div>
											</c:if>
											<div class="fileinput-preview fileinput-exists thumbnail" style="width: 167px; min-height: 100px;">
												<c:if test="${not empty model.websiteLogo }"><img src="${path }${model.websiteLogo }" /></c:if>
											</div>
											<div>
												<span class="btn btn-default btn-file">
													<span class="fileinput-new">选择图片</span>
													<span class="fileinput-exists">更改图片</span>
													<input type="file" name="filedata" />
												</span>
												<a href="javascript:;" class="btn btn-default fileinput-exists" data-dismiss="fileinput">删除图片</a>
											</div>
										</div>
									</div>
									<div class="form-group" role="form">
										<label>网站协议</label>
										<textarea name="protocol" class="summernote">
											${model.websiteProtocol }
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