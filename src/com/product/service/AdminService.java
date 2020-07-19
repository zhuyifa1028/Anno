package com.product.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Admin;
/**
 * 管理员-服务接口
 * @author ymc
 *
 */
public interface AdminService {
	
	/**
	 * 查询操作
	 * 
	 * @param id
	 *            用户主键
	 * @return
	 */
	public Admin getById(int id);

	/**
	 * 保存/更新操作
	 * 
	 * @param Admin
	 *            用户实体
	 * @param ip
	 *            初始地址
	 * @return
	 */
	public boolean saveOrUpdate(Admin admin, String ip);
	
	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Admin> getList(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public PageList<Admin> getPage(Map<String, String> param);
}
