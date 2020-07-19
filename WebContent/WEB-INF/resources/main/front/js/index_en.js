var bear = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
bear.setData([ 
//{
//	value : '0',
//	text : 'Contract & Fleet Manager Relationship'
//}, {
//	value : '1',
//	text : 'Quotations'
//}, {
//	value : '2',
//	text : 'Orders'
//}, {
//	value : '3',
//	text : 'Purchase and Delivery'
//}, 
{
	value : '4',
	text : 'Maintenace and mechanical repair'
}
//, {
//	value : '5',
//	text : 'Tyres'
//}, {
//	value : '6',
//	text : 'Roadside Assistance'
//}, {
//	value : '7',
//	text : 'Fuel'
//}, {
//	value : '8',
//	text : 'Damage & Insurance'
//}, {
//	value : '9',
//	text : 'Relief/Short term/Prelease'
//}, {
//	value : '10',
//	text : 'Fines & toll*'
//}, {
//	value : '11',
//	text : 'Vehicle Return'
//}, {
//	value : '12',
//	text : 'Invoicing'
//}, {
//	value : '13',
//	text : 'Reporting'
//}, {
//	value : '14',
//	text : 'Telematics'
//}
, {
	value : '15',
	text : 'chauffeur service'
}, 
//{
//	value : '16',
//	text : 'Remarketing'
//}, {
//	value : '17',
//	text : 'Administration/Data quality'
//}, {
//	value : '18',
//	text : 'Contact'
//}, {
//	value : '19',
//	text : 'Credit risk'
//}, 
//{
//	value : '20',
//	text : 'Digital (web&apps)'
//}, 
{
	value : '21',
	text : 'Vehicle'
}, {
	value : '22',
	text : 'Digital'
}, {
	value : '23',
	text : 'Other'
} ]);


var business = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
business.setData([{
	value: '0',
	text: 'Delay in finalising contract'
}, {
	value: '1',
	text: 'Response Time delay'
}, {
	value: '2',
	text: 'Insufficient communication'
}, {
	value: '3',
	text: 'No interest shown'
}, {
	value: '4',
	text: 'Being on hold too long'
}, {
	value: '5',
	text: 'Contact not available'
}, {
	value: '6',
	text: 'Recharge dispute / bonus'
}, {
	value: '7',
	text: 'Customer meeting'
}, {
	value: '8',
	text: 'Poor relationship'
}]);

var a = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
a.setData([{
	value: "120101",
	text: "Response time"
}, {
	value: "120102",
	text: "Wrong quotation"
}, {
	value: "120103",
	text: "Wrong car"
}, {
	value: "120104",
	text: "Insufficient / poor advice given"
}, {
	value: "120105",
	text: "Poor relationship"
}]);
var chao = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
chao.setData([{
	value: "130100",
	text: "Wrong contract adjustment"
}, {
	value: "130200",
	text: "Insufficient / poor advice given"
}, {
	value: "130300",
	text: "Poor relationship"
}]);
var c = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
c.setData([{
	value: "140100",
	text: "Late delivery"
}, {
	value: "140200",
	text: "Lack of comm"
}, {
	value: "140300",
	text: "Unclear scheduling on delivery"
}, {
	value: "140400",
	text: "Wrong car delivered"
}, {
	value: "140500",
	text: "Plate issue"
}, {
	value: "140600",
	text: "Insufficient / missing accessories"
}, {
	value: "140700",
	text: "Other problems at delivery"
}, {
	value: "140800",
	text: "Recharge dispute"
}, {
	value: "140900",
	text: "Poor relationship"
}, {
	value: "141000",
	text: "Condition of used car (dirty….)"
}]);
var d = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
d.setData([{
	value: "150100",
	text: "Technical problems / maintenace"
}, {
	value: "150200",
	text: "Dirty vehicle"
}]);
var e = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
e.setData([{
	value: "210100",
	text: "Poor service by the tire fitter"
}, {
	value: "210200",
	text: "Poor information provided"
}, {
	value: "210300",
	text: "Poor relationship"
}]);
var f = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
f.setData([{
	value: "220100",
	text: "Long waiting time on line"
}, {
	value: "220200",
	text: "Delay in service providing"
}, {
	value: "220300",
	text: "Recharge dispute"
}, {
	value: "220400",
	text: "Poor relationship"
}]);
var g = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
g.setData([{
	value: "230100",
	text: "Fuel card delivery delay"
}, {
	value: "230200",
	text: "Incorrect fuel card"
}, {
	value: "230300",
	text: "Fuel card does not work"
}, {
	value: "230400",
	text: "Unsubscribed service applied"
}, {
	value: "230500",
	text: "Recharge dispute"
}, {
	value: "230600",
	text: "Poor relationship"
}]);
var h = new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
h.setData([{
	value: "240100",
	text: "Unclear process"
}, {
	value: "240200",
	text: "Recharge dispute"
}, {
	value: "240300",
	text: "Claim documents"
}, {
	value: "240400",
	text: "Late payment of claim"
}, {
	value: "240500",
	text: "Delay in authorisation"
}, {
	value: "240600",
	text: "Not repaired properly"
}, {
	value: "240700",
	text: "Lack of comm on repair"
}, {
	value: "240800",
	text: "Poor relationship"
}]);
var i= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
i.setData([{
	value: "250100",
	text: "No equivalent category"
}, {
	value: "250200",
	text: "No vehicle available"
}, {
	value: "250300",
	text: "Chauffeur"
}, {
	value: "250400",
	text: "Vehicle condition"
}, {
	value: "250500",
	text: "Recharge dispute on rentals"
}, {
	value: "250600",
	text: "Service delay"
}, {
	value: "250700",
	text: "Long response time by dispatcher"
}, {
	value: "250800",
	text: "Poor relationship"
}]);
var j= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
j.setData([{
	value: "260100",
	text: "Erroneously charging fines"
}, {
	value: "260200",
	text: "Delay in sending fines"
}, {
	value: "260300",
	text: "Unclear process"
}, {
	value: "260400",
	text: "Recharge dispute"
}, {
	value: "260500",
	text: "Toll charges"
}, {
	value: "260600",
	text: "Poor relationship"
}]);
var k= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
k.setData([{
	value: "270100",
	text: "Unclear process"
}, {
	value: "270200",
	text: "Pick up/delivery point"
}, {
	value: "270300",
	text: "Poor info"
}, {
	value: "270400",
	text: "Rental still running"
}, {
	value: "270500",
	text: "EOC charge dispute"
}, {
	value: "270600",
	text: "Inspection report"
}, {
	value: "270700",
	text: "Poor relationship"
}]);
var l= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
l.setData([{
	value: "280100",
	text: "Wrong cost centre"
}, {
	value: "280200",
	text: "Wrong amount invoiced"
}, {
	value: "280300",
	text: "Unclear invoice"
}, {
	value: "280400",
	text: "Wrong vehicle"
}, {
	value: "280500",
	text: "Wrong address"
}, {
	value: "280600",
	text: "Missing invoicing"
}, {
	value: "280700",
	text: "Delayed invoicing"
}, {
	value: "280800",
	text: "Poor relationship"
}]);
var f= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
f.setData([{
	value: "290100",
	text: "Missing data/report"
}, {
	value: "290200",
	text: "Wrong data"
}, {
	value: "290300",
	text: "Delayed reporting"
}]);
var n= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
n.setData([{
	value: "300100",
	text: "Wrong data"
}, {
	value: "300200",
	text: "Does not work properly"
}]);
var o= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
o.setData([{
	value: "320100",
	text: "Chauffeur behaviour"
}, {
	value: "320200",
	text: "Driving behaviour"
}, {
	value: "320300",
	text: "Late arrival"
}, {
	value: "320400",
	text: "Communication"
}, {
	value: "320500",
	text: "Poor relationship"
}, {
	value: "320600",
	text: "Chauffeur's personal appearance"
}, {
	value: "320700",
	text: "Chauffeur's cheating behavior"
}, {
	value: "320800",
	text: "No suitable chauffeur provided"
}, {
	value: "320900",
	text: "Chauffeur compains to the client about AJ"
}]);
var p= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
p.setData([{
	value: "330100",
	text: "Wrong vehicle description"
}, {
	value: "330200",
	text: "Body repair problems"
}, {
	value: "330300",
	text: "Admin issues"
}, {
	value: "330400",
	text: "Technical issues"
}]);
var q= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
q.setData([{
	value: "340100",
	text: "Errors in adress/contact name"
}]);
var r= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
r.setData([{
	value: "350100",
	text: "Difficulty to reach my contact"
}]);
var s= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
s.setData([{
	value: "360100",
	text: "Delay to get the credit approval"
}, {
	value: "360200",
	text: "Credit risk decision"
}]);
var t= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
t.setData([{
	value: "310100",
	text: "Difficulty to access the website"
}]);
var u= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
u.setData([{
	value: "380100",
	text: "Condition of the car"
}, {
	value: "380200",
	text: "No replacement car"
}, {
	value: "380300",
	text: "Not repaired properly"
}, {
	value: "380400",
	text: "Delay in road assistance"
}]);
var v= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
v.setData([{
	value: "390100",
	text: "Difficulty to access the website "
}]);
var x= new mui.PopPicker({
	buttons: ['Cancel', 'Confirm']
});
x.setData([{
	value: "400100",
	text: "--"
}]);