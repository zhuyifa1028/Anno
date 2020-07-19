package com.product.rental.admin.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.product.annotation.SystemLog;
import com.product.bean.Admin;
import com.product.bean.Programa;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.AdminService;
import com.product.service.ProgramaService;
import com.product.utils.DesUtils;
import com.product.utils.MD5Utils;

import net.sf.json.JSONObject;

/**
 * 后段首页-控制类
 * 
 * @author ymc
 *
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {

	private static Log tag = LogFactory.getLog("tag");

	@Autowired
	private AdminService adminService;

	@Autowired
	private ProgramaService programaService;

	/**
	 * 用户登录-操作
	 * 
	 * @param request
	 * @return
	 */
	@SystemLog(description = "用户登录-操作")
	@RequestMapping("login")
	public JsonResult login(HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			String name = request.getParameter("tel");
			String pwd = request.getParameter("pwd");
			if (StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(name)) {
				DesUtils des = new DesUtils(Constants.PUBLIC_SECRET);
				pwd = des.decrypt(pwd);
				if (StringUtils.isNotBlank(pwd)) {
					des = new DesUtils(Constants.PRIVATE_SECRET);
					pwd = des.encrypt(pwd);
					Map<String, String> param = new HashMap<>();
					param.put("adminTel", name);
					param.put("adminPwd", MD5Utils.md5(pwd));
					List<Admin> admins = adminService.getList(param);
					if (admins != null && !admins.isEmpty() && admins.size() == 1) {
						Admin admin = admins.get(0);
						if (admin.getAdminState() != null
								&& admin.getAdminState().equals(Admin.ADMIN_ADMINISTRATE_YES)) {
							json.setMessage("用户不可使用,请联系管理员");
						} else {
							HttpSession session = request.getSession();
							session.setAttribute(Constants.ADMIN_USER_SESSION, admin);
							json.setCode(Status.SUCCESS.getCode());
							json.setMessage("用户登录成功");

						}
					} else {
						json.setMessage("用户名称或密码错误");
					}
				} else {
					json.setMessage("用户密码解密失败");
				}
			} else {
				json.setMessage("参数错误");
			}
		} catch (Exception e) {
			json.setCode(Status.ERROR.getCode());
			json.setMessage("系统错误,用户登录失败");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 用户菜单-页面
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("left.html")
	@SuppressWarnings("unchecked")
	public ModelAndView left(ModelAndView view, HttpServletRequest request) {
		try {
			Object user = super.getAdminUser(request);
			if (user != null) {
				HttpSession session = request.getSession();
				Object object = session.getAttribute(Constants.ADMIN_USER_MODULES_SESSION);
				if (object == null) {
					Map<String, String> param = new HashMap<>();
					param.put("parent_id", String.valueOf(Programa.PARENT_ID_DEFAULT));
					param.put("type", Programa.PROGRAMA_TYPE_COLUMN);
					object = programaService.getChilds(param);
					session.setAttribute(Constants.ADMIN_USER_MODULES_SESSION, object);
				}
				Map<String, String> ids = new HashMap<>();
				String pid = request.getParameter("mid");
				if (StringUtils.isNotBlank(pid)) {
					ids.put(pid, "true");
					view.addObject("mid", pid);
				}
				view.addObject("json", programaService.getChilds((List<Programa>) object, ids));
			} else {
				view.addObject(Constants.MODEL_MESSAGE, "该用户不存在");
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.setViewName("left");
		return view;
	}

	/**
	 * 导航标签
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("navigation.html")
	public ModelAndView navigation(ModelAndView view, HttpServletRequest request) {
		try {
			String name = request.getParameter("name");
			if (StringUtils.isNotBlank(name)) {
				view.addObject("name", name);
			}
			String mid = request.getParameter("mid");
			if (StringUtils.isNotBlank(mid)) {
				view.addObject("model", programaService.getById(Integer.valueOf(mid)));
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.setViewName("navigation");
		return view;
	}

	/**
	 * 顶部导航-页面
	 * 
	 * @param view
	 *            模型视图
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("top.html")
	public ModelAndView top(ModelAndView view, HttpServletRequest request) {
		String param = request.getParameter("top_params");
		if (StringUtils.isNotBlank(param)) {
			view.addObject("top_params", param);
		}
		view.setViewName("top");
		return view;
	}

	/**
	 * 后台首页-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("index.html")
	public ModelAndView index(ModelAndView view, HttpServletRequest request) {
		try {
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}

		return view;
	}
	/**
	 * 退出登录-页面
	 * 
	 * @param view
	 * @return
	 */
	@RequestMapping("logout.html")
	public ModelAndView logout(ModelAndView view, HttpSession session) {
		Enumeration<String> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			session.removeAttribute(key);
		}
		view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		view.setViewName("login");
		return view;
	}

	/**
	 * 后台列表-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param pid
	 *            上级主键
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/list/{aid}.html")
	public ModelAndView list(ModelAndView view, @PathVariable("aid") String aid, HttpServletRequest request) {
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

			if (aid.equals("22")) {
				Map<String, String> map = new LinkedHashMap<>();
				map.put("parent_id", "15");
				map.put("is_display", Programa.IS_DISPLAY_NOT);
				map.put("order_sequence", "asc");
				map.put("order_id", "desc");
				List<Programa> programas = programaService.getList(map);
				if (programas != null && !programas.isEmpty() && programas.size() > 0) {
					Map<Integer, Programa> town = new LinkedHashMap<>();
					for (Programa programa : programas) {
						town.put(programa.getId(), programa);
					}
					view.addObject("town", town);
				}
			}

			param.put("parent_id", aid);
			view.addObject("models", programaService.getPage(param));
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName("/index/list_" + aid);
		return view;
	}

	/**
	 * 后台编辑-视图
	 * 
	 * @param view
	 *            模型视图
	 * @param pid
	 *            上级主键
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/edit/{aid}.html")
	public ModelAndView edit(ModelAndView view, @PathVariable("aid") String aid, HttpServletRequest request,
			RedirectAttributes redirect) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				view.addObject("model", programaService.getById(Integer.valueOf(param.get("id"))));
			}
			if (aid.equals("22")) {
				Map<String, String> map = new LinkedHashMap<>();
				map.put("parent_id", "15");
				map.put("is_display", Programa.IS_DISPLAY_NOT);
				map.put("order_sequence", "asc");
				map.put("order_id", "desc");
				List<Programa> programas = programaService.getList(map);
				if (programas != null && !programas.isEmpty() && programas.size() > 0) {
					Map<String, List<Programa>> town = new LinkedHashMap<>();
					view.addObject("town", town);
				}
			}
			view.addObject("params", param);
			view.setViewName("/index/edit_" + aid);
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			if (param.containsKey("mid")) {
				view.setViewName("redirect:../list/" + param.get("mid") + ".html?mid=" + param.get("mid"));
			}
			redirect.addFlashAttribute(Constants.MODEL_MESSAGE, "参数错误");
			redirect.addFlashAttribute("params", param);
		} finally {
			param.remove("id");
			param.remove("pid");
			param.remove("mid");
		}
		return view;
	}
}
