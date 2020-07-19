package com.product.rental.admin.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.product.annotation.SystemLog;
import com.product.bean.Admin;
import com.product.bean.Driver;
import com.product.bean.User;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.DriverService;
import com.product.service.UserService;
import com.product.utils.DateUtils;
import com.product.utils.DesUtils;
import com.product.utils.MD5Utils;

import net.sf.json.JSONObject;

/**
 * 用户确认管理-控制类 投诉反馈--控制类
 * 
 * @author wh
 *
 */
@RestController
@RequestMapping("/")
public class UserController extends BaseController {

	private static Log tag = LogFactory.getLog("tag");

	@Autowired
	private UserService userService;

	@Autowired
	private DriverService driverService;

	/** 基础地址 */
	private String path = "/index/";

	/**
	 * 全部用户-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/10.html")
	public ModelAndView list(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			if (param.containsKey("pageNum")) {
				param.put("userDriverid", "1229");
				param.put("order_id", "desc");
				List<User> lists = userService.getPage(param);
				view.addObject("models", lists);
			}else{
				
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdminuser() != null && admin.getAdminAdminuser().equals(Admin.ADMIN_ADMINUSER_NOT)) {
					Map<String, String> map = new LinkedHashMap<>();
					map.put("userDriverid", "1229");
					map.put("order_id", "desc");
					List<User> lists = userService.getPage(map);
					view.addObject("models", lists);
				} else {
					view.addObject(Constants.MODEL_MESSAGE, "没有此管理权限,请联系管理员");
				}
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName(path + "list_10");// 跳转页面
		return view;
	}

	/**
	 * 用户编辑—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/edit/10.html")
	public ModelAndView edit(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				Map<String, String> countryparam = new HashMap<String, String>();
				countryparam.put("parent_id", "1");
				String id = param.get("id");
				User s = userService.getById(Integer.parseInt(id));
				view.addObject("model", s);
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		} finally {
			param.remove("mid");
			param.remove("id");
		}
		view.addObject("params", param);
		view.setViewName(path + "edit_10");
		return view;
	}

	/**
	 * 用户编辑-操作 save_or_update
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "用户编辑-操作")
	@RequestMapping("/edit/10")
	public JsonResult edits(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			DesUtils des = new DesUtils(Constants.PUBLIC_SECRET);
			Map<String, String> param = super.getParameters(request);
			String pwd = request.getParameter("pwd");
			if(pwd.length()!=32){
			des = new DesUtils(Constants.PRIVATE_SECRET);
			pwd = des.encrypt(pwd);
			param.put("Pwd", MD5Utils.md5(pwd));
			}
			User user = super.reflect("user", User.class, param);
			//定义规则
			user.setUserDriverid("1229");
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
	 * 
	 * 用户确认管理-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/20.html")
	public ModelAndView list1(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			if (param.containsKey("pageNum")) {
				param.put("userDriverid", "1229");
				param.put("order_id", "desc");
				List<User> list = userService.getPage(param);
				view.addObject("models", list);
			}else{
				
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdminuc() != null && admin.getAdminAdminuc().equals(Admin.ADMIN_ADMINUC_NOT)) {
					param.put("userDriverid", "1229");
					param.put("order_id", "desc");
					List<User> list = userService.getPage(param);
					view.addObject("models", list);
				} else {
					view.addObject(Constants.MODEL_MESSAGE, "没有此管理权限,请联系管理员");
				}
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName(path + "list_20");// 跳转页面
		return view;
	}

	/**
	 * 用户确认管理 编辑—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/edit/20.html")
	public ModelAndView edit1(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				User s = userService.getById(Integer.parseInt(id));
				view.addObject("model", s);
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		} finally {
			param.remove("mid");
			param.remove("id");
		}
		view.addObject("params", param);
		view.setViewName(path + "edit_20");
		return view;
	}

	/**
	 * 用户确认管理 明细—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/list/20_1.html")
	public ModelAndView edit201(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			String tels=request.getParameter("tels");
			if (param.containsKey("id")) {
				
			    Map<String, String> map=new HashMap<>();
			    map.put("driverIdcard", tels);
			    map.put("driverSendu", "1");
				List<Driver> list = driverService.getPage(map);
				view.addObject("models", list);
				view.addObject("tels",tels);
    		}else{
    			param.put("driverIdcard", tels);
    			param.put("driverSendu", "1");
 				List<Driver> list = driverService.getPage(param);
 				view.addObject("models", list);
 				view.addObject("tels",tels);
			}
			
			
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName(path + "list_20_1");
		return view;
	}

	/**
	 * 
	 * 行车记录管理-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/21.html")
	public ModelAndView list2(ModelAndView view, HttpServletRequest request) {

		Map<String, String> param = super.getParameters(request);
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			if (param.containsKey("pageNum")) {
				param.put("driverBeizhu", "rcjl");
				param.put("driverSendu", "1");
				param.put("order_id", "desc");
				List<Driver> list = driverService.getPage(param);
				view.addObject("models", list);
			}else{
				
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdminuc() != null && admin.getAdminAdminuc().equals(Admin.ADMIN_ADMINUC_NOT)) {
					param.put("driverBeizhu", "rcjl");
					param.put("driverSendu", "1");
					param.put("order_id", "desc");
					List<Driver> list = driverService.getPage(param);
					view.addObject("models", list);
				} else {
					view.addObject(Constants.MODEL_MESSAGE, "没有此管理权限,请联系管理员");
				}
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName(path + "list_21");// 跳转页面
		return view;
	}

	/**
	 * 行车记录管理 编辑—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/edit/21.html")
	public ModelAndView edit2(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Driver d = driverService.getById(Integer.parseInt(id));
				view.addObject("model", d);
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		} finally {
			param.remove("mid");
			param.remove("id");
		}
		view.addObject("params", param);
		view.setViewName(path + "edit_21");
		return view;
	}

	/**
	 * 行车日志管理-操作 save_or_update
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "用户编辑-操作")
	@RequestMapping("/edit/21")
	public JsonResult edits1(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			Map<String, String> param = super.getParameters(request);
			String milenow = request.getParameter("milenow");
			Double double3 = Double.parseDouble(milenow);
			Driver driver = super.reflect("driver", Driver.class, param);
			driver.setDriverMilenow(double3);
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
	 * 
	 * 加班费用管理-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/22.html")
	public ModelAndView list3(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			/*if (param.containsKey("pageNum")) {
				param.put("driverSendu", "1");
				List<Driver> list = driverService.getList(param);
				List<Driver> y = new LinkedList<>();
				List<Driver> n = new LinkedList<>();
				
				Map<String, Integer> n_tmp = new HashMap<>();
				Map<String, Integer> y_tmp = new HashMap<>();
				for (Driver driver : list) {
					String key = DateUtils.getDateFormat(driver.getDriverDate(), DateUtils.SHORT_DATE_FORMAT);
					if (driver.getDriverBeizhu().equals("jbjl") || driver.getDriverBeizhu().equals("fyjl")) {
						
						Driver d = driver;
							
							if(n_tmp.containsKey(key)) {
								d = n.get(n_tmp.get(key));
								n.remove(n_tmp.get(key).intValue());
								n_tmp.remove(key);
							}
							if(y_tmp.containsKey(key)) {
								d = y.get(y_tmp.get(key));
								d.setDriverOvertimehours(d.getDriverOvertimehours() + driver.getDriverOvertimehours());
								d.setDriverOvertimermbtotal(d.getDriverOvertimermbtotal() + driver.getDriverOvertimermbtotal());
								d.setDriverRmb(d.getDriverRmb()+driver.getDriverRmb());
								if (d.getDriverOvertimetype()==null) {
									d.setDriverOvertimetype(driver.getDriverOvertimetype());
								}
								y.set(y_tmp.get(key), d);
							} else {
								y.add(d);
								y_tmp.put(key, y.size() - 1);
							}
					}
				}
				view.addObject("y", y);
			}else{*/
			
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdminuc() != null && admin.getAdminAdminuc().equals(Admin.ADMIN_ADMINUC_NOT)) {
					param.put("driverSendu", "1");
					String pageSize = param.get("pageSize")==null?"10":param.get("pageSize");
					String pageNum = param.get("pageNum")==null?"1":param.get("pageNum");
					List<Driver> list = driverService.getList(param);
					Map<Integer, List<Driver>> sort = new LinkedHashMap<>();
					for(Driver driver : list) {
						Integer key = driver.getDriverId();
						if(sort.containsKey(key)) {
							sort.get(key).add(driver);
						} else {
							List<Driver> tmp = new LinkedList<>();
							tmp.add(driver);
							sort.put(key, tmp);
						}
					}
					List<Driver> newList = new ArrayList<Driver>();
					
					for(Entry<Integer, List<Driver>> drivers : sort.entrySet()) {
						List<Driver> y = new LinkedList<>();
						List<Driver> n = new LinkedList<>();
						
						Map<String, Integer> n_tmp = new HashMap<>();
						Map<String, Integer> y_tmp = new HashMap<>();
						for (Driver driver : drivers.getValue()) {
							String key = DateUtils.getDateFormat(driver.getDriverDate(), DateUtils.SHORT_DATE_FORMAT);
							if (driver.getDriverBeizhu().equals("jbjl") || driver.getDriverBeizhu().equals("fyjl")) {
								
								Driver d = driver;
									if(n_tmp.containsKey(key)) {
										d = n.get(n_tmp.get(key));
										n.remove(n_tmp.get(key).intValue());
										n_tmp.remove(key);
									}
									if(y_tmp.containsKey(key)) {
										d = y.get(y_tmp.get(key));
										d.setDriverOvertimehours(d.getDriverOvertimehours() + driver.getDriverOvertimehours());
										d.setDriverOvertimermbtotal(d.getDriverOvertimermbtotal() + driver.getDriverOvertimermbtotal());
										d.setDriverRmb(d.getDriverRmb()+driver.getDriverRmb());
										if (d.getDriverOvertimetype()==null) {
											d.setDriverOvertimetype(driver.getDriverOvertimetype());
										}
										y.set(y_tmp.get(key), d);
										
									} else {
										y.add(d);
										y_tmp.put(key, y.size() - 1);
									}
							}
						}
						
						newList.addAll(y);
					}
					Map<String, Object> page = new HashMap<String,Object>();
					page.put("page", Integer.valueOf(pageNum));
					page.put("limit", Integer.valueOf(pageSize));
					if(newList.size()%Integer.valueOf(pageSize) != 0)
						page.put("totalPages", newList.size()/Integer.valueOf(pageSize)+1);
					else
						page.put("totalPages", newList.size()/Integer.valueOf(pageSize));
					List<Driver> newLists = new ArrayList<Driver>();
					for(int index = (Integer.valueOf(pageNum)-1)*Integer.valueOf(pageSize);index<Integer.valueOf(pageSize)*Integer.valueOf(pageNum);index++){
						if(index==newList.size())
							break;
						else if(index>newList.size()){
							page.put("page", Integer.valueOf("1"));
							break;
						}
						newLists.add(newList.get(index));
					}
					view.addObject("y", newLists);
					view.addObject("modelsPaginator",page);
					
				} else {
					view.addObject(Constants.MODEL_MESSAGE, "没有此管理权限,请联系管理员");
			    }
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName(path + "list_22");// 跳转页面
		return view;
	}

	/**
	 * 加班费用管理 编辑—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/edit/22.html")
	public ModelAndView edit3(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");

				Driver d = driverService.getById(Integer.parseInt(id));
				view.addObject("model", d);
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		} finally {
			param.remove("mid");
			param.remove("id");
		}
		view.addObject("params", param);
		view.setViewName(path + "edit_22");
		return view;
	}

	/**
	 * 加班费用管理-操作 save_or_update
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "用户编辑-操作")
	@RequestMapping("/edit/22")
	public JsonResult edits2(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			Map<String, String> param = super.getParameters(request);
			String overtimehours = request.getParameter("overtimehours");
			String overtimermbtotal = request.getParameter("overtimermbtotal");
			String rmb = request.getParameter("rmb");
			Double double1 = null;
			Double double2 = null;
			Double double3 = null;
			if (overtimehours == null || overtimehours == "") {
				double1 = Double.parseDouble("0.0");// 加班时长
			} else {
				double1 = Double.parseDouble(overtimehours);// 加班时长
			}
			if (overtimermbtotal == null || overtimermbtotal == "") {
				double2 = Double.parseDouble("0.0");// 加班费用
			} else {
				double2 = Double.parseDouble(overtimermbtotal);// 加班费用
			}

			if (rmb == null || rmb == "") {
				double3 = Double.parseDouble("0.0");// 其他费用
			} else {
				double3 = Double.parseDouble(rmb);// 其他费用
			}

			Driver driver = super.reflect("driver", Driver.class, param);
			driver.setDriverOvertimehours(double1);
			driver.setDriverOvertimermbtotal(double2);
			driver.setDriverRmb(double3);

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
	 * 
	 * 评价信息-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/24.html")
	public ModelAndView list24(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			if (param.containsKey("pageNum")) {
				param.put("driverUsname", "0507");
				param.put("order_id", "desc");
				List<Driver> list = driverService.getPage(param);
				view.addObject("models", list);
			}else{
				
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdmints() != null
						&& admin.getAdminAdmints().equals(Admin.ADMIN_ADMINTS_NOT)) {
					param.put("driverUsname", "0507");
					param.put("order_id", "desc");
					List<Driver> list = driverService.getPage(param);
					view.addObject("models", list);
				} else {
					view.addObject(Constants.MODEL_MESSAGE, "没有此管理权限,请联系管理员");
				}
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName(path + "list_24");// 跳转页面
		return view;
	}

	/**
	 * 
	 * 投诉列表-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/23.html")
	public ModelAndView list23(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			if (param.containsKey("pageNum")) {
				param.put("userDriverid", "0407");
				param.put("order_id", "desc");
				List<User> list = userService.getPage(param);
				view.addObject("models", list);
			}else{
				
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdmints() != null
						&& admin.getAdminAdmints().equals(Admin.ADMIN_ADMINTS_NOT)) {
					param.put("userDriverid", "0407");
					param.put("order_id", "desc");
					List<User> list = userService.getPage(param);
					view.addObject("models", list);
				} else {
					view.addObject(Constants.MODEL_MESSAGE, "没有此管理权限,请联系管理员");
				}
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			e.printStackTrace();
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName(path + "list_23");// 跳转页面
		return view;
	}

	/**
	 * 评价信息导出
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/list/240.html")
	public ModelAndView editdc2(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = super.getParameters(request);
		param.put("driverUsname", "0507");
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
				List<Driver> list = driverService.getList(param);
				// 创建HSSFWorkbook对象声明一个工作簿
				HSSFWorkbook wb = new HSSFWorkbook();
				// 创建HSSFSheet对象声明一个单子并命名
				HSSFSheet sheet = wb.createSheet("评价信息记录");
				// 创建HSSFCellStyle声明一个样式
				HSSFCellStyle cellStyle = wb.createCellStyle();
				// 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
				HSSFFont fontStyle = wb.createFont();
				// 创建HSSFRow对象 创建第一行(也可以称为表头)
				HSSFRow row = sheet.createRow(0);
				// 创建HSSFCell对象 给表头第一行一次创建单元格
				HSSFCell cell = row.createCell(0);
				// 设置单元格内容
				cell.setCellValue("评价信息记录一览表");
				// 定义日期格式
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 设置字体样式
				fontStyle.setFontName("宋体");
				// 设置粗体
				fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 设置这些样式
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
				// 自动换行
				cellStyle.setWrapText(true);
				// 设置日期型数据的显示样式
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
				// 将字体对象赋值给单元格样式对象
				cellStyle.setFont(fontStyle);
				// 将样式应用到行，但有些样式只对单元格起作用
				row.setRowStyle(cellStyle);
				// 将单元格样式应用于单元格
				cell.setCellStyle(cellStyle);
				// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
				sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(1, 5000);
				sheet.setColumnWidth(2, 5000);
				sheet.setColumnWidth(3, 5000);
				sheet.setColumnWidth(4, 5000);
				sheet.setColumnWidth(5, 5000);
				sheet.setColumnWidth(6, 5000);
				sheet.setColumnWidth(7, 5000);
				// 在sheet里创建第二行
				HSSFRow row2 = sheet.createRow(1);
				// 创建单元格并设置单元格内容
				row2.createCell(0).setCellValue("手机号码");
				row2.createCell(1).setCellValue("用车人名称");
				row2.createCell(2).setCellValue("司机名称");
				row2.createCell(3).setCellValue("司机工号");
				row2.createCell(4).setCellValue("司机车牌号");
				row2.createCell(5).setCellValue("评价等级");
				row2.createCell(6).setCellValue("备注");
				row2.createCell(7).setCellValue("添加时间");
				// 在sheet里创建第三行
				int i = 2;
			for (Driver driver : list) {
				HSSFRow row3 = sheet.createRow(i);
				// 手机号码
				if (driver.getDriverCarname() == null) {
					row3.createCell(0).setCellValue(" ");
				} else {
					row3.createCell(0).setCellValue(driver.getDriverCarname());
				}
				// 用户名
				if (driver.getDriverIdcard() == null) {
					row3.createCell(1).setCellValue(" ");
				} else {
					row3.createCell(1).setCellValue(driver.getDriverIdcard().toString());
				}

				// 司机名称
				if (driver.getDriverName() == null) {
					row3.createCell(2).setCellValue(" ");
				} else {
					row3.createCell(2).setCellValue(driver.getDriverName());
				}
				// 司机工号
				if (driver.getDriverHeadpic() == null) {
					row3.createCell(3).setCellValue(" ");
				} else {
					row3.createCell(3).setCellValue(driver.getDriverHeadpic());
				}
				// 司机车牌
				if (driver.getDriverCarnum() == null) {
					row3.createCell(4).setCellValue(" ");
				} else {
					row3.createCell(4).setCellValue(driver.getDriverCarnum());
				}

				// 评星
				if (driver.getDriverLeavel() == null) {
					row3.createCell(5).setCellValue(" ");
				} else if (driver.getDriverLeavel().equals("1")) {
					row3.createCell(5).setCellValue("☆");
				} else if (driver.getDriverLeavel().equals("2")) {
					row3.createCell(5).setCellValue("☆☆");
				} else if (driver.getDriverLeavel().equals("3")) {
					row3.createCell(5).setCellValue("☆☆☆");
				} else if (driver.getDriverLeavel().equals("4")) {
					row3.createCell(5).setCellValue("☆☆☆☆");
				} else if (driver.getDriverLeavel().equals("5")) {
					row3.createCell(5).setCellValue("☆☆☆☆☆");
				}
				// 备注
				if (driver.getDriverBeizhu() == null) {
					row3.createCell(6).setCellValue(" ");
				} else {
					row3.createCell(6).setCellValue(driver.getDriverBeizhu().toString());
				}
				// 时间
				if (driver.getInitDate() == null) {
					row3.createCell(7).setCellValue(" ");
				} else {
					row3.createCell(7).setCellValue(formatter.format(driver.getInitDate()));
				}
				i++;
			}
				// excel 表文件名
				OutputStream out = response.getOutputStream();
				response.reset();
				String fileName = "评价信息记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
				String fileName11 = URLEncoder.encode(fileName, "UTF-8");
				String headStr = "attachment; filename=\"" + fileName11 + "\"";
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", headStr);
				wb.write(out);
				out.flush();
				out.close();
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName("/index/list_23");
		return view;
	}

	/**
	 * 投诉列表导出
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/list/230.html")
	public ModelAndView editdc(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = super.getParameters(request);
		param.put("userDriverid", "0407");
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
				List<User> list = userService.getList(param);
				// 创建HSSFWorkbook对象声明一个工作簿
				HSSFWorkbook wb = new HSSFWorkbook();
				// 创建HSSFSheet对象声明一个单子并命名
				HSSFSheet sheet = wb.createSheet("投诉列表记录");
				// 创建HSSFCellStyle声明一个样式
				HSSFCellStyle cellStyle = wb.createCellStyle();
				// 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
				HSSFFont fontStyle = wb.createFont();
				// 创建HSSFRow对象 创建第一行(也可以称为表头)
				HSSFRow row = sheet.createRow(0);
				// 创建HSSFCell对象 给表头第一行一次创建单元格
				HSSFCell cell = row.createCell(0);
				// 设置单元格内容
				cell.setCellValue("投诉列表记录一览表");
				// 设置字体样式
				fontStyle.setFontName("宋体");
				// 设置粗体
				fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 定义日期格式
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 设置这些样式
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
				// 自动换行
				cellStyle.setWrapText(true);
				// 设置日期型数据的显示样式
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
				// 将字体对象赋值给单元格样式对象
				cellStyle.setFont(fontStyle);
				// 将样式应用到行，但有些样式只对单元格起作用
				row.setRowStyle(cellStyle);
				// 将单元格样式应用于单元格
				cell.setCellStyle(cellStyle);
				// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
				sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(1, 5000);
				sheet.setColumnWidth(2, 5000);
				sheet.setColumnWidth(3, 5000);
				sheet.setColumnWidth(4, 5000);
				sheet.setColumnWidth(5, 5000);
				sheet.setColumnWidth(6, 5000);
				sheet.setColumnWidth(7, 5000);
				sheet.setColumnWidth(8, 5000);
				// 在sheet里创建第二行
				HSSFRow row2 = sheet.createRow(1);
				// 创建单元格并设置单元格内容
				row2.createCell(0).setCellValue("手机号码");
				row2.createCell(1).setCellValue("用车人名称");
				row2.createCell(2).setCellValue("司机名称");
				row2.createCell(3).setCellValue("司机工号");
				row2.createCell(4).setCellValue("司机车牌号");
				row2.createCell(5).setCellValue("第一分类");
				row2.createCell(6).setCellValue("第二分类");
				row2.createCell(7).setCellValue("备注");
				row2.createCell(8).setCellValue("添加时间");
				// 在sheet里创建第三行
			int i = 2;
			for (User obj : list) {
				HSSFRow row3 = sheet.createRow(i);
				// 手机号码
				if (obj.getUserTel() == null) {
					row3.createCell(0).setCellValue(" ");
				} else {
					row3.createCell(0).setCellValue(obj.getUserTel());
				}

				// 用户名
				if (obj.getUserName() == null) {
					row3.createCell(1).setCellValue(" ");
				} else {
					row3.createCell(1).setCellValue(obj.getUserName().toString());
				}

				// 司机名称
				if (obj.getUserAddre() == null) {
					row3.createCell(2).setCellValue(" ");
				} else {
					row3.createCell(2).setCellValue(obj.getUserAddre());
				}
				// 司机工号
				if (obj.getUserHeadpic() == null) {
					row3.createCell(3).setCellValue(" ");
				} else {
					row3.createCell(3).setCellValue(obj.getUserHeadpic());
				}
				// 司机车牌
				if (obj.getUserAdmin() == null) {
					row3.createCell(4).setCellValue(" ");
				} else {
					row3.createCell(4).setCellValue(obj.getUserAdmin());
				}

				// 第一分类
				if (obj.getUserComplsort() == null) {
					row3.createCell(5).setCellValue(" ");
				} else {
					row3.createCell(5).setCellValue(obj.getUserComplsort().toString());
				}
				// 第二分类
				if (obj.getUserComplsorts() == null) {
					row3.createCell(6).setCellValue(" ");
				} else {
					row3.createCell(6).setCellValue(obj.getUserComplsorts());
				}
				// 备注
				if (obj.getUserCompldetails() == null) {
					row3.createCell(7).setCellValue(" ");
				} else {
					row3.createCell(7).setCellValue(obj.getUserCompldetails().toString());
				}
				// 添加时间
				if (obj.getInitDate() == null) {
					row3.createCell(8).setCellValue(" ");
				} else {
					row3.createCell(8).setCellValue(formatter.format(obj.getInitDate()));
				}
				i++;
			}
				// excel 表文件名
				OutputStream out = response.getOutputStream();
				response.reset();
				String fileName = "投诉列表记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
				String fileName11 = URLEncoder.encode(fileName, "UTF-8");
				String headStr = "attachment; filename=\"" + fileName11 + "\"";
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", headStr);
				wb.write(out);
				out.flush();
				out.close();
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName("/index/list_23");
		return view;
	}
	
	/**
	 * 用户确认管理导出
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/list/200.html")
	public ModelAndView editdc200(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = super.getParameters(request);
	
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
				List<User> list = userService.getList(param);
				List<User> users=new LinkedList<>();
				for (int i = 0; i < list.size(); i++) {					
					if(list.get(i).getUserPwd()!=null){
						users.add(list.get(i));				
					}					
				}
				// 创建HSSFWorkbook对象声明一个工作簿
				HSSFWorkbook wb = new HSSFWorkbook();
				// 创建HSSFSheet对象声明一个单子并命名
				HSSFSheet sheet = wb.createSheet("用户确认管理记录");
				// 创建HSSFCellStyle声明一个样式
				HSSFCellStyle cellStyle = wb.createCellStyle();
				// 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
				HSSFFont fontStyle = wb.createFont();
				// 创建HSSFRow对象 创建第一行(也可以称为表头)
				HSSFRow row = sheet.createRow(0);
				// 创建HSSFCell对象 给表头第一行一次创建单元格
				HSSFCell cell = row.createCell(0);
				// 设置单元格内容
				cell.setCellValue("用户确认管理记录一览表");
				// 设置字体样式
				fontStyle.setFontName("宋体");
				// 设置粗体
				fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 设置这些样式
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
				// 自动换行
				cellStyle.setWrapText(true);
				// 设置日期型数据的显示样式
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
				// 将字体对象赋值给单元格样式对象
				cellStyle.setFont(fontStyle);
				// 将样式应用到行，但有些样式只对单元格起作用
				row.setRowStyle(cellStyle);
				// 将单元格样式应用于单元格
				cell.setCellStyle(cellStyle);
				// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
				sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(1, 5000);
				sheet.setColumnWidth(2, 5000);
				sheet.setColumnWidth(3, 5000);
				sheet.setColumnWidth(4, 5000);
				sheet.setColumnWidth(5, 5000);			
				// 在sheet里创建第二行
				HSSFRow row2 = sheet.createRow(1);
				// 创建单元格并设置单元格内容
				row2.createCell(0).setCellValue("手机号码");
				row2.createCell(1).setCellValue("用车人姓名");
				row2.createCell(2).setCellValue("公司名称");
				row2.createCell(3).setCellValue("邮箱号");
				row2.createCell(4).setCellValue("行政名称");
				row2.createCell(5).setCellValue("是否隐藏");			
				// 在sheet里创建第三行
				int i = 2;
				for (User user : users) {
					HSSFRow row3 = sheet.createRow(i);
					// 手机号码
					if (user.getUserTel() == null) {
						row3.createCell(0).setCellValue(" ");
					} else {
						row3.createCell(0).setCellValue(user.getUserTel());
					}
					// 用车人姓名
					if (user.getUserName() == null) {
						row3.createCell(1).setCellValue(" ");
					} else {
						row3.createCell(1).setCellValue(user.getUserName());
					}

					// 公司名称
					if (user.getUserCompany() == null) {
						row3.createCell(2).setCellValue(" ");
					} else {
						row3.createCell(2).setCellValue(user.getUserCompany());
					}
					// 邮箱号码
					if (user.getUserEmail() == null) {
						row3.createCell(3).setCellValue(" ");
					} else {
						row3.createCell(3).setCellValue(user.getUserEmail());
					}
					// 行政名称
					if (user.getUserAdmin() == null) {
						row3.createCell(4).setCellValue(" ");
					} else {
						row3.createCell(4).setCellValue(user.getUserAdmin());
					}
					
					// 是否隐藏
					if (user.getUserIsdisplay() == null) {
						row3.createCell(5).setCellValue(" ");
					} else if(user.getUserIsdisplay().equals("0")) {
						row3.createCell(5).setCellValue("否");
					}else if(user.getUserIsdisplay().equals("1")) {
						row3.createCell(5).setCellValue("是");
					}
					
					i++;
				}
				// excel 表文件名
				OutputStream out = response.getOutputStream();
				response.reset();
				String fileName = "用户确认管理记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
				String fileName11 = URLEncoder.encode(fileName, "UTF-8");
				String headStr = "attachment; filename=\"" + fileName11 + "\"";
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", headStr);
				wb.write(out);
				out.flush();
				out.close();
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName("/index/list_20");
		return view;
	}
	
	/**
	 * 行车日志导出
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/list/210.html")
	public ModelAndView editdc210(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = super.getParameters(request);
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if (redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if (key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			    param.put("driverBeizhu", "rcjl");
			    param.put("driverUaffirm", "1");
				List<Driver> list = driverService.getList(param);
				// 创建HSSFWorkbook对象声明一个工作簿
				HSSFWorkbook wb = new HSSFWorkbook();
				// 创建HSSFSheet对象声明一个单子并命名
				HSSFSheet sheet = wb.createSheet("行车日志记录");
				// 创建HSSFCellStyle声明一个样式
				HSSFCellStyle cellStyle = wb.createCellStyle();
				// 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
				HSSFFont fontStyle = wb.createFont();
				// 创建HSSFRow对象 创建第一行(也可以称为表头)
				HSSFRow row = sheet.createRow(0);
				// 创建HSSFCell对象 给表头第一行一次创建单元格
				HSSFCell cell = row.createCell(0);
				// 设置单元格内容
				cell.setCellValue("行车日志记录一览表");
				// 定义日期格式
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 定义日期格式
				SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd");
				// 设置字体样式
				fontStyle.setFontName("宋体");
				// 设置粗体
				fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 设置这些样式
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
				// 自动换行
				cellStyle.setWrapText(true);
				// 设置日期型数据的显示样式
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
				// 将字体对象赋值给单元格样式对象
				cellStyle.setFont(fontStyle);
				// 将样式应用到行，但有些样式只对单元格起作用
				row.setRowStyle(cellStyle);
				// 将单元格样式应用于单元格
				cell.setCellStyle(cellStyle);
				// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
				sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(1, 5000);
				sheet.setColumnWidth(2, 5000);
				sheet.setColumnWidth(3, 5000);
				sheet.setColumnWidth(4, 5000);
				sheet.setColumnWidth(5, 5000);	
				sheet.setColumnWidth(6, 5000);	
				sheet.setColumnWidth(7, 5000);	
				// 在sheet里创建第二行
				HSSFRow row2 = sheet.createRow(1);
				// 创建单元格并设置单元格内容
				row2.createCell(0).setCellValue("手机号码");
				row2.createCell(1).setCellValue("司机名称");
				row2.createCell(2).setCellValue("车牌号码");
				row2.createCell(3).setCellValue("开始时间");
				row2.createCell(4).setCellValue("结束时间");
				row2.createCell(5).setCellValue("总公里数");	
				row2.createCell(6).setCellValue("是否确认日常记录");	
				row2.createCell(7).setCellValue("添加时间");	
				// 在sheet里创建第三行
				int i = 2;
				for (Driver driver : list) {
					HSSFRow row3 = sheet.createRow(i);
					// 手机号码
					if (driver.getDriverTel() == null) {
						row3.createCell(0).setCellValue(" ");
					} else {
						row3.createCell(0).setCellValue(driver.getDriverTel());
					}
					// 司机名称
					if (driver.getDriverName() == null) {
						row3.createCell(1).setCellValue(" ");
					} else {
						row3.createCell(1).setCellValue(driver.getDriverName());
					}

					// 车牌号码
					if (driver.getDriverCarnum() == null) {
						row3.createCell(2).setCellValue(" ");
					} else {
						row3.createCell(2).setCellValue(driver.getDriverCarnum());
					}
					// 开始时间
					if (driver.getDriverLeavedateuser() == null) {
						row3.createCell(3).setCellValue(" ");
					} else {
						row3.createCell(3).setCellValue(formatter.format(driver.getDriverLeavedateuser()));
					}
					// 结束时间
					if (driver.getDriverBackdateuser() == null) {
						row3.createCell(4).setCellValue(" ");
					} else {
						row3.createCell(4).setCellValue(formatter.format(driver.getDriverBackdateuser()));
					}
					
					// 总公里数
					if (driver.getDriverMilenow() == null) {
						row3.createCell(5).setCellValue(" ");
					} else {
						row3.createCell(5).setCellValue(driver.getDriverMilenow());
					}
					// 是否确认日常记录
					if (driver.getDriverUaffirm() == null) {
						row3.createCell(6).setCellValue(" ");
					} else if(driver.getDriverUaffirm().equals("0")) {
						row3.createCell(6).setCellValue("否");
					}else if(driver.getDriverUaffirm().equals("1")) {
						row3.createCell(6).setCellValue("是");
					}
					// 添加时间
					if (driver.getDriverDate() == null) {
						row3.createCell(7).setCellValue(" ");
					} else {
						row3.createCell(7).setCellValue(formatters.format(driver.getDriverDate()));
					}
					i++;
				}
				// excel 表文件名
				OutputStream out = response.getOutputStream();
				response.reset();
				String fileName = "行车日志记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
				String fileName11 = URLEncoder.encode(fileName, "UTF-8");
				String headStr = "attachment; filename=\"" + fileName11 + "\"";
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", headStr);
				wb.write(out);
				out.flush();
				out.close();
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName("/index/list_21");
		return view;
	}

}
