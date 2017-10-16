package com.futurever.core.constants;

import java.util.regex.Pattern;

/**
 * description:
 *
 * @author: wxcsdb88
 * @since: 2017/10/16 10:24
 */
public interface RegConstants {
    String IS_NUMERIC = "[0-9]*";
    Pattern PATTERN_IS_NUMERIC = Pattern.compile(IS_NUMERIC);
    String NOT_INCLUDE_NUMERIC_AND_DOT = "[^0-9.]";
    Pattern PATTERN_NOT_INCLUDE_NUMERIC_AND_DOT = Pattern.compile(NOT_INCLUDE_NUMERIC_AND_DOT);
    String INCLUDE_NUMERIC_AND_DOT = "[^0-9.]";
    Pattern PATTERN_INCLUDE_NUMERIC_AND_DOT = Pattern.compile(INCLUDE_NUMERIC_AND_DOT);
}
