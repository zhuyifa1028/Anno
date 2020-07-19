package com.product.rental.admin.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.product.annotation.SystemLog;
import com.product.bean.Admin;
import com.product.bean.Driver;
import com.product.bean.Record;
import com.product.bean.User;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.DriverService;
import com.product.service.RecordService;
import com.product.service.UserService;
import com.product.utils.DesUtils;
import com.product.utils.MD5Utils;

import net.sf.json.JSONObject;

/**
 * 司机管理-控制类
 * <p>
 * 增加、修改、删除、查询
 *
 * @author ymc
 */
@RestController
@RequestMapping("/")
public class DriverController extends BaseController {
    private static Log tag = LogFactory.getLog("tag");

    @Autowired
    private UserService userService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RecordService recordService;

    /**
     * 后台首页-视图
     *
     * @param view 模型视图
     * @return
     */
    @RequestMapping("/list/11.html")
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
                param.put("driverLeavel", "9");
                param.put("order_id", "desc");
                List<Driver> list = driverService.getPage(param);
                view.addObject("models", list);
            } else {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("driverLeavel", "9");
                map.put("order_id", "desc");
                List<Driver> list = driverService.getPage(map);
                view.addObject("models", list);
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            e.printStackTrace();
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        view.addObject("params", param);
        view.setViewName("/index/list_11");
        return view;
    }

    /**
     * 修改—入口
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/edit/11.html")
    public ModelAndView edit(ModelAndView view, HttpServletRequest request) {
        Map<String, String> param = super.getParameters(request);
        try {
            if (param.containsKey("id")) {
                Map<String, String> countryparam = new HashMap<String, String>();
                countryparam.put("parent_id", "1");
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
        view.setViewName("/index/edit_11");
        return view;
    }

    /**
     * 司机列表编辑-操作 save_or_update
     *
     * @param request 请求对象
     * @return
     */
    @SystemLog(description = "司机列表编辑-操作")
    @RequestMapping("edit/11")
    public JsonResult edits(ModelAndView view, HttpServletRequest request) {
        JsonResult json = new JsonResult(Status.FAILED);
        try {
            DesUtils des = new DesUtils(Constants.PUBLIC_SECRET);
            Map<String, String> param = super.getParameters(request);
            String utel = request.getParameter("utel");
            Map<String, String> map = new HashMap<String, String>();
            map.put("userTel", utel);
            List<User> list = userService.getList(map);
            if (list.size() == 0) {
                json.setMessage("亲！服务用户不存在。");
            } else {
                String pwd = request.getParameter("pwd");

                if (pwd.length() != 32) {
                    des = new DesUtils(Constants.PRIVATE_SECRET);
                    pwd = des.encrypt(pwd);
                    param.put("pwd", MD5Utils.md5(pwd));
                }
                Driver driver = super.reflect("driver", Driver.class, param);
                //定义规则
                driver.setDriverLeavel("9");
                boolean flag = driverService.saveOrUpdate(driver, super.ip(request));

                Map<String, String> mp = new HashMap<String, String>();
                mp.put("recordKftel", "pxlb");//查找所有的培训记录
                List<Record> lists = recordService.getList(mp);
                if (!param.containsKey("_id")) {
                    for (Record record : lists) {
                        Driver d = new Driver();
                        d.setDriverUsname("pxtj");//pxtj
                        d.setDriverName(driver.getDriverName());
                        d.setDriverTel(driver.getDriverTel());
                        d.setDriverCarnum(driver.getDriverCarnum());
                        d.setDriverTrainstate("0");
                        d.setDriverId(record.getId()); //培训记录id
                        d.setDriverBeizhu(record.getRecordTraintitle());//培训记录标题
                        driverService.saveOrUpdate(d, super.ip(request));
                    }
                }

                if (flag) {
                    json.setCode(Status.SUCCESS.getCode());
                    json.setMessage("司机列表编辑成功");
                } else {
                    json.setMessage("司机列表编辑失败");
                }
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        return json;
    }
}
