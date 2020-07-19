package com.product.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Programa;

/**
 * 平台栏目-数据接口
 * 
 * @author ymc
 */
public interface ProgramaDao {
	/**
	 * 查询操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public Programa get(Map<String, String> param);

	/**
	 * 保存操作
	 * 
	 * @param programa
	 *            平台栏目实体
	 */
	public int save(Programa programa);

	/**
	 * 更新操作
	 * 
	 * @param programa
	 *            平台栏目实体
	 */
	public int update(Programa programa);

	/**
	 * 下级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> childs(Map<String, String> param);

	/**
	 * 下级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> child(Map<String, String> param);

	/**
	 * 上级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> parents(Map<String, String> param);

	/**
	 * 上级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> parent(Map<String, String> param);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public List<Programa> list(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @param bounds
	 *            分页对象
	 */
	public PageList<Programa> page(Map<String, String> param, PageBounds bounds);
}
