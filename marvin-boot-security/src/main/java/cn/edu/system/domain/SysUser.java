package cn.edu.system.domain;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-08 16:22
 **/
@Data
@ToString
public class SysUser implements Serializable {

    private Long id;

    /**
     * 用户编号
     */
    private String usercode;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 用户角色
     */
    private List<SysRole> roleList;

    public SysUser() {
    }

    public SysUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}