package cn.edu.oauth2.interceptor;

import cn.edu.oauth2.common.Constants;
import cn.edu.oauth2.common.SpringContextUtils;
import cn.edu.oauth2.enums.ErrorCodeEnum;
import cn.edu.oauth2.mapper.AuthClientDetailsMapper;
import cn.edu.oauth2.mapper.AuthClientUserMapper;
import cn.edu.oauth2.mapper.AuthScopeMapper;
import cn.edu.oauth2.model.AuthClientDetails;
import cn.edu.oauth2.model.AuthClientUser;
import cn.edu.oauth2.model.AuthScope;
import cn.edu.oauth2.model.User;
import cn.edu.oauth2.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查是否已经存在授权
 *
 * @author zifangsky
 * @date 2018/8/10
 * @since 1.0.0
 */
public class OauthInterceptor extends HandlerInterceptorAdapter{
    @Autowired
    private AuthClientUserMapper authClientUserMapper;
    @Autowired
    private AuthClientDetailsMapper authClientDetailsMapper;
    @Autowired
    private AuthScopeMapper authScopeMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //参数信息
        String params = "?redirectUri=" + SpringContextUtils.getRequestUrl(request);

        //客户端ID
        String clientIdStr = request.getParameter("client_id");
        //权限范围
        String scopeStr = request.getParameter("scope");
        //回调URL
        String redirectUri = request.getParameter("redirect_uri");
        //返回形式
        String responseType = request.getParameter("response_type");

        //获取session中存储的token
        User user = (User) session.getAttribute(Constants.SESSION_USER);

        if(StringUtils.isNoneBlank(clientIdStr) && StringUtils.isNoneBlank(scopeStr) && StringUtils.isNoneBlank(redirectUri) && "code".equals(responseType)){
            params = params + "&client_id=" + clientIdStr + "&scope=" + scopeStr;

            //1. 查询是否存在授权信息
            AuthClientDetails clientDetails = authClientDetailsMapper.selectByClientId(clientIdStr);
            AuthScope scope = authScopeMapper.selectByScopeName(scopeStr);

            if(clientDetails == null){
                return this.generateErrorResponse(response, ErrorCodeEnum.INVALID_CLIENT);
            }

            if(scope == null){
                return this.generateErrorResponse(response, ErrorCodeEnum.INVALID_SCOPE);
            }

            if(!clientDetails.getRedirectUri().equals(redirectUri)){
                return this.generateErrorResponse(response, ErrorCodeEnum.REDIRECT_URI_MISMATCH);
            }

            //2. 查询用户给接入的客户端是否已经授权
            AuthClientUser clientUser = authClientUserMapper.selectByClientId(clientDetails.getId(), user.getId(), scope.getId());
            if(clientUser != null){
                return true;
            }else{
                //如果没有授权，则跳转到授权页面
                response.sendRedirect(request.getContextPath() + "/oauth2.0/authorizePage" + params);
                return false;
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
