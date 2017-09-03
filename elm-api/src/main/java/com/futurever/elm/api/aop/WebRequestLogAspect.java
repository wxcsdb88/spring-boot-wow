package com.futurever.elm.api.aop;

import com.futurever.elm.api.common.ResponseData;
import com.futurever.elm.api.constants.RequestField;
import com.futurever.elm.api.constants.Response;
import com.futurever.elm.api.utils.RequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.futurever.elm.api.utils.IPUtils.getIpAddr;
import static com.futurever.elm.api.utils.IPUtils.getLocalRealIp;

/**
 * Created by wxcsdb88 on 2017/8/17 15:01.
 */
// 权限校验可能是1，日志优先级低于权限校验
@Order(5)
@Aspect
@Component
public class WebRequestLogAspect {
    private static Logger logger = LoggerFactory.getLogger(WebRequestLogAspect.class);

    @Pointcut("execution(public * com.futurever.elm.api.controller..*.*(..))")
    public void webRequestLog() {
    }

    @Around("webRequestLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.nanoTime();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            logger.error("error request!");
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        String queryString = request.getQueryString();
        String sign = RequestUtils.getSign(queryString, true, RequestField.SIGN);

        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String uri = request.getRequestURL().toString();

        String remoteAddr = getIpAddr(request);
        String token = (String) request.getHeader(RequestField.TOKEN);
        String method = request.getMethod();
        Object[] args = joinPoint.getArgs();
//      logger.warn(Arrays.toString(args));
//        Object result = joinPoint.proceed(args);
        Object result = joinPoint.proceed();
        double costTime = (System.nanoTime() - beginTime) * 1.0 / 1000000;
        ResponseData responseData = (ResponseData) result;
        int code = responseData.getCode();
        String msg = responseData.getMsg();

        String output_log = "({}.{}) doAround API_INFO[method={} uri={} token={} code={} from={} to={} cost={}ms parameters=({})] msg=({})";
        logger.info(output_log, beanName, methodName, method, uri, token, code, remoteAddr, getLocalRealIp(), String.format("%1$.3f", costTime), queryString, msg);

        return result;
    }

    @AfterThrowing(value = "webRequestLog()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            logger.error("error request!");

        }
        HttpServletRequest request = attributes.getRequest();
        String queryString = request.getQueryString();
        String sign = RequestUtils.getSign(queryString, true, RequestField.SIGN);
        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String uri = request.getRequestURL().toString();

        String remoteAddr = getIpAddr(request);
        String token = (String) request.getHeader(RequestField.TOKEN);
        String method = request.getMethod();

        String output_log = "({}.{}) doAfterThrowing API_INFO[method={} uri={} token={} code={} from={} to={} cost={}ms parameters=({})] msg=({})";
        logger.error(output_log, beanName, methodName, method, uri, token, Response.SERVICE_EXCEPTION, remoteAddr, getLocalRealIp(), String.format("%1$.3f", 0.00), queryString, ex);

    }

}
