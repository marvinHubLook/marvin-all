package cn.edu.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-03-06 11:58
 **/
@RestController
@RequestMapping("/session")
public class SessionController {

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Map<String, Object> getSession (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().getAttribute("request Url");
        map.put("request Url", request.getRequestURL());
        return map;
    }
}
