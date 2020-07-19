<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>
    <title>安诺租车</title>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">Convert&nbsp;your&nbsp;Message</h1>
		<a href="../zh/transport.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">中</a>
	</header>
	<div class="mui-content">
		<div class="bg-ffffff p-rl-75 p-tb-20">
			<div class="mui-segmented-control mui-segmented-green b-r-none">
				<a class="mui-control-item mui-active f-s-12" href="#not">Rugular Pickup</a>
				<a class="mui-control-item f-s-12" href="#has">Airport/Train station<br />Pickup</a>
			</div>
		</div>
		<div class="m-t-50">
			<div id="not" class="mui-control-content mui-active p-rl-45">
				<form class="mui-form-group" method="post" action="trip.html">
				    <input type="hidden" name="id" value="0"/>
					<div class="m-t-30 mui-select mui-select-boult mui-select-date">
						<span data-options='{"type":"date","beginYear":2017,"endYear":2022}' class="ft-999999 f-s-16">Date</span>
						<input type="hidden" name="servicedate" />
					</div>
					<div class="m-t-30 mui-select mui-select-boult mui-select-dtime">
						<span data-options='{"type":"time"}' class="ft-999999 f-s-16">Time</span>
						<input type="hidden" name="servicetime" />
					</div>
					<div class="m-t-30 mui-input pos-r">
						<span class="mui-input-icon mui-input-position">
							<%-- <img class="w-p-100" src="${files }/images/icon_position.png" alt="" onclick="location.href='http://m.amap.com'"/> --%>
						</span>
						<div class="mui-input-text">
							<input class="f-s-16" type="text" name="servicecontent" placeholder="Pickup location" />
						</div>
					</div>
					<button class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e" type="submit">Generate a message in CN</button>
				</form>
			</div>

			<div id="has" class="mui-control-content p-rl-45">
				<form class="mui-form-group" method="post" action="trip.html">
				    <input type="hidden" name="id" value="1"/>
					<div class="m-t-30 mui-select mui-select-boult mui-select-tdate">
						<span data-options='{"type":"date","beginYear":2017,"endYear":2022}' class="ft-999999 f-s-16">Date</span>
						<input type="hidden" name="servicedate" />
					</div>
					<div class="m-t-30 mui-select mui-select-boult mui-select-ttime">
						<span data-options='{"type":"time"}' class="ft-999999 f-s-16">Time</span>
						<input type="hidden" name="servicetime" />
					</div>
					<!-- <div class="m-t-30 mui-select mui-select-boult mui-select-trainorplane">
						<span class="ft-999999 f-s-16">Airport/Train station</span>
						<input type="hidden" name="futrain" />
					</div> -->
					<div id="showCityPicker" class="m-t-30 mui-select mui-select-boult mui-select-city">
						<span class="ft-999999 f-s-16">station/location</span>
						<input id="cityResult" type="hidden" name="fucity" />
					</div>
					<!-- <div class="m-t-30 mui-select mui-select-boult mui-select-position">
						<span class="ft-999999 f-s-16">Pickup location</span>
						<input type="hidden" name="fusite" />
					</div> -->
					<div class="m-t-30 mui-input">
						<input class="f-s-16" type="text" name="servicecontent" placeholder="Flight/Train number" />
					</div>
					<button class="mui-btn mui-btn-block m-t-80 ft-ffffff f-s-16 bg-2cad6e" type="submit">Generate a message in CN</button>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			mui.init();
			var year=new Date().getFullYear() ; 
			var month=new Date().getMonth()+1; 
			var day=new Date().getDate();
			var hours=new Date().getHours();       
			var minutes=new Date().getMinutes(); 
			
			var date = new mui.DtPicker({
				type: 'date',
				beginYear:year,
				beginMonth:month,
				beginDay:day,
				labels: ['Year', 'Month', 'Day'],
				buttons: ['Cancel', 'Confirm']
			});
			// 选择日期控件
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

			var time = new mui.DtPicker({
				type: 'time',
				Year:year,
				Month:month,
				Day:day,
				Hours:hours,
				Minutes:minutes,
				labels: ['Year', 'Month', 'Day', 'Hour', 'Minute'],
				buttons: ['Cancel', 'Confirm']
			});
			// 选择时间控件
			$('.mui-form-group').on('tap', '.mui-select-dtime', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				time.show(function(rs) {
					_span.html(rs.text);
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs.text);
				});

				return false;
			});

			var tdate = new mui.DtPicker({
				type: 'date',
				beginYear:year,
				beginMonth:month,
				beginDay:day,
				labels: ['Year', 'Month', 'Day'],
				buttons: ['Cancel', 'Confirm']
			});
			// 选择日期控件
			$('.mui-form-group').on('tap', '.mui-select-tdate', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				tdate.show(function(rs) {
					_span.html(rs.text);
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs.text);
				});

				return false;
			});

			var ttime = new mui.DtPicker({
				type: 'time',
				Year:year,
				Month:month,
				Day:day,
				Hours:hours,
				Minutes:minutes,
				labels: ['Year', 'Month', 'Day', 'Hour', 'Minute'],
				buttons: ['Cancel', 'Confirm']
			});
			// 选择时间控件
			$('.mui-form-group').on('tap', '.mui-select-ttime', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				ttime.show(function(rs) {
					_span.html(rs.text);
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs.text);
				});

				return false;
			});

			var trainorplane = new mui.PopPicker({
				buttons: ['Cancel', 'Confirm']
			});
			trainorplane.setData([{
				value: '0',
				text: 'Airport'
			}, {
				value: '1',
				text: 'Train'
			}]);
			// 选择航班或者火车
			$('.mui-form-group').on('tap', '.mui-select-trainorplane', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');

				trainorplane.show(function(rs) {
					_span.html(rs.text);
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs.text);
				});

				return false;
			});
			
			//选择城市和上车地点
			var cityPicker = new mui.PopPicker({
				buttons: ['Cancel', 'Confirm'],
				layer: 3
			});
			var a=${AREA_MAPS};
			cityPicker.setData(a);
			var cityResult = document.getElementById('cityResult');
			$('.mui-form-group').on('tap', '.mui-select-city', function() {
				var _this = $(this);
				var _input = _this.find('input[type="hidden"]');
				var _span = _this.find('span');
				cityPicker.show(function(rs) {
					if(rs[2].text==null){
						   rs[2].text="";
						}
					_span.html(rs[0].text+" "+rs[1].text+" "+rs[2].text);
					if(_span.hasClass('ft-999999')) {
						_span.removeClass('ft-999999').addClass('ft-333333');
					}
					_input.val(rs[0].text+" "+rs[1].text+" "+rs[2].text);
				});
				return false;
			});

		});
	</script>
</body>
</html>