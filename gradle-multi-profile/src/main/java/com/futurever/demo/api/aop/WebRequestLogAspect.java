package com.futurever.demo.api.aop;

import com.futurever.demo.api.common.ResponseData;
import com.futurever.demo.api.constants.Response;
import com.futurever.demo.api.exception.BusinessException;
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
import java.net.URLDecoder;

import static com.futurever.demo.api.utils.IPUtils.getIpAddr;
import static com.futurever.demo.api.utils.IPUtils.getLocalRealIp;

/**
 * description:
 *
 * @author : wxcsdb88
 * @since : 2017/10/10 15:20
 */
// 权限校验可能是1，日志优先级低于权限校验
@Order(5)
@Aspect
@Component
public class WebRequestLogAspect {
    private static Logger logger = LoggerFactory.getLogger(WebRequestLogAspect.class);

    @Pointcut("execution(public * com.futurever..*.controller..*.*(..))")
    public void webRequestLog() {
    }

    @Around("webRequestLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.nanoTime();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            logger.error("error request!");
            throw new BusinessException("error request!");
        }
        HttpServletRequest request = attributes.getRequest();
        String queryString = request.getQueryString();
        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String uri = request.getRequestURL().toString();

        String remoteAddr = getIpAddr(request);
        String method = request.getMethod();
        Object[] args = joinPoint.getArgs();
//      logger.warn(Arrays.toString(args));
//        Object result = joinPoint.proceed(args);
        Object result = joinPoint.proceed();
        double costTime = (System.nanoTime() - beginTime) * 1.0 / 1000000;
        ResponseData responseData = (ResponseData) result;
        int code = responseData.getCode();
        String msg = responseData.getMsg();

        String output_log = "({}.{}) doAround API_INFO[method={} uri={} code={} from={} to={} cost={}ms parameters=({})] msg=({})";
        logger.info(output_log, beanName, methodName, method, uri, code, remoteAddr, getLocalRealIp(), String.format("%1$.3f", costTime), queryString, msg);

        // todo debug use
//        String output_log2 = "doAround API_INFO[sign={} beanName={} methodName={} user={} method={} uri={}  code={} from={} to={} cost={}ms parameters=({})] msg=({})\nresponse={}";
//        LogUtils.warn(logger, output_log2, sign, beanName, methodName, user, method, uri, code, remoteAddr, getLocalRealIp(), String.format("%1$.3f", costTime), queryString, msg, responseData.getResult());
        return result;
    }

    @AfterThrowing(value = "webRequestLog()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String queryString = "";
        String uri = "";
        String remoteAddr = "";
        String method = "";
        if (null == attributes) {
            logger.error("error request!");

        } else {
            HttpServletRequest request = attributes.getRequest();
            queryString = request.getQueryString();
            queryString = URLDecoder.decode(queryString, "UTF-8");
            uri = request.getRequestURL().toString();
            remoteAddr = getIpAddr(request);
            method = request.getMethod();
        }
        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();


        String output_log = "({}.{}) doAfterThrowing API_INFO[method={} uri={} code={} from={} to={} cost={}ms parameters=({})] msg=({})";
        logger.error(output_log, beanName, methodName, method, uri, Response.SERVICE_EXCEPTION, remoteAddr, getLocalRealIp(), String.format("%1$.3f", 0.00), queryString, ex);
    }

}
