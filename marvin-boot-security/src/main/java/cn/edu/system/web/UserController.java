package cn.edu.system.web;

import cn.edu.system.domain.SysUser;
import cn.edu.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: marvin-all
 * @description: 用户中心
 * @author: Mr.Wang
 * @create: 2019-01-08 16:43
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 个人中心
     */
    @PreAuthorize("hasAuthority('UserIndex')")
    @GetMapping("/index")
    public String index() {
        return "/user/index";
    }

    @RequestMapping("/hi")
    @ResponseBody
    public String hi() {
        SysUser sysUser = userService.getUserByName("zhangsan");
        return sysUser.toString();
    }

}