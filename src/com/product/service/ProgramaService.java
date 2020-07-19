package com.product.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Programa;

import net.sf.json.JSONArray;

/**
 * 栏目信息-服务接口
 * 
 * @author ymc
 *
 */
public interface ProgramaService {
	/**
	 * 查询操作
	 * 
	 * @param id
	 *            栏目主键
	 * @return
	 */
	public Programa getById(int id);

	/**
	 * 保存/更新操作
	 * 
	 * @param programa
	 *            栏目实体
	 * @param ip
	 *            初始地址
	 * @return
	 */
	public boolean saveOrUpdate(Programa programa, String ip);
	/**
	 * 下级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> getChilds(Map<String, String> param);
	
	/**
	 * 下级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> getChild(Map<String, String> param);
	/**
	 * 遍历下级列表
	 * 
	 * @param modules
	 * 			模块集合
	 * @param ids
	 * 			主键集合
	 * @return
	 */
	public JSONArray getChilds(List<Programa> modules, Map<String, String> ids);
	
	/**
	 * 上级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> getParents(Map<String, String> param);
	
	/**
	 * 上级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> getParent(Map<String, String> param);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Programa> getList(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public PageList<Programa> getPage(Map<String, String> param);
}
