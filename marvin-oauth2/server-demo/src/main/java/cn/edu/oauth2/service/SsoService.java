package cn.edu.oauth2.service;

import cn.edu.oauth2.model.SsoAccessToken;
import cn.edu.oauth2.model.SsoClientDetails;
import cn.edu.oauth2.model.SsoRefreshToken;
import cn.edu.oauth2.model.User;

/**
 * SSO单点登录相关Service
 *
 * @author zifangsky
 * @date 2018/8/30
 * @since 1.0.0
 */
public interface SsoService {

    /**
     * 根据ID查询接入客户端
     * @author zifangsky
     * @date 2018/8/30 16:36
     * @since 1.0.0
     * @param id id
     * @return cn.edu.oauth2.model.SsoClientDetails
     */
    SsoClientDetails selectByPrimaryKey(Integer id);

    /**
     * 根据URL查询记录
     * @author zifangsky
     * @date 2018/8/30 16:36
     * @since 1.0.0
     * @param redirectUrl 回调URL
     * @return cn.edu.oauth2.model.SsoClientDetails
     */
    SsoClientDetails selectByRedirectUrl(String redirectUrl);

    /**
     * 通过主键ID查询记录
     * @author zifangsky
     * @date 2018/8/30 13:11
     * @since 1.0.0
     * @param id ID
     * @return cn.edu.oauth2.model.SsoAccessToken
     */
    SsoAccessToken selectByAccessId(Integer id);

    /**
     * 通过Access Token查询记录
     * @author zifangsky
     * @date 2018/8/30 13:11
     * @since 1.0.0
     * @param accessToken Access Token
     * @return cn.edu.oauth2.model.SsoAccessToken
     */
    SsoAccessToken selectByAccessToken(String accessToken);

    /**
     * 通过tokenId查询记录
     * @author zifangsky
     * @date 2018/8/30 13:11
     * @since 1.0.0
     * @param tokenId tokenId
     * @return cn.edu.oauth2.model.SsoRefreshToken
     */
    SsoRefreshToken selectByTokenId(Integer tokenId);

    /**
     * 通过Refresh Token查询记录
     * @author zifangsky
     * @date 2018/8/30 13:11
     * @since 1.0.0
     * @param refreshToken Refresh Token
     * @return cn.edu.oauth2.model.SsoRefreshToken
     */
    SsoRefreshToken selectByRefreshToken(String refreshToken);

    /**
     * 生成Access Token
     * @author zifangsky
     * @date 2018/8/30 13:11
     * @since 1.0.0
     * @param user 用户信息
     * @param expiresIn 过期时间
     * @param ssoClientDetails 接入客户端详情
     * @param requestIP 用户请求的IP
     * @return java.lang.String
     */
    String createAccessToken(User user, Long expiresIn, String requestIP, SsoClientDetails ssoClientDetails);


    /**
     * 生成Refresh Token
     * @author zifangsky
     * @date 2018/8/30 13:11
     * @since 1.0.0
     * @param user 用户信息
     * @param ssoAccessToken 生成的Access Token信息
     * @return java.lang.String
     */
    String createRefreshToken(User user, SsoAccessToken ssoAccessToken);

}
