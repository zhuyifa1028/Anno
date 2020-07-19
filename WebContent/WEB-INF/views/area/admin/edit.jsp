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
			$.get(_admin_area + "/search/" + mid + ".html", function(data) {
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
							区域管理-编辑
						</h2>
						<ol class="breadcrumb">
							<li><a href="javascript:;" data-action="${admin_area }/list.html?mid=0">区域管理</a></li>
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
									区域信息表单
								</h5>
								<div class="ibox-tools">
									<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
									<a class="close-link"><i class="fa fa-times"></i></a>
								</div>
							</div>
							<div class="ibox-content">
								<form class="form" action="${admin_area }/edit" method="post" novalidate="novalidate">
									<c:if test="${not empty model }">
										<input type="hidden" name="_id" value="${model.id }" />
									</c:if>
									
									<div class="form-group" role="form">
										<label>上级区域</label>
										<div class="input-group">
											<span class="input-group-addon icon-search"><i class="fa fa-search"></i></span>
											<a data-toggle="modal" href="#modal-form"></a>
											<input type="text" id="area_name" placeholder="请选择上级区域" class="form-control input-search" value="${model.parent.areaName }" data-validates="{required: true, messages: {required: '上级区域不允许为空'}}" />
											<input type="hidden" name="parent_id" value="${model.areaParentId }" />
										</div>
										<label class="error" for="parent_name"></label>
									</div>
									
									<div class="form-group" role="form">
										<label>区域名称</label>
										<input type="input" name="name" placeholder="请输入区域名称" class="form-control" value="${model.areaName }" data-validates="{required: true, messages: {required: '区域名称不允许为空'}}" />
									</div>
									
									
									<div class="form-group" role="form">
										<label>区域序列</label>
										<input type="text" name="sequence" placeholder="请输入区域序列" class="form-control" value="${model.areaSequence }" data-validates="{digits: true, messages: {digits: '区域序列只允许输入整数'}}" />
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