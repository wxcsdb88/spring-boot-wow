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

import static com.futurever.demo.api.utils.IpUtils.getClientAddr;

/**
 * description:
 *
 * @author wxcsdb88
 * @since 2017/10/10 15:20
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
        if (queryString != null && !"".equals(queryString)) {
            queryString = URLDecoder.decode(queryString, "UTF-8");
        }
        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String uri = request.getRequestURI();

        String remoteAddr = getClientAddr(request);
        String localAddr = request.getLocalAddr();
        String method = request.getMethod();
        Object[] args = joinPoint.getArgs();
//      logger.warn(Arrays.toString(args));
//        Object result = joinPoint.proceed(args);
        Object result = joinPoint.proceed();
        double costTime = (System.nanoTime() - beginTime) * 1.0 / 1000000;
        ResponseData responseData = (ResponseData) result;
        int code = responseData.getCode();
        String msg = responseData.getMsg();

        String outputLog = "({}.{}) doAround API_INFO[method={} uri={} code={} from={} to={} cost={}ms parameters=({})] msg=({})";
        logger.info(outputLog, beanName, methodName, method, uri, code, remoteAddr, localAddr, String.format("%1$.3f", costTime), queryString, msg);

        // todo debug use
//        String output_log2 = "doAround API_INFO[sign={} beanName={} methodName={} user={} method={} uri={}  code={} from={} to={} cost={}ms parameters=({})] msg=({})\nresponse={}";
//        LogUtils.warn(logger, output_log2, sign, beanName, methodName, user, method, uri, code, remoteAddr, getLocalRealIp(), String.format("%1$.3f", costTime), queryString, msg, responseData.getResult());
        return result;
    }

    @AfterThrowing(value = "webRequestLog()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String queryString = "";
        String uri = "";
        String remoteAddr = "";
        String localAddr = "";
        String method = "";
        if (null == attributes) {
            logger.error("error request!");

        } else {
            HttpServletRequest request = attributes.getRequest();
            queryString = request.getQueryString();
            if (queryString != null && !"".equals(queryString)) {
                queryString = URLDecoder.decode(queryString, "UTF-8");
            }
            uri = request.getRequestURI();
            remoteAddr = getClientAddr(request);
            localAddr = request.getLocalAddr();
            method = request.getMethod();
        }
        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        String outputLog = "({}.{}) doAfterThrowing API_INFO[method={} uri={} code={} from={} to={} cost={}ms parameters=({})] msg=({})";
        String msg = ex.getCause() == null ? ex.getMessage(): ex.getCause().getMessage();
        logger.error(outputLog, beanName, methodName, method, uri, Response.INTERNAL_SERVER_ERROR, remoteAddr, localAddr, String.format("%1$.3f", 0.00), queryString, msg);
    }

}
