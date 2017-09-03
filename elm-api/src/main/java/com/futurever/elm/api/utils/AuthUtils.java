package com.futurever.elm.api.utils;

import com.futurever.elm.api.constants.RequestField;
import com.futurever.elm.api.constants.Response;
import com.futurever.elm.api.exception.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wxcsdb88 on 2017/8/24 16:04.
 */
public class AuthUtils {
    private static Logger logger = LoggerFactory.getLogger(AuthUtils.class);

    public static boolean verifyHeader(String beanName, String methodName, String contentType, String token, String... headers) throws Exception {
        if (CommonUtils.isEmpty(contentType, token, headers)) {
            String response_out = " headers contains null value here!";
            logger.error(response_out);
            throw new AuthorizationException(Response.INVALID_HEADERS, response_out);
        }

        if (!RequestField.CONTENT_TYPE_VALUE_MAP.containsKey(contentType)) {
            String response_out = " headers Content-Type value error here!";
            logger.error(response_out);
            throw new AuthorizationException(Response.INVALID_HEADERS, response_out);
        }
        return true;
    }

    public static boolean verifyTimestamp(String beanName, String methodName, String timestamp, Long maxDiff) throws Exception {
        if (CommonUtils.isEmpty(timestamp) || timestamp.length() != 13 || !CommonUtils.isNumeric(timestamp)) {
            String response_out = " error timestamp!";
            logger.error(response_out);
            throw new AuthorizationException(Response.INVALID_TIMESTAMP, response_out);
        }
        Long timestampVal = Long.valueOf(timestamp);
        Long now = DateUtils.currentTimestamp();
        Long diff = now - timestampVal;
        if (diff <= 0 || diff > maxDiff) {
            String response_out = " error timestamp! [now=" + now + ", diff=" + diff + "]";
            logger.error(response_out);
            throw new AuthorizationException(Response.INVALID_TIMESTAMP, response_out);
        }
        return true;
    }

    public static boolean verifyRequiredParameter(String... necessaryParameters) throws Exception {
        if (CommonUtils.isEmpty(necessaryParameters)) {
            String response_out = " required parameter missing!";
            logger.error(response_out);
            throw new AuthorizationException(Response.MISSING_REQUIRED_PARAMETER, response_out);
        }
        return true;
    }

    public static boolean verifySign(String beanName, String methodName, TreeMap<String, String> parametersMap, String... removeKeys) throws Exception {
        String sign = parametersMap.get("sign");
        String realSign = RequestUtils.getSign(parametersMap, true, RequestField.SIGN);
        if (!realSign.equals(sign)) {
            //todo
            String response_out = " error sign, real is " + realSign;
            logger.error(response_out);
            throw new AuthorizationException(Response.INVALID_SIGN, response_out);
        }
        return true;
    }


    public static boolean verifyParameter(String beanName, String methodName, TreeMap<String, String> parameters, String... removeKeys) throws Exception {
        return verifyParameter(beanName, methodName, parameters, false, removeKeys);
    }

    public static boolean verifyParameter(String beanName, String methodName, TreeMap<String, String> parameters, boolean ignoreNullValue, String... removeKeys) throws Exception {
        // sign, timestamp, appId
        if (CommonUtils.isEmpty(parameters)) {
            return true;
        }
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (!RequestField.ALL_FIELDS_MAP.containsKey(entry.getKey())) {
//                String out_info = "({}.{}) parameter ({}) not allowed here!"; // not effect
//                logger.error("({}.{}) parameter ({}) not allowed here!", beanName, methodName, entry.getKey());
                StringBuilder out_info = new StringBuilder(" parameter (" + entry.getKey() + ") not allowde here!");
                String response_out = out_info.toString();

                out_info = out_info.insert(0, "(" + beanName + "." + methodName + ")");
                logger.error(out_info.toString());
                throw new AuthorizationException(Response.INVALID_PARAMETER, response_out);
            }
        }
        return true;
    }

}
