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
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">费用记录</h1>
		<a href="cost_history.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right">历史记录</a>
	</header>
	<div class="mui-content">
		<div class="p-rl-45 m-t-50">
			<form class="mui-form-group">
			
			      <!-- 费用记录编辑 -->
				 <c:if test="${not empty model }">
				       <input type="hidden" name="_id" value="${model.id }" />
				 </c:if>
				<div class="m-t-30 mui-select mui-select-boult mui-select-date">
					<span class="ft-333333 f-s-16" id="today" >
					  <fmt:formatDate value="${model.driverDate }" pattern="yyyy-MM-dd" />
					</span>
					<input type="hidden" name="datefa"  value="${model.driverDate }" />
				</div>
				
				<div class="m-t-30 mui-select mui-select-boult mui-select-bear">
					<span class="ft-333333 f-s-16" >
					    <c:if test="${empty model.driverRmbassume }">安诺久通先行垫付</c:if>
					    <c:if test="${ model.driverRmbassume  eq 0 }">客户直接支付</c:if>
					    <c:if test="${ model.driverRmbassume  eq 1 }">安诺久通先行垫付</c:if>
					</span>
					<input type="hidden" name="rmbassume"  value="${model.driverRmbassume }" />
				</div>
				<div class="m-t-30 mui-select mui-select-boult mui-select-business">
					<span class="ft-333333 f-s-16" >
					 <c:if test="${empty model.driverRmbgoal }">商务</c:if>
					 <c:if test="${ model.driverRmbgoal  eq 0 }">商务</c:if>
					 <c:if test="${ model.driverRmbgoal  eq 1 }">个人</c:if>
					</span>
					<input type="hidden" name="rmbgoal" value="${model.driverRmbgoal}" />
				</div>
				<div class="m-t-30 mui-select mui-select-boult mui-select-type">
					<span class="ft-999999 f-s-16" >
					 <c:if test="${empty model.driverRmbtype }">请选择费用类型</c:if>										 
					 <c:if test="${ model.driverRmbtype eq 0}">早餐 </c:if>
					 <c:if test="${ model.driverRmbtype eq 1}">中餐 </c:if>
					 <c:if test="${ model.driverRmbtype eq 2}">晚餐</c:if>
					 <c:if test="${ model.driverRmbtype eq 3}">通行费 </c:if>
					 <c:if test="${ model.driverRmbtype eq 4}">停车费 </c:if>
					 <c:if test="${ model.driverRmbtype eq 5}">住宿费 </c:if>
					 <c:if test="${ model.driverRmbtype eq 6}">差旅 </c:if>
					 <c:if test="${ model.driverRmbtype eq 7}">餐饮 </c:if>
					 <c:if test="${ model.driverRmbtype eq 8}">通讯费 </c:if>
					 <c:if test="${ model.driverRmbtype eq 9}">交通费 </c:if>
					 <c:if test="${ model.driverRmbtype eq 10}">加油 费</c:if>
					 <c:if test="${ model.driverRmbtype eq 11}">其他 </c:if>
					</span>
					<input type="hidden" name="rmbtype" value="${model.driverRmbtype }" />
				</div>
				<div class="m-t-30 mui-input">
					<input class="f-s-16" type="number" name="rmb" 
					 <c:set var="title" value="${fn:split(model.driverRmb, '.')}" />	
						<c:if test="${title[1] eq 0}">value="${title[0]}.00"</c:if>
						<c:if test="${fn:length(title[1]) eq 1}">value="${model.driverRmb}0"</c:if>							 
						<c:if test="${fn:length(title[1]) eq 2}">value="${model.driverRmb}"</c:if> 	  
					    placeholder="请输入费用" />
				</div>
				<button  class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e">完&nbsp;&nbsp;成</button>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			mui.init();

			//格式化日期
			Date.prototype.Format = function (fmt) { 
			    var o = {
			        "M+": this.getMonth() + 1, //月份 
			        "d+": this.getDate(), //日 
			        "h+": this.getHours(), //小时 
			        "m+": this.getMinutes(), //分 
			        "s+": this.getSeconds(), //秒 
			        "q+": Math.floor((this.getMonth()+3)/3), 
			        "S": this.getMilliseconds() //毫秒 
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
			var picker = new mui.DtPicker({
				endDate: new Date(today.getFullYear(), today.getMonth(), today.getDate()   ),	
				type: 'date',
				//value: '2017-06-08',
				labels: ['年', '月', '日'],
				buttons: ['取消', '确定']
			});

			// 选择日期控件
			$('.mui-form-group').on('tap', '.mui-select-date', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				picker.show(function(rs) {
					_span.html(rs.text);
					_input.val(rs.text);
				});

				return false;
			});

			var bear = new mui.PopPicker();
			bear.setData([{
				value: '0',
				text: '客户直接支付'
			}, {
				value: '1',
				text: '安诺久通先行垫付'
			}]);

			// 选择费用承担
			$('.mui-form-group').on('tap', '.mui-select-bear', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				bear.show(function(rs) {
					_span.html(rs[0].text);

					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}

					_input.val(rs[0].value);
				});
				return false;
			});

			var business = new mui.PopPicker();
			business.setData([{
				value: '0',
				text: '商务'
			}, {
				value: '1',
				text: '个人'
			}]);

			// 选择费用由来
			$('.mui-form-group').on('tap', '.mui-select-business', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				business.show(function(rs) {
					_span.html(rs[0].text);

					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}

					_input.val(rs[0].value);
				});
				return false;
			});

			var type = new mui.PopPicker();
			type.setData([{
				value: '3',
				text: '通行费'
			}, {
				value: '4',
				text: '停车费'
			}, {
				value: '5',
				text: '住宿费'
			},{
				value: '7',
				text: '餐饮'
			},{
				value: '10',
				text: '加油费'
			}, {
				value: '11',
				text: '其他'
			}]);

			// 选择费用类型
			$('.mui-form-group').on('tap', '.mui-select-type', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				type.show(function(rs) {
					_span.html(rs[0].text);

					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}

					_input.val(rs[0].value);
				});
				return false;
			});

			// 表单提交点击事件
			$('.mui-form-group').on('tap', '.mui-btn', function() {			 
	            setTimeout(function() { 
	            	mui(this).button('reset');
	            	var date=document.getElementById("today").innerText;			        
			        var _id=$('input[name="_id"]').val();	      		            
		           	var rmbassume=$('input[name="rmbassume"]').val();
		           	if(rmbassume==""||rmbassume==null){
		           		rmbassume="1";
		           	}		           	
		           	var rmbgoal=$('input[name="rmbgoal"]').val();
		        	if(rmbgoal==""||rmbgoal==null){
		        		rmbgoal="0";
		           	}		        	
		           	var rmbtype=$('input[name="rmbtype"]').val();
		         	var rmb=$('input[name="rmb"]').val();
		         	
		         	if(rmb==null||rmb==""||rmbassume==""||rmbassume==null||rmbgoal==null||rmbgoal==""||rmbtype==null||rmbtype==""){
		         		mui.toast("信息不完整，请检查！",{ duration:'long', type:'div' });
		         		return false;
		         	}	            	
               $.post('cost', {_id:_id,date:date,rmbassume:rmbassume,rmbgoal:rmbgoal,rmbtype:rmbtype,rmb:rmb }, function(data) {						
					if(data.succeed == true) {						
						window.location.href = 'cost_history.html';
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