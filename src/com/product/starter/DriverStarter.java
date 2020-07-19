package com.product.starter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.product.bean.Driver;
import com.product.initializer.Starter;

/**
 * 司机缓存-启动器
 * 
 * @author wh
 *
 */
@Component
public class DriverStarter extends Starter {

	@Override
	public void init(ServletContext servlet) {

		Map<String, String> param = null;
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
			param.put(Driver.DRIVER_RMBASSUME_BUSINESS, "安诺久通先行垫付");
			param.put(Driver.DRIVER_RMBASSUME_PERSONAGE, "客户直接支付");
			servlet.setAttribute(Driver.DRIVER_RMBASSUME_KEY, param);
		}
		// 设置司机缓存(费用类型)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_RMBTYPE_PERSONAGE, "早餐");
			param.put(Driver.DRIVER_RMBTYPE_BUSINESS, "中餐");
			param.put(Driver.DRIVER_RMBTYPE_WCBUSINESS, "晚餐");
			param.put(Driver.DRIVER_RMBTYPE_GLBUSINESS, "通行费");
			param.put(Driver.DRIVER_RMBTYPE_TCBUSINESS, "停车费");
			param.put(Driver.DRIVER_RMBTYPE_ZSBUSINESS, "住宿费");
			param.put(Driver.DRIVER_RMBTYPE_CLBUSINESS, "差旅");
			param.put(Driver.DRIVER_RMBTYPE_YPBUSINESS, "餐饮");
			param.put(Driver.DRIVER_RMBTYPE_TXBUSINESS, "通讯费");
			param.put(Driver.DRIVER_RMBTYPE_JTBUSINESS, "交通费");
			param.put(Driver.DRIVER_RMBTYPE_JYBUSINESS, "加油费");
			param.put(Driver.DRIVER_RMBTYPE_QTBUSINESS, "其它");
			servlet.setAttribute(Driver.DRIVER_RMBTYPE_KEY, param);
		}

		// 设置司机缓存(费用目的)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_RMBGOAL_JY, "商务");
			param.put(Driver.DRIVER_RMBGOAL_TC, "个人");
			servlet.setAttribute(Driver.DRIVER_RMBGOAL_KEY, param);
		}

		// 设置司机缓存(换车原因)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_REASON_PERSONAGE, "维修保养");
			param.put(Driver.DRIVER_REASON_OTHER, "事故");
			param.put(Driver.DRIVER_REASON_C, "处理罚单");
			param.put(Driver.DRIVER_REASON_D, "合同到期");
			param.put(Driver.DRIVER_REASON_E, "车况不佳");

			servlet.setAttribute(Driver.DRIVER_REASON_KEY, param);
		}

		// 设置司机缓存(用户是否确认)
		{
			param = new LinkedHashMap<>();
			param.put(Driver.DRIVER_UAFFIRM_NOT, "否");
			param.put(Driver.DRIVER_UAFFIRM_YES, "是");
			servlet.setAttribute(Driver.DRIVER_UAFFIRM_KEY, param);
		}

	}

}
