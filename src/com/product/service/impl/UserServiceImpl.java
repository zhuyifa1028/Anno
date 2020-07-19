package com.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.User;
import com.product.constants.Constants;
import com.product.dao.UserDao;
import com.product.service.UserService;

/**
 * 用户-服务类
 * 
 * @author ymc
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User getById(int id) {
		Map<String, String> param = new HashMap<>();
		param.put("id", String.valueOf(id));
		return userDao.get(param);
	}

	@Override
	public boolean saveOrUpdate(User user, String ip) {
		Date date = new Date();
		if(user.getId() != null && user.getId() > 0) {
			return userDao.update(user) > 0 ? true : false;
		}
		user.setInitDate(date);
		user.setInitAddr(ip);
		return userDao.save(user) > 0 ? true : false;
	}

	@Override
	public List<User> getList(Map<String, String> param) {
		
		return userDao.list(param);
	}

	@Override
	public PageList<User> getPage(Map<String, String> param) {
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
		return userDao.page(param, bounds);
	}

}
