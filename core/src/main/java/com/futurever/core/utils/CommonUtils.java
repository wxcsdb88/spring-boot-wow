package com.futurever.core.utils;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-08-31 00:32
 **/

public class CommonUtils {
    public static boolean isEmpty(Object obj) {
        return obj == null
                || obj instanceof String && ("".equals(obj) || "null".equals(obj) || "".equals(((String) obj).trim()))
                || obj instanceof List && ((List) obj).size() == 0
                || obj instanceof Map && ((Map) obj).size() == 0;
    }

    /**
     * once object is null or "" or "null" or len==0, will return true
     *
     * @param objects array
     * @return boolean
     */
    public static boolean isEmpty(Object... objects) {
        for (Object obj : objects) {
            if (isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

}
