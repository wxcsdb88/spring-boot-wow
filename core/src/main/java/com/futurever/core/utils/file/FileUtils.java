package com.futurever.core.utils.file;

import com.futurever.core.constants.FileSizeUnit;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String formatFileSize(long fileS, Integer num_digits) {
        if (num_digits == null) {
            num_digits = 1;
        }
        DecimalFormat df;
        switch (num_digits) {
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
        if (fileS < FileSizeUnit.sizeOfUnit("B")) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < FileSizeUnit.sizeOfUnit("KB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("B")) + "B";
        } else if (fileS < FileSizeUnit.sizeOfUnit("MB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("KB")) + "KB";
        } else if (fileS < FileSizeUnit.sizeOfUnit("GB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("MB")) + "MB";
        } else if (fileS < FileSizeUnit.sizeOfUnit("TB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("GB")) + "GB";
        } else if (fileS < FileSizeUnit.sizeOfUnit("PB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("TB")) + "TB";
        } else if (fileS < FileSizeUnit.sizeOfUnit("EB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("PB")) + "PB";
        } else if (fileS < FileSizeUnit.sizeOfUnit("ZB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("EB")) + "EB";
        } else if (fileS < FileSizeUnit.sizeOfUnit("YB")) {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("ZB")) + "ZB";
        } else {
            fileSizeString = df.format((double) fileS / FileSizeUnit.sizeOfUnit("YB")) + "YB";
        }
        return fileSizeString;
    }

    public static String getByteNumberStrFromSizeUnitStr(String sizeUnitStr) {
        long sizeByte = getByteNumberFromSizeUnitStr(sizeUnitStr);
        return sizeByte >= 0 ? (sizeByte + "B") : "";
    }

    public static long getByteNumberFromSizeUnitStr(String sizeUnitStr) {
        Pattern pattern = Pattern.compile("[^0-9.]");
        Matcher matcher = pattern.matcher(sizeUnitStr);

        double sizeVal = 1L;
        if (matcher.find()) {
            sizeVal = Double.valueOf(matcher.replaceAll(""));
        }
        pattern = Pattern.compile("[0-9.]");
        matcher = pattern.matcher(sizeUnitStr);
        String sizeUnit = "";
        if (matcher.find()) {
            sizeUnit = matcher.replaceAll("");
        } else {
            return -1L;
        }
        return (long) (sizeVal * FileSizeUnit.sizeOfUnit(sizeUnit));
    }

}
