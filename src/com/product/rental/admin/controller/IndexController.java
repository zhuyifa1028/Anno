package com.product.rental.admin.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.product.utils.DateUtils;
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
        HttpSession session = request.getSession();
        JsonResult result = new JsonResult(Status.FAILED);

        try {
            String name = request.getParameter("tel");
            String pwd = request.getParameter("pwd");
            if (StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(name)) {

                String lockingKey = "LOCKING_" + name;
                String errorTimesKey = "ERROR_TIMES_" + name;
                Object lockingValue = session.getAttribute(lockingKey);
                Object errorTimesValue = session.getAttribute(errorTimesKey);

                long currentTimeMillis = System.currentTimeMillis();
                // 检查是否锁定
                if (lockingValue != null) {
                    // 解锁时间
                    Date parse = DateUtils.parse((String) lockingValue);
                    if (parse.getTime() > currentTimeMillis) {

                        result.setMessage("账户锁定中，解锁时间为 " + lockingValue);
                        return result;
                    }
                }

                DesUtils des = new DesUtils(Constants.PUBLIC_SECRET);
                pwd = des.decrypt(pwd);
                if (StringUtils.isNotBlank(pwd)) {
                    des = new DesUtils(Constants.PRIVATE_SECRET);
                    pwd = des.encrypt(pwd);
                    Map<String, String> param = new HashMap<>();
                    param.put("adminTel", name);
                    param.put("adminPwd", MD5Utils.md5(pwd));
                    List<Admin> admins = adminService.getList(param);
                    if (admins != null && admins.size() == 1) {
                        Admin admin = admins.get(0);
                        if (admin.getAdminState() != null
                                && admin.getAdminState().equals(Admin.ADMIN_ADMINISTRATE_YES)) {
                            result.setMessage("用户不可使用,请联系管理员");
                        } else {
                            session.setAttribute(Constants.ADMIN_USER_SESSION, admin);
                            result.setCode(Status.SUCCESS.getCode());
                            result.setMessage("用户登录成功");
                        }
                        return result;
                    } else {
                        result.setMessage("用户名称或密码错误");
                    }
                } else {
                    result.setMessage("用户密码解密失败");
                }

                if (errorTimesValue == null) {
                    errorTimesValue = 0;
                }
                // 增加错误次数
                int errorTimesValue2 = (Integer) errorTimesValue;
                session.setAttribute(errorTimesKey, ++errorTimesValue2);

                // 超过5次密码输入错误，账号锁定30分钟
                if (errorTimesValue2 == 5) {
                    Date date = new Date(currentTimeMillis + 30 * 60 * 1000);

                    errorTimesValue = DateUtils.format(date);
                    session.setAttribute(lockingKey, errorTimesValue);
                }
            } else {
                result.setMessage("参数错误");
            }
        } catch (Exception e) {
            result.setCode(Status.ERROR.getCode());
            result.setMessage("系统错误,用户登录失败");
            tag.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 用户菜单-页面
     *
     * @param view    模型视图
     * @param request 请求对象
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

                    // 通过管理员筛选菜单
                    object = filterProgramaByAdmin((Admin) user, (List<Programa>) object);

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
     * 通过管理员筛选菜单
     *
     * @param admin     管理员
     * @param programas 菜单列表
     * @return 筛选后的菜单列表
     */
    private Object filterProgramaByAdmin(Admin admin, List<Programa> programas) {
        // 重新获取登录的管理员，防止缓存数据
        admin = adminService.getById(admin.getId());
        // 筛选后的菜单列表
        List<Programa> resultList = new ArrayList<>();

        for (Programa programa : programas) {
            // 菜单ID
            Integer programaId = programa.getId();
            // 用车人管理
            if (1 == programaId && "0".equals(admin.getAdminAdminuser())) {
                resultList.add(programa);
                continue;
            }
            // 司机管理
            if (2 == programaId && "0".equals(admin.getAdminAdmindriver())) {
                resultList.add(programa);
                continue;
            }
            // 统计管理
            if (3 == programaId && "0".equals(admin.getAdminAdmintj())) {
                resultList.add(programa);
                continue;
            }
            // 培训管理
            if (4 == programaId && "0".equals(admin.getAdminAdminpx())) {
                resultList.add(programa);
                continue;
            }
            // 用车人确认管理
            if (5 == programaId && "0".equals(admin.getAdminAdminuc())) {
                resultList.add(programa);
                continue;
            }
            // 投诉反馈
            if (6 == programaId && "0".equals(admin.getAdminAdmints())) {
                resultList.add(programa);
                continue;
            }
            // 帮助中心
            if (7 == programaId && "0".equals(admin.getAdminAdminbz())) {
                resultList.add(programa);
                continue;
            }
            // 飞机火车管理
            if (8 == programaId && "0".equals(admin.getAdminAdminarea())) {
                resultList.add(programa);
                continue;
            }
            // 权限管理
            if (9 == programaId && "0".equals(admin.getAdminAdministrate())) {
                resultList.add(programa);
            }
        }
        return resultList;
    }

    /**
     * 导航标签
     *
     * @param view    模型视图
     * @param request 请求对象
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
     * @param view    模型视图
     * @param request 请求对象
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
     * @param view 模型视图
     * @return
     */
    @RequestMapping("index.html")
    public ModelAndView index(ModelAndView view, HttpServletRequest request) {

        // 检查用户上次修改密码的时间
        Admin user = (Admin) this.getAdminUser(request);

        Admin admin = adminService.getById(user.getId());

        Date lastChangeDate = admin.getLastChangeDate();

        if (lastChangeDate != null) {
            long dateDiffer = DateUtils.getDateDiffer(lastChangeDate,
                    new Date(), DateUtils.DATE_TYPE_DAY);
            if (dateDiffer > 30) {
                view.addObject(Constants.MODEL_MESSAGE, "请修改登录密码");
            }
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
     * @param view    模型视图
     * @param pid     上级主键
     * @param request 请求对象
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
     * @param view    模型视图
     * @param pid     上级主键
     * @param request 请求对象
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
