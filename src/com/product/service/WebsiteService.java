package com.product.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Website;

/**
 * 网站信息-服务接口
 * 
 * @author ymc
 */
public interface WebsiteService {

	/**
	 * 查询操作
	 * 
	 * @param id
	 *            网站信息主键
	 * @return
	 */
	public Website getById(int id);

	/**
	 * 保存/更新操作
	 * 
	 * @param website
	 *            网站信息实体
	 * @param ip
	 *            初始地址
	 * @return
	 */
	public boolean saveOrUpdate(Website website, String ip);
	
	/**
	 * 列表操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public List<Website> getList(Map<String, String> param);

	/**
	 * 分页操作
	 * 
	 * @param param
	 *            条件对象
	 * @return
	 */
	public PageList<Website> getPage(Map<String, String> param);
}