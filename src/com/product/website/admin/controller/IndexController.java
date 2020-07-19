package com.product.website.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.product.annotation.SystemLog;
import com.product.bean.Website;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.initializer.Starter;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.WebsiteService;
import com.product.starter.WebsiteStarter;
import com.product.utils.SpringContextUtils;

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
	private WebsiteService websiteService;
	
	/**
	 * 网站信息编辑-页面
	 * 
	 * @param view
	 * @param request
	 * @return
	 */
	@RequestMapping("edit.html")
	public ModelAndView edit(ModelAndView view, HttpServletRequest request) {
		try {
			Map<String, String> param = new HashMap<>();
			param.put("order_id", "desc");
			List<Website> websites = websiteService.getList(null);
			if(websites != null && !websites.isEmpty() && websites.size() > 0) {
				view.addObject("model", websites.get(0));
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		view.setViewName("edit");
		return view;
	}
	
	/**
	 * 网站信息编辑-操作
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	@SystemLog(description = "网站信息编辑-操作")
	@RequestMapping("edit")
	public JsonResult edit(HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			json = super.upload(request, "filedata");
			if(json.isSucceed()) {
				Map<String, String> param = super.getParameters(request);
				Website website = super.reflect("website", Website.class, param);
				Object filename = json.getResult();
				if(filename != null) {
					website.setWebsiteLogo(filename.toString());
				}
				if(websiteService.saveOrUpdate(website, super.ip(request))) {
					// 刷新网站信息缓存
					Starter starter = SpringContextUtils.getBean(WebsiteStarter.class);
					starter.load(request.getServletContext());
					
					json.setCode(Status.SUCCESS.getCode());
					json.setMessage("网站信息编辑成功");
				} else {
					json.setMessage("网站信息编辑失败");
				}
			} else {
				json.setMessage("文件上传失败");
			}
		} catch (Exception e) {
			json.setCode(Status.ERROR.getCode());
			json.setMessage("系统错误,网站信息编辑");
			tag.error(e.getMessage(), e);
		}
		return json;
	}
}