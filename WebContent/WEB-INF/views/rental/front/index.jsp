<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 -->
<!DOCTYPE html >
<html class="bg-eceef0">
<head>
<%@ include file="taglibs.jsp"%>
<title>安诺租车</title>
</head>
<style>
     .to{border-radius:50%} 
     
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
	//onchange事件  //图片上传
	function upperCase(x) {
		var y = document.getElementById(x).value
		document.getElementById("hid").value = y.toUpperCase()			
		$("#isnottext").text("图片已选中");
	}	
	
	
	
	//onchange事件  //时间
	function dt(x) {
		var y = document.getElementById(x).innerText
		document.getElementById("dt").value = y.dt(x)
	}
	
</script>
<body>
	<nav class="mui-bar mui-bar-tab">
		<a class="mui-tab-item mui-active" href="#tab-home"> <span
			class="mui-icon mui-icon-index"></span> <span
			class="mui-item-label f-s-12 m-t-7">首页</span>
		</a> <a class="mui-tab-item" href="#tab-train"> <span
			class="mui-icon mui-icon-train"> 
			<c:if test="${isnotnew eq 1 }">
					<span class="mui-badge f-s-9"
						style="top: -0.066667rem; margin-left: -15px;">new</span>
				</c:if>
		</span> <span class="mui-item-label f-s-12 m-t-7">学习和通知</span>
		</a> <a class="mui-tab-item" href="#tab-user"> <span
			class="mui-icon mui-icon-user"></span> <span
			class="mui-item-label f-s-12 m-t-7">我的</span>
		</a>
	</nav>
	<div class="mui-content">
		<!-- 首页 -->
		<div id="tab-home" class="mui-control-content p-b-50 mui-active">
			<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
				<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">首页</h1>
				<a href="transfer.html"
					class="mui-btn f-s-14 mui-btn-link mui-pull-right">${driver.driverCarnum}</a>
			</header>
			<div class="h-100 ft-333333 bg-ffffff p-l-40 p-r-30 pos-r">
				<div class="f-s-16 l-h-d-100 mui-ellipsis">每日例行保养</div>
				<a href="index_history.html"
					class="tab-home-button pos-a bg-2cad6e f-s-16">历史信息</a>
			</div>

			<div class="p-rl-45 m-t-50">

				<form id="form" class="form mui-form-group" method="post"
					enctype="multipart/form-data">
					<!-- 每日例行保养 -->
					<c:if test="${not empty model }">
						<input type="hidden" name="_id" value="${model.id }" />
					</c:if>
					<div class="m-t-30 mui-select mui-select-boult mui-select-date">
						<span class="ft-333333 f-s-16" id="today" onchange="dt(this.id)">
							<fmt:formatDate value="${model.driverDate }" pattern="yyyy-MM-dd" />
						</span> <input type="hidden" id="dt" name="date" value="" />
					</div>
					<div class="m-t-30 mui-checkbox">
						<span class="ft-333333 f-s-16">每日出行前维护</span> <input
							type="checkbox" name="clock" checked />
					</div>
					<div class="m-t-30 mui-right-input">
						<span class="ft-333333 f-s-16">请输入例保路码 </span>							
						<input class="f-s-16 ft-2cad6e" type="number" id="startmile"
							name="startmile"  			 
							<c:if test="${not empty zuotian }">value="<fmt:formatNumber type="number" pattern="0" value="${zuotian}" />"</c:if>
							value="<fmt:formatNumber type="number" pattern="0" value="${model.driverStartmile}" />" />								
					</div>
					<div class="m-t-35 f-s-16 ft-e32929">请于每月25日油箱加满后上传车辆路码表照片</div>
					<div class="m-t-12 mui-input-file ">
						<span class="ft-2cad6e f-s-16" id="isnottext">＋&nbsp;点击上传照片</span> 
						<input type="file" id="fid" name="filedata"  onchange="upperCase(this.id)" /> 
						<input type="hidden" name="isnot" id="hid"/>
					</div>
					<button type="submit"  onclick=""
						class="mui-btn mui-btn-block m-t-30 ft-ffffff f-s-16 bg-2cad6e">确&nbsp;&nbsp;认</button>
				</form>

				<div class="m-t-40 f-s-12 ft-333333 t-a-c">
					<div class="m-t-3">
						<img class="w-19 v-a-m" src="${files}/images/icon_checkbox.png"
							alt="安诺租车" /> <span class="v-a-m">&nbsp;我已仔细确认车辆情况，包括安全因素、</span>
					</div>
					<div class="m-t-3">轮胎状况及胎压、油量、雨刮器以及车内外单清洁情况</div>
				</div>
			</div>

			<ul class="mui-table-view m-t-40">
				<li class="mui-table-view-cell h-80 l-h-d-80"><a
					href="daily.html" class="mui-navigate-right p-rl-40"> <img
						class="v-a-m w-32 m-l-20" src="${files }/images/home_daily.png" />
						<span class="v-a-m m-l-35 f-s-16">日常记录</span>
				</a></li>
				<li class="mui-table-view-cell h-80 l-h-d-80"><a
					href="cheer.html" class="mui-navigate-right p-rl-40"> <img
						class="v-a-m w-32 m-l-20" src="${files }/images/home_cheer.png" />
						<span class="v-a-m m-l-35 f-s-16">加油记录</span>
				</a></li>
				<li class="mui-table-view-cell h-80 l-h-d-80"><a
					href="work.html" class="mui-navigate-right p-rl-40"> <img
						class="v-a-m w-32 m-l-20" src="${files }/images/home_work.png" />
						<span class="v-a-m m-l-35 f-s-16">加班记录</span>
				</a></li>
				<li class="mui-table-view-cell h-80 l-h-d-80"><a
					href="cost.html" class="mui-navigate-right p-rl-40"> <img
						class="v-a-m w-32 m-l-20" src="${files }/images/home_cost.png" />
						<span class="v-a-m m-l-35 f-s-16">费用记录</span>
				</a></li>
			</ul>
		</div>

		<!-- 培训 -->
		<div id="tab-train" class="mui-control-content p-b-50">
			<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
				<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">学习和通知</h1>
			</header>
			<div class="mui-scroll-wrapper pos-r hide">
				<div class="mui-scroll pos-r">
					<div class="mui-loading m-t-40">
						<div class="mui-spinner w-48 h-48"></div>
					</div>
				</div>
			</div>
			<ul class="mui-table-view bg-transparent">
				<c:forEach items="${records}" var="m">		   
					<c:if test="${ not empty m.recordTraintitle }">				
						<li class="mui-table-view-cell mui-media p-a-0 m-t-20 bg-ffffff">						     		
							<a class="h-220" href="detail.html?id=${m.id }"
							style="margin: 0px; padding: 0px;"> <img
								class="mui-pull-right h-220 w-220"
								src="${front}${m.recordTrainstartpic}" alt="" />
								<div class="mui-media-body p-tb-20 p-l-38 p-r-38">								        
									<h1  class="f-s-16 ft-333333 f-w-n mui-ellipsis">
									   <c:forEach items="${listpx}" var="px"> 
										<c:if test="${px.driverId eq m.id }">	 
											<span class="mui-icon"  > 
												<span class="mui-badge f-s-9" style="left:0px;top:0px;position: static;margin-left: 0px;">new</span>	
											</span>
										</c:if> 
							           </c:forEach> 			
									   ${m.recordTraintitle}
									</h1>
									<div class="m-t-9 f-s-10 ft-e32929">${m.recordTrainvicetitle}</div>
									<div class="h-80">
										<p  class="f-s-14 ft-808080  mui-ellipsis-2 l-h-d-40">${m.recordTrainbrief}</p>
									</div>
									<input class=" f-s-11 ft-2cad6e" value="了解详情">
								</div>
						</a>
						</li>
					</c:if>
			
				</c:forEach>

			</ul>
		</div>

		<!-- 我的 -->
		<div id="tab-user" class="mui-control-content p-b-50">
			<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
				<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">我的</h1>
			</header>
			<form id="forms" class="mui-form-group" enctype="multipart/form-data" method="post">
				<div class="h-180 bg-2cad6e pos-r">
					<div class="pos-a h-208 w-p-100 t-a-c" style="top: 0.48rem;">	
					<c:if test="${empty driver.driverHeadpic }">
					<img class="h-208 w-208 to " src="${files }/images/user.png" alt="" />
					</c:if>
					<c:if test="${ not empty driver.driverHeadpic }">
					<img class="h-208 w-208 to"  src="${front}${driver.driverHeadpic}" alt="" />
					</c:if>
					<input name="file" class="file" type="file"
						style="top: 0.48rem; left: 50%; height: 2.773333rem; width: 2.773333rem; padding: 0px; margin: 0px 0px 0px -1.386667rem; opacity: 0; border: none; z-index: 10; cursor: pointer; display: none;" 
						/>
					</div>
				</div>
             </form>
			<div class="m-t-84 f-s-14 ft-4d4d4d t-a-c">${driver.driverName}</div>
			<ul class="mui-table-view m-t-80">
				<li class="mui-table-view-cell h-80 l-h-d-80"><a
					href="change.html" class="mui-navigate-right p-rl-40"> <img
						class="v-a-m w-32 m-l-20" src="${files }/images/icon_change.png">
						<span class="v-a-m m-l-35 f-s-16">修改密码</span>
				</a></li>
				<li class="mui-table-view-cell h-80 l-h-d-80"><a
					href="help.html" class="mui-navigate-right p-rl-40"> <img
						class="v-a-m w-32 m-l-20" src="${files }/images/icon_help.png">
						<span class="v-a-m m-l-35 f-s-16">帮助中心</span>
				</a></li>
				<li class="mui-table-view-cell h-80 l-h-d-80"><a
					href="customer.html" class="mui-navigate-right p-rl-40"> <img
						class="v-a-m w-32 m-l-20" src="${files }/images/icon_customer.png">
						<span class="v-a-m m-l-35 f-s-16">联系客服</span>
				</a></li>
			</ul>
			<div class="p-rl-45 m-t-80">
				<form action="logout.html" method="post">
					<button type="submit"
						class="mui-btn mui-btn-block ft-e32929 f-s-16 btn_logout">退出登录</button>
				</form>
			</div>
		</div>
		<a class="shouye" href="index.html"></a>
	</div>
	<script type="text/javascript">
	 	$(function() {
	 			
			mui('.mui-form-group').on('tap', '.w-208', function() {
				//点击头像触发input框的点击事件
				$('.file').trigger("click");
			});
			mui('.mui-form-group').on('change', '.file', function() {
				var file = $(".file").val();
				var forms = $("#forms")[0];				
				var forms = new FormData(forms);
				$.ajax({
					url : "./headupload",
					type : "post",
					data : forms,
					cache : false,
					dataType:'JSON',
					processData : false,
					contentType : false,
					success : function(data) {
						if (data.code == 1)
							//$(".w-208").attr("src","${front}/"+data.result)
							window.location.href = './index.html';
					},
					error : function(e) {
						alert("错误！！");
						window.clearInterval(timer);
					}
				});
			});
		}); 
	
		$(function() {
			mui.init();
			//格式化日期
			Date.prototype.Format = function(fmt) {
				var o = {
					"M+" : this.getMonth() + 1, //月份 
					"d+" : this.getDate(), //日 
					"h+" : this.getHours(), //小时 
					"m+" : this.getMinutes(), //分 
					"s+" : this.getSeconds(), //秒 
					"q+" : Math.floor((this.getMonth() + 3) / 3),
					"S" : this.getMilliseconds()
				//毫秒 
				};
				if (/(y+)/.test(fmt))
					fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
							.substr(4 - RegExp.$1.length));
				for ( var k in o)
					if (new RegExp("(" + k + ")").test(fmt))
						fmt = fmt.replace(RegExp.$1,
								(RegExp.$1.length == 1) ? (o[k])
										: (("00" + o[k])
												.substr(("" + o[k]).length)));
				return fmt;
			}
			//若没选择 默认时间为当前时间
			if ($('input[name="_id"]').val() == null
					|| $('input[name="_id"]').val() == "") {
				document.getElementById("today").innerHTML = new Date().Format("yyyy-MM-dd");
			}

			//默认时间为当前值。
			document.getElementById("dt").value = document
					.getElementById("today").innerText;
		 	var today = new Date();		 
			var picker = new mui.DtPicker({
			    endDate: new Date(today.getFullYear(), today.getMonth(), today.getDate()   ), 
				type : 'date',
				//value: '2017-06-08',				
				labels : [ '年', '月', '日' ],
				buttons : [ '取消', '确定' ]
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
				//return false;
			});

			$('.mui-form-group').on('tap', '.mui-btn', function() {
				setTimeout(function() {
					mui(this).button('reset');					
					var form = new FormData(document.getElementById("form"));
					var smile = $('input[name="startmile"]').val();
					var clock;
					if ($('input[name="clock"]').is(':checked')) {
						clock = "1";
					} else {
						clock = "0";
					}
					if (clock == "0") {
						mui.toast("请确认出行维护！",{ duration:'long', type:'div' });
						return false;
					}
					if (smile == "" || smile == null||smile=="0") {
						mui.toast("请输入例行路码！",{ duration:'long', type:'div' });
						return false;
					}	
					
			
					$.ajax({
						url : "./indexedit",
						type : "post",
						data : form,
						cache : false,
						processData : false,
						contentType : false,
						success : function(data) {
						    
							if (data.code == "1") {	//添加
								document.getElementById("today").innerHTML = new Date().Format("yyyy-MM-dd");
							  
								$("#startmile").val("${zuotian}");	
								$("#hid").val(" ");
								mui.toast(data.message,{ duration:'8000'});									
							}
							if(data.code == "100"){ //编辑								
								 window.location.href="index_history.html";																
							}
							
							if (data.code == "0") {
								mui.toast(data.message,{ duration:'3000'});
							}
							if (data.code == "-1") {
								mui.toast(data.message,{ duration:'3000'});
							}
						},
						error : function(data) {
						
						}

					});
				}.bind(this), 2000);
				mui(this).button('loading');
			});

		});
	</script>
</body>
</html>