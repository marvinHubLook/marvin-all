package cn.edu.oauth2.interceptor;

import cn.edu.oauth2.enums.ErrorCodeEnum;
import cn.edu.oauth2.model.AuthAccessToken;
import cn.edu.oauth2.service.AuthorizationService;
import cn.edu.oauth2.utils.DateUtils;
import cn.edu.oauth2.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于校验Access Token是否为空以及Access Token是否已经失效
 *
 * @author zifangsky
 * @date 2018/8/22
 * @since 1.0.0
 */
public class AuthAccessTokenInterceptor extends HandlerInterceptorAdapter{
    @Resource(name = "authorizationServiceImpl")
    private AuthorizationService authorizationService;

    /**
     * 检查Access Token是否已经失效
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getParameter("access_token");

        if(StringUtils.isNoneBlank(accessToken)){
            //查询数据库中的Access Token
            AuthAccessToken authAccessToken = authorizationService.selectByAccessToken(accessToken);

            if(authAccessToken != null){
                Long savedExpiresAt = authAccessToken.getExpiresIn();
                //过期日期
                LocalDateTime expiresDateTime = DateUtils.ofEpochSecond(savedExpiresAt, null);
                //当前日期
                LocalDateTime nowDateTime = DateUtils.now();

                //如果Access Token已经失效，则返回错误提示
                return expiresDateTime.isAfter(nowDateTime) || this.generateErrorResponse(response, ErrorCodeEnum.EXPIRED_TOKEN);
            }else{
                return this.generateErrorResponse(response, ErrorCodeEnum.INVALID_GRANT);
            }
        }else{
            return this.generateErrorResponse(response, ErrorCodeEnum.INVALID_REQUEST);
        }
    }
    
    /**
     * 组装错误请求的返回
     */
    private boolean generateErrorResponse(HttpServletResponse response,ErrorCodeEnum errorCodeEnum) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        Map<String,String> result = new HashMap<>(2);
        result.put("error", errorCodeEnum.getError());
        result.put("error_description",errorCodeEnum.getErrorDescription());

        response.getWriter().write(JsonUtils.toJson(result));
        return false;
    }

}
