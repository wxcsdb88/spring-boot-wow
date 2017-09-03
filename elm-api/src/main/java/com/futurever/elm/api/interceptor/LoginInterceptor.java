package com.futurever.elm.api.interceptor;

import com.futurever.elm.api.constants.InitConstant;
import com.futurever.elm.api.constants.RequestField;
import com.futurever.elm.api.constants.Response;
import com.futurever.elm.api.exception.AuthorizationException;
import com.futurever.elm.api.redis.RedisUtils;
import com.futurever.elm.api.utils.CommonUtils;
import com.futurever.elm.api.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wxcsdb88 on 2017/5/23 20:17.
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // intercept
        String token = request.getHeader(RequestField.TOKEN);
        if (CommonUtils.isEmpty(token)) {
            throw new AuthorizationException(Response.INVALID_TOKEN, "token is blank!");
        }
        boolean isExist = RedisUtils.exists(token);
        if (!isExist) {
            throw new AuthorizationException(Response.LOGIN_TIMEOUT, "user not login or login is outdated!");
        } else {
            // 更新超时
            RedisUtils.setExpire(token, InitConstant.TIMEOUT);
            // 验证 访问次数
            if (!TokenUtils.verifyRateLimit(token)) {
                String out_info = "访问受限，超过最大访问次数，请 %ss后重试!";
                logger.error(String.format(out_info, InitConstant.RATE_LIMIT_TIMEOUT));
                throw new AuthorizationException(Response.APP_REQUESTS_OUT_OF_RATE_LIMIT, String.format(out_info, InitConstant.RATE_LIMIT_TIMEOUT));
            }
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
