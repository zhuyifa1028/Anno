<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">培训详情</h1>
	</header>	
	
    <form class="mui-form-group"  method="post">
	<div class="mui-content p-tb-60 p-rl-20" style="padding-top: 1.786667rem;">	
		<h1 class="f-s-24 ft-333333">${model.recordTraintitle }</h1>
		<div class="f-s-14 ft-e32929 m-t-16">${model.recordTrainvicetitle }</div>
		<div class="m-t-40 mui-train-detail f-s-0">
		   <%--  <img src="${front}${model.recordTrainstartpic}" alt="" /> 	    --%> 
			 <p class="f-s-16">${model.recordTraincontent } </p>	 			 				
		</div>
		<div class="f-s-14 ft-999999 m-t-60"><fmt:formatDate value="${model.initDate }" pattern="yyyy-MM-dd HH:mm:ss" /></div>			   
	    <input type="hidden" name="_id" value="${model.id }">	  
		<div class="p-rl-25">
			    <c:if test="${models.driverTrainstate eq 1 }">
			        <c:if test="${models.driverId eq model.id }">
			          <button  disabled="true" class="mui-btn mui-btn-block m-t-80 ft-2cad6e f-s-16 bg-transparent" style="border: solid 2px #2cad6e;">我已完成以上培训</button>
			        </c:if>	
			    </c:if> 
				<c:if test="${ models.driverTrainstate  eq 0 }">
			      <button class="mui-btn mui-btn-block m-t-80 ft-2cad6e f-s-16 bg-transparent" style="border: solid 2px #2cad6e;">完成培训</button>
			    </c:if>
		</div>
		</a>		
	</div>
	</form>
	<script type="text/javascript">
		$(function() {			
			mui.init();
			// 表单提交点击事件	
			$('.mui-form-group').on('tap', '.mui-btn', function() {	
	            setTimeout(function() {	 
	            	mui(this).button('reset');	
	            	var _id =$('input[name="_id"]').val();					
					$.post('detail', {id:_id}, function(data) {						
						if(data.succeed == true) {			 					  				  
							window.location.href = 'index.html';
						}  else {
							mui.toast(data.message,{ duration:'long', type:'div' });							
						} 
					});				
	              
	            }.bind(this), 1000);
	        	mui(this).button('loading');
			});
		});
		
		$(document).ready(function(){			
			    $("p").attr("class","f-s-16");			 
		});
		
	</script>
</body>
</html>