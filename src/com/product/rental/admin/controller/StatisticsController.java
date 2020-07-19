package com.product.rental.admin.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.DriverService;

import net.sf.json.JSONObject;

/**
 * 统计管理 --控制类
 *
 * @author wh
 */
@RestController
@RequestMapping("/")
public class StatisticsController extends BaseController {
    private static Log tag = LogFactory.getLog("tag");

    @Autowired
    private DriverService driverService;

    /**
     * 每日出行后台首页-视图
     *
     * @param view 模型视图
     * @return
     */
    @RequestMapping("/list/12.html")
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
                param.put("driverBeizhu", "lxby");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            } else {

                param.put("driverBeizhu", "lxby");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            e.printStackTrace();
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        view.addObject("params", param);
        view.setViewName("/index/list_12");
        return view;
    }

    /**
     * 每日出行编辑—入口
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/edit/12.html")
    public ModelAndView edit(ModelAndView view, HttpServletRequest request) {
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
        view.setViewName("/index/edit_12");
        return view;
    }

    /**
     * 每日出行导出
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/list/123.html")
    public ModelAndView editdc(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> param = super.getParameters(request);
        param.put("driverBeizhu", "lxby");
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
            HSSFSheet sheet = wb.createSheet("每日例行记录");
            // 创建HSSFCellStyle声明一个样式
            HSSFCellStyle cellStyle = wb.createCellStyle();
            // 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
            HSSFFont fontStyle = wb.createFont();
            // 创建HSSFRow对象 创建第一行(也可以称为表头)
            HSSFRow row = sheet.createRow(0);
            // 创建HSSFCell对象 给表头第一行一次创建单元格
            HSSFCell cell = row.createCell(0);
            // 设置单元格内容
            cell.setCellValue("每日例行记录一览表");
            // 定义日期格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // 定义日期格式
            SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            row2.createCell(1).setCellValue("司机名称");
            row2.createCell(2).setCellValue("司机工号");
            row2.createCell(3).setCellValue("出行前维护");
            row2.createCell(4).setCellValue("出行公里数");
            row2.createCell(5).setCellValue("车牌号码");
            row2.createCell(6).setCellValue("例保日期");
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
                if (obj.getDriverId() == null) {
                    row3.createCell(2).setCellValue(" ");
                } else {
                    row3.createCell(2).setCellValue(obj.getDriverId());
                }
                if (obj.getDriverClock() == null) {
                    row3.createCell(3).setCellValue(" ");
                } else if (obj.getDriverClock().equals("1")) {
                    row3.createCell(3).setCellValue("是");
                } else {
                    row3.createCell(3).setCellValue("否");
                }

                if (obj.getDriverStartmile() == null || obj.getDriverStartmile() == 0) {
                    row3.createCell(4).setCellValue(0);
                } else {
                    row3.createCell(4).setCellValue(obj.getDriverStartmile());
                }
                if (obj.getDriverCarnum() == null) {
                    row3.createCell(5).setCellValue(" ");
                } else {
                    row3.createCell(5).setCellValue(obj.getDriverCarnum());
                }
                if (obj.getDriverDate() == null) {
                    row3.createCell(6).setCellValue(" ");
                } else {
                    row3.createCell(6).setCellValue(formatter.format(obj.getDriverDate()));
                }
                if (obj.getInitDate() == null) {
                    row3.createCell(7).setCellValue(" ");
                } else {
                    row3.createCell(7).setCellValue(formatters.format(obj.getInitDate()));
                }

                i++;
            }
            // excel 表文件名
            OutputStream out = response.getOutputStream();
            response.reset();
            String fileName = "每日例行记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
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
     * 日常记录导出
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/list/124.html")
    public ModelAndView editdc2(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> param = super.getParameters(request);
        param.put("driverBeizhu", "rcjl");
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
            HSSFSheet sheet = wb.createSheet("日常记录");
            // 创建HSSFCellStyle声明一个样式
            HSSFCellStyle cellStyle = wb.createCellStyle();
            // 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
            HSSFFont fontStyle = wb.createFont();
            // 创建HSSFRow对象 创建第一行(也可以称为表头)
            HSSFRow row = sheet.createRow(0);
            // 创建HSSFCell对象 给表头第一行一次创建单元格
            HSSFCell cell = row.createCell(0);
            // 设置单元格内容
            cell.setCellValue("日常记录一览表");
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
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
            sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 5000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            sheet.setColumnWidth(10, 5000);
            sheet.setColumnWidth(11, 5000);
            sheet.setColumnWidth(12, 5000);
            sheet.setColumnWidth(13, 5000);
            sheet.setColumnWidth(14, 5000);
            // 在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(1);
            // 创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("司机电话");
            row2.createCell(1).setCellValue("司机姓名");
            row2.createCell(2).setCellValue("司机工号");
            row2.createCell(3).setCellValue("车牌号码");
            row2.createCell(4).setCellValue("司机出站路码");
            row2.createCell(5).setCellValue("司机出站时间");
            row2.createCell(6).setCellValue("上车路码");
            row2.createCell(7).setCellValue("上车时间");
            row2.createCell(8).setCellValue("下车路码");
            row2.createCell(9).setCellValue("下车时间");
            row2.createCell(10).setCellValue("司机回站路码");
            row2.createCell(11).setCellValue("司机回站时间");
            row2.createCell(12).setCellValue("营运公里数");
            row2.createCell(13).setCellValue("日志日期");
            row2.createCell(14).setCellValue("添加时间");
            // 在sheet里创建第三行
            int i = 2;
            for (Driver obj : list) {
                HSSFRow row3 = sheet.createRow(i);
                // 电话
                if (obj.getDriverTel() == null) {
                    continue;
                } else {
                    row3.createCell(0).setCellValue(obj.getDriverTel().toString());
                }
                // 姓名
                if (obj.getDriverName() == null) {
                    row3.createCell(1).setCellValue(" ");
                } else {
                    row3.createCell(1).setCellValue(obj.getDriverName().toString());
                }
                // 司机工号
                if (obj.getDriverId() == null) {
                    row3.createCell(2).setCellValue(" ");
                } else {
                    row3.createCell(2).setCellValue(obj.getDriverId());
                }

                // 车牌号
                if (obj.getDriverCarnum() == null) {
                    row3.createCell(3).setCellValue(" ");
                } else {
                    row3.createCell(3).setCellValue(obj.getDriverCarnum().toString());
                }
                // 司机出发时公里数
                if (obj.getDriverLeavemile() == null || obj.getDriverLeavemile() == 0) {
                    row3.createCell(4).setCellValue(0);
                } else {
                    row3.createCell(4).setCellValue(obj.getDriverLeavemile());
                }
                // 司机出发时时间
                if (obj.getDriverLeavedate() == null) {
                    row3.createCell(5).setCellValue(" ");
                } else {
                    row3.createCell(5).setCellValue(formatter.format(obj.getDriverLeavedate()));
                }
                // 用户出发时公里数
                if (obj.getDriverLeavemileuser() == null || obj.getDriverLeavemileuser() == 0) {
                    row3.createCell(6).setCellValue(0);
                } else {
                    row3.createCell(6).setCellValue(obj.getDriverLeavemileuser());
                }
                // 用户出发时时间
                if (obj.getDriverLeavedateuser() == null) {
                    row3.createCell(7).setCellValue(" ");
                } else {
                    row3.createCell(7).setCellValue(formatter.format(obj.getDriverLeavedateuser()));
                }
                // 用户下车时公里数
                if (obj.getDriverBackmileuser() == null || obj.getDriverBackmileuser() == 0) {
                    row3.createCell(8).setCellValue(0);
                } else {
                    row3.createCell(8).setCellValue(obj.getDriverBackmileuser());
                }
                // 用户下车时时间
                if (obj.getDriverBackdateuser() == null) {
                    row3.createCell(9).setCellValue(" ");
                } else {
                    row3.createCell(9).setCellValue(formatter.format(obj.getDriverBackdateuser()));
                }

                // 司机下车时公里数
                if (obj.getDriverBackmile() == null || obj.getDriverBackmile() == 0) {
                    row3.createCell(10).setCellValue(0);
                } else {
                    row3.createCell(10).setCellValue(obj.getDriverBackmile());
                }
                // 司机下车时时间
                if (obj.getDriverBackdate() == null) {
                    row3.createCell(11).setCellValue(" ");
                } else {
                    row3.createCell(11).setCellValue(formatter.format(obj.getDriverBackdate()));
                }
                // 公里数
                if (obj.getDriverMilenow() == null || obj.getDriverMilenow() == 0) {
                    row3.createCell(12).setCellValue(0);
                } else {
                    row3.createCell(12).setCellValue(obj.getDriverMilenow());
                }
                // 时间
                if (obj.getDriverDate() == null) {
                    row3.createCell(13).setCellValue(" ");
                } else {
                    row3.createCell(13).setCellValue(formatters.format(obj.getDriverDate()));
                }
                // 时间
                if (obj.getInitDate() == null) {
                    row3.createCell(14).setCellValue(" ");
                } else {
                    row3.createCell(14).setCellValue(formatter.format(obj.getInitDate()));
                }

                i++;
            }
            // excel 表文件名
            OutputStream out = response.getOutputStream();
            response.reset();
            String fileName = "日常记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
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
        view.setViewName("/index/list_13");
        return view;
    }

    /**
     * 加油记录导出
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/list/125.html")
    public ModelAndView editdc3(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> param = super.getParameters(request);
        param.put("driverBeizhu", "jyjl");
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
            HSSFSheet sheet = wb.createSheet("加油记录");
            // 创建HSSFCellStyle声明一个样式
            HSSFCellStyle cellStyle = wb.createCellStyle();
            // 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
            HSSFFont fontStyle = wb.createFont();
            // 创建HSSFRow对象 创建第一行(也可以称为表头)
            HSSFRow row = sheet.createRow(0);
            // 创建HSSFCell对象 给表头第一行一次创建单元格
            HSSFCell cell = row.createCell(0);
            // 设置单元格内容
            cell.setCellValue("加油记录一览表");
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
            row2.createCell(1).setCellValue("司机名称");
            row2.createCell(2).setCellValue("司机工号");
            row2.createCell(3).setCellValue("车牌号码");
            row2.createCell(4).setCellValue("当前码表公里数");
            row2.createCell(5).setCellValue("油卡/现金");
            row2.createCell(6).setCellValue("加油日期及时间");
            row2.createCell(7).setCellValue("添加时间");
            // 在sheet里创建第三行
            int i = 2;
            for (Driver obj : list) {
                HSSFRow row3 = sheet.createRow(i);
                // 电话
                if (obj.getDriverTel() == null) {
                    continue;
                } else {
                    row3.createCell(0).setCellValue(obj.getDriverTel().toString());
                }
                // 姓名
                if (obj.getDriverName() == null) {
                    row3.createCell(1).setCellValue(" ");
                } else {
                    row3.createCell(1).setCellValue(obj.getDriverName().toString());
                }
                // 司机工号
                if (obj.getDriverId() == null) {
                    row3.createCell(2).setCellValue(" ");
                } else {
                    row3.createCell(2).setCellValue(obj.getDriverId());
                }

                // 车牌号
                if (obj.getDriverCarnum() == null) {
                    row3.createCell(3).setCellValue(" ");
                } else {
                    row3.createCell(3).setCellValue(obj.getDriverCarnum().toString());
                }
                // 当前码表数
                if (obj.getDriverMilenow() == null || obj.getDriverMilenow() == 0) {
                    row3.createCell(4).setCellValue(0);
                } else {
                    row3.createCell(4).setCellValue(obj.getDriverMilenow());
                }

                // 油卡或现金
                if (obj.getDriverGasrpaytype() == null) {
                    row3.createCell(5).setCellValue(" ");
                } else if (obj.getDriverGasrpaytype().equals("0")) {

                    row3.createCell(5).setCellValue("现金");
                } else if (obj.getDriverGasrpaytype().equals("1")) {

                    row3.createCell(5).setCellValue("油卡");
                }
                // 初始日期
                if (obj.getDriverDate() == null) {
                    row3.createCell(6).setCellValue(" ");
                } else {
                    row3.createCell(6).setCellValue(formatter.format(obj.getDriverDate()));
                }
                // 初始日期
                if (obj.getInitDate() == null) {
                    row3.createCell(7).setCellValue(" ");
                } else {
                    row3.createCell(7).setCellValue(formatter.format(obj.getInitDate()));
                }
                i++;
            }

            // excel 表文件名
            OutputStream out = response.getOutputStream();
            response.reset();
            String fileName = "加油记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
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
        view.setViewName("/index/list_14");
        return view;
    }

    /**
     * 加班记录导出
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/list/126.html")
    public ModelAndView editdc4(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> param = super.getParameters(request);
        param.put("driverBeizhu", "jbjl");
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
            HSSFSheet sheet = wb.createSheet("加班记录");
            // 创建HSSFCellStyle声明一个样式
            HSSFCellStyle cellStyle = wb.createCellStyle();
            // 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
            HSSFFont fontStyle = wb.createFont();
            // 创建HSSFRow对象 创建第一行(也可以称为表头)
            HSSFRow row = sheet.createRow(0);
            // 创建HSSFCell对象 给表头第一行一次创建单元格
            HSSFCell cell = row.createCell(0);
            // 设置单元格内容
            cell.setCellValue("加班记录一览表");
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
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
            sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 5000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            // 在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(1);
            // 创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("手机号码");
            row2.createCell(1).setCellValue("司机名称");
            row2.createCell(2).setCellValue("司机工号");
            row2.createCell(3).setCellValue("车牌号码");
            row2.createCell(4).setCellValue("加班时长/小时");
            row2.createCell(5).setCellValue("加班类型");
            row2.createCell(6).setCellValue("用车目的");
            row2.createCell(7).setCellValue("加班总金额");
            row2.createCell(8).setCellValue("加班日期");
            row2.createCell(9).setCellValue("添加时间");
            // 在sheet里创建第三行
            int i = 2;
            for (Driver obj : list) {
                HSSFRow row3 = sheet.createRow(i);
                // 电话
                if (obj.getDriverTel() == null) {
                    continue;
                } else {
                    row3.createCell(0).setCellValue(obj.getDriverTel().toString());
                }
                // 姓名
                if (obj.getDriverName() == null) {
                    row3.createCell(1).setCellValue(" ");
                } else {
                    row3.createCell(1).setCellValue(obj.getDriverName().toString());
                }
                // 司机工号
                if (obj.getDriverId() == null) {
                    row3.createCell(2).setCellValue(" ");
                } else {
                    row3.createCell(2).setCellValue(obj.getDriverId());
                }
                // 车牌号
                if (obj.getDriverCarnum() == null) {
                    row3.createCell(3).setCellValue(" ");
                } else {
                    row3.createCell(3).setCellValue(obj.getDriverCarnum().toString());
                }
                // 加班时长
                if (obj.getDriverOvertimehours() == null || obj.getDriverOvertimehours() == 0) {
                    row3.createCell(4).setCellValue(0);
                } else {
                    row3.createCell(4).setCellValue(obj.getDriverOvertimehours());
                }
                // 加班类型
                if (obj.getDriverOvertimetype() == null) {
                    row3.createCell(5).setCellValue(" ");
                } else if (obj.getDriverOvertimetype().equals("0")) {

                    row3.createCell(5).setCellValue("工作日");
                } else if (obj.getDriverOvertimetype().equals("1")) {

                    row3.createCell(5).setCellValue("节假日");
                } else {
                    row3.createCell(5).setCellValue("周末");
                }

                // 加班用车目的
                if (obj.getDriverOvertimeusecar() == null) {
                    row3.createCell(6).setCellValue(" ");
                } else if (obj.getDriverOvertimeusecar().equals("0")) {

                    row3.createCell(6).setCellValue("个人原因");
                } else if (obj.getDriverOvertimeusecar().equals("1")) {

                    row3.createCell(6).setCellValue("商务原因");
                }
                // 加班总费用
                if (obj.getDriverOvertimermbtotal() == null) {
                    row3.createCell(7).setCellValue(0);
                } else {
                    row3.createCell(7).setCellValue(obj.getDriverOvertimermbtotal());
                }

                // 初始日期
                if (obj.getDriverDate() == null) {
                    row3.createCell(8).setCellValue(" ");
                } else {
                    row3.createCell(8).setCellValue(formatters.format(obj.getDriverDate()));
                }
                // 初始日期
                if (obj.getInitDate() == null) {
                    row3.createCell(9).setCellValue(" ");
                } else {
                    row3.createCell(9).setCellValue(formatter.format(obj.getInitDate()));
                }
                i++;
            }
            // excel 表文件名
            OutputStream out = response.getOutputStream();
            response.reset();
            String fileName = "加班记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
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
        view.setViewName("/index/list_15");
        return view;
    }

    /**
     * 费用记录导出
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/list/127.html")
    public ModelAndView editdc5(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> param = super.getParameters(request);
        param.put("driverBeizhu", "fyjl");
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
            HSSFSheet sheet = wb.createSheet("费用记录");
            // 创建HSSFCellStyle声明一个样式
            HSSFCellStyle cellStyle = wb.createCellStyle();
            // 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
            HSSFFont fontStyle = wb.createFont();
            // 创建HSSFRow对象 创建第一行(也可以称为表头)
            HSSFRow row = sheet.createRow(0);
            // 创建HSSFCell对象 给表头第一行一次创建单元格
            HSSFCell cell = row.createCell(0);
            // 设置单元格内容
            cell.setCellValue("费用记录一览表");
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
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
            sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 5000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            // 在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(1);
            // 创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("手机号码");
            row2.createCell(1).setCellValue("司机名称");
            row2.createCell(2).setCellValue("司机工号");
            row2.createCell(3).setCellValue("车牌号码");
            row2.createCell(4).setCellValue("费用承担");
            row2.createCell(5).setCellValue("费用目的");
            row2.createCell(6).setCellValue("费用类型");
            row2.createCell(7).setCellValue("费用");
            row2.createCell(8).setCellValue("费用发生日期");
            row2.createCell(9).setCellValue("添加时间");
            // 在sheet里创建第三行
            int i = 2;
            for (Driver obj : list) {
                HSSFRow row3 = sheet.createRow(i);
                // 电话
                if (obj.getDriverTel() == null) {
                    continue;
                } else {
                    row3.createCell(0).setCellValue(obj.getDriverTel().toString());
                }
                // 姓名
                if (obj.getDriverName() == null) {
                    row3.createCell(1).setCellValue(" ");
                } else {
                    row3.createCell(1).setCellValue(obj.getDriverName().toString());
                }
                // 司机工号
                if (obj.getDriverId() == null) {
                    row3.createCell(2).setCellValue(" ");
                } else {
                    row3.createCell(2).setCellValue(obj.getDriverId());
                }
                // 车牌号
                if (obj.getDriverCarnum() == null) {
                    row3.createCell(3).setCellValue(" ");
                } else {
                    row3.createCell(3).setCellValue(obj.getDriverCarnum().toString());
                }

                // 费用承担
                if (obj.getDriverRmbassume() == null) {
                    row3.createCell(4).setCellValue(" ");
                } else if (obj.getDriverRmbassume().equals("0")) {

                    row3.createCell(4).setCellValue("客户直接支付");
                } else if (obj.getDriverRmbassume().equals("1")) {

                    row3.createCell(4).setCellValue("安诺久通先行垫付");
                }

                // 费用目的
                if (obj.getDriverRmbgoal() == null) {
                    row3.createCell(5).setCellValue(" ");
                } else if (obj.getDriverRmbgoal().equals("0")) {
                    row3.createCell(5).setCellValue("商务");
                } else if (obj.getDriverRmbgoal().equals("1")) {
                    row3.createCell(5).setCellValue("个人");
                }

                // 费用类型
                if (obj.getDriverRmbtype() == null) {
                    row3.createCell(6).setCellValue(" ");
                } else if (obj.getDriverRmbtype().equals("0")) {
                    row3.createCell(6).setCellValue("早餐");
                } else if (obj.getDriverRmbtype().equals("1")) {
                    row3.createCell(6).setCellValue("中餐");
                } else if (obj.getDriverRmbtype().equals("2")) {
                    row3.createCell(6).setCellValue("晚餐");
                } else if (obj.getDriverRmbtype().equals("3")) {
                    row3.createCell(6).setCellValue("通行费");
                } else if (obj.getDriverRmbtype().equals("4")) {
                    row3.createCell(6).setCellValue("停车费");
                } else if (obj.getDriverRmbtype().equals("5")) {
                    row3.createCell(6).setCellValue("住宿费");
                } else if (obj.getDriverRmbtype().equals("6")) {
                    row3.createCell(6).setCellValue("差旅");
                } else if (obj.getDriverRmbtype().equals("7")) {
                    row3.createCell(6).setCellValue("餐饮");
                } else if (obj.getDriverRmbtype().equals("8")) {
                    row3.createCell(6).setCellValue("通讯费");
                } else if (obj.getDriverRmbtype().equals("9")) {
                    row3.createCell(6).setCellValue("交通费");
                } else if (obj.getDriverRmbtype().equals("10")) {
                    row3.createCell(6).setCellValue("加油费");
                } else if (obj.getDriverRmbtype().equals("11")) {
                    row3.createCell(6).setCellValue("其它");
                }

                // 费用
                if (obj.getDriverRmb() == null) {
                    row3.createCell(7).setCellValue(0);
                } else {
                    row3.createCell(7).setCellValue(obj.getDriverRmb());
                }

                // 初始日期
                if (obj.getDriverDate() == null) {
                    row3.createCell(8).setCellValue(" ");
                } else {
                    row3.createCell(8).setCellValue(formatters.format(obj.getDriverDate()));
                }
                // 初始日期
                if (obj.getInitDate() == null) {
                    row3.createCell(9).setCellValue(" ");
                } else {
                    row3.createCell(9).setCellValue(formatter.format(obj.getInitDate()));
                }
                i++;
            }

            // excel 表文件名
            OutputStream out = response.getOutputStream();
            response.reset();
            String fileName = "费用记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
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
        view.setViewName("/index/list_16");
        return view;
    }

    /**
     * 换车记录导出
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/list/128.html")
    public ModelAndView editdc6(ModelAndView view, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> param = super.getParameters(request);
        param.put("driverBeizhu", "hcjl");
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
            HSSFSheet sheet = wb.createSheet("换车记录");
            // 创建HSSFCellStyle声明一个样式
            HSSFCellStyle cellStyle = wb.createCellStyle();
            // 创建HSSFFont对象（调用HSSFWorkbook 的createFont方法）
            HSSFFont fontStyle = wb.createFont();
            // 创建HSSFRow对象 创建第一行(也可以称为表头)
            HSSFRow row = sheet.createRow(0);
            // 创建HSSFCell对象 给表头第一行一次创建单元格
            HSSFCell cell = row.createCell(0);
            // 设置单元格内容
            cell.setCellValue("换车记录一览表");
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
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
            sheet.setColumnWidth(0, 5000); // 第一个参数代表列id(从0开始),第2个参数代表宽度值
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 5000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            // 在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(1);
            // 创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("手机号码");
            row2.createCell(1).setCellValue("司机名称");
            row2.createCell(2).setCellValue("司机工号");
            row2.createCell(3).setCellValue("原车牌号");
            row2.createCell(4).setCellValue("换车原因");
            row2.createCell(5).setCellValue("原车公里数");
            row2.createCell(6).setCellValue("更换后车牌号");
            row2.createCell(7).setCellValue("新车公里数");
            row2.createCell(8).setCellValue("换车日期及时间");
            row2.createCell(9).setCellValue("添加时间");
            // 在sheet里创建第三行
            int i = 2;
            for (Driver obj : list) {
                HSSFRow row3 = sheet.createRow(i);
                // 电话
                if (obj.getDriverTel() == null) {
                    continue;
                } else {
                    row3.createCell(0).setCellValue(obj.getDriverTel().toString());
                }
                // 姓名
                if (obj.getDriverName() == null) {
                    row3.createCell(1).setCellValue(" ");
                } else {
                    row3.createCell(1).setCellValue(obj.getDriverName().toString());
                }
                // 司机工号
                if (obj.getDriverId() == null) {
                    row3.createCell(2).setCellValue(" ");
                } else {
                    row3.createCell(2).setCellValue(obj.getDriverId());
                }

                // 车牌号
                if (obj.getDriverJcarnum() == null) {
                    row3.createCell(3).setCellValue(" ");
                } else {
                    row3.createCell(3).setCellValue(obj.getDriverJcarnum().toString());
                }

                // 换车原因
                if (obj.getDriverReason() == null) {
                    row3.createCell(4).setCellValue(" ");
                } else if (obj.getDriverReason().equals("0")) {
                    row3.createCell(4).setCellValue("维修保养");
                } else if (obj.getDriverReason().equals("1")) {
                    row3.createCell(4).setCellValue("事故");
                } else if (obj.getDriverReason().equals("2")) {
                    row3.createCell(4).setCellValue("处理罚单");
                } else if (obj.getDriverReason().equals("3")) {
                    row3.createCell(4).setCellValue("合同到期");
                } else if (obj.getDriverReason().equals("4")) {
                    row3.createCell(4).setCellValue("车况不佳");
                }

                // 旧车公里数
                if (obj.getDriverJcarmile() == null) {
                    row3.createCell(5).setCellValue(0);
                } else {
                    row3.createCell(5).setCellValue(obj.getDriverJcarmile());
                }

                // 新车车牌号
                if (obj.getDriverNewcarnum() == null) {
                    row3.createCell(6).setCellValue(" ");
                } else {
                    row3.createCell(6).setCellValue(obj.getDriverNewcarnum().toString());
                }

                // 新车公里数
                if (obj.getDriverNewcarmile() == null) {
                    row3.createCell(7).setCellValue(0);
                } else {
                    row3.createCell(7).setCellValue(obj.getDriverNewcarmile());
                }

                // 初始日期
                if (obj.getDriverDate() == null) {
                    row3.createCell(8).setCellValue(" ");
                } else {
                    row3.createCell(8).setCellValue(formatter.format(obj.getDriverDate()));
                }
                // 初始日期
                if (obj.getInitDate() == null) {
                    row3.createCell(9).setCellValue(" ");
                } else {
                    row3.createCell(9).setCellValue(formatter.format(obj.getInitDate()));
                }
                i++;
            }

            // excel 表文件名
            OutputStream out = response.getOutputStream();
            response.reset();
            String fileName = "换车记录" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
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
        view.setViewName("/index/list_17");
        return view;
    }

    /**
     * 每日出行-操作 save_or_update
     *
     * @param request 请求对象
     * @return
     */
    @SystemLog(description = "每日出行编辑-操作")
    @RequestMapping("/edit/12")
    public JsonResult edits(ModelAndView view, HttpServletRequest request) {
        JsonResult json = new JsonResult(Status.FAILED);
        try {
            Map<String, String> param = super.getParameters(request);
            String str = request.getParameter("startmile");
            Double double1 = null;
            if (str == null || str == "") {
                double1 = Double.parseDouble("0.0");
            } else {
                double1 = Double.parseDouble(str);
            }
            Driver driver = super.reflect("driver", Driver.class, param);
            driver.setDriverStartmile(double1);
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
     * 日常记录后台首页-视图
     *
     * @param view 模型视图
     * @return
     */
    @RequestMapping("/list/13.html")
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
                param.put("driverBeizhu", "rcjl");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            } else {

                param.put("driverBeizhu", "rcjl");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            e.printStackTrace();
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        view.addObject("params", param);
        view.setViewName("/index/list_13");
        return view;
    }

    /**
     * 日常记录编辑—入口
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/edit/13.html")
    public ModelAndView edit1(ModelAndView view, HttpServletRequest request) {
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
        view.setViewName("/index/edit_13");
        return view;
    }

    /**
     * 日常记录编辑-操作 save_or_update
     *
     * @param request 请求对象
     * @return
     */
    @SystemLog(description = "日常记录编辑-操作")
    @RequestMapping("/edit/13")
    public JsonResult edits1(ModelAndView view, HttpServletRequest request) {
        JsonResult json = new JsonResult(Status.FAILED);
        try {
            Map<String, String> param = super.getParameters(request);

            String mile = request.getParameter("leavemile");
            String mileuser = request.getParameter("leavemileuser");
            String backmileuser = request.getParameter("backmileuser");
            String backmile = request.getParameter("backmile");
            String milenow = request.getParameter("milenow");
            Double double1 = null;
            Double double2 = null;
            Double double3 = null;
            Double double4 = null;
            Double double5 = null;
            if (mile == null || mile == "") {
                double1 = Double.parseDouble("0.0");
            } else {
                double1 = Double.parseDouble(mile);
            }

            if (mileuser == null || mileuser == "") {
                double2 = Double.parseDouble("0.0");
            } else {
                double2 = Double.parseDouble(mileuser);
            }

            if (backmileuser == null || backmileuser == "") {
                double3 = Double.parseDouble("0.0");
            } else {
                double3 = Double.parseDouble(backmileuser);
            }

            if (backmile == null || backmile == "") {
                double4 = Double.parseDouble("0.0");
            } else {
                double4 = Double.parseDouble(backmile);
            }

            if (milenow == null || milenow == "") {
                double5 = Double.parseDouble("0.0");
            } else {
                double5 = Double.parseDouble(milenow);

            }

            Driver driver = super.reflect("driver", Driver.class, param);
            driver.setDriverLeavemile(double1);
            driver.setDriverLeavemileuser(double2);
            driver.setDriverBackmileuser(double3);
            driver.setDriverBackmile(double4);
            driver.setDriverMilenow(double5);

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
     * 加油记录后台首页-视图
     *
     * @param view 模型视图
     * @return
     */
    @RequestMapping("/list/14.html")
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
                param.put("driverBeizhu", "jyjl");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            } else {

                param.put("driverBeizhu", "jyjl");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            e.printStackTrace();
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        view.addObject("params", param);
        view.setViewName("/index/list_14");
        return view;
    }

    /**
     * 加油记录编辑—入口
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/edit/14.html")
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
        view.setViewName("/index/edit_14");
        return view;
    }

    /**
     * 加油记录-操作 save_or_update
     *
     * @param request 请求对象
     * @return
     */
    @SystemLog(description = "每日出行编辑-操作")
    @RequestMapping("/edit/14")
    public JsonResult editsjy(ModelAndView view, HttpServletRequest request) {
        JsonResult json = new JsonResult(Status.FAILED);
        try {
            Map<String, String> param = super.getParameters(request);

            String milenow = request.getParameter("milenow");
            String gastotal = request.getParameter("gastotal");
            String gasrmbtotal = request.getParameter("gasrmbtotal");

            Double double2 = null;
            Double double3 = null;
            Double double4 = null;
            if (milenow == null || milenow == "") {
                double2 = Double.parseDouble("0.0");
            } else {
                double2 = Double.parseDouble(milenow);
            }

            if (gastotal == null || gastotal == "") {
                double3 = Double.parseDouble("0.0");
            } else {
                double3 = Double.parseDouble(gastotal);
            }

            if (gasrmbtotal == null || gasrmbtotal == "") {
                double4 = Double.parseDouble("0.0");
            } else {
                double4 = Double.parseDouble(gasrmbtotal);
            }
            Driver driver = super.reflect("driver", Driver.class, param);
            driver.setDriverMilenow(double2);
            driver.setDriverGastotal(double3);
            driver.setDriverGasrmbtotal(double4);

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
     * 加班记录后台首页-视图
     *
     * @param view 模型视图
     * @return
     */
    @RequestMapping("/list/15.html")
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
            if (param.containsKey("pageNum")) {
                param.put("driverBeizhu", "jbjl");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            } else {

                param.put("driverBeizhu", "jbjl");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            e.printStackTrace();
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        view.addObject("params", param);
        view.setViewName("/index/list_15");
        return view;
    }

    /**
     * 加班记录编辑—入口
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/edit/15.html")
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
        view.setViewName("/index/edit_15");
        return view;
    }

    /**
     * 加班记录-操作 save_or_update
     *
     * @param request 请求对象
     * @return
     */
    @SystemLog(description = "加班记录编辑-操作")
    @RequestMapping("/edit/15")
    public JsonResult editsjb(ModelAndView view, HttpServletRequest request) {
        JsonResult json = new JsonResult(Status.FAILED);
        try {
            Map<String, String> param = super.getParameters(request);
            String overtimehours = request.getParameter("overtimehours");
            String overtimermbtotal = request.getParameter("overtimermbtotal");

            Double double2 = Double.parseDouble(overtimehours);
            Double double3 = Double.parseDouble(overtimermbtotal);
            Driver driver = super.reflect("driver", Driver.class, param);
            driver.setDriverOvertimehours(double2);
            driver.setDriverOvertimermbtotal(double3);
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
     * 费用记录后台首页-视图
     *
     * @param view 模型视图
     * @return
     */
    @RequestMapping("/list/16.html")
    public ModelAndView list4(ModelAndView view, HttpServletRequest request) {
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
                param.put("driverBeizhu", "fyjl");
                param.put("order_date", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            } else {

                    param.put("driverBeizhu", "fyjl");
                    param.put("order_date", "desc");
                    List<Driver> list = driverService.getPage(param);
                    view.addObject("models", list);
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            e.printStackTrace();
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        view.addObject("params", param);
        view.setViewName("/index/list_16");
        return view;
    }

    /**
     * 费用记录编辑—入口
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/edit/16.html")
    public ModelAndView edit4(ModelAndView view, HttpServletRequest request) {
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
        view.setViewName("/index/edit_16");
        return view;
    }

    /**
     * 费用记录-操作 save_or_update
     *
     * @param request 请求对象
     * @return
     */
    @SystemLog(description = "费用记录编辑-操作")
    @RequestMapping("/edit/16")
    public JsonResult editsfy(ModelAndView view, HttpServletRequest request) {
        JsonResult json = new JsonResult(Status.FAILED);
        try {
            Map<String, String> param = super.getParameters(request);
            String rmb = request.getParameter("rmb");
            Double double2 = Double.parseDouble(rmb);
            Driver driver = super.reflect("driver", Driver.class, param);
            driver.setDriverRmb(double2);
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
     * 换车后台首页-视图
     *
     * @param view 模型视图
     * @return
     */
    @RequestMapping("/list/17.html")
    public ModelAndView list5(ModelAndView view, HttpServletRequest request) {
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
                param.put("driverBeizhu", "hcjl");
                param.put("order_id", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            } else {

                    param.put("driverBeizhu", "hcjl");
                    param.put("order_id", "desc");
                    List<Driver> list = driverService.getPage(param);
                    view.addObject("models", list);
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            e.printStackTrace();
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        view.addObject("params", param);
        view.setViewName("/index/list_17");
        return view;
    }

    /**
     * 换车编辑—入口
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/edit/17.html")
    public ModelAndView edit5(ModelAndView view, HttpServletRequest request) {
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
        view.setViewName("/index/edit_17");
        return view;
    }

    /**
     * 换车记录-操作 save_or_update
     *
     * @param request 请求对象
     * @return
     */
    @SystemLog(description = "换车记录编辑-操作")
    @RequestMapping("/edit/17")
    public JsonResult editshc(ModelAndView view, HttpServletRequest request) {
        JsonResult json = new JsonResult(Status.FAILED);
        try {
            Map<String, String> param = super.getParameters(request);
            String jcarmile = request.getParameter("jcarmile");
            String newcarmile = request.getParameter("newcarmile");
            Double double1 = null;
            Double double2 = null;
            if (jcarmile == null || jcarmile == "") {
                double1 = Double.parseDouble("");
            } else {
                double1 = Double.parseDouble(jcarmile);
            }

            if (newcarmile == null || newcarmile == "") {
                double2 = Double.parseDouble("");
            } else {
                double2 = Double.parseDouble(newcarmile);
            }

            Driver driver = super.reflect("driver", Driver.class, param);
            driver.setDriverJcarmile(double1);
            driver.setDriverNewcarmile(double2);
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

}
