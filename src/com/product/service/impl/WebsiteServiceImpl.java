package com.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Website;
import com.product.constants.Constants;
import com.product.dao.WebsiteDao;
import com.product.service.WebsiteService;

/**
 * 网站信息-服务类
 * 
 * @author ymc
 *
 */
@Service
public class WebsiteServiceImpl implements WebsiteService {

	@Autowired
	private WebsiteDao websiteDao;
	
	@Override
	public Website getById(int id) {
		Map<String, String> param = new HashMap<>();
		param.put("id", String.valueOf(id));
		return websiteDao.get(param);
	}

	@Override
	public boolean saveOrUpdate(Website website, String ip) {
		Date date = new Date();
		if(website.getId() != null && website.getId() > 0) {
			return websiteDao.update(website) > 0 ? true : false;
		}
		website.setInitDate(date);
		website.setInitAddr(ip);
		return websiteDao.save(website) > 0 ? true : false;
	}

	@Override
	public List<Website> getList(Map<String, String> param) {
		return websiteDao.list(param);
	}

	@Override
	public PageList<Website> getPage(Map<String, String> param) {
		PageBounds bounds = new PageBounds();
		bounds.setContainsTotalCount(true);
		
		if(param == null) {
			param = new HashMap<>();
		}
		
		if(param.containsKey(Constants.PAGE_NUM) && !param.get(Constants.PAGE_NUM).equals(0)) {
			bounds.setPage(Integer.valueOf(param.get(Constants.PAGE_NUM)));
		} else {
			bounds.setPage(Constants.PAGE_DEFAULT_NUM);
		}
		if(param.containsKey(Constants.PAGE_SIZE) && !param.get(Constants.PAGE_SIZE).equals(0)) {
			bounds.setLimit(Integer.valueOf(param.get(Constants.PAGE_SIZE)));
		} else {
			bounds.setLimit(Constants.PAGE_DEFAULT_SIZE);
		}
		return websiteDao.page(param, bounds);
	}
}