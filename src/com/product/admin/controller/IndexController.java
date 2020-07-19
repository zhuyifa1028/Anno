package com.product.admin.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.product.constants.Constants;
import com.product.controller.BaseController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 后段首页-控制类
 * 
 * @author syl
 *
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {
	
//	private static Log tag = LogFactory.getLog("tag");
//	
//	@RequestMapping("send")
//	public void send(HttpServletRequest request) {
//		String msg = request.getParameter("msg");
//		Runnable run = new Runnable() {
//			private ScriptBuffer buffer = new ScriptBuffer();
//			@Override
//			public void run() {
//				buffer.appendCall("show", msg);
//				Collection<ScriptSession> sessions = Browser.getTargetSessions();
//				for (ScriptSession session : sessions) {
//					session.addScript(buffer);
//				}
//			}
//		};
//		Browser.withAllSessions(run);
//	}
	
	/**
	 * 用户配置-页面
	 * 
	 * @param view
	 * 			模型视图
	 * @return
	 */
	@RequestMapping("config.html")
	public ModelAndView config(ModelAndView view) {
		view.setViewName("config");
		return view;
	}
	
	/**
	 * 底部导航-页面
	 * 
	 * @param view
	 * 			模型视图
	 * @return
	 */
	@RequestMapping("foot.html")
	public ModelAndView foot(ModelAndView view) {
		view.setViewName("foot");
		return view;
	}
	
	/**
	 * 后台登录-页面
	 * 
	 * @param view
	 * 			模型视图
	 * @return
	 */
	@RequestMapping("login.html")
	public ModelAndView login(ModelAndView view) {
		view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		view.setViewName("login");
		return view;
	}
	
	/**
	 * 退出登录-页面
	 * 
	 * @param view
	 * 			模型视图
	 * @return
	 */
	@RequestMapping("logout.html")
	public ModelAndView logout(ModelAndView view, HttpSession session) {
		
		if(session.getAttribute(Constants.ADMIN_USER_SESSION) != null) {
			Enumeration<String> keys = session.getAttributeNames();
			while(keys.hasMoreElements()) {
				String key = keys.nextElement();
				session.removeAttribute(key);
			}
		}
		view.addObject(Constants.PUBLIC_SECRET_KEY, Constants.PUBLIC_SECRET);
		view.setViewName("login");
		return view;
	}
	
	/**
	 * 消息提示-页面
	 * 
	 * @param view
	 * 			模型视图
	 * @return
	 */
	@RequestMapping("message.html")
	public ModelAndView message(ModelAndView view) {
		view.setViewName("message");
		return view;
	}
	
	/**
	 *  分页标签
	 * 
	 * @param view
	 * 			模型视图
	 * @param request
	 * 			请求对象
	 * @return
	 */
	@RequestMapping("page.html")
	public ModelAndView page(ModelAndView view, HttpServletRequest request) {
		String pageNum = request.getParameter(Constants.PAGE_NUM);
		String pageSize = request.getParameter(Constants.PAGE_SIZE);
		String pageTotal = request.getParameter(Constants.PAGE_TOTAL);
		
		JSONArray page = new JSONArray();
		if(StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize) && StringUtils.isNotBlank(pageTotal)) {
			int num = Integer.valueOf(pageNum);
			int total = Integer.valueOf(pageTotal);
			int size = 9;
			for(int i = 1; i <= total; i++) {
				JSONObject json = new JSONObject();
				if(i == 1) {
					json.put("name", "‹");
					if(num == i) {
						json.put("page", i);
						json.put("disabled", "disabled");
					} else {
						json.put("page", num - 1);
					}
					page.add(json);
					json = new JSONObject();
				}
				json.put("name", i);
				json.put("page", i);
				if(i == num) {
					json.put("active", "active");
				}
				if(total > (size - 3)) {
					int avg = size / 2;
					if(num <= avg) {
						if(i> avg + 1 && i < total) {
							if (i != total - 1) {
								continue;
							}
							json.put("page", "…");
							json.put("name", "…");
							json.put("disabled", "disabled");
						}
					} else if(num <= total - avg) {
						if(i > 1 && i < num -1) {
							if(i != 2) {
								continue;
							}
							json.put("page", "…");
							json.put("name", "…");
							json.put("disabled", "disabled");
						}
						if(i> num + 1 && i < total) {
							if(i != total -1) {
								continue;
							}
							json.put("page", "…");
							json.put("name", "…");
							json.put("disabled", "disabled");
						}
					} else {
						if(i > 1 && i < total - avg) {
							if(i != 2) {
								continue;
							}
							json.put("page", "…");
							json.put("name", "…");
							json.put("disabled", "disabled");
						}
					}
				}
				page.add(json);
				if(i == total) {
					json.put("name", "›");
					if(num == total) {
						json.put("page", total);
						json.put("disabled", "disabled");
					} else {
						json.put("page", num+1 );
					}
					json.remove("active");
					page.add(json);
				}
			}
		} else {
			for(int i = 1; i <= 3; i++) {
				JSONObject json = new JSONObject();
				if(i == 1) {
					json.put("name", "‹");
				} else if(i == 3) {
					json.put("name", "›");
				} else {
					json.put("name", "1");
					json.put("active", "active");
				}
				json.put("page", "1");
				json.put("disabled", "disabled");
				page.add(json);
			}
			pageNum = String.valueOf(Constants.PAGE_DEFAULT_NUM);
			pageSize = String.valueOf(Constants.PAGE_DEFAULT_SIZE);
			pageTotal = String.valueOf(Constants.PAGE_DEFAULT_TOTAL);
		}
		view.addObject("page", page);
		view.addObject("pageSize", pageSize);
		view.addObject("pageNum", pageNum);
		view.addObject("pageTotal", pageTotal);
		view.setViewName("page");
		return view;
	}
}