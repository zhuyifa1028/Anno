package com.product.rental.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.product.utils.PasswordUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.product.annotation.SystemLog;
import com.product.bean.Admin;
import com.product.constants.Constants;
import com.product.controller.BaseController;
import com.product.result.JsonResult;
import com.product.result.Status;
import com.product.service.AdminService;
import com.product.utils.DesUtils;
import com.product.utils.MD5Utils;

import net.sf.json.JSONObject;

/**
 * 权限管理-控制类
 * <p>
 * 增加、修改、删除、查询
 *
 * @author ymc
 */
@RestController
@RequestMapping("/")
public class AdminController extends BaseController {
    private static Log tag = LogFactory.getLog("tag");

    @Autowired
    private AdminService adminService;

    /**
     * 后台首页-视图
     *
     * @param view 模型视图
     * @return
     */
    @RequestMapping("/list/29.html")
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
                param.put("order_id", "desc");
                List<Admin> list = adminService.getPage(param);
                view.addObject("models", list);
            } else {
                param.put("order_id", "desc");
                List<Admin> list = adminService.getPage(param);
                view.addObject("models", list);
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            e.printStackTrace();
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        view.addObject("params", param);
        view.setViewName("/index/list_29");
        return view;
    }

    /**
     * 修改—入口
     *
     * @param view    模型视图
     * @param request 请求对象
     * @return
     */
    @RequestMapping("/edit/29.html")
    public ModelAndView edit(ModelAndView view, HttpServletRequest request) {
        Map<String, String> param = super.getParameters(request);
        try {
            if (param.containsKey("id")) {
                Map<String, String> countryparam = new HashMap<String, String>();
                countryparam.put("parent_id", "1");
                String id = param.get("id");
                Admin d = adminService.getById(Integer.parseInt(id));
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
        view.setViewName("/index/edit_29");
        return view;
    }

    /**
     * 权限编辑-操作 save_or_update
     *
     * @param request 请求对象
     * @return
     */
    @SystemLog(description = "权限编辑-操作")
    @RequestMapping("edit/29")
    public JsonResult edits(ModelAndView view, HttpServletRequest request) {
        JsonResult result = new JsonResult(Status.FAILED);

        try {
            Map<String, String> param = super.getParameters(request);
            Admin admin = super.reflect("admin", Admin.class, param);

            boolean isUpdate = false;
            Integer adminId = admin.getId();
            String adminPwd = admin.getAdminPwd();

            // 当修改管理员时
            if (adminId != null) {
                // 检查密码是否更新
                Admin byId = adminService.getById(adminId);

                if (StringUtils.isNotBlank(adminPwd)) {
                    isUpdate = !adminPwd.equals(byId.getAdminPwd());
                }
            }

            // 当更新密码或新增管理员时
            if (isUpdate || adminId == null) {
                // 密码强度校验
                boolean strengthValid = PasswordUtils.strengthValid(adminPwd);
                if (!strengthValid) {
                    result.setMessage("密码设置至少一位大写，8位长度");
                    return result;
                }

                // 密码加密处理
                DesUtils des = new DesUtils(Constants.PRIVATE_SECRET);
                admin.setAdminPwd(MD5Utils.md5(des.encrypt(adminPwd)));
            }

            boolean flag = adminService.saveOrUpdate(admin, super.ip(request));
            if (flag) {
                // 请求缓存对象
                HttpSession session = request.getSession();
                // 清除菜单缓存
                session.removeAttribute(Constants.ADMIN_USER_MODULES_SESSION);

                result.setCode(Status.SUCCESS.getCode());
                result.setMessage("权限管理编辑成功");
            } else {
                result.setMessage("权限管理编辑失败");
            }
        } catch (Exception e) {
            tag.error(e.getMessage(), e);
            view.addObject(Constants.MODEL_MESSAGE, "参数错误");
        }
        return result;
    }
}
