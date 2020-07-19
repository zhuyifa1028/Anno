package com.product.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.product.bean.BaseBean;
import com.product.constants.Constants;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.utils.DateUtils;
import com.product.utils.StrUtils;

/**
 * 基础-控制类
 * 
 * @author syl
 *
 */
public class BaseController {
	
	private static final String[] HEADERS_TO_TRY = {
		"X-Forwarded-For",
		"Proxy-Client-IP",
		"WL-Proxy-Client-IP",
		"HTTP_X_FORWARDED_FOR",
		"HTTP_X_FORWARDED",
		"HTTP_X_CLUSTER_CLIENT_IP",
		"HTTP_CLIENT_IP",
		"HTTP_FORWARDED_FOR",
		"HTTP_FORWARDED",
		"HTTP_VIA",
		"REMOTE_ADDR",
		"X-Real-IP"
	};
	
	/**
	 * 格式化日期格式
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder) {
		DateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT);
		CustomDateEditor editor = new CustomDateEditor(df, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	/**
	 * 反射对象
	 * 
	 * @param prefix
	 * 			前缀
	 * @param cls
	 * 			实体
	 * @param param
	 * 			参数
	 * @return
	 * @throws Exception
	 */
	protected <T extends BaseBean> T reflect(String prefix, Class<T> cls, Map<String, String> param) throws Exception {
		T t = cls.newInstance();
		StringBuffer buffer = new StringBuffer();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			String key = entry.getKey();
			if((!key.contains("_id") || key.equals("parent_id")) && !key.equals("audit_status") && !key.equals("audit_note")) {
				buffer.append(prefix).append(StrUtils.toUpperCase("_", key));
			} else {
				buffer.append(StrUtils.toLowerCase(StrUtils.toUpperCase("_", key)));
			}
			
			Class<? super T> clazz = cls;
			while(clazz != Object.class) {
				try {
					Field field = clazz.getDeclaredField(buffer.toString());
					Type type = field.getType();
					field.setAccessible(true);
					if(type.equals(String.class)) {
						field.set(t, entry.getValue());
					} else if (type.equals(Integer.class)) {
						field.set(t, Integer.valueOf(entry.getValue()));
					} else if (type.equals(Date.class)) {
						SimpleDateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT);
						field.set(t, df.parse(entry.getValue()));
					} else if (type.equals(BigDecimal.class)) {
						field.set(t, new BigDecimal(entry.getValue()));
					}
					break;
				} catch (Exception e) {
					System.out.println(e);
				}
				
				clazz = clazz.getSuperclass();
			}
			buffer.setLength(0);
		}
		return t;
	}

	/**
	 * 获取请求参数
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	public Map<String, String> getParameters(HttpServletRequest request) {
		Map<String, String> result = new HashMap<>();
		
		Map<String, String[]> values = request.getParameterMap();
		if(values != null && !values.isEmpty()) {
			for (Map.Entry<String, String[]> entry : values.entrySet()) {
				StringBuffer buffer = new StringBuffer();
				for (String value : entry.getValue()) {
					if(StringUtils.isNotBlank(value)) {
						buffer.append(",").append(value);
					}
				}
				if(buffer.length() > 1) {
					buffer.deleteCharAt(0);
					result.put(entry.getKey(), buffer.toString());
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取后台用户
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	public Object getAdminUser(HttpServletRequest request) {
		if(request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		return request.getSession().getAttribute(Constants.ADMIN_USER_SESSION);
	}
	
	/**
	 * 获取前端用户
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	public Object getFrontUser(HttpServletRequest request) {
		if(request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		return request.getSession().getAttribute(Constants.FRONT_USER_SESSION);
	}
	
	/**
	 * 获取微信用户
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	public Object getWebUser(HttpServletRequest request) {
		if(request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		return request.getSession().getAttribute(Constants.WEB_USER_SESSION);
	}
	/**
	 * 获取微信用户
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	public Object getWebDriver(HttpServletRequest request) {
		if(request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		return request.getSession().getAttribute(Constants.WEB_DRIVER_SESSION);
	}
	/**
	 * 文件上传
	 * 
	 * @param request
	 * 			请求对象
	 * @param key
	 * 			参数名称
	 * @return
	 * @throws Exception
	 */
	public JsonResult upload(HttpServletRequest request, String key) throws Exception {
		JsonResult json = new JsonResult(Status.FAILED);
		
		//创建文件目录
		Date date = new Date();
		String path = request.getSession().getServletContext().getRealPath("/");
		String dirsName = "/" + Constants.UP_DOWN_LOAD_FILE + "/" + DateUtils.getDateFormat(date, DateUtils.SHORT_DATE_FORMAT);
		File dirs = new File(path, dirsName);
		if(!dirs.exists()) {
			dirs.mkdirs();
		}
		
		String datafile = request.getParameter(key);
		Pattern pattern = Pattern.compile("(data:\\s*image\\/(\\w+);base64,)");
		if(StringUtils.isNotBlank(datafile)) {
			Matcher matcher = pattern.matcher(datafile);
			if(matcher.find()) {
				String type = matcher.group(2);
				if(StringUtils.isNotBlank(type)) {
					String fileName = "/" + UUID.randomUUID() + "." + type;
					datafile = matcher.replaceFirst("");
					
					Decoder decoder = Base64.getDecoder();
					File file = new File(dirs, fileName);
					FileOutputStream out = new FileOutputStream(file);
					out.write(decoder.decode(datafile));
					out.close();
					
					json.setCode(Status.SUCCESS.getCode());
					json.setMessage("上传文件成功");
					json.setResult(dirsName + fileName);
				}
			}
		} else {
			if(request != null && ServletFileUpload.isMultipartContent(request)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				List<MultipartFile> files = multipartRequest.getFiles(key);
				if(files != null && !files.isEmpty() && files.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (MultipartFile file : files) {
						String type = file.getOriginalFilename();
						type = type.substring(type.lastIndexOf("."));
						
						String fileName = "/" + UUID.randomUUID() + type;
						
						FileCopyUtils.copy(file.getBytes(), new File(dirs, fileName));
						sb.append(",").append(dirsName).append(fileName);
					}
					if(sb.length() > 0) {
						sb.deleteCharAt(0);
					}
					json.setCode(Status.SUCCESS.getCode());
					json.setMessage("上传文件成功");
					json.setResult(sb.toString());
				}
			} else {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("没有文件上传");
			}
		}
		return json;
	}
	
	/**
	 * 获取客户端地址
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	public String ip(HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if(StringUtils.isNotBlank(ip)) {
				return ip;
			}
		}
		String ip = request.getRemoteAddr();
		if(ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		return ip;
	}
}