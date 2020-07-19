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
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff" style="line-height:40px">加班记录</h1>
		<a href="work_history.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right">历史记录</a>
	</header>
	<div class="mui-content">
		<div class="p-rl-45 m-t-50">
			<form class="mui-form-group" method="post">
			      <!-- 加班记录编辑 -->
				 <c:if test="${not empty model }">
				       <input type="hidden" name="_id" value="${model.id }" />
				 </c:if>
				<div class="m-t-30 mui-select mui-select-boult mui-select-date">
					<span class="ft-333333 f-s-16" id="today" >
					      <fmt:formatDate value="${model.driverDate }" pattern="yyyy-MM-dd" />
					</span>
					<input type="hidden" name="datefa" />
				</div>
				
				<c:set var="title" value="${fn:split(model.driverOvertimehours, '.') }" />
				<div class="m-t-30 mui-select mui-select-boult mui-select-hour">				    
					<span class="ft-999999 f-s-16" id="datehh" >					
					<c:if test="${empty model.driverOvertimehours }">请选择加班小时</c:if>
					<c:if test="${ not empty model.driverOvertimehours }">${title[0] }小时</c:if>						  
					</span>
					<input type="hidden" name="datehh" value="" />
				</div>
				<div class="m-t-30 mui-select mui-select-boult mui-select-minute">
				    <span class="ft-999999 f-s-16" id="datemin">
						<c:if test="${empty model.driverOvertimehours }">请选择加班分钟</c:if>
						<c:if test="${not empty model.driverOvertimehours }">						
							<c:if test="${fn:contains(title[1],'0')}">
									<c:set var="titles" value="${fn:split(title[1], '0') }"></c:set>
									<c:if test="${ empty titles[0] }"> 
									请选择加班分钟   
									</c:if>
									<c:if test="${not empty titles[0] }">
									${titles[0] }分钟
									</c:if>    
							</c:if>											
							<c:if test="${!fn:contains(title[1],'0')}">													         		
									<c:if test="${fn:length(title[1]) eq 1}">
									${title[1] }0分钟
									</c:if>
									<c:if test="${fn:length(title[1]) eq 2}">
									${title[1] }分钟
									</c:if>
							</c:if>							
						</c:if>			
					</span>
					<input type="hidden" name="datemin" value="" />
				</div>
				
				<div class="m-t-30 mui-select mui-select-boult mui-ellipsis mui-select-type">
					<span class="ft-999999 f-s-16" >					
						<c:if test="${model.driverOvertimetype eq 0 }">工作日 </c:if>
					    <c:if test="${model.driverOvertimetype eq 1 }">节假日 </c:if>
					    <c:if test="${model.driverOvertimetype eq 2 }">周末 </c:if>
					    <c:if test="${  empty model.driverOvertimetype }">
					                  请选择加班信息 
					       <em class="f-s-12">(工作日、<i class="ft-e9c239">节假日  </i>、<i class="ft-e93939">周末</i>)</em> 
					    </c:if>
					</span>
					<input type="hidden" name="overtimetype" value="${model.driverOvertimetype  }" />
				</div>
				
				<div class="m-t-30 mui-select mui-select-boult mui-select-why">		
					<span class="ft-999999 f-s-16"  id="usecar">
						<c:if test="${model.driverOvertimeusecar eq 0 }">个人原因 </c:if>
						<c:if test="${model.driverOvertimeusecar eq 1 }">商务原因</c:if>
						<c:if test="${ empty model.driverOvertimeusecar}">商务原因</c:if>
					</span>
					<input type="hidden" name="overtimeusecar" value="${model.driverOvertimeusecar }" />
				</div>
				
				<div class="m-t-30 mui-input">
					<input  class="f-s-16" type="number" name="overtimermbtotal" 
						<c:set var="title" value="${fn:split(model.driverOvertimermbtotal, '.')}" />	
						<c:if test="${title[1] eq 0}">value="${title[0]}.00"</c:if>
						<c:if test="${fn:length(title[1]) eq 1}">value="${model.driverOvertimermbtotal}0"</c:if>							 
						<c:if test="${fn:length(title[1]) eq 2}">value="${model.driverOvertimermbtotal}"</c:if> 	  
					    placeholder="请输入加班总金额" />
				</div>
				<button class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e">完&nbsp;&nbsp;成</button>
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
				endDate: new Date(today.getFullYear(), today.getMonth(), today.getDate()),	
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

			var hour = new mui.PopPicker();
			var _hour = [];
			for(var i = 0; i < 25; i++) {
				_hour.push({
					value: i,
					text: i + '小时'
				});
			}
			hour.setData(_hour);

			// 选择加班小时
			$('.mui-form-group').on('tap', '.mui-select-hour', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				hour.show(function(rs) {
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}

					if(rs[0].value == '0' && _span.hasClass('ft-333333')) {
						_span.removeClass('ft-333333').addClass('ft-999999');
						_span.html('请选择加班小时');
					} else {
						_span.html(rs[0].text);
					}
					_input.val(rs[0].value);
				});
				return false;
			});

			var minute = new mui.PopPicker();
			var _minute = [];
			for (var i = 0; i < 61; i++) {
				_minute.push({
					value: i,
					text: i + '分钟'
				});
			}
			minute.setData(_minute);

			// 选择加班小时
			$('.mui-form-group').on('tap', '.mui-select-minute', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				minute.show(function(rs) {
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}

					if(rs[0].value == '0' && _span.hasClass('ft-333333')) {
						_span.removeClass('ft-333333').addClass('ft-999999');
						_span.html('请选择加班分钟');
					} else {
						_span.html(rs[0].text);
					}
					_input.val(rs[0].value);
				});
				return false;
			});

			var type = new mui.PopPicker();
			type.setData([{
				value: '0',
				text: '工作日'
			}, {
				value: '1',
				text: '节假日'
			}, {
				value: '2',
				text: '周末'
			}]);

			// 选择加班信息
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

			var why = new mui.PopPicker();
			why.setData([{
				value: '0',
				text: '个人原因'
			}, {
				value: '1',
				text: '商务原因'
			}]);

			// 选择加班原因
			$('.mui-form-group').on('tap', '.mui-select-why', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				why.show(function(rs) {
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
		            	var date= document.getElementById("today").innerText;	
		            	var _id=$('input[name="_id"]').val();	
		            	  
		            	     var datehh=document.getElementById("datehh").innerText;
		            	     var datemin=document.getElementById("datemin").innerText;			            	    
  	     
			            	 if(datehh=="请选择加班小时"&&datemin=="请选择加班分钟"){
				            		mui.toast("请选择加班时间",{ duration:'long', type:'div' });
				            		return false;
				             }	     
		            	     if(datehh=="请选择加班小时"){
		            	    	 datehh="0";		            	    
			            	 }else{	 
			            		 datehh=datehh.substring(0,datehh.length-2);	 			            	     
			            	 }			            	        
		            	     if(datemin=="请选择加班分钟"){
		            	    	 datemin="0";		            	    
			            	 }else{	 
			            		 datemin=datemin.substring(0,datemin.length-2); 			            	     
			            	 }	
		            	     //判断分钟是否小于0.
		            	     if(Number(datemin)<10){
		            	    	datemin="0"+datemin; 		            	    
		            	     }			
	            	     
		            	var overtimehours=datehh+"."+datemin;		            	
		            	var overtimetype=$('input[name="overtimetype"]').val();
		            	var overtimeusecar=$('input[name="overtimeusecar"]').val();
		            	if(overtimeusecar==""||overtimeusecar==null){
		            		 overtimeusecar="1";
		            	}		            	
		            	var overtimermbtotal=$('input[name="overtimermbtotal"]').val();		               		            
		                if(overtimetype==""||overtimetype==null||overtimeusecar==""||overtimeusecar==null||overtimermbtotal==""||overtimermbtotal==null){		             	
		                	mui.toast("信息不完整，请检查！",{ duration:'long', type:'div' });
		                	return false;
		                }          	
		                $.post('work', {_id:_id,date:date,overtimehours:overtimehours,overtimetype:overtimetype,overtimeusecar:overtimeusecar,overtimermbtotal:overtimermbtotal }, function(data) {						
							if(data.succeed == true) {						
								window.location.href = 'work_history.html';
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