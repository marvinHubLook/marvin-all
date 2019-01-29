package cn.edu.system.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: marvin-all
 * @description: 功能展示
 * @author: Mr.Wang
 * @create: 2019-01-08 16:42
 **/
@Controller
@RequestMapping("/book")
public class BookController {
    @PreAuthorize("hasAuthority('BookList')")
    @GetMapping("/list.html")
    public String list() {
        return "book/list";
    }

    @PreAuthorize("hasAuthority('BookAdd')")
    @GetMapping("/add.html")
    public String add() {
        return "book/add";
    }

    @PreAuthorize("hasAuthority('BookDetail')")
    @GetMapping("/detail.html")
    public String detail() {
        return "book/detail";
    }
}
