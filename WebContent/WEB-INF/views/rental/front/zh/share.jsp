<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-eceef0">
<head>
    <%@ include file="../taglibs.jsp"%>>
    <title>安诺租车</title>
</head>

<body>
	<header class="mui-bar mui-bar-nav p-rl-30 h-74 bg-2cad6e">
		<h1 class="mui-title f-s-16 l-h-d-74 ft-ffffff">我要分享</h1>
		<a href="../en/share.html" class="mui-btn f-s-14 mui-btn-link mui-pull-right mui-en-zh">En</a>
	</header>
	<div class="mui-content">
		<img class="m-t-110 m-lr-auto d-b w-p-32" src="${files }/images/login.png" alt="" />
		<div class="m-t-100 f-s-14 ft-333333 t-a-c">喜欢安诺租车的朋友们</div>
		<div class="f-s-14 ft-333333 t-a-c">可以把公众号分享给您的小伙伴哦！</div>
		<div class="p-rl-45">
			<button class="mui-btn mui-btn-block m-t-150 ft-ffffff f-s-16 bg-2cad6e">确认提交</button>
		</div>
	</div>
	<div class="mui-share-mark hide">
		<div class="w-p-100 h-p-100 bg-000000"></div>
		<img src="${files }/images/share_zh_title.png"/>
	</div>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
		$(function() {
			mui.init();
			var title = "安诺久通公司介绍";
			var desc = "安诺久通汽车租赁有限公司是Arval（法巴安诺）和上海巴士汽车租赁的合资公司。法巴安诺为法国巴黎银行全资子公司，是欧洲全服务，经营性车辆租赁市场领导者。上海巴士汽车租赁是上海地区最大的经营性车辆租赁公司之一，隶属于上海强生控股股份有限公司。"
			//var url = 'http://mp.weixin.qq.com/s?__biz=MjM5ODQxMDY0OQ==&mid=309808253&idx=1&sn=e569e4c322dd3636729011900409d159&chksm=3148cc8b063f459dd7bca0b272f1ac9fcc84ef5bac257a2e02837e09908c0334a64411625e88&mpshare=1&scene=1&srcid=1010sYkOPp6JAAzNzV4syZ1m'; // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			var img = "http://"+window.location.host+"/${files }/images/share_icon.jpg";
			var url = "http://"+window.location.host+"/shareurl.html";
			console.log("${front}")
			$.ajax({
				type: "post",
				url: "${front}/share",//获取微信定位需要的参数
				dataType: "json",
				success: function (data) {
					var param = data.result;console.log(data)
					if(data.code == 1){
						wx.config({   //配置公众号 
						    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。    
						    appId: param.appId, // 必填，公众号的唯一标识    
						    timestamp: param.timeStamp , // 必填，生成签名的时间戳    
						    nonceStr: param.nonceStr, // 必填，生成签名的随机串    
						    signature: param.signature,// 必填，签名，见附录1    
						    jsApiList: ['checkJsApi',  
								    	'onMenuShareTimeline', 
								    	'onMenuShareAppMessage', 
								    	'onMenuShareQQ', 
								    	'onMenuShareQZone'
						               ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2    
						});  
						wx.ready(function(){ 
							wx.onMenuShareTimeline({
								  title:title, // 分享标题
								    link: url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
								    imgUrl: img, // 分享图标
							    success: function () { 
							        alert("分享成功")
							    },
							    cancel: function () { 
							    	alert("用户取消")
							    }
							});
							
							wx.onMenuShareAppMessage({
							    title: title,
							    desc: desc,
							    link: url,
							    imgUrl: img,
							    type: '', // 分享类型,music、video或link，不填默认为link
							    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
							    success: function () { 
							    	 alert("分享成功")
							    },
							    cancel: function () { 
							    	alert("用户取消")
							    }
							});
							
							wx.onMenuShareQQ({
								title: title,
							    desc: desc,
							    link: url,
							    imgUrl: img,
							    success: function () { 
							    	 alert("分享成功")
							    },
							    cancel: function () { 
							    	alert("用户取消")
							    }
							});
							
							
							wx.onMenuShareQZone({
								title: title,
							    desc: desc,
							    link: url,
							    imgUrl: img,
							    success: function () { 
							    	alert("分享成功")
							    },
							    cancel: function () { 
							    	alert("用户取消")
							    }
							});
						 })
					}
				},
				error: function (data) {
					console.log(data)
				}
			});
			$('.mui-content').on('tap', '.mui-btn', function() {
				$('.mui-share-mark.hide').removeClass('hide');
			});

			$('.mui-share-mark').on('tap', 'div', function() {
				$('.mui-share-mark').addClass('hide');
			});
		});
	</script>
</body>
</html>