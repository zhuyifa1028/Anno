package com.product.starter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.product.bean.Admin;
import com.product.bean.Area;
import com.product.bean.User;
import com.product.constants.Constants;
import com.product.bean.Driver;
import com.product.bean.Programa;
import com.product.bean.Record;
import com.product.initializer.Starter;

/**
 * amdin缓存-启动器
 * 
 * @author ymc
 *
 */
@Component
public class AdminStarter extends Starter {

	@Override
	public void init(ServletContext servlet) {
		Map<String, String> param = null;
		// 设置admin缓存(是否隐藏)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_IS_DISPLAY_NOT, "否");
			param.put(Admin.ADMIN_IS_DISPLAY_YES, "是");
			servlet.setAttribute(Admin.ADMIN_IS_DISPLAY_KEY, param);
		}
		// 设置admin缓存(语言)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_LANGUAGE_CN, "中文");
			param.put(Admin.ADMIN_LANGUAGE_US, "英文");
			servlet.setAttribute(Admin.ADMIN_LANGUAGE_KEY, param);
		}
		// 设置权限缓存(权限管理)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINISTRATE_NOT, "开通");
			param.put(Admin.ADMIN_ADMINISTRATE_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINISTRATE_KEY, param);
		}
		// 设置用户缓存(用户管理)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINUSER_NOT, "开通");
			param.put(Admin.ADMIN_ADMINUSER_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINUSER_KEY, param);
		}

		// 设置司机缓存(司机管理)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINDRIVER_NOT, "开通");
			param.put(Admin.ADMIN_ADMINDRIVER_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINDRIVER_KEY, param);
		}
		// 设置统计缓存(统计管理)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINTJ_NOT, "开通");
			param.put(Admin.ADMIN_ADMINTJ_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINTJ_KEY, param);
		}
		// 设置培训缓存(培训管理)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINPX_NOT, "开通");
			param.put(Admin.ADMIN_ADMINPX_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINPX_KEY, param);
		}
		// 设置用户确定缓存(用户确定管理)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINUC_NOT, "开通");
			param.put(Admin.ADMIN_ADMINUC_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINUC_KEY, param);
		}
		// 设置投诉反馈缓存(投诉反馈)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINTS_NOT, "开通");
			param.put(Admin.ADMIN_ADMINTS_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINTS_KEY, param);
		}
		// 设置帮助中心缓存(帮助中心)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINBZ_NOT, "开通");
			param.put(Admin.ADMIN_ADMINBZ_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINBZ_KEY, param);
		}
		// 设置飞机火车管理缓存(飞机火车管理)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_ADMINAREA_NOT, "开通");
			param.put(Admin.ADMIN_ADMINAREA_YES, "未开通");
			servlet.setAttribute(Admin.ADMIN_ADMINAREA_KEY, param);
		}

		// 设置管理员缓存(账号状态)
		{
			param = new LinkedHashMap<>();
			param.put(Admin.ADMIN_STATE_NOT, "启用");
			param.put(Admin.ADMIN_STATE_YES, "禁用");
			servlet.setAttribute(Admin.ADMIN_STATE_KEY, param);
		}

		// 设置用户缓存(是否隐藏)
		{
			param = new LinkedHashMap<>();
			param.put(User.USER_IS_DISPLAY_NOT, "否");
			param.put(User.USER_IS_DISPLAY_YES, "是");
			servlet.setAttribute(User.USER_IS_DISPLAY_KEY, param);
		}

		// 设置用户缓存(账号状态)
		{
			param = new LinkedHashMap<>();
			param.put(User.USER_STATE_NOT, "启用");
			param.put(User.USER_STATE_YES, "禁用");
			servlet.setAttribute(User.USER_STATE_KEY, param);
		}

		// 设置Record缓存(是否隐藏)
		{
			param = new LinkedHashMap<>();
			param.put(Record.RECORD_IS_DISPLAY_NOT, "否");
			param.put(Record.RECORD_IS_DISPLAY_YES, "是");
			servlet.setAttribute(Record.RECORD_IS_DISPLAY_KEY, param);
		}
		// 设置Record缓存(语言)
		{
			param = new LinkedHashMap<>();
			param.put(Record.RECORD_LANGUAGE_CN, "中文");
			param.put(Record.RECORD_LANGUAGE_US, "英文");
			servlet.setAttribute(Record.RECORD_LANGUAGE_KEY, param);
		}
		// 设置司机缓存(账号状态)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_STATE_NOT, "启用");
			param.put(Driver.DRIVER_STATE_YES, "禁用");
			servlet.setAttribute(Driver.DRIVER_STATE_KEY, param);
		}

		// 设置司机缓存(是否打卡)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_CLOCK_NOT, "否");
			param.put(Driver.DRIVER_CLOCK_YES, "是");
			servlet.setAttribute(Driver.DRIVER_CLOCK_KEY, param);
		}

		// 设置司机缓存(加油付款类型)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_GASRMBTYPE_CARD, "油卡");
			param.put(Driver.DRIVER_GASRMBTYPE_MONEY, "现金");
			servlet.setAttribute(Driver.DRIVER_GASRMBTYPE_KEY, param);
		}

		// 设置司机缓存(加班类型)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_OVERTIMETYPE_WORKDAY, "工作日");
			param.put(Driver.DRIVER_OVERTIMETYPE_HOLIDAYS, "节假日");
			param.put(Driver.DRIVER_OVERTIMETYPE_WEEKEND, "周末");
			servlet.setAttribute(Driver.DRIVER_OVERTIMETYPE_KEY, param);
		}

		// 设置司机缓存(加班用车类型)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_OVERTIMEUSERCAR_PERSONAGE, "个人原因");
			param.put(Driver.DRIVER_OVERTIMEUSERCAR_BUSINESS, "商务原因");
			servlet.setAttribute(Driver.DRIVER_OVERTIMEUSERCAR_KEY, param);
		}

		// 设置司机缓存(费用承担)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_RMBASSUME_BUSINESS, "安诺");
			param.put(Driver.DRIVER_RMBASSUME_PERSONAGE, "个人");
			servlet.setAttribute(Driver.DRIVER_RMBASSUME_KEY, param);
		}
		
		// 设置司机是否培训(司机培训状态)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_TRAINSTATE_NOT, "未培训");
			param.put(Driver.DRIVER_TRAINSTATE_YES, "已培训");
			servlet.setAttribute(Driver.DRIVER_TRAINSTATE_KEY, param);
		}

		// 设置司机缓存(换车原因)
		{
		param = new LinkedHashMap<>();
		param.put(Driver.DRIVER_REASON_PERSONAGE,"维修保养");
		param.put(Driver.DRIVER_REASON_OTHER,"事故");
		param.put(Driver.DRIVER_REASON_C,"处理罚单");
		param.put(Driver.DRIVER_REASON_D,"合同到期");
		param.put(Driver.DRIVER_REASON_E,"车况不佳");
		servlet.setAttribute(Driver.DRIVER_REASON_KEY, param);
		}		
				

		// 设置日常记录司机缓存(日常记录用户是否确认)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_UAFFIRM_NOT, "否");
			param.put(Driver.DRIVER_UAFFIRM_YES, "是");
			servlet.setAttribute(Driver.DRIVER_UAFFIRM_KEY, param);
		}

		// 设置费用记录司机缓存(费用记录用户是否确认)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_FYFFIRM_NOT, "否");
			param.put(Driver.DRIVER_FYFFIRM_YES, "是");
			servlet.setAttribute(Driver.DRIVER_FYFFIRM_KEY, param);
		}
		// 设置area缓存(是否隐藏)
		{
			param = new LinkedHashMap<>();
			param.put(Area.AREA_IS_DISPLAY_NOT, "否");
			param.put(Area.AREA_IS_DISPLAY_YES, "是");
			servlet.setAttribute(Area.AREA_IS_DISPLAY_KEY, param);
		}
		// 设置area缓存(语言)
		{
			param = new LinkedHashMap<>();
			param.put(Area.AREA_LANGUAGE_CN, "中文");
			param.put(Area.AREA_LANGUAGE_US, "英文");
			servlet.setAttribute(Area.AREA_LANGUAGE_KEY, param);
		}

		// 设置账号缓存(账号状态)
		servlet.setAttribute("DRIVER_STATE_NOT", Driver.DRIVER_STATE_NOT);
		servlet.setAttribute("DRIVER_STATE_YES", Driver.DRIVER_STATE_YES);
		
		// 设置账号缓存(日常记录是否确认)
		servlet.setAttribute("DRIVER_UAFFIRM_NOT", Driver.DRIVER_UAFFIRM_NOT);
		servlet.setAttribute("DRIVER_UAFFIRM_YES", Driver.DRIVER_UAFFIRM_YES);

		// 设置管理员缓存(管理员状态)
		servlet.setAttribute("ADMIN_STATE_NOT", Admin.ADMIN_STATE_NOT);
		servlet.setAttribute("ADMIN_STATE_YES", Admin.ADMIN_STATE_YES);
		// 设置用户缓存(是否隐藏)
		servlet.setAttribute("ADMIN_IS_DISPLAY_NOT", Admin.ADMIN_IS_DISPLAY_NOT);
		servlet.setAttribute("ADMIN_IS_DISPLAY_YES", Admin.ADMIN_IS_DISPLAY_YES);
		// 设置记录缓存(是否隐藏)
		servlet.setAttribute("RECORD_IS_DISPLAY_NOT", Record.RECORD_IS_DISPLAY_NOT);
		servlet.setAttribute("RECORD_IS_DISPLAY_YES", Record.RECORD_IS_DISPLAY_YES);
		// 是否拦截前端登录
		servlet.setAttribute(Constants.FRONT_CONTEXT_INTERCEPTOR, Constants.CONTEXT_INTERCEPTOR_YES);
		// 是否拦截微信登录
		servlet.setAttribute(Constants.WEB_CONTEXT_INTERCEPTOR, Constants.CONTEXT_INTERCEPTOR_YES);
		// 是否拦截后台登录
		servlet.setAttribute(Constants.ADMIN_CONTEXT_INTERCEPTOR, Constants.CONTEXT_INTERCEPTOR_YES);
		// 设置用户缓存(中文语言)
		servlet.setAttribute("PROGRAMA_LANGUAGE_CN", Programa.PROGRAMA_LANGUAGE_CN);
	}

	@Override
	public void load(ServletContext servlet) {
		return;
	}

}
