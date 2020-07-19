package com.product.area.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.product.bean.Area;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.AreaService;

import net.sf.json.JSONObject;

/**
 * 地区后段首页-控制类
 * 
 * @author syl
 *
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController{
	private static Log tag = LogFactory.getLog("tag");

	@Autowired
	private AreaService areaService;
	/**
	 * 区域首页-视图
	 * 
	 * @param view
	 * 			模型视图
	 * @param request
	 * 			请求对象
	 * @return
	 */
	@RequestMapping("list.html")
	public ModelAndView list(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			Map<String, ?> redirect = RequestContextUtils.getInputFlashMap(request);
			if(redirect != null && redirect.containsKey("params")) {
				JSONObject params = JSONObject.fromObject(redirect.get("params"));
				for (Object key : params.keySet()) {
					if(key.equals(Constants.MODEL_MESSAGE)) {
						view.addObject(Constants.MODEL_MESSAGE, redirect.get(Constants.MODEL_MESSAGE));
					} else {
						param.put(key.toString(), params.getString(key.toString()));
					}
				}
			}
			if(!param.containsKey("parent_id")) {
				param.put("parent_id", String.valueOf(Area.AREA_DEFAULT_ID));
			}
			
			view.addObject("models", areaService.getPage(param));
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.addObject("params", param);
		view.setViewName("list");
		return view;
	}
	/**
	 * 区域编辑-页面
	 * 
	 * @param view
	 * 			模型视图
	 * @param request
	 * 			请求对象
	 * @param redirect
	 * 			重新定向
	 * @return
	 */
	@RequestMapping("edit.html")
	public ModelAndView edit(ModelAndView view, HttpServletRequest request, RedirectAttributes redirect) {
		Map<String, String> param = super.getParameters(request);
		try {
			if(param.containsKey("id")) {
				Area area = areaService.getById(Integer.valueOf(param.get("id")));
				if(area.getAreaParentId() != null && area.getAreaParentId().equals(Area.AREA_DEFAULT_ID)) {
					// 设置默认上级区域
					{
						Area tmp = new Area();
						tmp.setAreaName(Area.AREA_DEFAULT_NAME);
						area.setParent(tmp);
					}
				}
				view.addObject("model", area);
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			if(param.containsKey("pid")) {
				view.setViewName("redirect:list.html?mid=" + param.get("pid"));
				param.remove("pid");
			}
			param.remove("mid");
			redirect.addFlashAttribute(Constants.MODEL_MESSAGE, "参数错误");
			redirect.addFlashAttribute("params", param);
			return view;
		}
		view.addObject("params", param);
		view.addObject("default_id", Area.AREA_DEFAULT_ID);
		view.setViewName("edit");
		return view;
	}
	
	/**
	 * 区域编辑-操作
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	@SystemLog(description = "区域编辑-操作")
	@RequestMapping("edit")
	public JsonResult edit(HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			Map<String, String> param = super.getParameters(request);
			Area area = super.reflect("area", Area.class, param);
			if(areaService.saveOrUpdate(area, super.ip(request))) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("区域编辑成功");
			} else {
				json.setMessage("区域编辑失败");
			}
		} catch (Exception e) {
			json.setCode(Status.ERROR.getCode());
			json.setMessage("系统错误,栏目编辑");
			tag.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 区域搜索-页面
	 * 
	 * @param view
	 * 			模型视图
	 * @param pid
	 * 			上级主键
	 * @return
	 */
	@RequestMapping("/search/{pid}.html")
	public ModelAndView search(ModelAndView view, @PathVariable("pid")String pid) {
		try {
			Map<String, String> param = new HashMap<>();
			param.put("parent_id", String.valueOf(Area.AREA_DEFAULT_ID));
			List<Area> areas = areaService.getChilds(param);
			Map<String, String> keys = new HashMap<>();
			keys.put(pid, "true");
			view.addObject("json", areaService.getChildsTree(areas, keys));
			view.addObject("default_id", Area.AREA_DEFAULT_ID);
			view.addObject("default_name", Area.AREA_DEFAULT_NAME);
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.setViewName("search");
		return view;
	}
	
	/**
	 * 区域树状-页面
	 * 
	 * @param view
	 * 			模型视图
	 * @param request
	 * 			请求对象
	 * @return
	 */
	@RequestMapping("tree.html")
	public ModelAndView tree(ModelAndView view, HttpServletRequest request) {
		try {
			String pid = request.getParameter("parent_id");
			if(StringUtils.isBlank(pid)) {
				pid = String.valueOf(Area.AREA_DEFAULT_ID);
			}
			Map<String, String> param = new HashMap<>();
			param.put("parent_id", String.valueOf(Area.AREA_DEFAULT_ID));
			List<Area> areas = areaService.getChilds(param);
			Map<String, String> keys = new HashMap<>();
			keys.put(pid, "true");
			view.addObject("json", areaService.getChildsTree(areas, keys));
			view.addObject("default_id", Area.AREA_DEFAULT_ID);
			view.addObject("default_name", Area.AREA_DEFAULT_NAME);
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.setViewName("tree");
		return view;
	}
}
