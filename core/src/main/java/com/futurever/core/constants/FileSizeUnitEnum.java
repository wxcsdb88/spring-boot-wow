package com.futurever.core.constants;

import com.google.common.base.MoreObjects;

/**
 * description:
 *
 * @author: wxcsdb88
 * @since: 2017/10/12 15:56
 */
public enum FileSizeUnitEnum {
    /**
     * 1B (byte 字节)；
     * 1KB(Kilobyte 千字节) = 2^10 B = 1024 B；
     * 1MB(Megabyte 兆字节) = 2^10 KB = 1024 KB = 2^20 B；
     * 1GB(Gigabyte 吉字节) = 2^10 MB = 1024 MB = 2^30 B；
     * 1TB(Trillionbyte 太字节) = 2^10 GB = 1024 GB = 2^40 B；
     * 1PB(Petabyte 拍字节) = 2^10 TB = 1024 TB = 2^50 B；
     * 1EB(Exabyte 艾字节) = 2^10 PB = 1024 PB = 2^60 B；
     * 1ZB(Zettabyte 泽(Z)字节) = 2^10 EB = 1024 EB = 2^70 B；
     * 1YB(YottaByte 尧(Y)字节) = 2^10 ZB = 1024 ZB = 2^80 B；
     * 1BB(Brontobyte ) = 2^10 YB = 1024 YB = 2^90 B；
     * 1NB(NonaByte ) = 2^10 BB = 1024 BB = 2^100 B；
     * 1DB(DoggaByte) = 2^10 NB = 1024 NB = 2^110 B；
     */
    B(1, "B", "字节"),
    KB(1 << 10, "KB", "千字节"),
    MB(1 << 20, "MB", "兆字节"),
    GB(1 << 30, "GB", "吉字节"),
    TB((long) Math.pow(2.0, 40.0), "TB", "太字节"),
    PB((long) Math.pow(2.0, 50.0), "PB", "拍字节"),
    EB((long) Math.pow(2.0, 60.0), "EB", "艾字节"),
    ZB((long) Math.pow(2.0, 70.0), "ZB", "Z字节"),
    YB((long) Math.pow(2.0, 80.0), "YB", "Y字节");

    private long size = 0L;
    private String unit;
    private String unitCh;

    FileSizeUnitEnum(long size, String unit, String unitCh) {
        this.size = size;
        this.unit = unit;
        this.unitCh = unitCh;
    }

    public static FileSizeUnitEnum sizeOf(long size) {
        FileSizeUnitEnum[] fileSizeUnits = FileSizeUnitEnum.values();
        for (FileSizeUnitEnum fileSizeUnit : fileSizeUnits) {
            if (fileSizeUnit.size == size) return fileSizeUnit;
        }
        return null;
    }

    public static long sizeOfUnit(String unit) {
        FileSizeUnitEnum[] fileSizeUnits = FileSizeUnitEnum.values();
        for (FileSizeUnitEnum fileSizeUnit : fileSizeUnits) {
            if (fileSizeUnit.unit.equals(unit)) return fileSizeUnit.size;
        }
        return -1L;
    }

    public static FileSizeUnitEnum unitOf(String unit) {
        FileSizeUnitEnum[] fileSizeUnits = FileSizeUnitEnum.values();
        for (FileSizeUnitEnum fileSizeUnit : fileSizeUnits) {
            if (fileSizeUnit.unit.equals(unit)) return fileSizeUnit;
        }
        return null;
    }

    public static FileSizeUnitEnum unitChOf(String unitCh) {
        FileSizeUnitEnum[] fileSizeUnits = FileSizeUnitEnum.values();
        for (FileSizeUnitEnum fileSizeUnit : fileSizeUnits) {
            if (fileSizeUnit.unitCh.equals(unitCh)) return fileSizeUnit;
        }
        return null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("unit", unit)
                .add("unitCh", unitCh)
                .toString();
    }
}
