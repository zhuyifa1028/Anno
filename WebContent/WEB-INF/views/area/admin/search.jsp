<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../main/admin/taglib.jsp"%>

<div id="modal-form" class="modal fade" aria-hidden="true" data-type="modal">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<button type="button" style="display: none;" data-dismiss="modal" aria-hidden="true"></button>
			<div class="modal-body">
				<div class="row">
					<h3 class="m-t-none m-b">区域信息管理</h3>
					<p>双击选择对应区域信息</p>
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
										addDiyDom: diy,
										dblClickExpand: false
									},
									callback: {
										beforeDblClick: onDblClick
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
								
								function onDblClick(tid, node) {
									var _id = node.id;
									var _name = node.name;
									var form = $('form.form');
									form.find('input[id="area_name"]').val(_name);
									form.find('input[name="parent_id"]').val(_id);
									$('#' + tid).closest('div.modal-content').find('button[data-dismiss="modal"]').trigger('click');
									return false;
								}
							});
						</script>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>