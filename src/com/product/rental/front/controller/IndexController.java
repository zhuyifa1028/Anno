package com.product.rental.front.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.product.annotation.SystemLog;
import com.product.bean.Driver;
import com.product.bean.Record;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.DriverService;
import com.product.service.RecordService;
import com.product.utils.DateUtils;
import com.product.utils.DesUtils;
import com.product.utils.MD5Utils;
import com.product.utils.ShaUtils;
import com.product.utils.StrUtils;
import com.product.utils.WxUtils;

/**
 * 司机前端首页-控制类
 * 
 * @author wh
 *
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {
	private static Log tag = LogFactory.getLog("tag");

	@Autowired
	private DriverService driverService;

	@Autowired
	private RecordService recordService;

	/**
	 * 司机首页-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("index.html")
	public ModelAndView index(ModelAndView view, HttpServletRequest request, HttpSession session) {
		Map<String, String> param = super.getParameters(request);
		Driver d = (Driver) session.getAttribute("driver");// 当前用户
		try {
			param.put("recordIsDisplay", "0");
			param.put("order_id", "desc");
			List<Record> records = recordService.getList(param);// 所有未隐藏的培训信息
			view.addObject("records", records);

			// 查找该用户所有未培训的记录
			Map<String, String> map = new HashMap<String, String>();
			map.put("driverTel", d.getDriverTel());
			map.put("driverUsname", "pxtj");
			map.put("driverTrainstate", "0");
			List<Driver> drivers = driverService.getList(map);
			view.addObject("listpx", drivers);

			for (Driver driver : drivers) {
				for (Record record : records) {
					if (record.getId().equals(driver.getDriverId())) {
						view.addObject("isnotnew", "1");// 则证明有培训未完成。显示new
					}
				}
			}

			// 默认显示最近日期的回站路码
			Map<String, String> tmp = new HashMap<String, String>();
			tmp.put("driverBeizhu", "rcjl");
			tmp.put("driverTel", d.getDriverTel());
			tmp.put("order_date", "desc");
			List<Driver> listdri = driverService.getList(tmp);// 倒序查找最近日期的记录
			if (listdri != null && !listdri.isEmpty()) {
				view.addObject("zuotian", "0");
				if (listdri.get(0).getDriverBackmile() != null) {
					int i = new Double(listdri.get(0).getDriverBackmile()).intValue();
					view.addObject("zuotian", i);
				}
			}

			view.setViewName("index");
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return view;
	}

	@RequestMapping("shareurl.html")
	public ModelAndView shareurl(ModelAndView view, HttpServletRequest request, HttpSession session) {
		String url = "http://mp.weixin.qq.com/s?__biz=MjM5ODQxMDY0OQ==&mid=309808253&idx=1&sn=e569e4c322dd3636729011900409d159&chksm=3148cc8b063f459dd7bca0b272f1ac9fcc84ef5bac257a2e02837e09908c0334a64411625e88&mpshare=1&scene=1&srcid=1010sYkOPp6JAAzNzV4syZ1m";

		view.setViewName("redirect:" + url);
		return view;
	}

	/**
	 * 历史信息-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("index_history.html")
	public ModelAndView index_history(ModelAndView view, HttpServletRequest request, HttpSession session) {
		Driver d = (Driver) session.getAttribute("driver");// 当前用户
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("driverTel", d.getDriverTel());
			param.put("order_date", "desc");
			List<Driver> lists = driverService.getList(param);

			view.addObject("models", lists);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("index_history");
		return view;
	}

	/**
	 * 历史信息编辑—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("indexhistoryedit")
	public ModelAndView edit(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				view.addObject("model", driver);
			}

		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.setViewName("index");
		return view;
	}

	/**
	 * upload head
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "每日例行保养")
	@RequestMapping("headupload")
	public JsonResult headupload(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.FAILED);
		Driver d = (Driver) session.getAttribute("driver");
		try {
			Driver drivers = new Driver();
			json = super.upload(request, "file");
			Object filename = json.getResult();
			drivers.setId(d.getId());
			drivers.setDriverHeadpic(filename.toString());
			boolean flag = driverService.saveOrUpdate(drivers, super.ip(request));

			Driver drievrs2 = driverService.getById(d.getId());
			session.setAttribute("driver", drievrs2);
			if (flag) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("编辑成功");
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 每日例行保养编辑—操作
	 * 
	 * 每日例行保养
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "每日例行保养")
	@RequestMapping("indexedit")
	public JsonResult indexedit(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		HttpSession session = request.getSession();
		Map<String, String> mp = new HashMap<String, String>();
		Driver driver = (Driver) session.getAttribute("driver");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> param = super.getParameters(request);
		try {
			// 判断当日是否已存在例行保养记录
			mp.put("driverTel", driver.getDriverTel());
			mp.put("driverBeizhu", "lxby");
			List<Driver> lists = driverService.getList(mp);// 全部例行保养记录

			int k = 0;
			if (param.containsKey("_id")) {
				k = Integer.parseInt(param.get("_id"));
			}
			for (Driver d : lists) {
				String s = DateUtils.getDateFormat(d.getDriverDate(), DateUtils.SHORT_DATE_FORMAT);
				if (s.equals(param.get("date"))) {
					if (k != 0 && k != d.getId()) { // 编辑判断 ，是编辑，且该id与d.id 不相同

						if (s.equals(param.get("date"))) { // 日期重复就 return。
							json.setCode(Status.ERROR.getCode());
							json.setMessage("编辑日期已存在记录，请检查");
							return json;
						}
					} else if (k == 0) { // 添加判断：是否已存在该记录
						json.setCode(Status.ERROR.getCode());
						json.setMessage("当日已填写，请去历史记录中进行修改");
						return json;
					}
				}
			}

			Driver drivers = super.reflect("driver", Driver.class, param);
			Object filename = null;
			if (param.containsKey("isnot")) { // 判断是否有上传图片，没有则跳过。
				json = super.upload(request, "filedata");
				filename = json.getResult();
				drivers.setDriverMilepic(filename.toString());
			}
			String c = null;
			if (param.containsKey("clock")) {
				c = "1";
			} else {
				c = "0";
			}
			drivers.setDriverBeizhu("lxby");// 标识是否是例行保养
			drivers.setDriverClock(c);
			drivers.setDriverName(driver.getDriverName());
			drivers.setDriverCarnum(driver.getDriverCarnum());
			drivers.setDriverTel(driver.getDriverTel());
			drivers.setDriverStartmile(Double.parseDouble(param.get("startmile")));
			drivers.setDriverDate(sdf.parse(param.get("date")));
			drivers.setDriverId(driver.getDriverId());
			;
			boolean flags = driverService.saveOrUpdate(drivers, super.ip(request));
			if (flags) {
				json.setCode(Status.SUCCESS.getCode());
				if (param.containsKey("_id")) {
					json.setCode("100");
					json.setMessage("编辑成功");
				} else if (filename != null) {
					json.setMessage("打卡完成，图片上传成功");
				} else {
					json.setMessage("打卡成功");
				}

			} else {
				json.setMessage("编辑失败");
			}

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;

	}

	/**
	 * 司机登录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping(value = { "/", "login.html" })
	public ModelAndView login(ModelAndView view, HttpServletRequest request) {
		try {
			String url = request.getHeader("Referer");
			if (StringUtils.isNotBlank(url)) {
				view.addObject("url", url);
			}
			String retUrl = request.getParameter("reqUrl");
			if (StrUtils.isEmpty(retUrl)) {
				retUrl = request.getHeader("Referer");
			}
			view.addObject("requestUrl", retUrl);
			view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("login");
		return view;
	}

	/**
	 * 司机登录-操作
	 * 
	 * @param request
	 * @return
	 */
	@SystemLog(description = "司机登录-操作")
	@RequestMapping("login")
	public JsonResult login(HttpServletRequest request, HttpServletResponse response) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			if (StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(name)) {
				DesUtils des = new DesUtils(Constants.PUBLIC_SECRET);
				pwd = des.encrypt(pwd);
				if (StringUtils.isNotBlank(pwd)) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("driverTel", name);
					param.put("driverPwd", MD5Utils.md5(pwd));
					List<Driver> drivers = driverService.getList(param);
					if (drivers != null && !drivers.isEmpty() && drivers.size() == 1) {
						Driver driver = drivers.get(0);
						if (driver.getDriverState() != null
								&& driver.getDriverState().equals(Driver.DRIVER_STATE_YES)) {
							json.setMessage("用户不可使用,请联系管理员");
						} else {
							HttpSession session = request.getSession();
							session.setAttribute(Constants.WEB_DRIVER_SESSION, driver);
							json.setCode(Status.SUCCESS.getCode());
							json.setMessage("用户登录成功");
							session.setAttribute("driver", driver);
							json.setResult(driver);
							// 记录登录过的账号
							Cookie cookie = new Cookie("logined_driver", driver.getDriverTel());
							response.addCookie(cookie);
						}
					} else {
						json.setMessage("用户名称或密码错误");
					}
				} else {
					json.setMessage("用户密码解密失败");
				}
			} else {
				json.setMessage("用户名称或密码错误");
			}
		} catch (Exception e) {
			json.setCode(Status.ERROR.getCode());
			json.setMessage("系统错误,用户登录失败");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 退出登录-页面
	 * 
	 * @param view
	 * @return
	 */
	@RequestMapping("logout.html")
	public ModelAndView logout(ModelAndView view, HttpSession session, HttpServletRequest request) {
		Enumeration<String> keys = session.getAttributeNames();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			if (key.equals(Constants.WEB_DRIVER_SESSION)) {// 清除用户表中openid记录，保证下次访问不会自动登录
				Driver driver = (Driver) session.getAttribute(Constants.WEB_DRIVER_SESSION);
				driver.setDriverOpenid("");
				driverService.saveOrUpdate(driver, super.ip(request));
			}
			session.removeAttribute(key);
		}
		session.removeAttribute("driver");
		view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		view.setViewName("login");
		return view;
	}

	/**
	 * 日常记录-页面
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("daily.html")
	public ModelAndView daily(ModelAndView view, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			Driver d = (Driver) session.getAttribute("driver");
			// 默认显示最近日期的回站路码
			Map<String, String> tmp = new HashMap<String, String>();
			tmp.put("driverBeizhu", "rcjl");
			tmp.put("driverTel", d.getDriverTel());
			tmp.put("order_date", "desc");
			List<Driver> listdri = driverService.getList(tmp);// 倒序查找最近日期的记录
			if (!listdri.isEmpty()) {
				view.addObject("zuotian", listdri.get(0).getDriverBackmile());
			}

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("daily");
		return view;
	}

	/**
	 * 日常历史记录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("daily_history.html")
	public ModelAndView daily_history(ModelAndView view, HttpServletRequest request) {
		try {
			HttpSession s = request.getSession();
			Driver d = (Driver) s.getAttribute("driver");
			Map<String, String> map = new HashMap<>();
			map.put("driverTel", d.getDriverTel());
			map.put("driverBeizhu", "rcjl");
			map.put("order_date", "desc");
			List<Driver> lists = driverService.getList(map);
			view.addObject("modelsdaily", lists);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("daily_history");
		return view;
	}

	/**
	 * 日常记录编辑前判断
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("dailypd")
	public JsonResult dailypd(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.SUCCESS);
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				if (driver.getDriverUaffirm().equals("1")) {
					json.setCode(Status.ERROR.getCode());
					json.setMessage("用户已确认不可修改");
					return json;
				}
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 日常记录-编辑 入口
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("dailyedit")
	public ModelAndView dailyedit(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				view.addObject("model", driver);
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("daily");
		return view;
	}

	/**
	 * 日常记录-操作
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "日常记录")
	@RequestMapping("daily")
	public JsonResult dailys(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);
		Map<String, String> mp = new HashMap<String, String>();
		Driver drivers = (Driver) session.getAttribute("driver");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm");
		try {
			if (param.containsKey("_id")) {
				String id = param.get("_id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				if (driver.getDriverUaffirm().equals("1")) {

					json.setMessage("用户已确认不可修改");
					return json;
				}
			}

			boolean flags = false;
			if (param.get("date") != null && param.get("date") != "") {
				mp.put("driverTel", drivers.getDriverTel());
				mp.put("driverBeizhu", "rcjl");
				List<Driver> lists = driverService.getList(mp);// 全部日常记录

				int k = 0;
				if (param.containsKey("_id")) {
					k = Integer.parseInt(param.get("_id"));
				}
				for (Driver driver : lists) {
					String s = DateUtils.getDateFormat(driver.getDriverDate(), DateUtils.SHORT_DATE_FORMAT);
					if (s.equals(param.get("date"))) {

						if (k != driver.getId()) { // 编辑判断：是否已存在该记录
							json.setCode(Status.ERROR.getCode());
							json.setMessage("编辑日期已存在记录，请检查");
							return json;
						} else if (k == 0) { // 添加判断：是否已存在该记录
							json.setCode(Status.ERROR.getCode());
							json.setMessage("当日已填写，请去历史记录中进行修改");
							return json;
						}
					}
				}

				Driver driver = super.reflect("driver", Driver.class, param);
				// Driver driver =new Driver();

				driver.setDriverBeizhu("rcjl");// 标识是否是日常记录
				driver.setDriverName(drivers.getDriverName());
				driver.setDriverCarnum(drivers.getDriverCarnum());
				driver.setDriverTel(drivers.getDriverTel());
				driver.setDriverIdcard(drivers.getDriverUtel());// idcard字段
																// 加入用户手机号码

				driver.setDriverDate(sdfs.parse(param.get("date")));
				driver.setDriverId(drivers.getDriverId());
				;
				if (param.get("leavemile") == "" || param.get("leavemile") == null) {
					driver.setDriverLeavemile(null);
				} else {
					driver.setDriverLeavemile(Double.parseDouble(param.get("leavemile")));
				}

				if (param.get("leavedate").length() == 10 || param.get("leavedate") == ""
						|| param.get("leavedate") == null) {
					driver.setDriverLeavedate(null);
				} else {
					driver.setDriverLeavedate(sdf.parse(param.get("leavedate")));
				}

				if (param.get("leavemileuser") == "" || param.get("leavemileuser") == null) {
					driver.setDriverLeavemileuser(null);
				} else {
					driver.setDriverLeavemileuser(Double.parseDouble(param.get("leavemileuser")));
				}

				if (param.get("leavedateuser").length() == 10 || param.get("leavedateuser") == ""
						|| param.get("leavedateuser") == null) {
					driver.setDriverLeavedateuser(null);
				} else {
					driver.setDriverLeavedateuser(sdf.parse(param.get("leavedateuser")));
				}

				if (param.get("backmileuser") == "" || param.get("backmileuser") == null) {
					driver.setDriverBackmileuser(null);
				} else {
					driver.setDriverBackmileuser(Double.parseDouble(param.get("backmileuser")));
				}

				if (param.get("backdateuser").length() == 10 || param.get("backdateuser") == ""
						|| param.get("backdateuser") == null) {
					driver.setDriverBackdateuser(null);
				} else {
					driver.setDriverBackdateuser(sdf.parse(param.get("backdateuser")));
				}

				if (param.get("backmile") == "" || param.get("backmile") == null) {
					driver.setDriverBackmile(null);
				} else {
					driver.setDriverBackmile(Double.parseDouble(param.get("backmile")));
				}

				if (param.get("backdate").length() == 10 || param.get("backdate") == ""
						|| param.get("backdate") == null) {
					driver.setDriverBackdate(null);
				} else {
					driver.setDriverBackdate(sdf.parse(param.get("backdate")));
				}

				if (param.get("milenow") == "" || param.get("milenow") == null) {
					driver.setDriverMilenow(null);
				} else {
					driver.setDriverMilenow(Double.parseDouble(param.get("milenow")));
				}
				flags = driverService.saveOrUpdate(driver, super.ip(request));

			} else {
				for (String key : param.keySet()) {
					String value = param.get(key);
					String[] ary = value.split(",");
					for (String item : ary) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("_id", item);
						map.put("sendu", "1");

						Map<String, String> params = new HashMap<String, String>();
						params.put("driverUaffirm", "1");
						params.put("driverTel", drivers.getDriverTel());
						List<Driver> lists = driverService.getList(params);// 获得用户已确认的记录
						Driver d = driverService.getById(Integer.parseInt(item));// 获得当前发送的记录
						for (Driver driver2 : lists) { // 判断是否可以发送
							DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
							String s = dFormat.format(d.getDriverDate());
							String t = dFormat.format(driver2.getDriverDate());
							if (driver2.getDriverBeizhu().equals("rcjl")) {
								if (s.equals(t)) {
									if (d.getId() != driver2.getId()) {
										json.setCode(Status.FAILED.getCode());
										driverService.delById(Integer.parseInt(item));
										json.setMessage("用户已确认今日信息，无法再次发送！该记录已删除！");
										return json;
									}
								}
							}
						}
						if (d.getDriverMilenow() == null || d.getDriverLeavemile() == null
								|| d.getDriverLeavemileuser() == null || d.getDriverBackmile() == null
								|| d.getDriverBackmileuser() == null) {
							json.setCode(Status.FAILED.getCode());
							json.setMessage("日志还不完整，请填写完整再发送");
							return json;
						}
						if (d.getDriverLeavedate() == null || d.getDriverLeavedateuser() == null
								|| d.getDriverBackdate() == null || d.getDriverBackdateuser() == null) {
							json.setCode(Status.FAILED.getCode());
							json.setMessage("日志还不完整，请填写完整再发送");
							return json;
						}
						// 发送操作
						Driver driver = super.reflect("driver", Driver.class, map);
						flags = driverService.saveOrUpdate(driver, super.ip(request));
					}
				}
			}

			if (flags) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("编辑成功");
			} else {
				json.setMessage("编辑失败");
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 加班记录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("work.html")
	public ModelAndView work(ModelAndView view, HttpServletRequest request) {
		try {

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("work");
		return view;
	}

	/**
	 * 加班历史记录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("work_history.html")
	public ModelAndView work_history(ModelAndView view, HttpServletRequest request) {
		try {
			HttpSession s = request.getSession();
			Driver d = (Driver) s.getAttribute("driver");
			Map<String, String> map = new HashMap<>();
			map.put("driverTel", d.getDriverTel());
			map.put("driverBeizhu", "jbjl");
			map.put("order_date", "desc");
			List<Driver> lists = driverService.getList(map);
			view.addObject("modelswork", lists);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("work_history");
		return view;
	}

	/**
	 * 加班记录编辑前判断
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("workpd")
	public JsonResult workpd(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.SUCCESS);
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				if (driver.getDriverUaffirm().equals("1")) {
					json.setCode(Status.ERROR.getCode());
					json.setMessage("用户已确认不可修改");
					return json;
				}
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}

		return json;
	}

	/**
	 * 加班记录-编辑 入口
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("workedit")
	public ModelAndView workedit(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				view.addObject("model", driver);
			}

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("work");
		return view;
	}

	/**
	 * 加班记录-操作
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "加班记录")
	@RequestMapping("work")
	public JsonResult works(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);
		Map<String, String> mp = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HttpSession session = request.getSession();
		Driver drivers = (Driver) session.getAttribute("driver");
		try {
			if (param.containsKey("_id")) {
				String id = param.get("_id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				if (driver.getDriverUaffirm().equals("1")) {
					json.setMessage("用户已确认不可修改");
					return json;
				}
			}

			boolean flags = false;
			if (param.get("overtimehours") != null && param.get("overtimehours") != "") {
				mp.put("driverTel", drivers.getDriverTel());
				mp.put("driverBeizhu", "jbjl");
				List<Driver> lists = driverService.getList(mp);// 全部加班记录

				int k = 0;
				if (param.containsKey("_id")) {
					k = Integer.parseInt(param.get("_id"));
				}
				for (Driver driver : lists) {
					String s = DateUtils.getDateFormat(driver.getDriverDate(), DateUtils.SHORT_DATE_FORMAT);
					if (s.equals(param.get("date"))) {
						if (k != driver.getId()) { // 编辑判断：是否已存在该记录
							json.setCode(Status.ERROR.getCode());
							json.setMessage("编辑日期已存在记录，请检查");
							return json;
						} else if (k == 0) { // 添加判断：是否已存在该记录
							json.setCode(Status.ERROR.getCode());
							json.setMessage("当日已填写，请去历史记录中进行修改");
							return json;
						}
					}
				}

				Driver driver = super.reflect("driver", Driver.class, param);
				driver.setDriverBeizhu("jbjl");// 标识是否是加班记录
				driver.setDriverIdcard(drivers.getDriverUtel());// idcard字段
																// 加入用户手机号码
				driver.setDriverName(drivers.getDriverName());
				driver.setDriverCarnum(drivers.getDriverCarnum());
				driver.setDriverTel(drivers.getDriverTel());
				driver.setDriverDate(sdf.parse(param.get("date")));
				driver.setDriverOvertimehours(Double.parseDouble(param.get("overtimehours")));
				driver.setDriverOvertimermbtotal(Double.parseDouble(param.get("overtimermbtotal")));
				driver.setDriverId(drivers.getDriverId());
				;
				flags = driverService.saveOrUpdate(driver, super.ip(request));
			} else {
				for (String key : param.keySet()) {
					String value = param.get(key);
					String[] ary = value.split(",");
					for (String item : ary) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("_id", item);
						map.put("sendu", "1");

						Map<String, String> params = new HashMap<String, String>();
						params.put("driverUaffirm", "1");
						params.put("driverTel", drivers.getDriverTel());
						List<Driver> lists = driverService.getList(params);// 获得用户已确认的记录
						Driver d = driverService.getById(Integer.parseInt(item));// 获得当前发送的记录
						for (Driver driver2 : lists) { // 判断是否可发送
							DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
							String s = dFormat.format(d.getDriverDate());
							String t = dFormat.format(driver2.getDriverDate());
							if (driver2.getDriverBeizhu().equals("jbjl") || driver2.getDriverBeizhu().equals("fyjl")) {
								if (s.equals(t)) {
									if (d.getId() != driver2.getId()) {
										json.setCode(Status.FAILED.getCode());
										driverService.delById(Integer.parseInt(item));
										json.setMessage("用户已确认今日信息，无法再次发送！该记录已删除！");
										return json;
									}
								}
							}
						}
						// 发送操作
						Driver driver = super.reflect("driver", Driver.class, map);
						flags = driverService.saveOrUpdate(driver, super.ip(request));
					}
				}
			}

			if (flags) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("编辑成功");
			} else {
				json.setMessage("编辑失败");
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 换车信息-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("transfer.html")
	public ModelAndView transfer(ModelAndView view, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			Driver d = (Driver) session.getAttribute("driver");
			view.addObject("modelstransfer", d);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("transfer");
		return view;
	}

	/**
	 * 判断车牌号是否与他人重复-操作
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "换车编辑")
	@RequestMapping("transfercarnum")
	public JsonResult transferscarnum(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.SUCCESS);
		try {
			Map<String, String> param = super.getParameters(request);
			String carnum = param.get("newcarnum"); // 新车牌号码

			List<Driver> dall = driverService.getList(param);// 全部司机用户
			if (dall.size() > 1) {
				for (Driver driver : dall) {
					if (driver.getDriverPwd() != null && driver.getDriverPwd() != ""
							&& driver.getDriverCarnum().equals(carnum)) {
						json.setCode(Status.FAILED.getCode());
						json.setMessage("车牌号与他人重复，请重新输入！");
					}
				}
			}

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;

	}

	/**
	 * 换车信息-操作
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "换车编辑")
	@RequestMapping("transfer")
	public JsonResult transfers(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			Map<String, String> param = super.getParameters(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm");
			String carnum = param.get("newcarnum"); // 新车牌号码

			// 修改用户车牌
			Driver driver = (Driver) session.getAttribute("driver");
			driver.setDriverCarnum(carnum);
			boolean flag = driverService.saveOrUpdate(driver, super.ip(request));
			if (flag) {
				// 添加换车记录
				Driver drivers = new Driver();
				drivers.setDriverBeizhu("hcjl");// 标识是否是换车记录
				drivers.setDriverDate(sdf.parse(param.get("date"))); // 换车日期
				drivers.setDriverName(driver.getDriverName()); // 司机名
				drivers.setDriverTel(driver.getDriverTel()); // 司机电话
				drivers.setDriverReason(param.get("reason")); // 换车原因
				drivers.setDriverNewcarnum(param.get("newcarnum")); // 新车号码
				drivers.setDriverNewcarmile(Double.parseDouble(param.get("newcarmile")));// 新车里程
				drivers.setDriverJcarmile(Double.parseDouble(param.get("jcarmile")));// 旧车里程
				drivers.setDriverJcarnum(param.get("jcarnum")); // 旧车号码
				drivers.setDriverId(driver.getDriverId());
				; // 工号
				boolean flags = driverService.saveOrUpdate(drivers, super.ip(request));
				if (flags) {
					json.setCode(Status.SUCCESS.getCode());
					json.setMessage("添加成功");
				} else {
					json.setMessage("添加失败");
				}
			} else {
				json.setMessage("修改车牌失败");
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;

	}

	/**
	 * 帮助中心-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("help.html")
	public ModelAndView help(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			param.put("recordIsDisplay", "0");
			List<Record> list = recordService.getPage(param); // 未隐藏的帮助信息
			view.addObject("models", list);
			view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("help");
		return view;
	}

	/**
	 * 忘记密码-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("forget.html")
	public ModelAndView forget(ModelAndView view, HttpServletRequest request) {
		try {

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("forget");
		return view;
	}

	/**
	 * 培训详情-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("detail.html")
	public ModelAndView detail(ModelAndView view, HttpServletRequest request, HttpSession session) {
		Driver driver = (Driver) session.getAttribute("driver");
		try {
			String id = request.getParameter("id");
			view.addObject(recordService.browse(id));// 浏览量

			Record r = recordService.getById(Integer.parseInt(id));// 获得培训详情
			view.addObject("model", r);

			Map<String, String> tmp = new HashMap<String, String>();// 获得司机培训记录
			tmp.put("driverId", id);
			tmp.put("driverTel", driver.getDriverTel());
			tmp.put("driverUsname", "pxtj");
			Driver d = driverService.getList(tmp).get(0);
			view.addObject("models", d);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("detail");
		return view;
	}

	/**
	 * 培训详情-操作
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "培训编辑")
	@RequestMapping("detail")
	public JsonResult detailedit(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);
		Driver drivers = (Driver) session.getAttribute("driver");
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Map<String, String> tmp = new HashMap<String, String>();
				tmp.put("driverId", id);
				tmp.put("driverUsname", "pxtj");
				tmp.put("driverTel", drivers.getDriverTel());
				Driver d = driverService.getList(tmp).get(0);// 查找出相对应的培训记录

				d.setDriverTrainstate("1");// 改变培训状态
				boolean flags = driverService.saveOrUpdate(d, super.ip(request));
				if (flags) {
					json.setCode(Status.SUCCESS.getCode());
					json.setMessage("编辑成功");
				} else {
					json.setMessage("编辑失败");
				}
			}

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 联系客服-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("customer.html")
	public ModelAndView customer(ModelAndView view, HttpServletRequest request) {
		try {

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("customer");
		return view;
	}

	/**
	 * 费用记录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("cost.html")
	public ModelAndView cost(ModelAndView view, HttpServletRequest request) {
		try {

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("cost");
		return view;
	}

	/**
	 * 费用历史记录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("cost_history.html")
	public ModelAndView cost_history(ModelAndView view, HttpServletRequest request) {
		try {
			HttpSession s = request.getSession();
			Driver d = (Driver) s.getAttribute("driver");
			Map<String, String> map = new HashMap<>();
			map.put("driverTel", d.getDriverTel());
			map.put("driverBeizhu", "fyjl");
			map.put("order_date", "desc");
			List<Driver> lists = driverService.getList(map);
			view.addObject("modelscost", lists);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("cost_history");
		return view;
	}

	/**
	 * 费用记录编辑前判断
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("costpd")
	public JsonResult costpd(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.SUCCESS);
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				if (driver.getDriverUaffirm().equals("1")) {
					json.setCode(Status.ERROR.getCode());
					json.setMessage("用户已确认不可修改");
					return json;
				}
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 费用记录-编辑 入口
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("costedit")
	public ModelAndView costedit(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				view.addObject("model", driver);
			}

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("cost");
		return view;
	}

	/**
	 * 费用记录-操作
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "费用记录")
	@RequestMapping("cost")
	public JsonResult costs(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Driver drivers = (Driver) session.getAttribute("driver");
		try {
			if (param.containsKey("_id")) {
				String id = param.get("_id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				if (driver.getDriverUaffirm().equals("1")) {

					json.setMessage("用户已确认不可修改");
					return json;
				}
			}

			boolean flags = false;
			if (param.get("rmb") != null && param.get("rmb") != "") {

				// 判断要添加记录是否已存在
				Map<String, String> tmp = new HashMap<String, String>();
				tmp.put("driverBeizhu", "fyjl");
				tmp.put("driverTel", drivers.getDriverTel());
				List<Driver> lists = driverService.getList(tmp);// 获得所有的费用记录

				String rmbassume = param.get("rmbassume");// 费用承担
				String rmbgoal = param.get("rmbgoal");// 费用目的
				String rmbtype = param.get("rmbtype");// 费用类型
				int ids = 0;
				if (param.containsKey("_id")) {
					ids = Integer.parseInt(param.get("_id"));
				}
				String date = param.get("date");
				for (Driver driver : lists) { // 循环判断是否是同一种记录
					String key = DateUtils.getDateFormat(driver.getDriverDate(), DateUtils.SHORT_DATE_FORMAT);
					if (date.equals(key)) {
						if (rmbassume.equals(driver.getDriverRmbassume()) && rmbgoal.equals(driver.getDriverRmbgoal())
								&& rmbtype.equals(driver.getDriverRmbtype())) {
							if (ids != 0 && ids != driver.getId()) { // 编辑时判断：该记录是否已存在
								json.setCode(Status.FAILED.getCode());
								json.setMessage("编辑日期已存在记录，请检查");
								return json;

							} else if (ids == 0) { // 添加时判断：该记录是否已存在
								json.setCode(Status.FAILED.getCode());
								json.setMessage("该记录已填写，请去历史记录中进行修改");
								return json;
							}
						}
					}
				}

				Driver driver = new Driver();
				driver = super.reflect("driver", Driver.class, param);
				driver.setDriverBeizhu("fyjl");// 标识是否是费用记录
				driver.setDriverIdcard(drivers.getDriverUtel());// idcard字段
																// 加入用户手机号码
				driver.setDriverName(drivers.getDriverName());
				driver.setDriverCarnum(drivers.getDriverCarnum());
				driver.setDriverTel(drivers.getDriverTel());
				driver.setDriverDate(sdf.parse(param.get("date")));
				driver.setDriverRmb(Double.parseDouble(param.get("rmb")));
				driver.setDriverId(drivers.getDriverId());
				;
				flags = driverService.saveOrUpdate(driver, super.ip(request));
			} else {
				for (String key : param.keySet()) {
					String value = param.get(key);
					String[] ary = value.split(",");
					for (String item : ary) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("_id", item);
						map.put("sendu", "1");
						Map<String, String> params = new HashMap<String, String>();
						params.put("driverUaffirm", "1");
						params.put("driverTel", drivers.getDriverTel());
						List<Driver> lists = driverService.getList(params);// 获得用户已确认的记录
						Driver d = driverService.getById(Integer.parseInt(item));// 获得当前发送的记录
						// 判断是否可以发送
						for (Driver driver2 : lists) {
							DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
							String s = dFormat.format(d.getDriverDate());
							String t = dFormat.format(driver2.getDriverDate());
							if (driver2.getDriverBeizhu().equals("jbjl") || driver2.getDriverBeizhu().equals("fyjl")) {
								if (s.equals(t)) {
									if (d.getId() != driver2.getId()) {
										json.setCode(Status.FAILED.getCode());
										driverService.delById(Integer.parseInt(item));
										json.setMessage("用户已确认今日信息，无法再次发送！该记录已删除!");
										return json;
									}
								}
							}
						}
						// 发送操作
						Driver driver = super.reflect("driver", Driver.class, map);
						flags = driverService.saveOrUpdate(driver, super.ip(request));
					}
				}
			}
			if (flags) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("编辑成功");
			} else {
				json.setMessage("编辑失败");
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 加油记录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("cheer.html")
	public ModelAndView cheer(ModelAndView view, HttpServletRequest request) {
		try {

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("cheer");
		return view;
	}

	/**
	 * 加油历史记录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("cheer_history.html")
	public ModelAndView cheer_history(ModelAndView view, HttpServletRequest request) {
		try {
			HttpSession s = request.getSession();
			Driver d = (Driver) s.getAttribute("driver");
			Map<String, String> param = super.getParameters(request);
			param.put("driverTel", d.getDriverTel());
			param.put("driverBeizhu", "jyjl");
			param.put("order_date", "desc");
			List<Driver> lists = driverService.getList(param);
			view.addObject("modelscheer", lists);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("cheer_history");
		return view;
	}

	/**
	 * 加油编辑—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("cheerhistoryedit")
	public ModelAndView cheeredit(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver driver = driverService.getById(Integer.parseInt(id));
				view.addObject("model", driver);
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		} finally {

		}
		view.setViewName("cheer");
		return view;
	}

	/**
	 * 加油记录-操作
	 * 
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "加油记录")
	@RequestMapping("cheer")
	public JsonResult cheers(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm");
		HttpSession session = request.getSession();
		try {
			Driver drivers = (Driver) session.getAttribute("driver");

			// 按照日期查找最近的所有加油记录 倒序
			/*
			 * Map<String,String> tmp=new HashMap<String,String>();
			 * tmp.put("driverBeizhu", "jyjl"); tmp.put("driverTel",
			 * drivers.getDriverTel()); tmp.put("order_date","desc");
			 * List<Driver> listdri=driverService.getList(tmp);
			 * if(!listdri.isEmpty()){
			 * if(listdri.get(0).getDriverMilenow()>=Integer.parseInt(param.get(
			 * "milenow"))){ json.setCode(Status.FAILED.getCode());
			 * json.setMessage("路码小于上一条，请重新输入。"); return json; } }
			 */

			/*
			 * Map<String,String> tmp=new HashMap<String,String>();
			 * tmp.put("driverBeizhu", "jyjl"); tmp.put("driverTel",
			 * drivers.getDriverTel()); tmp.put("order_date","desc");
			 * List<Driver> listdri=driverService.getList(tmp); Date
			 * date=sdf.parse(param.get("date"));//选择的加油日期
			 * if(!listdri.isEmpty()){ for (int i = 0; i < listdri.size(); i++)
			 * {
			 * 
			 * //date在 dates和dates2之间，判断输入的路码是否正确
			 * if(date.after(listdri.get(i).getDriverDate())){
			 * if(listdri.get(0).getDriverMilenow()>=Integer.parseInt(param.get(
			 * "milenow"))){ json.setCode(Status.FAILED.getCode());
			 * json.setMessage("路码小于上一条，请重新输入。"); return json; }else
			 * if(listdri.get(1).getDriverMilenow()<=Integer.parseInt(param.get(
			 * "milenow"))){ json.setCode(Status.FAILED.getCode());
			 * json.setMessage("路码大于下一条，请重新输入。"); return json; } }
			 * 
			 * } }
			 */

			Driver driver = super.reflect("driver", Driver.class, param);
			driver.setDriverBeizhu("jyjl");// 标识是否是加油记录
			driver.setDriverName(drivers.getDriverName());
			driver.setDriverCarnum(drivers.getDriverCarnum());
			driver.setDriverTel(drivers.getDriverTel());
			driver.setDriverDate(sdf.parse(param.get("date")));
			driver.setDriverMilenow(Double.parseDouble(param.get("milenow")));
			driver.setDriverId(drivers.getDriverId());
			;
			boolean flags = driverService.saveOrUpdate(driver, super.ip(request));
			if (flags) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("编辑成功");
			} else {
				json.setMessage("编辑失败");
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		return json;

	}

	/**
	 * 修改密码-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("change.html")
	public ModelAndView change(ModelAndView view, HttpServletRequest request) {
		try {

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName("change");
		return view;
	}

	/**
	 * 修改密码-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "修改密码-操作")
	@RequestMapping("change")
	public JsonResult changes(HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			String pwd = request.getParameter("npwd");// 新密码
			String upwd = request.getParameter("pwd");// 新密码重复输入
			String userpwd = request.getParameter("opwd");// 判断输入的密码是否和原先的一致(旧密码)
			if (StringUtils.isNotBlank(userpwd) && StringUtils.isNotBlank(upwd) && StringUtils.isNotBlank(pwd)) {
				DesUtils des = new DesUtils(Constants.PUBLIC_SECRET);
				userpwd = des.encrypt(userpwd);
				if (StringUtils.isNotBlank(userpwd)) {
					Map<String, String> params = new HashMap<>();
					params.put("driverPwd", MD5Utils.md5(userpwd));
					List<Driver> drivers = driverService.getList(params);
					if (drivers != null && !drivers.isEmpty()) {
						if (upwd.equals(pwd)) {

							des = new DesUtils(Constants.PRIVATE_SECRET);
							pwd = des.encrypt(pwd);
							HttpSession session = request.getSession();
							Driver driver = (Driver) session.getAttribute("driver");// 获得当前用户
							driver.setDriverPwd(MD5Utils.md5(pwd));
							boolean flag = driverService.saveOrUpdate(driver, super.ip(request));
							if (flag) {
								json.setCode(Status.SUCCESS.getCode());
								json.setMessage("修改成功");
								session.removeAttribute("driver");
							} else {
								json.setMessage("修改失败");
							}
						} else {
							json.setMessage("密码错误");
						}
					} else {
						json.setMessage("密码错误");
					}
				} else {
					json.setMessage("密码错误");
				}
			} else {
				json.setMessage("密码错误");
			}
		} catch (Exception e) {
			json.setCode(Status.ERROR.getCode());
			json.setMessage("系统错误,用户登录失败");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 微信定位需要的参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("share")
	public JsonResult share(HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(Status.FAILED);
		String currTime = StrUtils.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = StrUtils.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String timestamp = ShaUtils.getTimeStamp();
		ServletContext servlet = request.getSession().getServletContext();
		Map<String, Object> map = (Map<String, Object>) servlet.getAttribute(Constants.WEB_WX_ACCESS_TOKEN);
		map = WxUtils.getTokenOrTicket(servlet);
		String ticket = map.get("ticket").toString();
		String url = request.getHeader("Referer");
		String sign = WxUtils.sign(ticket, url, strReq, timestamp);
		Map<String, String> param = new HashMap<>();
		param.put("appId", Constants.WEB_WX_APPID);
		param.put("timeStamp", timestamp);
		param.put("nonceStr", strReq);
		param.put("signature", sign);
		jsonResult.setCode(Status.SUCCESS.getCode());
		jsonResult.setResult(param);
		return jsonResult;
	}

	@RequestMapping("/openid.html")
	public ModelAndView openid(HttpServletRequest request, ModelAndView view, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			if (StrUtils.isNotEmpty(code)) {
				String openid = WxUtils.getOpenid(code);
				HttpSession session = request.getSession();
				session.setAttribute(Constants.WEB_DRIVWER_OPENID_SESSION, openid);// 将访问用户的openid存入session，
				view.addObject("openid", openid);
				Driver user = (Driver) super.getWebDriver(request);
				if (user != null) {
					user.setDriverOpenid(openid);
					driverService.saveOrUpdate(user, super.ip(request));
					String redirectUrl = request.getParameter("redirectUrl");
					if (StrUtils.isNotEmpty(redirectUrl))
						view.setViewName("redirect:" + request.getParameter("redirectUrl"));
					else
						view.setViewName("redirect:/index.html");
				} else {
					Map<String, String> map = new HashMap<>();
					map.put("driverOpenid", openid);
					map.put("driverIsdisplay", Constants.IS_DISPLAY_NOT);
					List<Driver> uList = driverService.getList(map);
					String requrl = request.getParameter("ref");
					if (StrUtils.isEmpty(requrl))
						requrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
								+ "/index.html";
					if (uList != null && uList.size() == 1) {
						user = uList.get(0);
						session.setAttribute(Constants.WEB_DRIVER_SESSION, user);
						session.setAttribute("driver", user);
						view.setViewName("redirect:" + requrl);
					} else {
						view.setViewName("redirect:/login.html");
					}

				}
			} else {
				view.setViewName("redirect:/login.html");
			}

		} catch (Exception e) {
			e.printStackTrace();
			view.setViewName("redirect:/login.html");
		}
		return view;
	}

	@RequestMapping("/wxapi.html")
	public void wxapi(HttpServletRequest request, HttpServletResponse response) {
		try {
			String redirectUrl = request.getParameter("redirectUrl");

			String ref = request.getParameter("ref") + "";
			String Url = request.getScheme() + "://" + request.getServerName() + "/openid.html?ref=" + ref;
			if (StrUtils.isNotEmpty(redirectUrl))
				Url += "&redirectUrl=" + redirectUrl;
			WxUtils.getCode(Url, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
