package com.product.constants;

/**
 * 系统设置-常量
 * 
 * @author syl
 *
 */
public class Constants {

	/** 字符编码 */
	public static final String utf8 = "UTF-8";

	/** 当前页数 */
	public static final String PAGE_NUM = "pageNum";

	/** 当前页数(默认：1) */
	public static final Integer PAGE_DEFAULT_NUM = 1;

	/** 页面大小 */
	public static final String PAGE_SIZE = "pageSize";

	/** 页面大小(默认：10) */
	public static final Integer PAGE_DEFAULT_SIZE = 10;

	/** 总共页数 */
	public static final String PAGE_TOTAL = "pageTotal";

	/** 总共页数(默认：0) */
	public static final Integer PAGE_DEFAULT_TOTAL = 0;

	/** 公共密钥主键 */
	public static final String PUBLIC_SECRET_KEY = "publickey";

	/** 公共密钥 */
	public static final String PUBLIC_SECRET = "framework_public_session";

	/** 私密密钥 */
	public static final String PRIVATE_SECRET = "framework_private";

	/** 模型消息 */
	public static final String MODEL_MESSAGE = "message";

	/** 后台权限 */
	public static final String ADMIN_MODULES_SESSION = "admin_modules_session";
	/** 后台用户配置 */
	public static final String ADMIN_USER_CONFIG = "admin_user_config";
	/** 后台用户权限 */
	public static final String ADMIN_USER_MODULES_SESSION = "admin_user_modules_session";

	/** 后台用户缓存 */
	public static final String ADMIN_USER_SESSION = "admin_user_session";
	/** 微信用户缓存 */
	public static final String WEB_USER_SESSION = "web_user_session";
	/** 微信司机缓存 */
	public static final String WEB_DRIVER_SESSION = "web_driver_session";
	/** 前端用户缓存 */
	public static final String FRONT_USER_SESSION = "front_user_session";

	/** 是否拦截后台登录用户(0:否、1:是) */
	public static final String ADMIN_CONTEXT_INTERCEPTOR = "admin_context_interceptor";
	/** 是否拦截微信登录用户(0:否、1:是) */
	public static final String WEB_CONTEXT_INTERCEPTOR = "web_context_interceptor";
	/** 是否拦截前端登录用户(0:否、1:是) */
	public static final String FRONT_CONTEXT_INTERCEPTOR = "front_context_interceptor";

	/** 是否拦截(否) */
	public static final String CONTEXT_INTERCEPTOR_NOT = "0";
	/** 是否拦截(是) */
	public static final String CONTEXT_INTERCEPTOR_YES = "1";

	/** 重定向、转发路径 */
	public static final String REDIRECT_FORWARD_URL = "redirect_forward_url";

	/** 文件上传、下载路径 */
	public static final String UP_DOWN_LOAD_FILE = "file";

	/** 是否隐藏(是) */
	public static final String IS_DISPLAY_YES = "1";
	/** 是否隐藏(否) */
	public static final String IS_DISPLAY_NOT = "0";
	/** 微信-appid缓存 */
	public static final String WEB_WX_APPID = "wx3923dff40b848f11";
	/** 微信-secret缓存 */
	public static final String WEB_WX_SECRET = "c931b5ffb0bdd5a34d3b7be9bdd4fa57";
	/** 用户openid */
	public static final String WEB_USER_OPENID_SESSION = "user_openid";

	/** 用户openid */
	public static final String WEB_DRIVWER_OPENID_SESSION = "driver_openid";

	/** 微信支付-access_token缓存 */
	public static final String WEB_WX_ACCESS_TOKEN = "web_wx_access_token";
	/** 微信支付-appid缓存 */
	// public static final String WEB_WX_APPID = "wx72b1c9f1e3630655";

	/** 微信支付-secret缓存 */
	// public static final String WEB_WX_SECRET =
	// "b8b4e66c5c4a26daaf8c157e34fb1d65";

}