package com.futurever.core.utils.file;

import com.futurever.core.constants.FileSizeUnitEnum;

import java.text.DecimalFormat;
import java.util.regex.Matcher;

import static com.futurever.core.constants.RegConstants.PATTERN_INCLUDE_NUMERIC_AND_DOT;
import static com.futurever.core.constants.RegConstants.PATTERN_NOT_INCLUDE_NUMERIC_AND_DOT;

/**
 * description:
 *
 * @author: wxcsdb88
 * @since: 2017/10/12 15:53
 */
public class FileUtils {

    public static String formatFileSize(long fileS) {
        return formatFileSize(fileS, 1);
    }

    public static String formatFileSize(long fileS, Integer numDigits) {
        if (numDigits == null) {
            numDigits = 1;
        }
        DecimalFormat df;
        switch (numDigits) {
            case 0:
                df = new DecimalFormat("#");
                break;
            case 2:
                df = new DecimalFormat("#.##");
                break;
            case 3:
                df = new DecimalFormat("#.###");
                break;
            default:
                df = new DecimalFormat("#.#");
                break;
        }

        String fileSizeString = "";
        if (fileS < FileSizeUnitEnum.sizeOfUnit("B")) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < FileSizeUnitEnum.sizeOfUnit("KB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("B")) + "B";
        } else if (fileS < FileSizeUnitEnum.sizeOfUnit("MB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("KB")) + "KB";
        } else if (fileS < FileSizeUnitEnum.sizeOfUnit("GB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("MB")) + "MB";
        } else if (fileS < FileSizeUnitEnum.sizeOfUnit("TB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("GB")) + "GB";
        } else if (fileS < FileSizeUnitEnum.sizeOfUnit("PB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("TB")) + "TB";
        } else if (fileS < FileSizeUnitEnum.sizeOfUnit("EB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("PB")) + "PB";
        } else if (fileS < FileSizeUnitEnum.sizeOfUnit("ZB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("EB")) + "EB";
        } else if (fileS < FileSizeUnitEnum.sizeOfUnit("YB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("ZB")) + "ZB";
        } else {
            fileSizeString = df.format((double) fileS / FileSizeUnitEnum.sizeOfUnit("YB")) + "YB";
        }
        return fileSizeString;
    }

    public static String getByteNumberStrFromSizeUnitStr(String sizeUnitStr) {
        long sizeByte = getByteNumberFromSizeUnitStr(sizeUnitStr);
        return sizeByte >= 0 ? (sizeByte + "B") : "";
    }

    public static long getByteNumberFromSizeUnitStr(String sizeUnitStr) {
        Matcher matcher = PATTERN_NOT_INCLUDE_NUMERIC_AND_DOT.matcher(sizeUnitStr);

        double sizeVal = 1L;
        if (matcher.find()) {
            sizeVal = Double.valueOf(matcher.replaceAll(""));
        }
        matcher = PATTERN_INCLUDE_NUMERIC_AND_DOT.matcher(sizeUnitStr);
        String sizeUnit = "";
        if (matcher.find()) {
            sizeUnit = matcher.replaceAll("");
        } else {
            return -1L;
        }
        return (long) (sizeVal * FileSizeUnitEnum.sizeOfUnit(sizeUnit));
    }

}
