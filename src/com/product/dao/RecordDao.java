package com.product.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Record;

/**
 * 记录信息-数据接口
 * 
 * @author ymc
 *
 */
public interface RecordDao {
	/**
	 * 查询操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public Record get(Map<String, String> param);

	/**
	 * 保存操作
	 * 
	 * @param record
	 *            用户实体
	 */
	public int save(Record record);

	/**
	 * 更新操作
	 * 
	 * @param record
	 *            用户实体
	 */
	public int update(Record record);
	

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 */
	public List<Record> list(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @param bounds
	 *            分页对象
	 */
	public PageList<Record> page(Map<String, String> param, PageBounds bounds);
}
