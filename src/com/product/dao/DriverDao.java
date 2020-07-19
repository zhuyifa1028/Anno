package com.product.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Driver;

/**
 * 司机-数据接口
 * 
 * @author ymc
 *
 */
public interface DriverDao {

	public long count(Map<String, Object> param);

	/**
	 * 查询操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public Driver get(Map<String, String> param);

	/**
	 * 保存操作
	 * 
	 * @param driver
	 *            用户实体
	 */
	public int save(Driver driver);

	/**
	 * 删除操作
	 * 
	 * @param id
	 * @return
	 */
	public int del(Map<String, String> param);

	/**
	 * 更新操作
	 * 
	 * @param driver
	 *            用户实体
	 */
	public int update(Driver driver);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public List<Driver> list(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @param bounds
	 *            分页对象
	 */
	public PageList<Driver> page(Map<String, String> param, PageBounds bounds);

}
