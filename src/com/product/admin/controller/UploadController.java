package com.product.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.controller.BaseController;
import com.product.result.JsonResult;

/**
 * 后端上传-控制类
 * 
 * @author syl
 *
 */
@RestController
@RequestMapping("/")
public class UploadController extends BaseController {

	private static Log tag = LogFactory.getLog("tag");
	
	/**
	 * 文件上传-接口
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	@RequestMapping("/froala/upload")
	public Map<String, Object> froala(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		try {
			JsonResult json = super.upload(request, "filedata");
			if(json.isSucceed()) {
				param.put("link", request.getContextPath() + json.getResult());
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
		}
		return param;
	}
}