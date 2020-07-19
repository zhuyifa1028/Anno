package com.product.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Record;

import net.sf.json.JSONArray;

public interface RecordService {
	
	/**
	 * 浏览
	 * 
	 * @param id
	 * 			信息主键
	 * @return
	 */
	public Record browse(String id);
	
	/**
	 * 查询操作
	 * 
	 * @param id
	 *            栏目主键
	 * @return
	 */
	public Record getById(int id);

	/**
	 * 保存/更新操作
	 * 
	 * @param Record
	 *            栏目实体
	 * @param ip
	 *            初始地址
	 * @return
	 */
	public boolean saveOrUpdate(Record record, String ip);

	/**
	 * 下级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Record> getChilds(Map<String, String> param);

	/**
	 * 下级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Record> getChild(Map<String, String> param);
	
	/**
	 * 遍历下级列表
	 * 
	 * @param modules
	 * 			模块集合
	 * @param ids
	 * 			主键集合
	 * @return
	 */
	public JSONArray getChilds(List<Record> modules, Map<String, String> ids);

	/**
	 * 上级列表(无限递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Record> getParents(Map<String, String> param);

	/**
	 * 上级列表(一级递归)
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Record> getParent(Map<String, String> param);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Record> getList(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public PageList<Record> getPage(Map<String, String> param);
}
