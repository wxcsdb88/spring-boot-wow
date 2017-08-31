package com.futurever.core.utils;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-08-31 00:36
 **/

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-08-31 00:36
 **/
public class StringUtils {

    /**
     * if string array contain null or ""
     *
     * @param strArr string array
     * @return boolean
     */
    public static boolean containsBlank(String... strArr) {
        return containsEmpty(false, strArr);
    }

    /**
     * if string array contain null or "" or trim("   ") === ""
     *
     * @param trim   String.trim()
     * @param strArr string array
     * @return boolean
     */
    public static boolean containsBlank(boolean trim, String... strArr) {
        return containsEmpty(trim, strArr);
    }

    public static boolean containsEmpty(boolean trim, String... strArr) {
        for (String str : strArr) {
            if (str == null || "".equals(str) || "".equals((str).trim()) || "null".equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param strArr
     * @return
     */
    public static boolean isEmpty(String... strArr) {
        for (String str : strArr) {
            if (str == null || "".equals(str) || "".equals((str).trim()) || "null".equals(str)) {
                return true;
            }
        }
        return false;
    }


}
