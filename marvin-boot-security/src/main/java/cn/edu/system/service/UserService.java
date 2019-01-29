package cn.edu.system.service;

import cn.edu.system.domain.SysUser;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-08 16:23
 **/
public interface UserService {
    /**
     * 根据用户名获取系统用户
     */
    SysUser getUserByName(String username);

}
