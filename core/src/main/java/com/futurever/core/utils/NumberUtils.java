package com.futurever.core.utils;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-08-31 00:53
 **/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-08-31 00:53
 **/
public class NumberUtils {

    public static boolean isNumeric(String... strArr) {
        if (strArr == null) {
            return false;
        }
        for (String str : strArr) {
            if (!isNumeric(false, str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(boolean trim, String... strArr) {
        if (strArr == null) {
            return false;
        }
        for (String str : strArr) {
            if (!isNumeric(trim, str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        return isNumeric(false, str);
    }

    public static boolean isNumeric(boolean trim, String str) {
        if (str == null) {
            return false;
        }
        if (trim) {
            str = str.trim();
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
