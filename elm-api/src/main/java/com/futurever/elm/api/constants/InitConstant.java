package com.futurever.elm.api.constants;


/**
 * Created by wxcsdb88 on 2017/8/24 20:21.
 */
public class InitConstant {
    public static Long TIMESTAMP_DIFF;
    public static Long TIMEOUT;
    public static Long RATE_LIMIT_TIMEOUT;
    public static Integer RATE_LIMIT_COUNT;
    public static String RATE_LIMIT_SUFFIX;


    static {
        TIMESTAMP_DIFF = 600 * 1000L; // 600S
        TIMEOUT = 30 * 60L; // 1800s 0.5hour
        RATE_LIMIT_TIMEOUT = 30L; // 10s
//        RATE_LIMIT_COUNT = 1000; // 1000 p/s
        RATE_LIMIT_COUNT = 10; // 1000 p/s
        RATE_LIMIT_SUFFIX = "_rate_limit";
    }
}
