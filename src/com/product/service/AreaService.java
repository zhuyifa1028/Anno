package com.product.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Area;

import net.sf.json.JSONArray;
/**
 * 区域字典-服务接口
 * @author ymc
 *
 */
public interface AreaService {
	/**
	 * 查询操作
	 * 
	 * @param id
	 *            区域字典主键
	 * @return
	 */
	public Area getById(int id);

	/**
	 * 保存/更新操作
	 * 
	 * @param area
	 *            区域字典实体
	 * @param ip
	 *            初始地址
	 * @return
	 */
	public boolean saveOrUpdate(Area area, String ip);

	/**
	 * 下级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> getChilds(Map<String, String> param);

	/**
	 * 下级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> getChild(Map<String, String> param);

	/**
	 * 上级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> getParents(Map<String, String> param);

	/**
	 * 上级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> getParent(Map<String, String> param);

	/**
	 * 树形结构
	 * 
	 * @param param
	 *            条件对象
	 * @param keys
	 *            对比选中
	 * @return
	 */
	public JSONArray getChildsTree(List<Area> areas, Map<String, String> keys);
	
	/**
	 * 树形结构
	 * 
	 * @param param
	 *            条件对象
	 * @param keys
	 *            对比选中
	 * @return
	 */
	public JSONArray getChildsTrees(List<Area> areas, Map<String, String> keys);
	
	/**
	 * 树形结构英文
	 * 
	 * @param param
	 *            条件对象
	 * @param keys
	 *            对比选中
	 * @return
	 */
	public JSONArray getChildsTreeyw(List<Area> areas, Map<String, String> keys);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Area> getList(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public PageList<Area> getPage(Map<String, String> param);
}
