package com.product.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Admin;

/**
 * 管理员-数据接口
 * 
 * @author ymc
 *
 */
public interface AdminDao {
	/**
	 * 查询操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public Admin get(Map<String, String> param);

	/**
	 * 保存操作
	 * 
	 * @param admin
	 *            用户实体
	 */
	public int save(Admin admin);

	/**
	 * 更新操作
	 * 
	 * @param admin
	 *            用户实体
	 */
	public int update(Admin admin);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public List<Admin> list(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @param bounds
	 *            分页对象
	 */
	public PageList<Admin> page(Map<String, String> param, PageBounds bounds);
}
