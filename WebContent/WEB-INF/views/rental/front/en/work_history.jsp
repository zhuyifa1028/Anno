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
		<button class="mui-btn f-s-16 mui-btn-link mui-pull-right h-80" id="submit">Validate</button>
	</div>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">Overtime&nbsp;&&nbsp;Expenses</h1>
		<a href="../zh/work_history.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">中</a>
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
						<div class="table-cell h-80 v-a-m">Overtime<br />duration</div>
						<div class="table-cell h-80 v-a-m p-r-5">Overtime<br />cost</div>
						<div class="table-cell h-80 v-a-m p-l-5">Total<br />expenses</div>
					</div>
					<div class="mui-scroll-wrapper p-b-80" style="top: 1.066667rem;">
						<div class="mui-scroll">
						  <div class="table-list f-s-12">
							<c:forEach items="${n}" var="m">
						      
						       <!--只发送费用记录时的状态-->
							  <c:if test="${empty m.driverOvertimetype}">
							   <div class="table m-t-10 t-a-c ft-999999">
									<div class="table-cell h-80 v-a-m bg-ffffff">
										<input name="checkboxname" value="${m.id }" type="checkbox" />
										<input type="hidden" name="uaffirm" value="${1}" />
								    </div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									   <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								       <c:if test="${empty m.driverDate }">-</c:if>
									</div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <c:set var="result" value="${fn:split(m.driverOvertimehours, '.') }" />
										${result[0] }h
										<c:if test="${fn:contains(result[1],'0')}">
										   <c:set var="titles" value="${fn:split(result[1], '0') }"></c:set>
											  <c:if test="${ empty titles[0] }">    
											  </c:if>
											  <c:if test="${not empty titles[0] }">
													${titles[0] }m
											  </c:if>    
									   </c:if>																			
											  <c:if test="${!fn:contains(result[1],'0')}">													         		
												  <c:if test="${fn:length(result[1]) eq 1}">
														${result[1] }0m
												  </c:if>
												  <c:if test="${fn:length(result[1]) eq 2}">
														${result[1] }m
												  </c:if>
											  </c:if>
									</div>
									<div class="table-cell h-80 v-a-m p-r-5">
										<span class="mui-right-semicircle bg-ffffff">
										<fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</div>
									<a href="work_detail.html?id=${m.id }" name="del" class="table-cell h-80 v-a-m p-l-5 ft-5377d2">
										<span class="mui-left-semicircle t-d-u bg-ffffff">
										<fmt:formatNumber value="${m.driverRmb}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</a>
								</div>
							  </c:if>
						      
						       <c:if test="${m.driverOvertimetype eq 0 }">
						        <div class="table m-t-10 t-a-c ft-999999">
									<div class="table-cell h-80 v-a-m bg-ffffff">
										<input name="checkboxname" value="${m.id }" type="checkbox" />
										<input type="hidden" name="uaffirm" value="${1}" />
								    </div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								        <c:if test="${empty m.driverDate }">-</c:if>
									</div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <c:set var="result" value="${fn:split(m.driverOvertimehours, '.') }" />
										${result[0] }h
										<c:if test="${fn:contains(result[1],'0')}">
										   <c:set var="titles" value="${fn:split(result[1], '0') }"></c:set>
											  <c:if test="${ empty titles[0] }">    
											  </c:if>
											  <c:if test="${not empty titles[0] }">
													${titles[0] }m
											  </c:if>    
									   </c:if>																			
											  <c:if test="${!fn:contains(result[1],'0')}">													         		
												  <c:if test="${fn:length(result[1]) eq 1}">
														${result[1] }0m
												  </c:if>
												  <c:if test="${fn:length(result[1]) eq 2}">
														${result[1] }m
												  </c:if>
											  </c:if>
									</div>
									<div class="table-cell h-80 v-a-m p-r-5">
										<span class="mui-right-semicircle bg-ffffff">
										<fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</div>
									<a href="work_detail.html?id=${m.id }" name="del" class="table-cell h-80 v-a-m p-l-5 ft-5377d2">
										<span class="mui-left-semicircle t-d-u bg-ffffff">
										<fmt:formatNumber value="${m.driverRmb}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</a>
								</div>
						       </c:if>
						       
						       <c:if test="${m.driverOvertimetype eq 1 }">
								<div class="table m-t-10 t-a-c ft-e9c239">
									<div class="table-cell h-80 v-a-m bg-ffffff">
										<input name="checkboxname" value="${m.id }" type="checkbox" />
										<input type="hidden" name="uaffirm" value="${1}" />
								    </div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								        <c:if test="${empty m.driverDate }">-</c:if>
									</div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <c:set var="result" value="${fn:split(m.driverOvertimehours, '.') }" />
										${result[0] }h
										<c:if test="${fn:contains(result[1],'0')}">
										   <c:set var="titles" value="${fn:split(result[1], '0') }"></c:set>
											  <c:if test="${ empty titles[0] }">    
											  </c:if>
											  <c:if test="${not empty titles[0] }">
													${titles[0] }m
											  </c:if>    
									   </c:if>																			
											  <c:if test="${!fn:contains(result[1],'0')}">													         		
												  <c:if test="${fn:length(result[1]) eq 1}">
														${result[1] }0m
												  </c:if>
												  <c:if test="${fn:length(result[1]) eq 2}">
														${result[1] }m
												  </c:if>
											  </c:if>
									</div>
									<div class="table-cell h-80 v-a-m p-r-5">
										<span class="mui-right-semicircle bg-ffffff">
										<fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</div>
									<a href="work_detail.html?id=${m.id }" name="del" class="table-cell h-80 v-a-m p-l-5 ft-5377d2">
										<span class="mui-left-semicircle t-d-u bg-ffffff">
										<fmt:formatNumber value="${m.driverRmb}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</a>
								</div>
							   </c:if>
							   
							   <c:if test="${m.driverOvertimetype eq 2 }">
							    <div class="table m-t-10 t-a-c ft-e93939">
									<div class="table-cell h-80 v-a-m bg-ffffff">
										<input name="checkboxname" value="${m.id }" type="checkbox" />
										<input type="hidden" name="uaffirm" value="${1}" />
								    </div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								        <c:if test="${empty m.driverDate }">-</c:if>
									</div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <c:set var="result" value="${fn:split(m.driverOvertimehours, '.') }" />
										${result[0] }h
										<c:if test="${fn:contains(result[1],'0')}">
										   <c:set var="titles" value="${fn:split(result[1], '0') }"></c:set>
											  <c:if test="${ empty titles[0] }">    
											  </c:if>
											  <c:if test="${not empty titles[0] }">
													${titles[0] }m
											  </c:if>    
									   </c:if>																			
											  <c:if test="${!fn:contains(result[1],'0')}">													         		
												  <c:if test="${fn:length(result[1]) eq 1}">
														${result[1] }0m
												  </c:if>
												  <c:if test="${fn:length(result[1]) eq 2}">
														${result[1] }m
												  </c:if>
											  </c:if>
									</div>
									<div class="table-cell h-80 v-a-m p-r-5">
										<span class="mui-right-semicircle bg-ffffff">
										<fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</div>
									<a href="work_detail.html?id=${m.id }" name="del" class="table-cell h-80 v-a-m p-l-5 ft-5377d2">
										<span class="mui-left-semicircle t-d-u bg-ffffff">
										<fmt:formatNumber value="${m.driverRmb}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</a>
								</div>
							    </c:if>
							    
						    </c:forEach>
							</div>

							<div class="m-t-40 p-l-30 f-s-12">
								<div class="ft-999999 m-t-10">Weekdays Overtime</div>
								<div class="ft-e93939 m-t-10">Weekend Overtime</div>
								<div class="ft-e9c239 m-t-10">Bank Holidays Overtime</div>
							</div>
						</div>
					</div>

				</div>

				<div id="has" class="mui-slider-item mui-control-content">
					<div class="table bg-ffffff f-s-14 ft-999999 t-a-c">
						<div class="table-cell h-80 v-a-m">Date</div>
						<div class="table-cell h-80 v-a-m">Overtime<br />duration</div>
						<div class="table-cell h-80 v-a-m p-r-5">Overtime<br />cost</div>
						<div class="table-cell h-80 v-a-m p-l-5">Total<br />expenses</div>
					</div>
					<div class="mui-scroll-wrapper p-b-80" style="top: 1.066667rem;">
						<div class="mui-scroll">
						  <div class="table-list f-s-12">
							 <c:forEach items="${y }" var="m">
							 
							  <!--只发送费用记录时的状态-->
							  <c:if test="${empty m.driverOvertimetype}">
							   <div class="table m-t-10 t-a-c ft-999999">
									<div class="table-cell h-80 v-a-m bg-ffffff">
									   <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								       <c:if test="${empty m.driverDate }">-</c:if>
									</div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <c:set var="result" value="${fn:split(m.driverOvertimehours, '.') }" />
										${result[0] }h
										<c:if test="${fn:contains(result[1],'0')}">
										   <c:set var="titles" value="${fn:split(result[1], '0') }"></c:set>
											  <c:if test="${ empty titles[0] }">    
											  </c:if>
											  <c:if test="${not empty titles[0] }">
													${titles[0] }m
											  </c:if>    
									   </c:if>																			
											  <c:if test="${!fn:contains(result[1],'0')}">													         		
												  <c:if test="${fn:length(result[1]) eq 1}">
														${result[1] }0m
												  </c:if>
												  <c:if test="${fn:length(result[1]) eq 2}">
														${result[1] }m
												  </c:if>
											  </c:if>
									</div>
									<div class="table-cell h-80 v-a-m p-r-5">
										<span class="mui-right-semicircle bg-ffffff">
										<fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</div>
									<a href="work_detail.html?id=${m.id }" name="del" class="table-cell h-80 v-a-m p-l-5 ft-5377d2">
										<span class="mui-left-semicircle t-d-u bg-ffffff">
										<fmt:formatNumber value="${m.driverRmb}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</a>
								</div>
							  </c:if>
						      
						       <c:if test="${m.driverOvertimetype eq 0 }">
						        <div class="table m-t-10 t-a-c ft-999999">
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								        <c:if test="${empty m.driverDate }">-</c:if>
									</div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <c:set var="result" value="${fn:split(m.driverOvertimehours, '.') }" />
										${result[0] }h
										<c:if test="${fn:contains(result[1],'0')}">
										   <c:set var="titles" value="${fn:split(result[1], '0') }"></c:set>
											  <c:if test="${ empty titles[0] }">    
											  </c:if>
											  <c:if test="${not empty titles[0] }">
													${titles[0] }m
											  </c:if>    
									   </c:if>																			
											  <c:if test="${!fn:contains(result[1],'0')}">													         		
												  <c:if test="${fn:length(result[1]) eq 1}">
														${result[1] }0m
												  </c:if>
												  <c:if test="${fn:length(result[1]) eq 2}">
														${result[1] }m
												  </c:if>
											  </c:if>
									</div>
									<div class="table-cell h-80 v-a-m p-r-5">
										<span class="mui-right-semicircle bg-ffffff">
										<fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</div>
									<a href="work_detail.html?id=${m.id }" name="del" class="table-cell h-80 v-a-m p-l-5 ft-5377d2">
										<span class="mui-left-semicircle t-d-u bg-ffffff">
										<fmt:formatNumber value="${m.driverRmb}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</a>
								</div>
						       </c:if>
						       
						       <c:if test="${m.driverOvertimetype eq 1 }">
								<div class="table m-t-10 t-a-c ft-e9c239">
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								        <c:if test="${empty m.driverDate }">-</c:if>
									</div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <c:set var="result" value="${fn:split(m.driverOvertimehours, '.') }" />
										${result[0] }h
										<c:if test="${fn:contains(result[1],'0')}">
										   <c:set var="titles" value="${fn:split(result[1], '0') }"></c:set>
											  <c:if test="${ empty titles[0] }">    
											  </c:if>
											  <c:if test="${not empty titles[0] }">
													${titles[0] }m
											  </c:if>    
									   </c:if>																			
											  <c:if test="${!fn:contains(result[1],'0')}">													         		
												  <c:if test="${fn:length(result[1]) eq 1}">
														${result[1] }0m
												  </c:if>
												  <c:if test="${fn:length(result[1]) eq 2}">
														${result[1] }m
												  </c:if>
											  </c:if>
									</div>
									<div class="table-cell h-80 v-a-m p-r-5">
										<span class="mui-right-semicircle bg-ffffff">
										<fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</div>
									<a href="work_detail.html?id=${m.id }" name="del" class="table-cell h-80 v-a-m p-l-5 ft-5377d2">
										<span class="mui-left-semicircle t-d-u bg-ffffff">
										<fmt:formatNumber value="${m.driverRmb}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</a>
								</div>
							   </c:if>
							   
							   <c:if test="${m.driverOvertimetype eq 2 }">
							    <div class="table m-t-10 t-a-c ft-e93939">
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
								        <c:if test="${empty m.driverDate }">-</c:if>
									</div>
									<div class="table-cell h-80 v-a-m bg-ffffff">
									    <c:set var="result" value="${fn:split(m.driverOvertimehours, '.') }" />
										${result[0] }h
										<c:if test="${fn:contains(result[1],'0')}">
										   <c:set var="titles" value="${fn:split(result[1], '0') }"></c:set>
											  <c:if test="${ empty titles[0] }">    
											  </c:if>
											  <c:if test="${not empty titles[0] }">
													${titles[0] }m
											  </c:if>    
									   </c:if>																			
											  <c:if test="${!fn:contains(result[1],'0')}">													         		
												  <c:if test="${fn:length(result[1]) eq 1}">
														${result[1] }0m
												  </c:if>
												  <c:if test="${fn:length(result[1]) eq 2}">
														${result[1] }m
												  </c:if>
											  </c:if>
									</div>
									<div class="table-cell h-80 v-a-m p-r-5">
										<span class="mui-right-semicircle bg-ffffff">
										<fmt:formatNumber value="${m.driverOvertimermbtotal}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</div>
									<a href="work_detail.html?id=${m.id }" name="del" class="table-cell h-80 v-a-m p-l-5 ft-5377d2">
										<span class="mui-left-semicircle t-d-u bg-ffffff">
										<fmt:formatNumber value="${m.driverRmb}" var="result" pattern="0.00" groupingUsed="false"/>
										<c:out value="¥${result}"  default="--"/>
										</span>
									</a>
								</div>
							    </c:if>
							    
						    </c:forEach>
							</div>

							<div class="m-t-40 p-l-30 f-s-12">
								<div class="ft-999999 m-t-10">Weekdays Overtime</div>
								<div class="ft-e93939 m-t-10">Weekend Overtime</div>
								<div class="ft-e9c239 m-t-10">Bank Holidays Overtime</div>
							</div>
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
			$('.mui-form-group').on('tap', '#submit', function() {
	            setTimeout(function() {
	                mui(this).button('reset');
	                var chk_value =[]; 
	                $('input[name="checkboxname"]:checked').each(function(){ 
	                chk_value.push($(this).val()); 
	                }); 
	                
	                var _uaffirm =$('input[name="uaffirm"]').val();
					$.post('../en/work_history', {chk_value: chk_value}, function(data) {
						if(data.succeed == true) {
							window.location.href = '../en/work_history.html';
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