package com.product.rental.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.product.annotation.SystemLog;
import com.product.bean.Admin;
import com.product.bean.Driver;
import com.product.bean.Programa;
import com.product.bean.Record;
import com.product.bean.User;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.DriverService;
import com.product.service.RecordService;
import com.product.service.UserService;
import com.product.utils.POIUtil;

import net.sf.json.JSONObject;

/**
 * record-控制类
 * 
 * @author wh
 *
 */
@RestController
@RequestMapping("/")
public class RecordController extends BaseController {

	private static Log tag = LogFactory.getLog("tag");

	@Autowired
	private RecordService recordService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private UserService userService;
	/** 基础地址 */
	private String path = "/index/";

	/**
	 * 全部培训列表-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/18.html")
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
				param.put("recordKftel", "pxlb");
				param.put("order_id", "desc");
				List<Record> list = recordService.getPage(param);
				view.addObject("models", list);
			}else{
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdminpx() != null && admin.getAdminAdminpx().equals(Admin.ADMIN_ADMINPX_NOT)) {
					param.put("recordKftel", "pxlb");
					param.put("order_id", "desc");
					List<Record> list = recordService.getPage(param);
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
		view.setViewName(path + "list_18");// 跳转页面
		return view;
	}

	/**
	 * 培训编辑—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/edit/18.html")
	public ModelAndView edit(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Record s = recordService.getById(Integer.parseInt(id));
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
		view.setViewName(path + "edit_18");
		return view;
	}

	/**
	 * 用户编辑-操作 save_or_update
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "编辑-操作")
	@RequestMapping("/edit/18")
	public JsonResult edits(HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			json = super.upload(request, "filedata");
			if (json.isSucceed()) {
				Map<String, String> param = super.getParameters(request);
				Record record = super.reflect("record", Record.class, param);
				record.setRecordKftel("pxlb");
				boolean flag = recordService.saveOrUpdate(record, super.ip(request));
				
				Map<String, String> tmps = new HashMap<String,String>();
				tmps.put("recordKftel", "pxlb");
				tmps.put("order_id", "desc");
				List<Record> rs=recordService.getList(tmps);//培训列表
				List<Driver> lists=driverService.getList(null);//所有司机
				if(!param.containsKey("_id")){ //判断不是		
					for (Driver driver : lists) {//给所有司机添加培训记录，培训状态为0
						if(driver.getDriverPwd()!=""&&driver.getDriverPwd()!=null){
							Driver ds=new Driver();
							ds.setDriverUsname("pxtj");
							ds.setDriverName(driver.getDriverName());
							ds.setDriverCarnum(driver.getDriverCarnum());
							ds.setDriverTel(driver.getDriverTel());
							ds.setDriverTrainstate("0");					
							ds.setDriverBeizhu(param.get("traintitle"));// 将培训标题赋值到driverbeizhu
							ds.setDriverId(rs.get(0).getId());//获得最新插入的培训的id
						    driverService.saveOrUpdate(ds, super.ip(request));
						}
					}
				}
				
				if (flag) {
					json.setCode(Status.SUCCESS.getCode());
					json.setMessage("编辑成功");
				} else {
					json.setMessage("编辑失败");
				}
				Object filename = json.getResult();
				if (filename != null) {
					String trainstartpic = record.getRecordTrainstartpic();
					if (StringUtils.isNotBlank(trainstartpic)) {
						String[] trainstartpics = trainstartpic.split(",");
						String[] filenames = filename.toString().split(",");
						int index = 0;
						if (trainstartpics != null && filenames != null) {
							for (int i = 0; i < trainstartpics.length; i++) {
								if (trainstartpics[i].equals(Programa.REPLACE_PLACEHOLDER)) {
									if (index < filenames.length) {
										trainstartpics[i] = filenames[index];
										index++;
									} else {
										trainstartpics[i] = "";
									}
								}
							}
							record.setRecordTrainstartpic(StringUtils.join(trainstartpics, ","));
						} else {
							record.setRecordTrainstartpic("");
						}
					} else {
						record.setRecordTrainstartpic(filename.toString());
					}
					
					if(recordService.saveOrUpdate(record, super.ip(request))) {
						String key = String.format(Record.RECORD_CACHE_KEY, String.valueOf(record.getId()));
						ServletContext context = request.getServletContext();
						if(context.getAttribute(key) != null) {
							param = new LinkedHashMap<>();
							List<Record> records = recordService.getList(param);
							if(records != null && !records.isEmpty() && records.size() > 0) {
								Map<String, Record> map = new LinkedHashMap<>();
								for (Record tmp : records) {
									map.put(String.valueOf(tmp.getId()), tmp);
								}
								context.setAttribute(key, map);
							}
						}
						json.setCode(Status.SUCCESS.getCode());
						json.setMessage("消息编辑成功");
					} else {
						json.setMessage("消息编辑失败");
					}
				}
			} else {
				json.setMessage("文件上传失败");
			}
		} catch (Exception e) {
			json.setCode(Status.ERROR.getCode());
			json.setMessage("系统错误,消息编辑");
			tag.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 培训统计-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/19.html")
	public ModelAndView lists(ModelAndView view, HttpServletRequest request) {
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
				param.put("driverUsname", "pxtj");
			    param.put("order_id", "desc");
				List<Driver> list = driverService.getPage(param);
				view.addObject("models", list);
			}else{
				
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdminpx() != null && admin.getAdminAdminpx().equals(Admin.ADMIN_ADMINPX_NOT)) {
					param.put("driverUsname", "pxtj");
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
		view.setViewName(path + "list_19");// 跳转页面
		return view;
	}
	
	/**
	 * 培训统计导出
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/list/190.html")
	public ModelAndView editdc(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = super.getParameters(request);
		param.put("driverUsname", "pxtj");
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
				view.addObject("models", list);
				// 创建HSSFWorkbook对象声明一个工作簿
				HSSFWorkbook wb = new HSSFWorkbook();
				// 创建HSSFSheet对象声明一个单子并命名
				HSSFSheet sheet = wb.createSheet("培训统计记录");
				// 创建HSSFCellStyle声明一个样式
				HSSFCellStyle cellStyle = wb.createCellStyle();
				// 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
				HSSFFont fontStyle = wb.createFont();
				// 创建HSSFRow对象 创建第一行(也可以称为表头)
				HSSFRow row = sheet.createRow(0);
				// 创建HSSFCell对象 给表头第一行一次创建单元格
				HSSFCell cell = row.createCell(0);
				// 设置单元格内容
				cell.setCellValue("培训统计记录一览表");
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
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
				sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(1, 5000);
				sheet.setColumnWidth(2, 5000);
				sheet.setColumnWidth(3, 5000);
				sheet.setColumnWidth(4, 5000);
				// 在sheet里创建第二行
				HSSFRow row2 = sheet.createRow(1);
				// 创建单元格并设置单元格内容
				row2.createCell(0).setCellValue("手机号码");
				row2.createCell(1).setCellValue("司机姓名");
				row2.createCell(2).setCellValue("车牌号码");
				row2.createCell(3).setCellValue("是否培训");
				row2.createCell(4).setCellValue("培训标题");
				// 在sheet里创建第三行
				int i = 2;
				for (Driver obj : list) {
					HSSFRow row3 = sheet.createRow(i);
					if (obj.getDriverTel() == null) {
						continue;
					} else {
						row3.createCell(0).setCellValue(obj.getDriverTel().toString());
					}
					if (obj.getDriverName() == null) {
						row3.createCell(1).setCellValue(" ");
					} else {
						row3.createCell(1).setCellValue(obj.getDriverName().toString());
					}
					if (obj.getDriverCarnum() == null) {
						row3.createCell(2).setCellValue(" ");
					} else {
						row3.createCell(2).setCellValue(obj.getDriverCarnum());
					}
					if (obj.getDriverTrainstate() == null) {
						row3.createCell(3).setCellValue(" ");
					} else if (obj.getDriverTrainstate().equals("1")) {
						row3.createCell(3).setCellValue("已培训");
					} else {
						row3.createCell(3).setCellValue("未培训");
					}

					if (obj.getDriverBeizhu() == null ) {
						row3.createCell(4).setCellValue(0);
					} else {
						row3.createCell(4).setCellValue(obj.getDriverBeizhu());
					}
					
					i++;
				}
				// excel 表文件名
				OutputStream out = response.getOutputStream();
				response.reset();
				String fileName = "培训统计记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
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
		view.setViewName("/index/list_12");
		return view;
	}

	/**
	 * 后台首页-视图
	 * 
	 * @param view
	 *            模型视图
	 * @return
	 */
	@RequestMapping("/list/25.html")
	public ModelAndView listbz(ModelAndView view, HttpServletRequest request) {
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
				param.put("recordKftel", "bzzx");
				param.put("order_id", "desc");
				List<Record> list = recordService.getPage(param);
				view.addObject("models", list);
			}else{
				
				HttpSession session = request.getSession();
				Admin admin=(Admin)session.getAttribute(Constants.ADMIN_USER_SESSION);
				if (admin.getAdminAdminbz() != null
						&& admin.getAdminAdminbz().equals(Admin.ADMIN_ADMINBZ_NOT)) {
					param.put("recordKftel", "bzzx");
					param.put("order_id", "desc");
					List<Record> list = recordService.getPage(param);
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
		view.setViewName("/index/list_25");
		return view;
	}

	/**
	 * 修改—入口
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/edit/25.html")
	public ModelAndView editbz(ModelAndView view, HttpServletRequest request) {
		Map<String, String> param = super.getParameters(request);
		try {
			if (param.containsKey("id")) {
				String id = param.get("id");
				Record r = recordService.getById(Integer.parseInt(id));
				view.addObject("model", r);
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		} finally {
			param.remove("mid");
			param.remove("id");
		}
		view.addObject("params", param);
		view.setViewName("/index/edit_25");
		return view;
	}

	/**
	 * 帮助中心编辑-操作 save_or_update
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@SystemLog(description = "帮助中心编辑-操作")
	@RequestMapping("/edit/25")
	public JsonResult editsbz(ModelAndView view, HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			Map<String, String> param = super.getParameters(request);
			Record record = super.reflect("record", Record.class, param);
			record.setRecordKftel("bzzx");
			boolean flag = recordService.saveOrUpdate(record,super.ip(request));
			if (flag) {
				json.setCode(Status.SUCCESS.getCode());
				json.setMessage("帮助中心编辑成功");
			} else {
				json.setMessage("帮助中心编辑失败");
			}
		} catch (Exception e) {
			tag.error(e.getMessage(), e);
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
		}
		return json;
	}
	
	
	/**
	 * 消息编辑-操作-导入
	 * 
	 * @param request
	 * 			请求对象
	 * @return
	 */
	@SystemLog(description = "消息编辑-操作")
	@RequestMapping("edit/1000")
	public JsonResult edit(HttpServletRequest request) {
		JsonResult json = new JsonResult(Status.FAILED);
		try {
			json = super.upload(request, "filedata");
			if(json.isSucceed()) {
				Map<String, String> param = super.getParameters(request);
				User user = super.reflect("user", User.class, param);
				String path = request.getSession().getServletContext().getRealPath("/");
				Object filename = json.getResult();
				String file=filename.toString();
				//获得上传过后的文件名字
				filename = file.substring(file.lastIndexOf("/")+1);  
				File dirs = new File(path, file);
				if (filename != null) {
					if (filename.toString().endsWith(".xls") || filename.toString().endsWith(".xlsx")) {
						String avatar = user.getUserDetails();
						if (StringUtils.isNotBlank(avatar)) {
							String[] avatars = avatar.split(",");
							String[] filenames = filename.toString().split(",");
							int index = 0;
							if (avatars != null && filenames != null) {
								for (int i = 0; i < avatars.length; i++) {
									if (avatars[i].equals(Programa.REPLACE_PLACEHOLDER)) {
										if (index < filenames.length) {
											avatars[i] = filenames[index];
											index++;
										} else {
											avatars[i] = "";
										}
									}
								}
								user.setUserDetails(StringUtils.join(avatars, ","));
							} else {
								user.setUserDetails("");
							}
						} else {
							user.setUserDetails(filename.toString());
						}
						InputStream fis = new FileInputStream(dirs);  
						//对应得到Excel的行
						Row row=null;
						//对应得到Excel的列
						Cell cell=null;
						//创建工作簿
						Workbook wb = null; 
						//得到工作表
						Sheet sheet=null;
						if (filename.toString().endsWith(".xls") ) {
							wb=new HSSFWorkbook(fis);//2003
						}
						if (filename.toString().endsWith(".xlsx")) {
							wb=new XSSFWorkbook(fis);//2007
						}
						
						for(int i=0;i<wb.getNumberOfSheets();i++){
							sheet=wb.getSheetAt(i);
							if (sheet==null) {
								continue;
							}
							//遍历当前sheet中的所有行  
				            //包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部 
							for(int j=sheet.getFirstRowNum()+2;j<=sheet.getLastRowNum();j++){
								User user2=null;
								//读取第一行
								row=sheet.getRow(j);
								//去掉空行和表头  
				                if(row==null||row.getFirstCellNum()==j){
				                   continue;
				                }  
				                if (row!=null) {
				                	user2=new User();
				                	user2.setUserDriverid("1229");
				                	user2.setUserName((String) POIUtil.getCellValue(row.getCell(0)));
				                	user2.setUserPwd(row.getCell(1).toString());
				                	user2.setUserTel(row.getCell(2).toString());
				                	user2.setUserCompany(row.getCell(3).toString());
				                	user2.setUserState("0");
				                	boolean flag =userService.saveOrUpdate(user2, super.ip(request));
				                	if (flag) {					
				        				json.setCode(Status.SUCCESS.getCode());
				        				json.setMessage("导入成功");
				        			} else {
				        				json.setCode(Status.ERROR.getCode());
				        				json.setMessage("导入失败");
				        			}
								}
//				                //遍历所有的列  
//				                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {  
//				                    cell = row.getCell(y);
//				                }  
							}
						}
//						//得到Excel的总记录条数
//						int totalRow=sheet.getLastRowNum();
						
					}else{
						json.setCode(Status.ERROR.getCode());
						json.setMessage("文件上传格式不对！");
					}
				}
				
			} else {
				json.setCode(Status.ERROR.getCode());
				json.setMessage("文件上传失败");
			}
		} catch (Exception e) {
			json.setCode(Status.ERROR.getCode());
			json.setMessage("系统错误,消息编辑");
			tag.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 培训统计导出
	 * 
	 * 
	 * @param view
	 *            模型视图
	 * 
	 * @param request
	 *            请求对象
	 * @return
	 */
	@RequestMapping("/list/111.html")
	public ModelAndView editds(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = super.getParameters(request);
		param.put("driverLeavel", "9");
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
				view.addObject("models", list);
				// 创建HSSFWorkbook对象声明一个工作簿
				HSSFWorkbook wb = new HSSFWorkbook();
				// 创建HSSFSheet对象声明一个单子并命名
				HSSFSheet sheet = wb.createSheet("司机管理");
				// 创建HSSFCellStyle声明一个样式
				HSSFCellStyle cellStyle = wb.createCellStyle();
				// 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
				HSSFFont fontStyle = wb.createFont();
				// 创建HSSFRow对象 创建第一行(也可以称为表头)
				HSSFRow row = sheet.createRow(0);
				// 创建HSSFCell对象 给表头第一行一次创建单元格
				HSSFCell cell = row.createCell(0);
				// 设置单元格内容
				cell.setCellValue("司机管理一览表");
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
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
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
				row2.createCell(1).setCellValue("司机名称");
				row2.createCell(2).setCellValue("司机英文名称");
				row2.createCell(3).setCellValue("工号");
				row2.createCell(4).setCellValue("车牌号码");
				row2.createCell(5).setCellValue("服务用户");
				row2.createCell(6).setCellValue("账号状态");
				row2.createCell(7).setCellValue("添加时间");
				// 在sheet里创建第三行
				int i = 2;
				for (Driver obj : list) {
					HSSFRow row3 = sheet.createRow(i);
					if (obj.getDriverTel() == null) {
						continue;
					} else {
						row3.createCell(0).setCellValue(obj.getDriverTel().toString());
					}
					if (obj.getDriverName() == null) {
						row3.createCell(1).setCellValue(" ");
					} else {
						row3.createCell(1).setCellValue(obj.getDriverName().toString());
					}
					if (obj.getDriverUsname() == null) {
						row3.createCell(2).setCellValue(" ");
					} else {
						row3.createCell(2).setCellValue(obj.getDriverUsname());
					}
					if (obj.getDriverId() == null) {
						row3.createCell(3).setCellValue(" ");
					} else {
						row3.createCell(3).setCellValue(obj.getDriverId());
					}
					if (obj.getDriverCarnum() == null) {
						row3.createCell(4).setCellValue(" ");
					} else {
						row3.createCell(4).setCellValue(obj.getDriverCarnum());
					}
					if (obj.getDriverUtel() == null) {
						row3.createCell(5).setCellValue(" ");
					} else {
						row3.createCell(5).setCellValue(obj.getDriverUtel());
					}
					if (obj.getDriverState()== null) {
						row3.createCell(6).setCellValue(" ");
					} else if (obj.getDriverState().equals("1")) {
						row3.createCell(6).setCellValue("禁用");
					} else {
						row3.createCell(6).setCellValue("启用");
					}
					if (obj.getInitDate() == null ) {
						row3.createCell(7).setCellValue(0);
					} else {
						row3.createCell(7).setCellValue(simpleDateFormat.format(obj.getInitDate()));
					}
					
					i++;
				}
				// excel 表文件名
				OutputStream out = response.getOutputStream();
				response.reset();
				String fileName = "司机管理" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
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
		view.setViewName("/index/list_11");
		return view;
	}
}
