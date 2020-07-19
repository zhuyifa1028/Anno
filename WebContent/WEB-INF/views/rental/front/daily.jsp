<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html class="bg-eceef0">
<head>
    <%@ include file="taglibs.jsp"%>
    <title>安诺租车</title>
    <style type="text/css">
		p {
			font-size: 16px;
		}
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
<script type="text/javascript">
	


</script>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">日常记录</h1>
		<a href="daily_history.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right">历史记录</a>
	</header>
	<div class="mui-content p-b-40">
		<form class="mui-form-group" methd="post">
		    <!-- 每日出行编辑 -->
			<c:if test="${not empty model }">
			       <input type="hidden" name="_id" value="${model.id }" />
			 </c:if>
			<div class="mui-select-time p-tb-38 p-l-75 bg-ffffff pos-r">
				<span class="f-s-16 ft-333333" id="today">
				   <fmt:formatDate value="${model.driverDate }" pattern="yyyy-MM-dd" />		
				</span>
				<input type="hidden" name="datefa" />
			</div>
			   
			<div class="m-t-40">
				<div class="mui-dailv-item m-t-20 bg-ffffff">
					<div class="p-tb-28 p-l-70 pos-r ft-333333" id="div1">
					    <input class="f-s-16" style="width: 4rem;" type="text" value="出站路码"  readonly="readonly" />					   
						<input class="f-s-16"  style="width: 3.066667rem;" type="number" name="leavelmile"  id="czlm"
						    <c:if test="${not empty zuotian }">value="<fmt:formatNumber type="number" pattern="0" value="${zuotian}" />"</c:if>
							 value="<fmt:formatNumber type="number" pattern="0" value="${model.driverLeavemile}" />" />
						<span class="f-s-16">&nbsp;Km</span>
					</div>
					<div class="p-tb-28 p-l-70 pos-r mui-select-date">
						<small class="f-s-16"  >
						               选择时间
						</small>  
						<span class="f-s-16 ft-999999" id="ld" style="color: black;    margin-left: 2.3rem;">				  
							  <c:if test="${empty model.driverLeavedate }"> </c:if>
							  <fmt:formatDate value="${model.driverLeavedate }" pattern="HH:mm" />									
						</span>
						<input type="hidden" name="leaveldate" />
					</div>
				</div>
				<div class="mui-dailv-item m-t-20 bg-ffffff" id="div2">
					<div class="p-tb-28 p-l-70 pos-r ft-333333">
						<input class="f-s-16" style="width: 4rem;" type="text" value="上车路码"  readonly="readonly" />					   
						<input class="f-s-16" style="width: 3.066667rem;"  type="number" name="leavelmileuser" id="sclm"			  
						    value="<fmt:formatNumber type="number" pattern="0" value="${model.driverLeavemileuser}" />" 	
						     />
						<span class="f-s-16">&nbsp;Km</span>
					</div>
					<div class="p-tb-28 p-l-70 pos-r mui-select-date">
					    <small class="f-s-16" >
						               选择时间
						</small>  
						<span class="f-s-16 ft-999999" id="ldu" style="color: black; margin-left: 2.3rem;" >						 
						  <fmt:formatDate value="${model.driverLeavedateuser }" pattern="HH:mm" />	
						</span>
						<input type="hidden" name="leaveldateuser" />
					</div>
				</div>
				<div class="mui-dailv-item m-t-20 bg-ffffff" id="div3">
					<div class="p-tb-28 p-l-70 pos-r ft-333333">
					    <input class="f-s-16" style="width: 4rem;" type="text" value="下车路码"  readonly="readonly" />	
						<input class="f-s-16" style="width: 3.066667rem;" type="number" name="backmileuser" id="xclm"    	
					        value="<fmt:formatNumber type="number" pattern="0"  value="${model.driverBackmileuser}"  />" 	 						
						     />
						<span class="f-s-16">&nbsp;Km</span>
					</div>
					<div class="p-tb-28 p-l-70 pos-r mui-select-date">
					    <small class="f-s-16" >
						               选择时间
						</small>  
						<span class="f-s-16 ft-999999" id="bdu" style="color: black; margin-left: 2.3rem;">						
						   <fmt:formatDate value="${model.driverBackdateuser }" pattern="HH:mm" />	
						</span>
						<input type="hidden" name="backdateuser" />
					</div>
				</div>
				<div class="mui-dailv-item m-t-20 bg-ffffff" id="div4">
					<div class="p-tb-28 p-l-70 pos-r ft-333333">
					    <input class="f-s-16"  style="width: 4rem;" type="text" value="回站路码"  readonly="readonly" />	
						<input class="f-s-16" style="width: 3.066667rem;"  type="number" name="backmile"  id="hzlm"
						     value="<fmt:formatNumber type="number" pattern="0"  value="${model.driverBackmile}"  />" 							
						      />
						<span class="f-s-16">&nbsp;Km</span>
					</div>
					<div class="p-tb-28 p-l-70 pos-r mui-select-date">
					    <small class="f-s-16" >
						               选择时间
						</small>  
						<span class="f-s-16 ft-999999" id="bd" style="color: black; margin-left: 2.3rem;">						 
						   <fmt:formatDate value="${model.driverBackdate }" pattern="HH:mm" />	
						</span>
						<input type="hidden" name="backdate" />
					</div>
				</div>
				<div class="m-t-20 bg-ffffff" id="div5">
					<div class="mui-dailv-item p-tb-28 p-l-70 pos-r ft-333333">
					    <input class="f-s-16" style="width: 4rem;" type="text" value="请输入营运公里数"  readonly="readonly" />							
						<input style="width: 3.066667rem;" class="f-s-16" type="number" id="milen"
						     value="<fmt:formatNumber type="number" pattern="0"  value="${model.driverMilenow}"  />"   
						     name="milenow"  />
						<span class="f-s-16">&nbsp;Km</span>
					</div>
				</div>
				
				<div class="m-t-20 bg-ffffff">
					<div class="mui-dailv-item p-tb-28 p-l-70 pos-r ft-333333">
					 <input class="f-s-16" style="width: 4rem;" type="text" value="当日总行驶公里数"  readonly="readonly" />	
						<input style="width: 3.066667rem;" class="f-s-16" type="number"  
						     value=" "  id="hid"  readonly="readonly"  />
						<span class="f-s-16">&nbsp;Km</span>
					</div>
				</div>
				
				<div class="p-rl-45">
					<button class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e">确&nbsp;&nbsp;认</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			mui.init();
			
            //获取焦点
			$("#div1").click(function(){				
				$("#czlm").focus();
			})
			 $("#div2").click(function(){				
				$("#sclm").focus();
			}) 
			$("#div3").click(function(){				
				$("#xclm").focus();
			})
			$("#div4").click(function(){				
				$("#hzlm").focus();
			})
			
			$("#div5").click(function(){				
				$("#milen").focus();
			}) 
			
			//计算公里数
			$("#div5").click(function(){				
				var num=$("#hzlm").val()-$("#czlm").val();			
				$("#hid").val(num); 		
			})
					
			
			//格式化日期
			Date.prototype.Format = function (fmt) { 
			    var o = {
			        "M+": this.getMonth() + 1, //月份 
			        "d+": this.getDate(), //日 
			        "h+": this.getHours(), //小时 
			        "m+": this.getMinutes(), //分 
			        "s+": this.getSeconds(), //秒 
			        "q+": Math.floor((this.getMonth()+3 )/3), 
			        "S": this.getMilliseconds() 
			    };
			    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			    for (var k in o)
			    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			    return fmt;
			}
			

		 	if($('input[name="_id"]').val()==null||$('input[name="_id"]').val()==""){
	        	 document.getElementById("today").innerHTML = new Date().Format("yyyy-MM-dd");
	        	}	
		 	
		 	var today = new Date();		
			var time = new mui.DtPicker({
				endDate: new Date(today.getFullYear(), today.getMonth(), today.getDate()   ),	
				type: 'date',
				//value: '2017-06-08',				
				labels: ['年', '月', '日'],
				buttons: ['取消', '确定']
			});
			// 选择日期控件
			$('.mui-form-group').on('tap', '.mui-select-time', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				time.show(function(rs) {
					_span.html(rs.text);
					_input.val(rs.text);
				});

				return false;
			});

			var date = new mui.DtPicker({
				//endDate: new Date(today.getFullYear(), today.getMonth(), today.getDate(),today.getHours(),today.getMinutes()   ),				
				type: 'time',
				//value: '2017-06-08',
				labels: ['年', '月', '日', '时', '分'],
				buttons: ['取消', '确定']
			});
			// 选择时间控件
			$('.mui-form-group').on('tap', '.mui-select-date', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				date.show(function(rs) {
					_span.html(rs.text);
					
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs.text);
				});

				return false;
			});

			// 表单提交点击事件
			$('.mui-form-group').on('tap', '.mui-btn', function() {			
	            setTimeout(function() {
	              mui(this).button('reset');
	              var date=document.getElementById("today").innerText;//根时间
	              var _id=$('[name="_id"]').val();
	              var lm=$('[name="leavelmile"]').val();
	                  var ldmin=document.getElementById("ld").innerText;
	                	                  
	                  var ld;
	                  if(ldmin==" "){
	                	  var ld=null;
	                  }else{
	                	  var ld=date+ldmin;  
	                  }   
	                  
	              var lmu=$('[name="leavelmileuser"]').val();
	                  var ldumin=document.getElementById("ldu").innerText;
	                  var ldu;
	                  if(ldumin==" "){
	                	  var ldu=null;
	                  }else{
	                	  var ldu=date+ldumin;
	                  }  
	                  
	              var bm=$('[name="backmile"]').val()
	                  var bdumin=document.getElementById("bdu").innerText;
	                  var bdu;
	                  if(bdumin==" "){
	                	  var bdu=null;
	                  }else{
	                	  var bdu=date+bdumin;
	                  } 
	                  
	              var bmu=$('[name="backmileuser"]').val()       
	                  var bdmin=document.getElementById("bd").innerText;
	                  var bd;
	                  if(bdmin==" "){
	                	  var bd=null;
	                  }else{
	                	  var bd=date+bdmin;
	                  } 
	              var mn=$('[name="milenow"]').val();	
	             	           
	              $.post('daily', {_id:_id,date:date,leavemile:lm,leavedate:ld,leavemileuser:lmu,leavedateuser:ldu,backmileuser:bmu,backdateuser:bdu,backmile:bm,backdate:bd,milenow:mn}, function(data) {						
						if(data.succeed == true) {								
							window.location.href = 'daily_history.html';
						}  else {
							mui.toast(data.message,{ duration:'long', type:'div' });							
						} 
					});		
    
	            }.bind(this), 2000);
	        	mui(this).button('loading');
			});
		});
	</script>
</body>
</html>