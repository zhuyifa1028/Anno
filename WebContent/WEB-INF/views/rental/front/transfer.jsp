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
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">换车信息</h1>
	</header>
	<div class="mui-content">
		<div class="p-rl-45 m-t-50">
			<form class="mui-form-group" method="post">
			    <div class="m-t-30 mui-select mui-select-boult mui-select-date">
					<span class="ft-333333 f-s-16" id="today"  >
					   <fmt:formatDate value="${model.driverDate }" pattern="yyyy-MM-dd" />		
					</span>
					<input type="hidden" name="datefa" value="${model.driverDate }" />
				</div>
								
				<div class="m-t-30 mui-select mui-select-boult mui-select-times" >
						<span class="ft-999999 f-s-16" id="time">
						   <c:if test="${ empty model.driverBackdate }" >请选择换车时间</c:if>
						   <fmt:formatDate value="${model.driverBackdate }" pattern="HH:mm" />	
						</span>
						<input type="hidden" name="${model.driverDate }" />
				</div>
			    
				<div class="m-t-30 mui-select mui-select-boult mui-select-why">
					<span class="ft-999999 f-s-16" >请选择更换车辆原因</span>
					<input type="hidden" name="reason" />
				</div>				
				<div class="m-t-30 mui-input">			  
					<input class="f-s-16 ft-333333" readonly="readonly" type="text" name="jcarnum" placeholder="" value="${modelstransfer.driverCarnum }" />
				</div>
				<div class="m-t-30 mui-input">
					<input class="f-s-16 " type="number" name="jcarmile"  value="" placeholder="请输入原车辆公里数" />
				</div>
				<div class="m-t-30 mui-input">
					<input class="f-s-16 " type="text" name="newcarnum"  value="" placeholder="请输入换车后车牌号，字母请大写" />
				</div>
				<div class="m-t-30 mui-input">
					<input class="f-s-16 " type="number" name="newcarmile" value="" placeholder="请输入更换后车辆公里数" />
				</div>
				<button class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e">确&nbsp;&nbsp;认</button>
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
			var date = new mui.DtPicker({	
				endDate: new Date(today.getFullYear(), today.getMonth(), today.getDate()   ),	
				type: 'date',
				//value: '',
				labels: ['年', '月', '日'], 
				buttons: ['取消', '确定']
			});
					
			
			// 选择日期控件
			$('.mui-form-group').on('tap', '.mui-select-date', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				date.show(function(rs) {
					_span.html(rs.text);
					_input.val(rs.text);
				});

				return false;
			});

			
			var dates = new mui.DtPicker({					
				//endDate: new Date(today.getFullYear(), today.getMonth(), today.getDate(),today.getHours(),today.getMinutes()   ),	
				type: 'time',					
				labels: ['年', '月', '日', '时', '分'],
				buttons: ['取消', '确定']	
			});
			// 选择时间控件
			$('.mui-form-group').on('tap', '.mui-select-times', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				dates.show(function(rs) {
					_span.html(rs.text);
					
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs.text);
				});

				return false;
			});
			
			
			
			var why = new mui.PopPicker();
			why.setData([{
				value: '0',
				text: '维修保养'
			}, {
				value: '1',
				text: '事故'
			}, {
				value: '2',
				text: '处理罚单'
			}, {
				value: '3',
				text: '合同到期'
			}, {
				value: '4',
				text: '车况不佳'
			}]);

			    // 选择换车原因
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
						  
					//return false;
				    }); 
				});
				    
			 // 表单提交点击事件
				$('.mui-form-group').on('tap', '.mui-btn', function() {	
					   
					   setTimeout(function() {
						   mui(this).button('reset');	
						    var date=document.getElementById("today").innerText;
			            	var time=document.getElementById("time").innerText;
			            	var dates=new Date().Format("yyyy-MM-dd");				            				            
			            	if(document.getElementById("time").innerText=="请选择换车时间"){
				            	mui.toast("数据不完整，请检查！",{ duration:'long', type:'div' });
				                return  false;
			            	}				            	
			               date=date+time;				              
						   var reason=$('input[name="reason"]').val();						   
			               var jcarnum=$('input[name="jcarnum"]').val();
				           var jcarmile=$('input[name="jcarmile"]').val();	                
				           var newcarnum=$('input[name="newcarnum"]').val();
				           var newcarmile=$('input[name="newcarmile"]').val();
				  	       if(newcarmile==null||newcarmile==""||reason==null||reason==""||jcarnum==null||jcarnum==""||jcarmile==null||jcarmile==""||newcarnum==null||newcarnum==""){
				  	            mui.toast("数据不完整，请检查！",{ duration:'long', type:'div' });
				  	            return false;
				  	        }
				  	       
				  	   /*   判断车牌号是否已存在
				  	   $.post('transfercarnum',{newcarnum:newcarnum}, function(data) {						
								if(data.succeed == true) {									
									
									  $.post('transfer',{date:date,newcarmile:newcarmile,reason:reason,jcarnum:jcarnum,jcarmile:jcarmile,newcarnum:newcarnum}, function(data) {						
											if(data.succeed == true) {									
												window.location.href = 'index.html';
											}  else {	
												mui.toast(data.message,{ duration:'long', type:'div' });							
											} 
										});	
									
									
								}  else {	
									mui.toast(data.message,{ duration:'long', type:'div' });							
								} 
							});	  */
				  	       
				  	     $.post('transfer',{date:date,newcarmile:newcarmile,reason:reason,jcarnum:jcarnum,jcarmile:jcarmile,newcarnum:newcarnum}, function(data) {						
								if(data.succeed == true) {									
									window.location.href = 'index.html';
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