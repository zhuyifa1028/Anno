package com.product.rental.front.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.product.bean.Area;
import com.product.bean.Driver;
import com.product.bean.Record;
import com.product.bean.User;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.AreaService;
import com.product.service.DriverService;
import com.product.service.RecordService;
import com.product.service.UserService;
import com.product.utils.DateUtils;
import com.product.utils.DesUtils;
import com.product.utils.MD5Utils;
import com.product.utils.StrUtils;
import com.product.utils.WxUtils;

import net.sf.json.JSONArray;

/**
 * 用户首页-控制类
 * 
 * @author ymc
 *
 */
@RestController
@RequestMapping("/zh")
public class ScienceController extends BaseController {

	private static Log tag = LogFactory.getLog("tag");

	@Autowired
	private RecordService recordService;

	@Autowired
	private UserService userService;

	@Autowired
	private DriverService driverService;

	@Autowired
	private AreaService areaService;

	/** 基础地址 */
	private String path = "/zh/";

	/**
	 * 用户登录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("login.html")
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
		view.setViewName(path + "login");
		return view;
	}

	/**
	 * 用户登录-操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SystemLog(description = "用户登录-操作")
	@RequestMapping("userlogin")
	public JsonResult login(HttpServletRequest request, ModelAndView view, HttpServletResponse response) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			String usertel = request.getParameter("tel");
			String userpwd = request.getParameter("pwd");
			if (StringUtils.isNotBlank(usertel) && StringUtils.isNotBlank(userpwd)) {
				DesUtils des = new DesUtils(Constants.PUBLIC_SECRET);
				userpwd = des.encrypt(userpwd);
				if (StringUtils.isNotBlank(userpwd)) {
					Map<String, String> param = new HashMap<>();
					param.put("userTel", usertel);
					param.put("userPwd", MD5Utils.md5(userpwd));
					List<User> users = userService.getList(param);
					if (users != null && !users.isEmpty() && users.size() == 1) {
						User user = users.get(0);
						if (user.getUserState() != null && user.getUserState().equals(User.USER_STATE_YES)) {
							json.setMessage("用户不可使用,请联系管理员");
						} else {
							HttpSession session = request.getSession();
							session.setAttribute(Constants.WEB_USER_SESSION, user);
							session.setAttribute("user", user);
							json.setCode(Status.SUCCESS.getCode());
							json.setMessage("用户登录成功");
							json.setResult(user);
							// 记录登录过的账号
							Cookie cookie = new Cookie("logined_user", user.getUserTel());
							response.addCookie(cookie);
						}
					} else {
						json.setMessage("用户名称或密码错误");
					}
				} else {
					json.setMessage("用户密码加密失败");
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
	 * 首页-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("index.html")
	public ModelAndView index(ModelAndView view, HttpServletRequest request) {
		boolean hasNew = false;

		try {
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");// 当前用户

			// 日常记录
			Map<String, Object> param = new HashMap<>();
			param.put("driverBeizhu", "rcjl");
			param.put("driverSendu", "1");
			param.put("driverIdcard", u.getUserTel());
			param.put("driverUaffirm", "0");

			long rcjlCount = driverService.getCount(param);
			if (rcjlCount > 0) {
				hasNew = true;
			}

			param.put("driverBeizhu", "fyjl");

			long fyjlCount = driverService.getCount(param);
			if (fyjlCount > 0) {
				hasNew = true;
			}

			param.put("driverBeizhu", "jbjl");

			long jbjlCount = driverService.getCount(param);
			if (jbjlCount > 0) {
				hasNew = true;
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}

		view.addObject("hasNew", hasNew);
		view.setViewName(path + "index");
		return view;
	}

	/**
	 * 日志确认-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("daily.html")
	public ModelAndView daily(ModelAndView view, HttpServletRequest request) {
		boolean hasNew_rcjl = false;
		boolean hasNew_fyjlAndJbjl = false;

		try {
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");// 当前用户

			// 日常记录
			Map<String, Object> param = new HashMap<>();
			param.put("driverBeizhu", "rcjl");
			param.put("driverSendu", "1");
			param.put("driverIdcard", u.getUserTel());
			param.put("driverUaffirm", "0");

			long rcjlCount = driverService.getCount(param);
			if (rcjlCount > 0) {
				hasNew_rcjl = true;
			}

			param.put("driverBeizhu", "fyjl");

			long fyjlCount = driverService.getCount(param);
			if (fyjlCount > 0) {
				hasNew_fyjlAndJbjl = true;
			}

			param.put("driverBeizhu", "jbjl");

			long jbjlCount = driverService.getCount(param);
			if (jbjlCount > 0) {
				hasNew_fyjlAndJbjl = true;
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}

		view.addObject("hasNew_rcjl", hasNew_rcjl);
		view.addObject("hasNew_fyjlAndJbjl", hasNew_fyjlAndJbjl);
		view.setViewName(path + "daily");
		return view;
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
			if (key.equals(Constants.WEB_USER_SESSION)) {// 清除用户表中openid记录，保证下次访问不会自动登录
				User user = (User) session.getAttribute(Constants.WEB_USER_SESSION);
				user.setUserOpenid("");
				userService.saveOrUpdate(user, super.ip(request));
			}
			session.removeAttribute(key);
		}
		view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		view.setViewName(path + "login");
		return view;
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
			param.put("recordKftel", "bzzx");
			List<Record> list = recordService.getPage(param);
			view.addObject("models", list);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.addObject("params", param);
		view.setViewName(path + "help");
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
			String url = request.getHeader("Referer");
			if (StringUtils.isNotBlank(url)) {
				view.addObject("url", url);
			}
			view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName(path + "forget");
		return view;
	}

	/**
	 * 日常记录-操作
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("daily_history")
	public JsonResult daily_historys(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);
		try {
			String uaffirm = request.getParameter("uaffirm");
			for (String key : param.keySet()) {
				String value = param.get(key);
				String[] ary = value.split(",");
				for (String item : ary) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("_id", item);
					map.put("uaffirm", uaffirm);
					if (Integer.parseInt(item) == 1) {
						json.setMessage("你还没有选择任何内容!");
					} else {
						Driver driver = super.reflect("driver", Driver.class, map);
						boolean flag = driverService.saveOrUpdate(driver, super.ip(request));
						if (flag) {
							json.setCode(Status.SUCCESS.getCode());
							json.setMessage("编辑成功");
						} else {
							json.setMessage("编辑失败");
						}
					}
				}
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		return json;
	}

	/**
	 * 日常记录-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("daily_history.html")
	public ModelAndView daily_history(ModelAndView view, HttpServletRequest request) {
		List<Driver> list = new ArrayList<>();

		try {
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");// 当前用户

			Map<String, String> param = super.getParameters(request);
			param.put("driverBeizhu", "rcjl");
			param.put("driverSendu", "1");
			param.put("driverIdcard", u.getUserTel());

			list = driverService.getList(param);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}

		view.addObject("models", list);
		view.setViewName(path + "daily_history");
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
	@SystemLog(description = "修改密码-操作")
	@RequestMapping("change")
	public JsonResult changes(HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			String pwd = request.getParameter("pwd");// 新密码
			String upwd = request.getParameter("upwd");// 第二次输入的密码
			String userpwd = request.getParameter("userpwd");// 判断输入的密码是否和原先的一致
			String id = request.getParameter("id");// 判断输入的密码是否和原先的一致
			if (StringUtils.isNotBlank(userpwd) && StringUtils.isNotBlank(upwd) && StringUtils.isNotBlank(pwd)
					&& StringUtils.isNotBlank(id)) {
				DesUtils des = new DesUtils(Constants.PUBLIC_SECRET);
				userpwd = des.encrypt(userpwd);
				if (StringUtils.isNotBlank(userpwd)) {
					Map<String, String> params = new HashMap<>();
					params.put("id", id);
					params.put("userPwd", MD5Utils.md5(userpwd));
					// 判断输入的原密码是否和数据库相等
					List<User> users = userService.getList(params);
					if (users != null && !users.isEmpty() && users.size() == 1) {
						// 判断俩次输入的密码是否相等
						if (upwd.equals(pwd)) {
							Map<String, String> param = new HashMap<String, String>();
							des = new DesUtils(Constants.PRIVATE_SECRET);
							pwd = des.encrypt(pwd);
							param.put("pwd", MD5Utils.md5(pwd));
							param.put("_id", id);
							User user = super.reflect("user", User.class, param);
							boolean flag = userService.saveOrUpdate(user, super.ip(request));
							if (flag) {
								json.setCode(Status.SUCCESS.getCode());
								json.setMessage("修改成功");
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
	 * 修改密码-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("change.html")
	public ModelAndView change(ModelAndView view, HttpServletRequest request, HttpSession session) {
		try {
			User u = (User) session.getAttribute("user");// 当前用户
			view.addObject("model", u);
			view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName(path + "change");
		return view;
	}

	/**
	 * 投诉建议-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("advice.html")
	public ModelAndView advice(ModelAndView view, HttpServletRequest request, HttpSession session) {
		try {
			User u = (User) session.getAttribute("user");// 当前用户
			view.addObject("model", u);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName(path + "advice");
		return view;
	}

	/**
	 * 投诉建议-操作
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("advice")
	public JsonResult advices(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);
		try {
			String id = request.getParameter("driverid");
			Driver d = driverService.getById(Integer.parseInt(id));
			User user = super.reflect("user", User.class, param);
			user.setUserAdmin(d.getDriverCarnum());
			user.setUserAddre(d.getDriverName());
			user.setUserHeadpic(d.getDriverId().toString());
			user.setUserDriverid("0407");
			boolean flag = userService.saveOrUpdate(user, super.ip(request));
			if (flag) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("编辑成功");
			} else {
				json.setMessage("编辑失败");
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		return json;
	}

	/**
	 * 满意度-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("satisfied.html")
	public ModelAndView satisfied(ModelAndView view, HttpServletRequest request, HttpSession session) {
		try {
			User u = (User) session.getAttribute("user");// 当前用户
			view.addObject("model", u);
			String tel = u.getUserTel();
			Map<String, String> map = new HashMap<String, String>();
			map.put("driverUtel", tel);
			List<Driver> list = driverService.getPage(map);
			view.addObject("models", list);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName(path + "satisfied");
		return view;
	}

	/**
	 * 满意度-操作
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("satisfied")
	public JsonResult satisfieds(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);
		try {
			String id = request.getParameter("utel");
			int a = Integer.parseInt(id);
			Driver drivers = driverService.getById(a);
			String carnum = drivers.getDriverCarnum();
			String name = drivers.getDriverName();
			String jobnum = drivers.getDriverId().toString();

			String uid = request.getParameter("id");
			User user = userService.getById(Integer.parseInt(uid));
			Driver driver = super.reflect("driver", Driver.class, param);
			driver.setDriverCarnum(carnum);
			driver.setDriverName(name);
			driver.setDriverIdcard(user.getUserName());
			driver.setDriverCarname(user.getUserTel());
			driver.setDriverHeadpic(jobnum);
			driver.setDriverUsname("0507");
			boolean flag = driverService.saveOrUpdate(driver, super.ip(request));
			if (flag) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("编辑成功");
			} else {
				json.setMessage("编辑失败");
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		return json;
	}

	/**
	 * 我要分享-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("share.html")
	public ModelAndView share(ModelAndView view, HttpServletRequest request) {
		try {
			String url = request.getHeader("Referer");
			if (StringUtils.isNotBlank(url)) {
				view.addObject("url", url);
			}
			view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName(path + "share");
		return view;
	}

	/**
	 * 用车信息-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("transport.html")
	public ModelAndView transport(ModelAndView view, HttpServletRequest request) {
		try {
			// 设置区域缓存
			String pid = String.valueOf(Area.AREA_DEFAULT_ID);
			Map<String, String> param1 = new HashMap<>();
			param1.put("parent_id", String.valueOf(Area.AREA_DEFAULT_ID));
			param1.put("areaIsDisplay", "0");
			List<Area> areas = areaService.getChilds(param1);
			Map<String, String> keys = new HashMap<>();
			keys.put(pid, "true");
			keys.put("areaIsDisplay", "0");
			JSONArray json = areaService.getChildsTrees(areas, keys);
			ServletContext servlet = request.getServletContext();
			servlet.setAttribute(Area.AREA_MAP_KEY, json);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName(path + "transport");
		return view;
	}

	/**
	 * 信息生成-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("trip.html")
	public ModelAndView trip(ModelAndView view, HttpServletRequest request) {
		try {
			view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName(path + "trip");
		return view;
	}

	/**
	 * 费用详情-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("work_detail.html")
	public ModelAndView work_detail(ModelAndView view, HttpServletRequest request, HttpSession session) {
		User u = (User) session.getAttribute("user");// 当前用户
		view.addObject("model", u);
		try {
			Map<String, String> parameters = super.getParameters(request);
			List<Driver> lists = driverService.getList(parameters);

			view.addObject("models2", lists);

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.setViewName(path + "work_detail");
		return view;
	}

	/**
	 * 加班和费用-操作
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("work_history")
	public JsonResult work_historys(ModelAndView view, HttpServletRequest request, HttpSession session) {
		JsonResult json = new JsonResult(Status.FAILED);
		Map<String, String> param = super.getParameters(request);

		if (param.isEmpty()) {
			json.setMessage("你还没有选择任何内容!");
		}

		try {
			for (String key : param.keySet()) {
				String value = param.get(key);
				String[] ary = value.split(",");
				for (String item : ary) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("_id", item);
					if (Integer.parseInt(item) == 1) {
						json.setMessage("你还没有选择任何内容!");
					} else {
						Driver driver = driverService.getById(Integer.parseInt(item));// 获得当前所选中的时间
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String string = simpleDateFormat.format(driver.getDriverDate());

						User u = (User) session.getAttribute("user");// 当前用户
						view.addObject("model", u);
						Map<String, String> maps = new HashMap<String, String>();
						maps.put("driverIdcard", u.getUserTel());
						maps.put("driverSendu", "1");
						List<Driver> list = driverService.getList(maps);
						for (Driver drivers : list) {
							String keys = DateUtils.getDateFormat(drivers.getDriverDate(), DateUtils.SHORT_DATE_FORMAT);
							if (string.equals(keys)) {
								if (!drivers.getDriverBeizhu().equals("rcjl")) {
									drivers.setDriverUaffirm("1");
									boolean flag = driverService.saveOrUpdate(drivers, super.ip(request));
									if (flag) {
										json.setCode(Status.SUCCESS.getCode());
										json.setMessage("编辑成功");
									} else {
										json.setMessage("编辑失败");
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		return json;
	}

	/**
	 * 加班和费用-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("work_history.html")
	public ModelAndView work_history(ModelAndView view, HttpServletRequest request, HttpSession session) {
		Map<String, String> param = super.getParameters(request);
		try {
			User u = (User) session.getAttribute("user");// 当前用户
			view.addObject("model", u);
			param.put("driverSendu", "1");
			param.put("driverIdcard", u.getUserTel());

			param.put("order_date", "desc");
			List<Driver> list = driverService.getList(param);

			List<Driver> y = new LinkedList<>();
			List<Driver> n = new LinkedList<>();

			for (Driver driver : list) {
				String beizhu = driver.getDriverBeizhu();
				if ("jbjl".equals(beizhu) || "fyjl".equals(beizhu)) {
					if ("0".equals(driver.getDriverUaffirm())) {
						n.add(driver);
					} else {
						y.add(driver);
					}
				}
			}

			view.addObject("y", y);
			view.addObject("n", n);

		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.addObject("params", param);
		view.setViewName(path + "work_history");
		return view;
	}

	/**
	 * 选择评价司机-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("selectdriver.html")
	public ModelAndView selectdriver(ModelAndView view, HttpServletRequest request, HttpSession session) {
		Map<String, String> param = super.getParameters(request);
		try {
			User u = (User) session.getAttribute("user");// 当前用户
			String tel = u.getUserTel();
			Map<String, String> map = new HashMap<String, String>();
			map.put("driverUtel", tel);
			map.put("driverLeavel", "9");
			List<Driver> list = driverService.getPage(map);
			view.addObject("models", list);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.addObject("params", param);
		view.setViewName(path + "selectdriver");
		return view;
	}

	/**
	 * 选择评价司机-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("selectdrivers.html")
	public ModelAndView selectdrivers(ModelAndView view, HttpServletRequest request, HttpSession session) {
		Map<String, String> param = super.getParameters(request);
		try {
			User u = (User) session.getAttribute("user");// 当前用户
			String tel = u.getUserTel();
			Map<String, String> map = new HashMap<String, String>();
			map.put("driverUtel", tel);
			map.put("driverLeavel", "9");
			List<Driver> list = driverService.getPage(map);
			view.addObject("models", list);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.addObject("params", param);
		view.setViewName(path + "selectdrivers");
		return view;
	}

	/**
	 * 打电话 选择司机-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("phone.html")
	public ModelAndView phone(ModelAndView view, HttpServletRequest request, HttpSession session) {
		Map<String, String> param = super.getParameters(request);
		try {
			User u = (User) session.getAttribute("user");// 当前用户
			String tel = u.getUserTel();
			Map<String, String> map = new HashMap<String, String>();
			map.put("driverUtel", tel);
			map.put("driverLeavel", "9");
			List<Driver> list = driverService.getPage(map);
			view.addObject("models", list);
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
		view.addObject("params", param);
		view.setViewName(path + "phone");
		return view;
	}

	@RequestMapping("/openid.html")
	public ModelAndView openid(HttpServletRequest request, ModelAndView view, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			if (StrUtils.isNotEmpty(code)) {
				String openid = WxUtils.getOpenid(code);
				HttpSession session = request.getSession();
				session.setAttribute(Constants.WEB_USER_OPENID_SESSION, openid);// 将访问用户的openid存入session，
				view.addObject("openid", openid);
				User user = (User) super.getWebUser(request);
				if (user != null) {
					user.setUserOpenid(openid);
					userService.saveOrUpdate(user, super.ip(request));
					String redirectUrl = request.getParameter("redirectUrl");
					if (StrUtils.isNotEmpty(redirectUrl))
						view.setViewName("redirect:" + request.getParameter("redirectUrl"));
					else
						view.setViewName("redirect:/zh/index.html");
				} else {
					Map<String, String> map = new HashMap<>();
					map.put("openid", openid);
					map.put("is_display", Constants.IS_DISPLAY_NOT);
					List<User> uList = userService.getList(map);
					String requrl = request.getParameter("ref");
					if (StrUtils.isEmpty(requrl))
						requrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
								+ "/zh/index.html";
					if (uList != null && uList.size() == 1) {
						user = uList.get(0);
						session.setAttribute(Constants.WEB_USER_SESSION, user);
						session.setAttribute("user", user);
						view.setViewName("redirect:" + requrl);
					} else {
						view.setViewName("redirect:/zh/login.html");
					}
				}
			} else {
				view.setViewName("redirect:/zh/login.html");
			}

		} catch (Exception e) {
			e.printStackTrace();
			view.setViewName("redirect:/zh/login.html");
		}
		return view;
	}

	@RequestMapping("/wxapi.html")
	public void wxapi(HttpServletRequest request, HttpServletResponse response) {
		try {
			String redirectUrl = request.getParameter("redirectUrl");

			String ref = request.getParameter("ref") + "";
			String Url = request.getScheme() + "://" + request.getServerName() + "/zh/openid.html?ref=" + ref;
			if (StrUtils.isNotEmpty(redirectUrl))
				Url += "&redirectUrl=" + redirectUrl;
			WxUtils.getCode(Url, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
