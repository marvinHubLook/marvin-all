package cn.edu.oauth2.interceptor;

import cn.edu.oauth2.common.Constants;
import cn.edu.oauth2.common.SpringContextUtils;
import cn.edu.oauth2.model.bo.UserBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 定义一些页面需要做登录检查
 *
 * @author zifangsky
 * @date 2018/7/26
 * @since 1.0.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

    /**
     * 检查是否已经登录
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        //获取session中存储的用户信息
        UserBo user = (UserBo) session.getAttribute(Constants.SESSION_USER);

        if(user != null){
            return true;
        }else{
            //如果token不存在，则跳转等登录页面
            response.sendRedirect(request.getContextPath() + "/login?redirectUrl=" + SpringContextUtils.getRequestUrl(request));

            return false;
        }
    }
}
