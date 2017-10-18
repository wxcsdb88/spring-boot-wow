package com.futurever.demo.api.utils;

import com.futurever.demo.api.common.RequestDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.futurever.demo.api.utils.IpUtils.getClientAddr;

/**
 * description:
 *
 * @author wxcsdb88
 * @since 2017/10/17 09:58
 */
public class RequestUtils {
    private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * 当前请求 参数
     *
     * @param request {@link HttpServletRequest}
     * @return {@link RequestDO}
     */
    public static RequestDO getCurrentRequestDO(HttpServletRequest request) {
        RequestDO requestDO = new RequestDO();
        String queryString = request.getQueryString();
        try {
            if (queryString != null && !"".equals(queryString)) {
                queryString = URLDecoder.decode(queryString, "UTF-8");
            }
        } catch (UnsupportedEncodingException ex) {
            queryString = "";
            String msg = String.format("%s : exception %s", RequestUtils.class.getName(), ex.getMessage());
            logger.error(msg);
        }

        requestDO.setMethod(request.getMethod());
        requestDO.setQueryString(queryString);
        // 真实ip地址获取
        requestDO.setClientAddr(getClientAddr(request));
        requestDO.setScheme(request.getScheme());
        requestDO.setScheme(request.getScheme());
        requestDO.setProtocol(request.getProtocol());
        requestDO.setRequestURL(request.getRequestURL().toString());
        requestDO.setRequestURI(request.getRequestURI());
        requestDO.setLocalPort(request.getLocalPort());
        requestDO.setLocalName(request.getLocalName());
        requestDO.setLocalAddr(request.getLocalAddr());
        requestDO.setServerName(request.getServerName());
        requestDO.setServerPort(request.getServerPort());
        requestDO.setRemoteHost(request.getRemoteHost());
        requestDO.setRemoteAddr(request.getRemoteAddr());
        return requestDO;
    }
}
