<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>
    <title>安诺租车</title>
    <style type="text/css">
    .mui-toast-message {
     font-size:0.55rem;
    width:6.5rem;
    padding: 0px 15px;
    text-align: center;
    color: #fff;
    border-radius: 6px;
    background-color: #323232;
    line-height: 0.7rem;
    }
    </style>
</head>

<body>
<form class="m-t-150 mui-form-group" method="post" novalidate="novalidate">
	<div class="daily-history-fixed h-80 bg-ffffff">
		<input name="" value="" type="checkbox" />
		<span class="f-s-16 ft-333333 v-a-m l-h-d-80 m-l-40">Select all</span>
		<button class="mui-btn f-s-16 mui-btn-link mui-pull-right h-80" type="submit">Validate</button>
	</div>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">Timetable&nbsp;&&nbsp;Mileage</h1>
		<a href="../zh/daily_history.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">中</a>
	</header>
	<div class="mui-content">
		<div class="mui-slider mui-fullscreen">
			<div class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control">
				<div class="mui-scroll">
					<a class="mui-control-item mui-active f-s-16" href="#not">To&nbsp;be&nbsp;validated</a>
					<a class="mui-control-item f-s-16" href="#has">Validated</a>
				</div>
			</div>
			<div class="mui-slider-group">
				<div id="not" class="mui-slider-item mui-control-content mui-active">
					<div class="table bg-ffffff f-s-14 ft-999999 t-a-c">
						<div class="table-cell h-80 v-a-m">&nbsp;</div>
						<div class="table-cell h-80 v-a-m">Date</div>
						<div class="table-cell h-80 v-a-m">Start&nbsp;Time</div>
						<div class="table-cell h-80 v-a-m">End&nbsp;Time</div>
						<div class="table-cell h-80 v-a-m">Mileage</div>
					</div>
					<div class="mui-scroll-wrapper p-b-80" style="top: 1.066667rem;">
						<div class="mui-scroll f-s-12 ft-999999">
						  <c:forEach items="${models }" var="m">
						  <c:if test="${m.driverUaffirm eq 0 }">
							<div class="table m-t-10 bg-ffffff t-a-c">
								<div class="table-cell h-80 v-a-m">
									<input name="checkboxname" value="${m.id }" type="checkbox" />
									<input type="hidden" name="uaffirm" value="${1}" />
								</div>
								<div class="table-cell h-80 v-a-m">
								   <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								   <c:if test="${empty m.driverDate }">-</c:if>
								</div>
								<div class="table-cell h-80 v-a-m">
								   <fmt:formatDate value="${m.driverLeavedateuser }" pattern="HH:mm:ss" />
							       <c:if test="${empty m.driverLeavedateuser }">-</c:if>
								</div>
								<div class="table-cell h-80 v-a-m">
								   <fmt:formatDate value="${m.driverBackdateuser }" pattern="HH:mm:ss" />
								   <c:if test="${empty m.driverBackdateuser }">-</c:if>
								</div>
								<div class="table-cell h-80 v-a-m">
								   <fmt:formatNumber value="${m.driverMilenow}" var="result" pattern="0" groupingUsed="false"/>
								   <c:out value="${result}Km"  default="--"/>
								</div>
							</div>
							</c:if>
						  </c:forEach>
						</div>
					</div>
				</div>

				<div id="has" class="mui-slider-item mui-control-content">
					<div class="table bg-ffffff f-s-14 ft-999999 t-a-c">
						<div class="table-cell h-80 v-a-m">Date</div>
						<div class="table-cell h-80 v-a-m">Start&nbsp;Time</div>
						<div class="table-cell h-80 v-a-m">End&nbsp;Time</div>
						<div class="table-cell h-80 v-a-m">Mileage</div>
					</div>
					<div class="mui-scroll-wrapper p-b-80" style="top: 1.066667rem;">
						<div class="mui-scroll f-s-12 ft-999999">
						 <c:forEach items="${models }" var="mm">
						  <c:if test="${mm.driverUaffirm eq 1 }">
							<div class="table m-t-10 bg-ffffff t-a-c">
								<div class="table-cell h-80 v-a-m">
								   <fmt:formatDate value="${mm.driverDate }" pattern="yyyy-MM-dd" />
								   <c:if test="${empty mm.driverDate }">-</c:if>
								</div>
								<div class="table-cell h-80 v-a-m">
								   <fmt:formatDate value="${mm.driverLeavedateuser }" pattern="HH:mm:ss" />
							       <c:if test="${empty mm.driverLeavedateuser }">-</c:if>
								</div>
								<div class="table-cell h-80 v-a-m">
								   <fmt:formatDate value="${mm.driverBackdateuser }" pattern="HH:mm:ss" />
								   <c:if test="${empty mm.driverBackdateuser }">-</c:if>
								</div>
								<div class="table-cell h-80 v-a-m">
								   <fmt:formatNumber value="${mm.driverMilenow}" var="result" pattern="0" groupingUsed="false"/>
								   <c:out value="${result}Km"  default="--"/>
								</div>
							</div>
							</c:if>
						  </c:forEach>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	</form>
	<script type="text/javascript">
		mui.init();
		mui.ready(function() {
			mui.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, item) {
				mui(item).pullToRefresh({
					up: {
						auto: true,
						show: false,
						contentinit: 'Scroll up to refresh',
						contentdown: 'Scroll up to refresh',
						contentrefresh: 'Loading...',
						contentnomore: 'No more data',
						callback: function() {
							var self = this;
							setTimeout(function() {
								self.endPullUpToRefresh(true);
							}, 1000);
						}
					}
				});
			});

			var _history = $('.daily-history-fixed');
			$('#not').append(_history);

			// 全选按钮
			$('.daily-history-fixed').on('change', 'input[type="checkbox"]', function() {
				var _flag = this.checked;
				$('#not').find('input[type="checkbox"]').each(function(index, itme) {
					itme.checked = _flag;
				});
				return false;
			});
		});
		
		$(function() {
			// 表单提交点击事件
			$('.mui-form-group').on('tap', '.mui-btn', function() {
	            setTimeout(function() {
	                mui(this).button('reset');
	                var chk_value =[]; 
	                $('input[name="checkboxname"]:checked').each(function(){ 
	                chk_value.push($(this).val()); 
	                }); 
	                
	                var _uaffirm =$('input[name="uaffirm"]').val();
					$.post('../en/daily_history', {chk_value: chk_value,uaffirm:_uaffirm}, function(data) {
						if(data.succeed == true) {
							window.location.href = '../en/daily_history.html';
						} else {
						   mui.toast(data.message,{duration:5000,type:'div'});
						}
					});
	            }.bind(this), 1000);
	            mui(this).button('loading');
			});
		});
	</script>
</body>
</html>