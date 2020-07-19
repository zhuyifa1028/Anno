/**
 * 工具类
 */
var utils = (function() {
	String.prototype.replaceAll = function (find, rep){
		var exp = new RegExp(find, "g");
		return this.replace(exp, rep);
	};
	return {
		alert : function(msg) {// 弹框
			if (!utils.isEmpty(msg)) {
				$.mobile.loading('show', {
					text : msg, // 加载器中显示的文字
					textVisible : true, // 是否显示文字
					theme : 'b', // 加载器主题样式a-e
					textonly : true, // 是否只显示文字
					html : "" // 要显示的html内容，如图片等
				});
			}
			setTimeout(utils.hide, 5000);
		},
		hide : function() {// 隐藏弹框
			$.mobile.loading('hide');
		},
		getJSON : function(option) {// GET方式获取JSON
			if(!utils.isEmpty(option)) {
				$.ajax({
					type: "GET",
					dataType: 'json',
					url: option.url,
					data: option.data,
					beforeSend: function() {
						$.mobile.loading('show', {
							text : '加载中...', // 加载器中显示的文字
							textVisible : true, // 是否显示文字
							theme : 'b', // 加载器主题样式a-e
							textonly : false, // 是否只显示文字
							html : '' // 要显示的html内容，如图片等
						});
					},
					success: function(data) {
						if(utils.isFunction(option.success)) {
							utils.hide();
							option.success.apply(this, arguments);
						} else {
							return false;
						}
					},
					error: function() {
						utils.hide();
						utils.alert("加载失败！");
					}
				});
			} else {
				utils.alert("请求参数错误");
			}
		},
		postJSON : function(option) {// POST方式获取JSON
			if(!utils.isEmpty(option)) {
				$.ajax({
					type: "POST",
					dataType: 'json',
					url: option.url,
					data: option.data,
					beforeSend: function() {
						$.mobile.loading('show', {
							text : '加载中...', // 加载器中显示的文字
							textVisible : true, // 是否显示文字
							theme : 'b', // 加载器主题样式a-e
							textonly : false, // 是否只显示文字
							html : '' // 要显示的html内容，如图片等
						});
					},
					success: function(data) {
						if(utils.isFunction(option.success)) {
							utils.hide();
							option.success.apply(this, arguments);
						} else {
							return false;
						}
					},
					error: function() {
						utils.hide();
						utils.alert("加载失败！");
					}
				});
			} else {
				utils.alert("请求参数错误");
			}
		},
		getDefaultValue : function(val, def) {
			if(!utils.isEmpty(val)) {
				return val;
			} else if(!utils.isEmpty(def)) {
				return def;
			} else {
				return "";
			}
		},
		isEmpty : function(obj) {// 判断是否为空(juicer)
			var obj = obj || '';
			if (!obj) {
				return true;
			} else {
				return false;
			}
		},
		isFunction : function(fn) {// 判断是否为函数
			return (!!fn && !fn.nodename && fn.constructor!=String && fn.constructor!=RegExp && fn.constructor!=Array && /function/i.test(fn+""));
		},
		number : {// 数值工具类
			getNumberFormat : function(num) {// 获取格式化数值
				if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(num)) {
					return num;
				}
				var a = RegExp.$1,b = RegExp.$2,c = RegExp.$3;
				var re = new RegExp().compile("(\\d)(\\d{3})(,|$)");
				while(!!re && re.test(b)){
					b = b.replace(re,"$1,$2$3");
				}
				return a +""+ b +""+ c;  
			}
		},
		date : {// 日期工具类
			getDateByDay : function(sdate, edate) {// 获取日期相差的天数
				var days = edate.getTime() - sdate.getTime();
				return Math.ceil(days / (1000 * 60 * 60 * 24));
			},
			getDateByHours : function(sdate, edate) {// 获取日期相差的小时
				var days = edate.getTime() - sdate.getTime();
				return Math.ceil(days / (1000 * 60 * 60));
			},
			getDateByMinutes : function(sdate, edate) {// 获取日期相差的分钟
				var days = edate.getTime() - sdate.getTime();
				return Math.ceil(days / (1000 * 60));
			},
			getDateBySeconds : function(sdate, edate) {// 获取日期相差的秒钟
				var days = edate.getTime() - sdate.getTime();
			    var seconds=Math.ceil(days / 1000);
			    if(seconds <= 0){
			    	seconds=1;
			    }
				return seconds;
			},
			getDateFormat : function(date, format) {// 获取格式化日期
				if(utils.isEmpty(date)) {
					return '';
				}
				if(!(date instanceof Date)) {
					date = new Date(date);
				}
				var str = {
					"M+" : date.getMonth()+1, // month
					"d+" : date.getDate(),    // day
					"h+" : date.getHours(),   // hour
					"m+" : date.getMinutes(), // minute
					"s+" : date.getSeconds(), // second
					"q+" : Math.floor((date.getMonth()+3)/3),  // quarter
					"S" : date.getMilliseconds() // millisecond
				};
				
				var week = {
					"0" : "星期天",
					"1" : "星期一",
					"2" : "星期二",
					"3" : "星期三",
					"4" : "星期四",
					"5" : "星期五",
					"6" : "星期六"
				}; 
				
				if(/(y+)/.test(format)) {
					format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4- RegExp.$1.length));
				}
				
				if(/(E+)/.test(format)) {
					format = format.replace(RegExp.$1, week[date.getDay() + ""]);
				}
				
				for(var k in str) {
					if(new RegExp("(" + k + ")").test(format)) {
						format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? str[k] : ("00"+ str[k]).substr((""+ str[k]).length));
					}
				}
				
				return format;
			},
			getWeekByDay : function(date) {// 获取周一时间
				var d = date;
				if(!(date instanceof Date)) {
					d = new Date(date);
				}
				var week = d.getDay();
				var day = d.getDate();
				return d.setDate(day - week);
			},
			add : function(date, type, num) {// 日期添加
				if(!(date instanceof Date)) {
					date = new Date(date);
				}
				if(type === 'day') {
					return date.setDate(date.getDate() + num);
				} else if(type == 'hour') {
					return date.setHours(date.getHours() + num);
				} else {
					return date;
				}
			}
		},
		html : {// html工具类
			getElementOffset : function(e) {// 获取元素位置
				var t = e.offsetTop;
				var l = e.offsetLeft;
				var w = e.offsetWidth;
				var h = e.offsetHeight;
				
				while(e=e.offsetParent) {
					t += e.offsetTop;
					l += e.offsetLeft;
				}
				
				return {
					top : t,
					left : l,
					width : w,
					height : h
				}
			}
		},
		des : {// des加密工具
			/**加密*/
			encrypt : function(msg, key) {
				try {
					var keyHex = CryptoJS.enc.Utf8.parse(key);
				    var encrypted = CryptoJS.DES.encrypt(msg, keyHex, {
				        mode: CryptoJS.mode.ECB,
				        padding: CryptoJS.pad.Pkcs7
				    });
				    
				    var str =  encrypted.toString();
				    str = str.replaceAll("\\+","[s]");
				    str = str.replaceAll("/","[y]");
				    str = str.replaceAll("=","[l]");
				    str = str.replaceAll(" ","");
				    return str;
				} catch(e) {
					return "";
				}
			},
			/**解密*/
			decrypt : function(msg, key) {
				ciphertext = ciphertext.replaceAll("[s]","\\+");
				ciphertext = ciphertext.replaceAll("[y]","/");
				ciphertext = ciphertext.replaceAll("[l]","=");
				ciphertext = ciphertext.replaceAll(" ","");
				
			    var keyHex = CryptoJS.enc.Utf8.parse(key);
			    var decrypted = CryptoJS.DES.decrypt({
			        ciphertext: CryptoJS.enc.Base64.parse(msg)
			    }, keyHex, {
			        mode: CryptoJS.mode.ECB,
			        padding: CryptoJS.pad.Pkcs7
			    });
			 
			    return decrypted.toString(CryptoJS.enc.Utf8);
			}
		}
	}
})();