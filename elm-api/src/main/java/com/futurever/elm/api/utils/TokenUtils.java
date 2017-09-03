package com.futurever.elm.api.utils;

import com.futurever.elm.api.common.TokenUser;
import com.futurever.elm.api.constants.InitConstant;
import com.futurever.elm.api.constants.Response;
import com.futurever.elm.api.exception.AuthorizationException;
import com.futurever.elm.api.redis.RedisUtils;
import com.futurever.elm.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wxcsdb88 on 2017/8/17 11:34.
 */
@Component
public class TokenUtils {
    private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);
    private static TokenUtils tokenUtils;
    @Autowired
    private UserService userService;

    public static TokenUser getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (CommonUtils.isEmpty(token) || !RedisUtils.exists(token)) {
            throw new AuthorizationException("token失效，请重新登录！");
        }

        Object tokenUserObj = RedisUtils.get(token);
        TokenUser tokenUser = new TokenUser();
//        TokenUser tokenUser = (TokenUser) tokenUserObj;
        BeanUtils.copyProperties(tokenUserObj, tokenUser);
        if (tokenUser == null || tokenUser.getId() == null) {
            logger.error("用户未登录");
            throw new AuthorizationException(Response.LOGIN_TIMEOUT, "用户未登录");
        }
        return tokenUser;
    }

    public static void updateCurrentUser(String token, TokenUser tokenUser) {
        if (!RedisUtils.exists(token)) {
            throw new AuthorizationException("token失效，请重新登录！");
        }

        if (tokenUser == null || tokenUser.getId() == null) {
            throw new AuthorizationException("用户信息错误！");
        }
        boolean updateFlag = RedisUtils.set(token, tokenUser, InitConstant.TIMEOUT);
        if (!updateFlag) {
            throw new AuthorizationException("token 存储失败！");
        }
    }

    public static boolean removeCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (CommonUtils.isEmpty(token) || !RedisUtils.exists(token)) {
            throw new AuthorizationException("token不能为空！");
        }
        RedisUtils.remove(token);
        if (RedisUtils.exists(token)) {
            return false;
        }
        return true;
    }

    public static String insertCurrentUser(TokenUser tokenUser) {
        if (tokenUser == null || tokenUser.getId() == null) {
            throw new AuthorizationException("用户信息错误！");
        }
        String token = generateToken(tokenUser);
        boolean updateFlag = RedisUtils.set(token, tokenUser, InitConstant.TIMEOUT);
        if (!updateFlag) {
            throw new AuthorizationException("token 存储失败！");
        }
        return token;
    }

    private static String generateToken(TokenUser tokenUser) {
        String current = DateUtils.currentTimestampStr();
        Long userId = tokenUser.getId();
        String username = tokenUser.getUsername();
        String tokenStr = userId + "-" + username + "-" + current;
        return MD5Util.generate(tokenStr);
    }

    public static boolean verifyRateLimit(String token) {
        String rateLimitKey = token + InitConstant.RATE_LIMIT_SUFFIX;
        Integer rateLimitCount = (Integer) RedisUtils.get(rateLimitKey);

        if (rateLimitCount == null) {
            RedisUtils.set(rateLimitKey, 0, InitConstant.RATE_LIMIT_TIMEOUT);
            return true;
        }

        if (rateLimitCount < InitConstant.RATE_LIMIT_COUNT) {
            RedisUtils.increment(rateLimitKey);
        } else {
            Long leftTime = RedisUtils.getExpire(rateLimitKey);
            if (leftTime >= InitConstant.RATE_LIMIT_TIMEOUT) {
                RedisUtils.set(rateLimitKey, 0, InitConstant.RATE_LIMIT_TIMEOUT);
            } else {
                String info = "访问频次超限[%s次/%ss],剩余重置时间 %ss";
                String out_info = String.format(info, InitConstant.RATE_LIMIT_COUNT, InitConstant.RATE_LIMIT_TIMEOUT, leftTime);
                throw new AuthorizationException(Response.APP_REQUESTS_OUT_OF_RATE_LIMIT, out_info);
            }
            String info = "访问频次[%s次/%ss],剩余次数%s,剩余重置时间 %ss";
            String out_info = String.format(info, InitConstant.RATE_LIMIT_COUNT, InitConstant.RATE_LIMIT_TIMEOUT, rateLimitCount, leftTime);
            logger.info(out_info);
        }
        return true;
    }

    @PostConstruct
    public void init() {
        tokenUtils = this;
        tokenUtils.userService = this.userService;
    }
}
