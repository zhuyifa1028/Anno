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
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">历史信息</h1>
		<a class="mui-btn f-s-14 mui-btn-link mui-pull-right" style="float: left;" href="index.html"  >首页</a>			
	    <a class="mui-btn f-s-14 mui-btn-link mui-pull-right" id="editbj" href="" >编辑</a>	
	    <!-- <button class="mui-btn f-s-14 mui-btn-link mui-pull-right">编辑</button> -->
	</header>
	<div class="mui-content">
		<div class="p-rl-50">
		
		<c:forEach items="${modelscheer}" var="m">
		     <c:if test="${not empty m.driverGasrpaytype  }">
			<div data-id="${m.id }" class="mui-history-item bg-ffffff m-t-20 p-t-15 p-b-40 p-rl-80 ft-999999 f-s-14">
				<div class="m-t-25"><fmt:formatDate value="${m.driverDate }" pattern="yyyy-MM-dd HH:mm" /></div>
				<div class="m-t-25">路码：				   
					  <fmt:formatNumber value="${m.driverMilenow }" pattern="0" />KM									
				</div>
		
				<div class="m-t-25">支付类型:
				  <c:if test="${m.driverGasrpaytype eq 0}">现金</c:if>
				  <c:if test="${m.driverGasrpaytype eq 1}">油卡 </c:if>
				</div>
			</div>
			</c:if>
		</c:forEach>	
		
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$('div.mui-content').on('tap', 'div.mui-history-item', function() {
				var _this = $(this);
				_this.closest('div.p-rl-50').find('div.mui-history-item').removeClass('active');
				_this.addClass('active');
			});

			// 编辑按钮
			/*  $('header').on('tap', '.mui-btn', function() {
				
			    var _id = $('.mui-content').find('.mui-history-item.active').attr('data-id') || '';							
	
				if(_id != '') {				
					//window.location.href = "cheerhistoryedit?id="+_id;
				
				} else {

				} 
			}); 
			 */
			
			 
			 $("#editbj").click(function(){
				   var _id = $('.mui-content').find('.mui-history-item.active').attr('data-id') || '';	
					$("#editbj").attr("href","cheerhistoryedit?id="+_id)	;		 
			 })
			 
			
		})
	</script>
</body>
</html>