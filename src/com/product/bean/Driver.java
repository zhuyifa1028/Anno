package com.product.bean;

import java.util.Date;

/**
 * 司机-实体类
 * 
 * @author YMC
 *
 */
public class Driver extends BaseBean {

	private static final long serialVersionUID = 1L;

	/** 是否隐藏(是) */
	public static final String DRIVER_IS_DISPLAY_YES = "1";
	/** 是否隐藏(否) */
	public static final String DRIVER_IS_DISPLAY_NOT = "0";
	/** 系统缓存(是否隐藏) */
	public static final String DRIVER_IS_DISPLAY_KEY = "DRIVER_IS_DISPLAY";

	/** 语言环境(中文) */
	public static final String DRIVER_LANGUAGE_CN = "0";
	/** 语言环境(英文) */
	public static final String DRIVER_LANGUAGE_US = "1";
	/** 系统缓存(语言环境) */
	public static final String DRIVER_LANGUAGE_KEY = "DRIVER_LANGUAGE";

	/** 司机培训的状态(未培训) */
	public static final String DRIVER_TRAINSTATE_NOT = "0";
	/** 司机培训的状态(已培训) */
	public static final String DRIVER_TRAINSTATE_YES = "1";
	/** 系统缓存(司机培训状态) */
	public static final String DRIVER_TRAINSTATE_KEY = "DRIVER_TRAINSTATE";

	/** 司机是否打卡(未打卡) */
	public static final String DRIVER_CLOCK_NOT = "0";
	/** 司机是否打卡(已打卡) */
	public static final String DRIVER_CLOCK_YES = "1";
	/** 系统缓存(司机是否打卡) */
	public static final String DRIVER_CLOCK_KEY = "DRIVER_CLOCK";

	/** 加班类型(工作日) */
	public static final String DRIVER_OVERTIMETYPE_WORKDAY = "0";
	/** 加班类型(节假日) */
	public static final String DRIVER_OVERTIMETYPE_HOLIDAYS = "1";
	/** 加班类型(周末) */
	public static final String DRIVER_OVERTIMETYPE_WEEKEND = "2";
	/** 系统缓存(加班类型) */
	public static final String DRIVER_OVERTIMETYPE_KEY = "DRIVER_OVERTIMETYPE";

	/** 用车目的(个人原因) */
	public static final String DRIVER_OVERTIMEUSERCAR_PERSONAGE = "0";
	/** 用车目的(商务原因) */
	public static final String DRIVER_OVERTIMEUSERCAR_BUSINESS = "1";
	/** 系统缓存(用车目的) */
	public static final String DRIVER_OVERTIMEUSERCAR_KEY = "DRIVER_OVERTIMEUSERCAR";

	/** 费用目的(商务) */
	public static final String DRIVER_RMBGOAL_JY = "0";
	/** 费用目的(个人) */
	public static final String DRIVER_RMBGOAL_TC = "1";
	/** 系统缓存(费用目的) */
	public static final String DRIVER_RMBGOAL_KEY = "DRIVER_RMBGOAL";

	/** 费用类型(早餐) */
	public static final String DRIVER_RMBTYPE_PERSONAGE = "0";
	/** 费用类型(中餐) */
	public static final String DRIVER_RMBTYPE_BUSINESS = "1";
	/** 费用类型(晚餐) */
	public static final String DRIVER_RMBTYPE_WCBUSINESS = "2";
	/** 费用类型(通行费) */
	public static final String DRIVER_RMBTYPE_GLBUSINESS = "3";
	/** 费用类型(停车费) */
	public static final String DRIVER_RMBTYPE_TCBUSINESS = "4";
	/** 费用类型(住宿费) */
	public static final String DRIVER_RMBTYPE_ZSBUSINESS = "5";
	/** 费用类型(差旅) */
	public static final String DRIVER_RMBTYPE_CLBUSINESS = "6";
	/** 费用类型(餐饮) */
	public static final String DRIVER_RMBTYPE_YPBUSINESS = "7";
	/** 费用类型(通讯费) */
	public static final String DRIVER_RMBTYPE_TXBUSINESS = "8";
	/** 费用类型(交通费) */
	public static final String DRIVER_RMBTYPE_JTBUSINESS = "9";
	/** 费用类型(加油费) */
	public static final String DRIVER_RMBTYPE_JYBUSINESS = "10";
	/** 费用类型(其他) */
	public static final String DRIVER_RMBTYPE_QTBUSINESS = "11";
	/** 系统缓存(费用类型) */
	public static final String DRIVER_RMBTYPE_KEY = "DRIVER_RMBTYPE";

	/** 费用承担(个人) */
	public static final String DRIVER_RMBASSUME_PERSONAGE = "0";
	/** 费用承担(安诺) */
	public static final String DRIVER_RMBASSUME_BUSINESS = "1";
	/** 系统缓存(费用承担) */
	public static final String DRIVER_RMBASSUME_KEY = "DRIVER_RMBASSUME";

	/** 换车原因(维修保养) */
	public static final String DRIVER_REASON_PERSONAGE = "0";
	/** 换车原因(事故) */
	public static final String DRIVER_REASON_OTHER = "1";
	/** 换车原因(处理罚单) */
	public static final String DRIVER_REASON_C = "2";
	/** 换车原因(合同到期) */
	public static final String DRIVER_REASON_D = "3";
	/** 换车原因(车况不佳) */
	public static final String DRIVER_REASON_E = "4";
	/** 系统缓存(换车原因) */
	public static final String DRIVER_REASON_KEY = "DRIVER_REASON";

	/** 司机状态(启用) */
	public static final String DRIVER_STATE_NOT = "0";
	/** 司机状态(禁用) */
	public static final String DRIVER_STATE_YES = "1";
	/** 系统缓存(司机状态) */
	public static final String DRIVER_STATE_KEY = "DRIVER_STATE";

	/** 支付费用类型(现金) */
	public static final String DRIVER_GASRMBTYPE_MONEY = "0";
	/** 支付费用类型(油卡) */
	public static final String DRIVER_GASRMBTYPE_CARD = "1";
	/** 系统缓存(支付费用类型) */
	public static final String DRIVER_GASRMBTYPE_KEY = "DRIVER_GASRMBTYPE";

	/** 日常记录用户是否确认(否) */
	public static final String DRIVER_UAFFIRM_NOT = "0";
	/** 日常记录用户是否确认(是) */
	public static final String DRIVER_UAFFIRM_YES = "1";
	/** 系统缓存(日常记录用户是否确认) */
	public static final String DRIVER_UAFFIRM_KEY = "DRIVER_UAFFIRM";

	/** 费用记录用户是否确认(否) */
	public static final String DRIVER_FYFFIRM_NOT = "0";
	/** 费用记录用户是否确认(是) */
	public static final String DRIVER_FYFFIRM_YES = "1";
	/** 系统缓存(费用记录用户是否确认) */
	public static final String DRIVER_FYFFIRM_KEY = "DRIVER_FYFFIRM";

	/** 是否发送给用户(否) */
	public static final String DRIVER_SENDU_NOT = "0";
	/** 是否发送给用户(是) */
	public static final String DRIVER_SENDU_YES = "1";
	/** 系统缓存(是否发送给用户) */
	public static final String DRIVER_SENDU_KEY = "DRIVER_SENDU";

	/**
	 * 登录地址
	 */
	private String driverOpenid;

	/**
	 * 是否发送
	 */
	private String driverSendu;
	/**
	 * 费用记录用户是否确认
	 */
	private String driverFyffirm;

	/**
	 * 日常记录用户是否确认
	 */
	private String driverUaffirm;

	/**
	 * 司机工号
	 */
	private Integer driverId;
	/**
	 * 司机名称
	 */
	private String driverName;

	/**
	 * 司机英文名
	 */
	private String driverUsname;

	/**
	 * 司机等级
	 */
	private String driverLeavel;

	/**
	 * 司机备注
	 */
	private String driverBeizhu;
	/**
	 * 司机电话
	 */
	private String driverTel;
	/**
	 * 司机密码
	 */
	private String driverPwd;
	/**
	 * 用户手机号
	 */
	private String driverUtel;

	/**
	 * 司机身份证号码
	 */
	private String driverIdcard;
	/**
	 * 车牌号
	 */
	private String driverCarnum;
	/**
	 * 车名称
	 */
	private String driverCarname;
	/**
	 * 司机头像
	 */
	private String driverHeadpic;

	/**
	 * 路码表照片
	 */
	private String driverMilepic;

	/**
	 * 记录添加时间
	 */
	private Date driverDate;

	/**
	 * 司机是否打卡
	 */
	private String driverClock;
	/**
	 * 司机出行时的公里数
	 */
	private Double driverStartmile;

	/**
	 * 司机离开家的公里数
	 */
	private Double driverLeavemile;
	/**
	 * 司机离开家的时间
	 */
	private Date driverLeavedate;
	/**
	 * 司机从用户家离开时的公里数
	 */
	private Double driverLeavemileuser;
	/**
	 * 司机从用户家离开时的时间
	 */
	private Date driverLeavedateuser;
	/**
	 * 返回用户家时的公里数
	 */
	private Double driverBackmileuser;
	/**
	 * 返回用户家时的时间
	 */
	private Date driverBackdateuser;
	/**
	 * 司机回家时的公里数
	 */
	private Double driverBackmile;
	/**
	 * 司机回家时的时间
	 */
	private Date driverBackdate;
	/**
	 * 当前码表的公里数
	 */
	private Double driverMilenow;
	/**
	 * 加油多少
	 */
	private Double driverGastotal;
	/**
	 * 加油总费用
	 */
	private Double driverGasrmbtotal;
	/**
	 * 加油支付类型
	 */
	private String driverGasrpaytype;

	/**
	 * 支付类型
	 */
	private String driverPaytype;
	/**
	 * 加班时长
	 */
	private Double driverOvertimehours;
	/**
	 * 加班类型
	 */
	private String driverOvertimetype;
	/**
	 * 用车目的
	 */
	private String driverOvertimeusecar;
	/**
	 * 加班总费用
	 */
	private Double driverOvertimermbtotal;

	/**
	 * 报销费用承担
	 */
	private String driverRmbassume;
	/**
	 * 报销费用类别（费用目的）
	 */
	private String driverRmbgoal;
	/**
	 * 报销费用类型
	 */
	private String driverRmbtype;
	/**
	 * 报销费用
	 */
	private Double driverRmb;
	/**
	 * 报销总费用
	 */
	private Double driverRmbtotal;
	/**
	 * 换车原因
	 */
	private String driverReason;
	/**
	 * 旧车号码
	 */
	private String driverJcarnum;
	/**
	 * 旧车公里数
	 */
	private Double driverJcarmile;
	/**
	 * 新车号码
	 */
	private String driverNewcarnum;
	/**
	 * 新车公里数
	 */
	private Double driverNewcarmile;
	/**
	 * 状态
	 */
	private String driverState;
	/**
	 * 培训状态
	 */
	private String driverTrainstate;
	/**
	 * 是否隐藏(1:是、0:否)
	 */
	private String driverIsdisplay;
	/**
	 * 语言环境(中文、英文)
	 */
	private String driverLanguage;

	/**
	 * 登录时间
	 */
	private Date loginDate;

	/**
	 * 登录地址
	 */
	private String loginAddre;

	public String getDriverOpenid() {
		return driverOpenid;
	}

	public void setDriverOpenid(String driverOpenid) {
		this.driverOpenid = driverOpenid;
	}

	public String getLoginAddre() {
		return loginAddre;
	}

	public void setLoginAddre(String loginAddre) {
		this.loginAddre = loginAddre == null ? null : loginAddre.trim();
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName == null ? null : driverName.trim();
	}

	public String getDriverTel() {
		return driverTel;
	}

	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel == null ? null : driverTel.trim();
	}

	public String getDriverPwd() {
		return driverPwd;
	}

	public void setDriverPwd(String driverPwd) {
		this.driverPwd = driverPwd == null ? null : driverPwd.trim();
	}

	public String getDriverUtel() {
		return driverUtel;
	}

	public void setDriverUtel(String driverUtel) {
		this.driverUtel = driverUtel == null ? null : driverUtel.trim();
	}

	public String getDriverIdcard() {
		return driverIdcard;
	}

	public void setDriverIdcard(String driverIdcard) {
		this.driverIdcard = driverIdcard == null ? null : driverIdcard.trim();
	}

	public String getDriverCarnum() {
		return driverCarnum;
	}

	public void setDriverCarnum(String driverCarnum) {
		this.driverCarnum = driverCarnum == null ? null : driverCarnum.trim();
	}

	public String getDriverCarname() {
		return driverCarname;
	}

	public void setDriverCarname(String driverCarname) {
		this.driverCarname = driverCarname == null ? null : driverCarname.trim();
	}

	public String getDriverHeadpic() {
		return driverHeadpic;
	}

	public void setDriverHeadpic(String driverHeadpic) {
		this.driverHeadpic = driverHeadpic;
	}

	public String getDriverState() {
		return driverState;
	}

	public void setDriverState(String driverState) {
		this.driverState = driverState;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getDriverMilepic() {
		return driverMilepic;
	}

	public void setDriverMilepic(String driverMilepic) {
		this.driverMilepic = driverMilepic;
	}

	public String getDriverClock() {
		return driverClock;
	}

	public void setDriverClock(String driverClock) {
		this.driverClock = driverClock;
	}

	public Double getDriverStartmile() {
		return driverStartmile;
	}

	public void setDriverStartmile(Double driverStartmile) {
		this.driverStartmile = driverStartmile;
	}

	public Double getDriverLeavemile() {
		return driverLeavemile;
	}

	public void setDriverLeavemile(Double driverLeavemile) {
		this.driverLeavemile = driverLeavemile;
	}

	public Date getDriverLeavedate() {
		return driverLeavedate;
	}

	public void setDriverLeavedate(Date driverLeavedate) {
		this.driverLeavedate = driverLeavedate;
	}

	public Double getDriverLeavemileuser() {
		return driverLeavemileuser;
	}

	public void setDriverLeavemileuser(Double driverLeavemileuser) {
		this.driverLeavemileuser = driverLeavemileuser;
	}

	public Date getDriverLeavedateuser() {
		return driverLeavedateuser;
	}

	public void setDriverLeavedateuser(Date driverLeavedateuser) {
		this.driverLeavedateuser = driverLeavedateuser;
	}

	public Double getDriverBackmileuser() {
		return driverBackmileuser;
	}

	public void setDriverBackmileuser(Double driverBackmileuser) {
		this.driverBackmileuser = driverBackmileuser;
	}

	public Date getDriverBackdateuser() {
		return driverBackdateuser;
	}

	public void setDriverBackdateuser(Date driverBackdateuser) {
		this.driverBackdateuser = driverBackdateuser;
	}

	public Double getDriverBackmile() {
		return driverBackmile;
	}

	public void setDriverBackmile(Double driverBackmile) {
		this.driverBackmile = driverBackmile;
	}

	public Date getDriverBackdate() {
		return driverBackdate;
	}

	public void setDriverBackdate(Date driverBackdate) {
		this.driverBackdate = driverBackdate;
	}

	public Double getDriverMilenow() {
		return driverMilenow;
	}

	public void setDriverMilenow(Double driverMilenow) {
		this.driverMilenow = driverMilenow;
	}

	public Double getDriverGastotal() {
		return driverGastotal;
	}

	public void setDriverGastotal(Double driverGastotal) {
		this.driverGastotal = driverGastotal;
	}

	public Double getDriverGasrmbtotal() {
		return driverGasrmbtotal;
	}

	public void setDriverGasrmbtotal(Double driverGasrmbtotal) {
		this.driverGasrmbtotal = driverGasrmbtotal;
	}

	public String getDriverGasrpaytype() {
		return driverGasrpaytype;
	}

	public void setDriverGasrpaytype(String driverGasrpaytype) {
		this.driverGasrpaytype = driverGasrpaytype;
	}

	public String getDriverPaytype() {
		return driverPaytype;
	}

	public void setDriverPaytype(String driverPaytype) {
		this.driverPaytype = driverPaytype;
	}

	public Double getDriverOvertimehours() {
		return driverOvertimehours;
	}

	public void setDriverOvertimehours(Double driverOvertimehours) {
		this.driverOvertimehours = driverOvertimehours;
	}

	public String getDriverOvertimetype() {
		return driverOvertimetype;
	}

	public void setDriverOvertimetype(String driverOvertimetype) {
		this.driverOvertimetype = driverOvertimetype;
	}

	public String getDriverOvertimeusecar() {
		return driverOvertimeusecar;
	}

	public void setDriverOvertimeusecar(String driverOvertimeusecar) {
		this.driverOvertimeusecar = driverOvertimeusecar;
	}

	public Double getDriverOvertimermbtotal() {
		return driverOvertimermbtotal;
	}

	public void setDriverOvertimermbtotal(Double driverOvertimermbtotal) {
		this.driverOvertimermbtotal = driverOvertimermbtotal;
	}

	public String getDriverRmbassume() {
		return driverRmbassume;
	}

	public void setDriverRmbassume(String driverRmbassume) {
		this.driverRmbassume = driverRmbassume;
	}

	public String getDriverRmbgoal() {
		return driverRmbgoal;
	}

	public void setDriverRmbgoal(String driverRmbgoal) {
		this.driverRmbgoal = driverRmbgoal;
	}

	public String getDriverRmbtype() {
		return driverRmbtype;
	}

	public void setDriverRmbtype(String driverRmbtype) {
		this.driverRmbtype = driverRmbtype;
	}

	public Double getDriverRmb() {
		return driverRmb;
	}

	public void setDriverRmb(Double driverRmb) {
		this.driverRmb = driverRmb;
	}

	public Double getDriverRmbtotal() {
		return driverRmbtotal;
	}

	public void setDriverRmbtotal(Double driverRmbtotal) {
		this.driverRmbtotal = driverRmbtotal;
	}

	public String getDriverReason() {
		return driverReason;
	}

	public void setDriverReason(String driverReason) {
		this.driverReason = driverReason;
	}

	public String getDriverJcarnum() {
		return driverJcarnum;
	}

	public void setDriverJcarnum(String driverJcarnum) {
		this.driverJcarnum = driverJcarnum;
	}

	public Double getDriverJcarmile() {
		return driverJcarmile;
	}

	public void setDriverJcarmile(Double driverJcarmile) {
		this.driverJcarmile = driverJcarmile;
	}

	public String getDriverNewcarnum() {
		return driverNewcarnum;
	}

	public void setDriverNewcarnum(String driverNewcarnum) {
		this.driverNewcarnum = driverNewcarnum;
	}

	public Double getDriverNewcarmile() {
		return driverNewcarmile;
	}

	public void setDriverNewcarmile(Double driverNewcarmile) {
		this.driverNewcarmile = driverNewcarmile;
	}

	public String getDriverTrainstate() {
		return driverTrainstate;
	}

	public void setDriverTrainstate(String driverTrainstate) {
		this.driverTrainstate = driverTrainstate;
	}

	public String getDriverIsdisplay() {
		return driverIsdisplay;
	}

	public void setDriverIsdisplay(String driverIsdisplay) {
		this.driverIsdisplay = driverIsdisplay;
	}

	public String getDriverLanguage() {
		return driverLanguage;
	}

	public void setDriverLanguage(String driverLanguage) {
		this.driverLanguage = driverLanguage;
	}

	public String getDriverUaffirm() {
		return driverUaffirm;
	}

	public void setDriverUaffirm(String driverUaffirm) {
		this.driverUaffirm = driverUaffirm == null ? null : driverUaffirm.trim();
	}

	public String getDriverLeavel() {
		return driverLeavel;
	}

	public void setDriverLeavel(String driverLeavel) {
		this.driverLeavel = driverLeavel;
	}

	public String getDriverBeizhu() {
		return driverBeizhu;
	}

	public void setDriverBeizhu(String driverBeizhu) {
		this.driverBeizhu = driverBeizhu;
	}

	public String getDriverFyffirm() {
		return driverFyffirm;
	}

	public void setDriverFyffirm(String driverFyffirm) {
		this.driverFyffirm = driverFyffirm == null ? null : driverFyffirm.trim();
	}

	public String getDriverSendu() {
		return driverSendu;
	}

	public void setDriverSendu(String driverSendu) {
		this.driverSendu = driverSendu;
	}

	public String getDriverUsname() {
		return driverUsname;
	}

	public void setDriverUsname(String driverUsname) {
		this.driverUsname = driverUsname;
	}

	public Date getDriverDate() {
		return driverDate;
	}

	public void setDriverDate(Date driverDate) {
		this.driverDate = driverDate;
	}

}
