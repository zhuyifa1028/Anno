<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/admin/taglib.jsp"%>

<c:if test="${not empty json }">
<div class="col-lg-3">
	<div class="ibox float-e-margins">
		<form class="ztree" method="post" novalidate="novalidate">
			<input type="hidden" name="parent_id" />
			<div class="ibox-content">
				<link rel="stylesheet" type="text/css" href="${files }/css/ztree/ztree.nestable.css" />
				<ul class="dd nestable ztree" id="nestable">
					<script type="text/javascript" src="${files }/js/ztree/jquery.ztree.core.js"></script>
					<script type="text/javascript">
						$(function() {
							var data = '${json }' || '';
							if(!!data) {
								data = jQuery.parseJSON(data);
								data = {"id": "${default_id}", "name": "${default_name}", "children": data, "open": "true"};
							} else {
								data = {"id": "${default_id}", "name": "${default_name}", "checked": "true"};
							}
							$.fn.zTree.init($(".nestable"), {
								view: {
									showIcon: false,
									showLine: false,
									selectedMulti: false,
									addDiyDom: diy
								},
								callback: {
									beforeClick: onClick
								}
							}, data);
							
							function diy(id, node) {
								if(node.isParent === true) {
									var swith = $("#" + node.tId + "_switch");
									swith.remove();
									var ico = $("#" + node.tId + "_ico");
									swith.html("&nbsp;");
									ico.before(swith);
								}
								var checked = node.checked || "false";
								if(checked === "true") {
									var ztree = $.fn.zTree.getZTreeObj(id);
									ztree.selectNode(node);
								}
							}
							
							function onClick(id, node, flag) {
								var form = $('#' + id).closest('form.ztree');
								var _id = node.id || '';
								if(form.length > 0 && !!_id) {
									form.find('input[name="parent_id"]').val(_id);
									form.submit();
								}
							}
						});
					</script>
				</ul>
			</div>
		</form>
	</div>
</div>
</c:if>