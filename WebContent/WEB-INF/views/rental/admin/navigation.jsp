<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/admin/taglib.jsp"%>

<div class="row wrapper border-bottom white-bg page-heading">
	<form class="navigation" method="post" novalidate="novalidate">
		<div class="col-lg-10">
			<h2>
				${name }
				<c:if test="${empty name }">
					${model.programaTitle }
				</c:if>
			</h2>
			<ol class="breadcrumb">
				<c:if test="${not empty name }">
					<li><a <c:if test="${not empty model.programaUrl }">data-action="${admin }${model.programaUrl }?mid=${model.id }"</c:if> href="javascript:;">${model.programaTitle }</a></li>
					<li class="active"><strong>${name }</strong></li>
				</c:if>
				<c:if test="${empty name }">
					<li class="active"><strong>${model.programaTitle }</strong></li>
				</c:if>
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