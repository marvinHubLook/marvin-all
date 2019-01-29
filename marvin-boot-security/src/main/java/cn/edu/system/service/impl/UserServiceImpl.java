package cn.edu.system.service.impl;

import cn.edu.system.dao.UserDao;
import cn.edu.system.domain.SysUser;
import cn.edu.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-08 16:31
 **/
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Cacheable(cacheNames = "authority", key = "#username")
    @Override
    public SysUser getUserByName(String username) {
        return userDao.selectByName(username);
    }
}
