var bear = new mui.PopPicker();
bear.setData([ 
//{
//	value : '0',
//	text : '合同或车队经理关系'
//}, {
//	value : '1',
//	text : '报价'
//}, {
//	value : '2',
//	text : '订单'
//}, {
//	value : '3',
//	text : '采购和交付'
//}, 
{
	value : '4',
	text : '保养和维修'
 }, 
//{
//	value : '5',
//	text : '轮胎'
//}, {
//	value : '6',
//	text : '路救'
//}, {
//	value : '7',
//	text : '燃油'
//}, {
//	value : '8',
//	text : '车损或保险'
//}, {
//	value : '9',
//	text : '替代/机动/新车替代'
//}, {
//	value : '10',
//	text : '罚款或路桥费*'
//}, {
//	value : '11',
//	text : '还车'
//}, {
//	value : '12',
//	text : '开票'
//}, {
//	value : '13',
//	text : '报告'
//}, {
//	value : '14',
//	text : '车载信息系统'
//},
{
	value : '15',
	text : '司机服务'
}
//		, {
//	value : '16',
//	text : '旧车销售'
//}, {
//	value : '17',
//	text : '行政管理/数据质量'
//}, {
//	value : '18',
//	text : '联系'
//}, {
//	value : '19',
//	text : '信用风险'
//}, {
//	value : '20',
//	text : '数字（网页/手机应用）'
//}
, {
	value : '21',
	text : '车辆'
}, {
	value : '22',
	text : '网站主页'
}, {
	value : '23',
	text : '其它'
} ]);



var business = new mui.PopPicker();
business.setData([{
	value: '0',
	text: '合同延迟'
}, {
	value: '1',
	text: '在客户收到回复前有延迟'
}, {
	value: '2',
	text: '交流不充分'
}, {
	value: '3',
	text: '没显示兴趣'
}, {
	value: '4',
	text: '太长时间没有办理完'
}, {
	value: '5',
	text: '无法联系上联系人'
}, {
	value: '6',
	text: '收费上的争议/奖励'
}, {
	value: '7',
	text: '客户会议'
}, {
	value: '8',
	text: '关系差'
}]);

var a = new mui.PopPicker();
a.setData([{
	value: "120101",
	text: "延迟回复报价"
}, {
	value: "120102",
	text: "报价错误"
}, {
	value: "120103",
	text: "错误的车辆"
}, {
	value: "120104",
	text: "给出不充分的/很少的意见"
}, {
	value: "120105",
	text: "关系差"
}]);
var chao = new mui.PopPicker();
chao.setData([{
	value: "130100",
	text: "错误的合同调整"
}, {
	value: "130200",
	text: "给出不充分的/很少的意见"
}, {
	value: "130300",
	text: "关系差"
}]);
var c = new mui.PopPicker();
c.setData([{
	value: "140100",
	text: "交付延迟"
}, {
	value: "140200",
	text: "缺乏沟通"
}, {
	value: "140300",
	text: "交付流程不清晰"
}, {
	value: "140400",
	text: "交付错误的车辆"
}, {
	value: "140500",
	text: "牌照问题"
}, {
	value: "140600",
	text: "配件不全/丢失"
}, {
	value: "140700",
	text: "交付上的问题"
}, {
	value: "140800",
	text: "收费上的争议"
}, {
	value: "140900",
	text: "关系差"
}, {
	value: "141000",
	text: "次新车状态（很脏…）"
}]);
var d = new mui.PopPicker();
d.setData([{
	value: "150100",
	text: "技术问题/保养"
}, {
	value: "150200",
	text: "车辆不清洁"
}]);
var e = new mui.PopPicker();
e.setData([{
	value: "210100",
	text: "轮胎装配服务差"
}, {
	value: "210200",
	text: "提供的信息少"
}, {
	value: "210300",
	text: "关系差"
}]);
var f = new mui.PopPicker();
f.setData([{
	value: "220100",
	text: "长的等待时间"
}, {
	value: "220200",
	text: "在提供服务上有延迟"
}, {
	value: "220300",
	text: "收费争议"
}, {
	value: "220400",
	text: "关系差"
}]);
var g = new mui.PopPicker();
g.setData([{
	value: "230100",
	text: "延迟交付燃油卡"
}, {
	value: "230200",
	text: "不正确的燃油卡"
}, {
	value: "230300",
	text: "燃油卡不工作"
}, {
	value: "230400",
	text: "使用未订购的服务"
}, {
	value: "230500",
	text: "收费争议"
}, {
	value: "230600",
	text: "关系差"
}]);
var h = new mui.PopPicker();
h.setData([{
	value: "240100",
	text: "流程不清晰"
}, {
	value: "240200",
	text: "收费争议"
}, {
	value: "240300",
	text: "事故材料"
}, {
	value: "240400",
	text: "事故赔付不及时"
}, {
	value: "240500",
	text: "授权有延迟"
}, {
	value: "240600",
	text: "车辆未得到合适的修理"
}, {
	value: "240700",
	text: "缺失对维修的沟通"
}, {
	value: "240800",
	text: "关系差"
}]);
var i= new mui.PopPicker();
i.setData([{
	value: "250100",
	text: "无相当级别车辆"
}, {
	value: "250200",
	text: "无车可用"
}, {
	value: "250300",
	text: "司机"
}, {
	value: "250400",
	text: "车况差"
}, {
	value: "250500",
	text: "对租金收费的争议"
}, {
	value: "250600",
	text: "服务有延迟"
}, {
	value: "250700",
	text: "调度长时间无回复"
}, {
	value: "250800",
	text: "关系差"
}]);
var j= new mui.PopPicker();
j.setData([{
	value: "260100",
	text: "错误地收取罚金"
}, {
	value: "260200",
	text: "违章信息发送延迟"
}, {
	value: "260300",
	text: "流程不清晰"
}, {
	value: "260400",
	text: "收费争议"
}, {
	value: "260500",
	text: "路桥费"
}, {
	value: "260600",
	text: "关系差"
}]);
var k= new mui.PopPicker();
k.setData([{
	value: "270100",
	text: "流程不清晰"
}, {
	value: "270200",
	text: "对还车点/取车服务不满意"
}, {
	value: "270300",
	text: "信息不足"
}, {
	value: "270400",
	text: "到期仍开月租发票"
}, {
	value: "270500",
	text: "合同到期收费（金额）"
}, {
	value: "270600",
	text: "交接单缺少交流"
}, {
	value: "270700",
	text: "关系差"
}]);
var l= new mui.PopPicker();
l.setData([{
	value: "280100",
	text: "客户成本中心错误"
}, {
	value: "280200",
	text: "开票金额不正确"
}, {
	value: "280300",
	text: "发票明细不清晰"
}, {
	value: "280400",
	text: "不正确的车辆"
}, {
	value: "280500",
	text: "地址不正确"
}, {
	value: "280600",
	text: "没有开票"
}, {
	value: "280700",
	text: "延迟开票"
}, {
	value: "280800",
	text: "关系差"
}]);
var f= new mui.PopPicker();
f.setData([{
	value: "290100",
	text: "缺少数据/报告"
}, {
	value: "290200",
	text: "数据错误"
}, {
	value: "290300",
	text: "延迟报告"
}]);
var n= new mui.PopPicker();
n.setData([{
	value: "300100",
	text: "数据错误"
}, {
	value: "300200",
	text: "未正常运转"
}]);
var o= new mui.PopPicker();
o.setData([{
	value: "320100",
	text: "司机行为"
}, {
	value: "320200",
	text: "驾驶行为"
}, {
	value: "320300",
	text: "迟到"
}, {
	value: "320400",
	text: "交流"
}, {
	value: "320500",
	text: "关系差"
}, {
	value: "320600",
	text: "司机的仪表"
}, {
	value: "320700",
	text: "司机欺骗行为"
}, {
	value: "320800",
	text: "未提供合适的司机"
}, {
	value: "320900",
	text: "司机向客户抱怨"
}]);
var p= new mui.PopPicker();
p.setData([{
	value: "330100",
	text: "错误的车辆描述"
}, {
	value: "330200",
	text: "钣金维修问题"
}, {
	value: "330300",
	text: "管理问题"
}, {
	value: "330400",
	text: "技术问题"
}]);
var q= new mui.PopPicker();
q.setData([{
	value: "340100",
	text: "地址/联系人错误"
}]);
var r= new mui.PopPicker();
r.setData([{
	value: "350100",
	text: "很难联系上联系人"
}]);
var s= new mui.PopPicker();
s.setData([{
	value: "360100",
	text: "信用审批有延迟"
}, {
	value: "360200",
	text: "信用风险决策"
}]);
var t= new mui.PopPicker();
t.setData([{
	value: "310100",
	text: "很难进入网页"
}]);
var u= new mui.PopPicker();
u.setData([{
	value: "380100",
	text: "车况"
}, {
	value: "380200",
	text: "无替代车"
}, {
	value: "380300",
	text: "未恰当维修车俩"
}, {
	value: "380400",
	text: "救援延迟"
}]);
var v= new mui.PopPicker();
v.setData([{
	value: "390100",
	text: "难以登陆网站"
}]);
var x= new mui.PopPicker();
x.setData([{
	value: "400100",
	text: "--"
}]);