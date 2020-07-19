<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html class="bg-eceef0">
<head>
    <%@ include file="taglibs.jsp"%>
    <title>安诺租车</title>
</head>
<style>
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
<body>
<form class="m-t-150 mui-form-group" method="post" novalidate="novalidate">
	<div class="daily-history-fixed h-80 bg-ffffff">
		<input name="" value="" type="checkbox" />
		<span class="f-s-16 ft-333333 v-a-m l-h-d-80 m-l-40">全选</span>
		<button class=" fszy mui-btn f-s-16 mui-btn-link mui-pull-right h-80">发送</button>
	</div>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<a class="mui-btn f-s-14 mui-btn-link mui-pull-right" style="float: left;" href="index.html"  >首页</a>		
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">历史信息</h1>
		
		<a class="bj mui-btn f-s-14 mui-btn-link mui-pull-right"  id="editbj" href=""  >编辑</a>		
		<!-- <button class="bj mui-btn f-s-14 mui-btn-link mui-pull-right">编辑</button> -->
	</header>
	<div class="mui-content">
		<div class="mui-slider mui-fullscreen">
			<div class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control">
				<div class="mui-scroll">
					<a class="mui-control-item mui-active f-s-16" href="#not">待确认</a>
					<a class="mui-control-item f-s-16" href="#has">已确认</a>
				</div>
			</div>
			<div class="mui-slider-group">
				<div id="not" class="mui-slider-item mui-control-content mui-active">
					<div class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="daily-history-view m-t-20">
							
							<c:forEach items="${modelscost }"  var="m">
									<c:if test="${m.driverBeizhu eq 'fyjl'}">  	
									<c:if test="${m.driverSendu eq 0 }">
									<c:if test="${m.driverUaffirm eq 0}">		
								<div class="daily-history-item m-t-15 f-s-0">
									<img src="${files }/images/waves_top.png" alt="" />			
									<!-- 未发送未确认 -->
															
									<div class="p-t-75 p-r-73 p-b-73 p-l-30 bg-ffffff">
									    <input name="checkboxname" value="${m.id }" type="checkbox" /> 
										<span class="daily-history-state f-s-12 unconfirmed">未发送</span>
										<div class="p-rl-100 f-s-14 ft-999999">
											<div class="ft-999999">
											   <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />										
											</div>
											<div class="m-t-25">费用承担：
												<c:if test="${m.driverRmbassume eq 0 }">客户直接支付</c:if>
												<c:if test="${m.driverRmbassume eq 1 }">安诺久通先行垫付</c:if>
											</div>
											<div class="m-t-25">费用目的：
											     <c:if test="${m.driverRmbgoal eq 0 }">商务</c:if>
												 <c:if test="${m.driverRmbgoal eq 1 }">个人</c:if>
											</div>
											<div class="m-t-25">费用类型：
											     <c:if test="${empty m.driverRmbtype }">-</c:if>										 
												 <c:if test="${ m.driverRmbtype eq 0}">早餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 1}">中餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 2}">晚餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 3}">通行费</c:if>
												 <c:if test="${ m.driverRmbtype eq 4}">停车费</c:if>
												 <c:if test="${ m.driverRmbtype eq 5}">住宿费</c:if>
												 <c:if test="${ m.driverRmbtype eq 6}">差旅</c:if>
												 <c:if test="${ m.driverRmbtype eq 7}">餐饮</c:if>
												 <c:if test="${ m.driverRmbtype eq 8}">通讯费</c:if>
												 <c:if test="${ m.driverRmbtype eq 9}">交通费</c:if>
												 <c:if test="${ m.driverRmbtype eq 10}">加油费</c:if>
												 <c:if test="${ m.driverRmbtype eq 11}">其他</c:if>
											</div>									
											<div class="daily-history-dotted m-t-30">&nbsp;</div>
											<div class="m-t-30 ft-333333"> 费用：¥
											      <c:set var="title" value="${fn:split(m.driverRmb, '.')}" />
													 ${title[0]}.<c:if test="${fn:length(title[1]) eq 1}">${title[1]}0</c:if><c:if test="${fn:length(title[1]) eq 2}">${title[1]}</c:if>
												  <c:if test="${empty m.driverRmb}">-</c:if>	
											</div>											
										</div>
									</div>
										
									<img src="${files }/images/waves_bottom.png" alt="" />
								</div>
								     </c:if>
									 </c:if> 
									 </c:if> 
									</c:forEach>	
								
	                               <!--已发送未确认-->
	                            <c:forEach items="${modelscost }" var="m">
							   	<c:if test="${m.driverBeizhu eq 'fyjl'}">  
								<c:if test="${m.driverSendu eq 1 }">
								<c:if test="${m.driverUaffirm eq 0 }">
								<div class="daily-history-item m-t-15 f-s-0">
									<img src="${files }/images/waves_top.png" alt="" />					
									<div class="p-t-75 p-r-73 p-b-73 p-l-30 bg-ffffff">
										<input name="checkboxname" value="${m.id }" type="checkbox" /> 
										<span class="daily-history-state f-s-12 tobeconfirmed">待确认</span>
										<div class="p-rl-100 f-s-14 ft-999999">
											<div class="ft-999999">
											   <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />										
											</div>
											<div class="m-t-25">费用承担：
												<c:if test="${m.driverRmbassume eq 0 }">客户直接支付</c:if>
												<c:if test="${m.driverRmbassume eq 1 }">安诺久通先行垫付</c:if>
											</div>
											<div class="m-t-25">费用目的：
											      <c:if test="${m.driverRmbgoal eq 0 }">商务</c:if>
												  <c:if test="${m.driverRmbgoal eq 1 }">个人</c:if>
											</div>
											<div class="m-t-25">费用类型：
											     <c:if test="${empty m.driverRmbtype }">-</c:if>										 
												 <c:if test="${ m.driverRmbtype eq 0}">早餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 1}">中餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 2}">晚餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 3}">通行费</c:if>
												 <c:if test="${ m.driverRmbtype eq 4}">停车费</c:if>
												 <c:if test="${ m.driverRmbtype eq 5}">住宿费</c:if>
												 <c:if test="${ m.driverRmbtype eq 6}">差旅</c:if>
												 <c:if test="${ m.driverRmbtype eq 7}">餐饮</c:if>
												 <c:if test="${ m.driverRmbtype eq 8}">通讯费</c:if>
												 <c:if test="${ m.driverRmbtype eq 9}">交通费</c:if>
												 <c:if test="${ m.driverRmbtype eq 10}">加油费</c:if>
												 <c:if test="${ m.driverRmbtype eq 11}">其他</c:if>
											</div>									
											<div class="daily-history-dotted m-t-30">&nbsp;</div>
											<div class="m-t-30 ft-333333">费用：¥
											      <c:set var="title" value="${fn:split(m.driverRmb, '.')}" />
													 ${title[0]}.<c:if test="${fn:length(title[1]) eq 1}">${title[1]}0</c:if><c:if test="${fn:length(title[1]) eq 2}">${title[1]}</c:if>
												  <c:if test="${empty m.driverRmb}">-</c:if>
											</div>										
										</div>
									</div>								
									<img src="${files }/images/waves_bottom.png" alt="" />
								</div>
	                            </c:if>
							    </c:if>								
							    </c:if>
								</c:forEach>
								
							</div>
						</div>
					</div>

				</div>

				<div id="has" class="mui-slider-item mui-control-content">
					<div class="mui-scroll-wrapper">					   
						<div class="mui-scroll">
							<div class="daily-history-view m-t-20">
							    <!-- 已确认 -->
								<c:forEach items="${modelscost }" var="m">
								<c:if test="${m.driverBeizhu eq 'fyjl'}">  
								<c:if test="${ m.driverUaffirm eq 1 }">
								<c:if test="${ m.driverSendu eq 1 }">
								<div class="daily-history-item m-t-15 f-s-0">
									<img src="${files }/images/waves_top.png" alt="" />
									<div class="p-t-75 p-r-73 p-b-73 p-l-30 bg-ffffff">
										<span class="daily-history-state f-s-12 confirmed">已确认</span>
										<div class="p-rl-100 f-s-14 ft-999999">
											<div class="ft-999999">
											   <fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />										
											</div>
											<div class="m-t-25">费用承担：
												<c:if test="${m.driverRmbassume eq 0 }">客户直接支付</c:if>
												<c:if test="${m.driverRmbassume eq 1 }">安诺久通先行垫付</c:if>
											</div>
											<div class="m-t-25">费用目的：
											    <c:if test="${m.driverRmbgoal eq 0 }">商务</c:if>
												<c:if test="${m.driverRmbgoal eq 1 }">个人</c:if>
											</div>
											<div class="m-t-25">费用类型：										
												 <c:if test="${empty m.driverRmbtype }">-</c:if>
												 <c:if test="${ m.driverRmbtype eq 0}">早餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 1}">中餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 2}">晚餐</c:if>
												 <c:if test="${ m.driverRmbtype eq 3}">通行费</c:if>
												 <c:if test="${ m.driverRmbtype eq 4}">停车费</c:if>
												 <c:if test="${ m.driverRmbtype eq 5}">住宿费</c:if>
												 <c:if test="${ m.driverRmbtype eq 6}">差旅</c:if>
												 <c:if test="${ m.driverRmbtype eq 7}">餐饮</c:if>
												 <c:if test="${ m.driverRmbtype eq 8}">通讯费</c:if>
												 <c:if test="${ m.driverRmbtype eq 9}">交通费</c:if>
												 <c:if test="${ m.driverRmbtype eq 10}">加油费</c:if>
												 <c:if test="${ m.driverRmbtype eq 11}">其他</c:if>										 
											</div>									
											<div class="daily-history-dotted m-t-30">&nbsp;</div>
											<div class="m-t-30 ft-333333">费用：
											      <c:set var="title" value="${fn:split(m.driverRmb, '.')}" />
													 ${title[0]}.<c:if test="${fn:length(title[1]) eq 1}">${title[1]}0</c:if><c:if test="${fn:length(title[1]) eq 2}">${title[1]}</c:if>
												  <c:if test="${empty m.driverRmb}">-</c:if>
											</div>										
										</div>
									</div>							       
									<img src="${files }/images/waves_bottom.png" alt="" />
									 </c:if>
									 </c:if>
									 </c:if>
									 </c:forEach>
								</div>
					
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

			// 编辑按钮 旧
		/* 	$('header').on('tap', '.bj', function() {
				var _checkbox = $('.daily-history-view').find('input:checked');
				var _checkbox2 = $('.daily-history-view').find('input:checked').val();
				if(_checkbox.length == 1) {
					$.post('costpd', {id: _checkbox2}, function(data) {
						if(data.succeed == true) {
							$('#not').find('input[type="checkbox"]').each(
									function(index, itme) {
									itme.checked = false;
									});						
							window.location.href = "costedit?id="+_checkbox2;
						} else {
						   mui.toast(data.message,{ duration:'long', type:'div' });
						}
					});				

				} else if(_checkbox.length > 1) {
					mui.toast("你选择了多个项目",{ duration:'long', type:'div' });
					// 选择了多个
				} else {
					// 没有选择
				}
			}); */
			
			
			//编辑新！
			$("#editbj").click(function(){
				var _checkbox = $('.daily-history-view').find('input:checked');
				var _checkbox2 = $('.daily-history-view').find('input:checked').val();
				if(_checkbox.length == 1) {
					$('#not').find('input[type="checkbox"]').each(
							function(index, itme) {
							itme.checked = false;
							});
					$("#editbj").attr("href","costedit?id="+_checkbox2)	;	
				} else if(_checkbox.length > 1) {
					mui.toast("你选择了多个项目",{ duration:'long', type:'div' });
					// 选择了多个
				} else {
					// 没有选择
				}
				
			})
			
			
		});
		
		
		$(function() {
			// 表单提交点击事件
			$('.mui-form-group').on('tap', '.fszy', function() {
	            setTimeout(function() {
	                mui(this).button('reset');
	                var chk_value =[]; 
	                $('input[name="checkboxname"]:checked').each(function(){ 
	                chk_value.push($(this).val()); 
	                }); 			              
	                if(chk_value.length==0){
	                	mui.toast("你还没选择任何内容发送",{ duration:'long', type:'div' });
	                	return;
	                }	              		                		              
					$.post('cost', {chk_value: chk_value}, function(data) {
						if(data.succeed == true) {
							window.location.href = 'cost_history.html';
						} else {
						   mui.toast(data.message,{ duration:'long', type:'div' });
						}
					});
	            }.bind(this), 1000);
	            mui(this).button('loading');
			});
		});
		
	</script>
</body>
</html>