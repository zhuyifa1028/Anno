package com.product.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.User;

/**
 * 用户-数据接口
 * 
 * @author ymc
 *
 */
public interface UserDao {
	/**
	 * 查询操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public User get(Map<String, String> param);

	/**
	 * 保存操作
	 * 
	 * @param user
	 *            用户实体
	 */
	public int save(User user);

	/**
	 * 更新操作
	 * 
	 * @param user
	 *            用户实体
	 */
	public int update(User user);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public List<User> list(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @param bounds
	 *            分页对象
	 */
	public PageList<User> page(Map<String, String> param, PageBounds bounds);
}
