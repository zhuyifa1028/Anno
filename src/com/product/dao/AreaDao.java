package com.product.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Area;

/**
 * 区域字典-数据接口
 * 
 * @author ymc
 */
public interface AreaDao {
	
	/**
	 * 查询操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public Area get(Map<String, String> param);
	/**
	 * 保存操作
	 * 
	 * @param area
	 *            区域实体
	 */
	public int save(Area area);

	/**
	 * 更新操作
	 * 
	 * @param area
	 *            区域实体
	 */
	public int update(Area area);

	/**
	 * 下级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> childs(Map<String, String> param);

	/**
	 * 下级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> child(Map<String, String> param);

	/**
	 * 上级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> parents(Map<String, String> param);
	
	/**
	 * 上级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> parent(Map<String, String> param);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public List<Area> list(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @param bounds
	 *            分页对象
	 */
	public PageList<Area> page(Map<String, String> param, PageBounds bounds);
}
