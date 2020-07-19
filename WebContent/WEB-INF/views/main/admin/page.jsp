<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>

<input type="hidden" name="pageNum" value="${pageNum }" />
<input type="hidden" name="pageSize" value="${pageSize }" />
<ul class="paginations pull-right footable-page">
	<c:forEach items="${page }" var="p">
		<li class="footable-page-arrow ${p.disabled } ${p.active }" data-page="${p.page }">
			<a href="javascript:;">${p.name }</a>
		</li>
	</c:forEach>
</ul>
<script type="text/javascript">
$('ul.footable-page').on('click', 'li.footable-page-arrow', function() {
	var _this = $(this);
	if(!_this.hasClass('disabled')) {
		var num = _this.closest('td').find('input[name="pageNum"]');
		var form = _this.closest('form');
		var _page = _this.attr('data-page') || '';
		if(num.length > 0 && form.length > 0 && !!_page) {
			num.val(_page);
			var _type = form.attr('data-type');
			if(_type == 'modal') {
				$(form).ajaxSubmit({
					type: "post",
					dataType: "html",
					success: function(data) {
						var _table = form.find('table.footable');
						_table.find('tbody').remove();
						_table.find('tfoot').remove();
						_table.append(data);
					}
				});
			} else {
				form.submit();
			}
		}
	}
	return false;
});
</script>