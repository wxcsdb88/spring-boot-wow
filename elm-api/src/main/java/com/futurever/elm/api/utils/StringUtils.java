package com.futurever.elm.api.utils;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangxin on 2017/6/21.
 */
public class StringUtils {

    private static Logger log = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 特殊字符 反转义
     *
     * @param xml
     * @return
     */
    public static String unescapeXml(String xml) {
        return StringEscapeUtils.unescapeXml(xml);
    }

    /**
     * 特殊字符 反转义
     *
     * @param json
     * @return
     */
    public static String unescapeJson(String json) {
        return StringEscapeUtils.unescapeJson(json);
    }

    /**
     * 特殊字符 反转义
     *
     * @param java
     * @return
     */
    public static String unescapeJava(String java) {
        return StringEscapeUtils.unescapeJava(java);
    }
}
