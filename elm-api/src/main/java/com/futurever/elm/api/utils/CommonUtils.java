package com.futurever.elm.api.utils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wxcsdb88 on 2017/5/11 18:50.
 */
public class CommonUtils {

    public static boolean isEmpty(Object obj) {
        return obj == null
                || obj instanceof String && ("".equals(obj) || "null".equals(obj) || "".equals(((String) obj).trim()))
                || obj instanceof List && ((List) obj).size() == 0
                || obj instanceof Map && ((Map) obj).size() == 0;
    }

    /**
     * string array contains at least one null or "" or "null"
     *
     * @param strs
     * @return
     */
    public static boolean isEmpty(String... strs) {
        for (String str : strs) {
            if (str == null || "".equals(str) || "".equals((str).trim()) || "null".equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * once object is null or "" or "null", will return true
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

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 生成编号
     *
     * @return String
     */
    public static String generateSerialNo() {
        //yyMMddHHmmss+6 random 18bits
        StringBuffer sb = new StringBuffer(DateUtils.currentDateTimeStr("yyMMddHHmmss"));
        System.out.println(sb.length());
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        sb.append(String.format("%06d", hashCodeV).substring(0, 6));
        return sb.toString();
    }
}
