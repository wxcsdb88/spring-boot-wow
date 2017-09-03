package com.futurever.elm.api.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wxcsdb88 on 2017/5/16 9:51.
 */
public class RequestUtils {

    public static String requestParamsOutput(HttpServletRequest request) {
        StringBuilder requestParamMap = new StringBuilder("[");
        String requestParams = "";
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String[] val = entry.getValue();
            if (val.length == 1) {
                requestParamMap.append(entry.getKey() + "=" + entry.getValue()[0] + ",");
            } else {
                requestParamMap.append(entry.getKey() + "=" + Arrays.toString(entry.getValue()) + ",");
            }
        }
        requestParams = requestParamMap.subSequence(0, requestParamMap.length() - 1).toString() + "]";
        return requestParams;
    }

    public static List<NameValuePair> parseQueryParamMapToBasicNameValuePair(List<NameValuePair> params, TreeMap<String, String> queryParamMap) {
        queryParamMap.forEach((k, v) -> {
            params.add(new BasicNameValuePair(k, v));
        });
        return params;
    }

    public static String parseQueryParamMapToQuereyString(TreeMap<String, String> queryParamMap, String... removeKeys) {
        return parseQueryParamMapToQuereyString(queryParamMap, false, removeKeys);
    }

    public static String parseQueryParamMapToQuereyString(TreeMap<String, String> queryParamMap, boolean ignoreNullValue, String... removeKeys) {
        if (queryParamMap == null || queryParamMap.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Arrays.stream(removeKeys).forEach(key -> {
            queryParamMap.remove(key);
        });
        queryParamMap.forEach((k, v) -> {
            if (!ignoreNullValue) {
                builder.append(k).append("=").append(v).append("&");
            }
        });
        if (!queryParamMap.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public static String parseQueryString(String queryString, boolean ignoreNullValue, String... removeKeys) {
        if (queryString == null || "".equals(queryString)) {
            return "";
        }
        TreeMap<String, String> queryMap = parseQueryStringToMap(queryString, ignoreNullValue, removeKeys);
        StringBuilder builder = new StringBuilder();
        queryMap.forEach((k, v) -> {
            builder.append(k).append("=").append(v).append("&");
        });
        if (!queryMap.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public static TreeMap<String, String> parseQueryStringToMap(String queryString, boolean ignoreNullValue, String... removeKeys) {
        TreeMap<String, String> sortMap = new TreeMap<String, String>();
        if (CommonUtils.isEmpty(queryString)) {
            return sortMap;
        }
        String[] queryParamPairs = queryString.split("&");
        Arrays.stream(queryParamPairs).forEach((arrayItem) -> {
            String[] kv = arrayItem.split("=");
            if (kv.length == 1) {
                if (!ignoreNullValue) {
                    sortMap.put(kv[0], "");
                }
            } else {
                sortMap.put(kv[0], kv[1]);
            }
        });

        Arrays.stream(removeKeys).forEach(key -> {
            sortMap.remove(key);
        });
        return sortMap;
    }

    public static String getSign(String queryString, boolean ignoreNullValue, boolean toUpper, String... removeKeys) {
        String queryStr = parseQueryString(queryString, ignoreNullValue, removeKeys);
        return toUpper ? MD5Util.MD5(queryStr).toUpperCase() : MD5Util.MD5(queryStr);
    }

    public static String getSign(TreeMap<String, String> parameterMap, boolean toUpper, String... removeKeys) {
        String queryStr = parseQueryParamMapToQuereyString(parameterMap, removeKeys);
        return toUpper ? MD5Util.MD5(queryStr).toUpperCase() : MD5Util.MD5(queryStr);
    }

    public static String getSign(String queryString, boolean toUpper, String... removeKeys) {
        return getSign(queryString, false, toUpper);
    }
}
