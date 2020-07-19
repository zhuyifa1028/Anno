package com.product.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Website;

/**
 * 网站信息-数据接口
 * 
 * @author ymc
 */
public interface WebsiteDao {
	/**
	 * 查询操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public Website get(Map<String, String> param);

	/**
	 * 保存操作
	 * 
	 * @param website
	 *            网站信息实体
	 */
	public int save(Website website);

	/**
	 * 更新操作
	 * 
	 * @param website
	 *            网站信息实体
	 */
	public int update(Website website);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public List<Website> list(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @param bounds
	 *            分页对象
	 */
	public PageList<Website> page(Map<String, String> param, PageBounds bounds);
}