<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
	<a class="mui-btn f-s-14 mui-btn-link mui-pull-right" style="float: left;" href="index.html"  >首页</a>	
	<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">历史信息</h1>   
	<!-- <button type="submit" class="mui-btn f-s-14 mui-btn-link mui-pull-right" >编辑</button>  -->
	<a class="mui-btn f-s-14 mui-btn-link mui-pull-right" id="editbj" href=" "  >编辑</a>		
	</header>
	<div class="mui-content">
		<div class="p-rl-50">			
				<c:forEach items="${models}" var="m">
					<c:if test="${not empty m.driverClock }">	
							<input type="hidden" name="id" value="${m.id }"> 
						    <div data-id="${m.id }"
							  class="mui-history-item bg-ffffff m-t-20 p-t-15 p-b-40 p-rl-80 ft-999999 f-s-14"> 						
						 						
							<div class="m-t-25">
								<fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd" />
							</div>
							<div class="m-t-25">
								<c:if test="${m.driverClock eq 1 }">已经维护好车辆</c:if>
								<c:if test="${m.driverClock eq 0 }">未维护</c:if>
							</div>
							<div class="m-t-25">
							   <fmt:formatNumber type="number" pattern="0" value="${m.driverStartmile}" />							
							   KM						
							</div>
				
						</div>				
					</c:if>
				</c:forEach>			
		</div>
	</div>
                     <script type="text/javascript">
							$(function() {
								$('div.mui-content').on('tap','div.mui-history-item',function() {
											var _this = $(this);
											_this.closest('div.p-rl-50').find(
													'div.mui-history-item').removeClass('active');
											_this.addClass('active');
											
										});
					
								// 编辑按钮
								 	/* $('header').on('tap', '.mui-btn', function() {
										var _id = $('.mui-content').find('.mui-history-item.active').attr('data-id') || '';													    
										if(_id != '') {				
											
											window.location.href = "indexhistoryedit?id="+_id;
																						
										} else {
					
										}
									});    */
									
									//编辑  新
									$("#editbj").click(function(){							
										var _id = $('.mui-content').find('.mui-history-item.active').attr('data-id') || '';	
										$("#editbj").attr("href","indexhistoryedit?id="+_id)	;	
									});
									
							})
	                    </script>
	
</body>
</html>