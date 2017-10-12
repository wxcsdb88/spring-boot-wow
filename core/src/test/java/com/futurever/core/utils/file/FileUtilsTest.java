package com.futurever.core.utils.file;

import org.junit.Test;

/**
 * Description:
 *
 * @author wxcsdb88
 * @since 2017-10-12 21:12
 **/
public class FileUtilsTest {

    @Test
    public void test() throws Exception {
        System.out.println(FileUtils.formatFileSize(0));
        System.out.println(FileUtils.formatFileSize(100));
        System.out.println(FileUtils.formatFileSize(1024));
        System.out.println(FileUtils.formatFileSize(1025));
        System.out.println(FileUtils.formatFileSize(2048));
        System.out.println(FileUtils.formatFileSize(2 << 10));
        System.out.println(FileUtils.formatFileSize(2 << 20));
        System.out.println(FileUtils.formatFileSize(2 << 22));
        System.out.println(FileUtils.formatFileSize(2 << 25));
        System.out.println(FileUtils.formatFileSize(2 << 28));
        System.out.println(FileUtils.formatFileSize(2 << 29));
        System.out.println(FileUtils.formatFileSize((long) Math.pow(2.0, 32.0)));
        System.out.println(FileUtils.formatFileSize((long) Math.pow(2.0, 40.0)));
        System.out.println(FileUtils.formatFileSize((long) Math.pow(2.0, 45.0)));
        System.out.println(FileUtils.formatFileSize((long) Math.pow(2.0, 44.5)));
        System.out.println(FileUtils.formatFileSize((long) Math.pow(2.0, 52)));
        System.out.println(FileUtils.formatFileSize((long) Math.pow(2.0, 52.8)));
        System.out.println(FileUtils.formatFileSize((long) Math.pow(2.0, 30)));
        System.out.println(FileUtils.formatFileSize((long) Math.pow(2.0, 31)));
        System.out.println(FileUtils.formatFileSize(109512000));
        System.out.println(FileUtils.formatFileSize(8178282));
        System.out.println(FileUtils.formatFileSize(4566679));
        System.out.println(FileUtils.formatFileSize(Integer.MAX_VALUE));
        System.out.println(FileUtils.formatFileSize(Long.MAX_VALUE));

        System.out.println(FileUtils.getByteNumberFromSizeUnitStr("1MB"));
        System.out.println(FileUtils.getByteNumberFromSizeUnitStr("2KB"));
        System.out.println(FileUtils.getByteNumberFromSizeUnitStr("3.5KB"));
        System.out.println(FileUtils.getByteNumberFromSizeUnitStr("3.5GB"));
        System.out.println(FileUtils.getByteNumberStrFromSizeUnitStr("3.5GB"));
        System.out.println(FileUtils.getByteNumberStrFromSizeUnitStr("3.5KB"));
        System.out.println(FileUtils.getByteNumberStrFromSizeUnitStr("3.KB"));
    }

}
