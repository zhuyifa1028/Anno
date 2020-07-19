package com.product.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Driver;

/**
 * 司机-服务接口
 * 
 * @author ymc
 *
 */
public interface DriverService {

	public long getCount(Map<String, Object> param);

	/**
	 * 查询操作
	 * 
	 * @param id
	 *            用户主键
	 * @return
	 */
	public Driver getById(int id);

	/**
	 * 删除操作
	 * 
	 * @param id
	 *            用户主键
	 * @return
	 */
	public int delById(int id);

	/**
	 * 保存/更新操作
	 * 
	 * @param Driver
	 *            用户实体
	 * @param ip
	 *            初始地址
	 * @return
	 */
	public boolean saveOrUpdate(Driver driver, String ip);

	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Driver> getList(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public PageList<Driver> getPage(Map<String, String> param);

}
