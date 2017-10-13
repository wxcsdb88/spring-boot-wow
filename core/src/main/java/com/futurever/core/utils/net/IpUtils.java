package com.futurever.core.utils.net;

import java.math.BigInteger;

/**
 * description:
 *
 * @author: wxcsdb88
 * @since: 2017/10/13 09:58
 */
public class IpUtils {

    public static long ipv4ToLong(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            // 1. 192 * 256^3
            // 2. 168 * 256^2
            // 3. 1 * 256^1
            // 4. 2 * 256^0
            result += ip * Math.pow(256, power);
        }
        return result;
    }

    public static String longToIpv4(long i) {
        return ((i >> 24) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + (i & 0xFF);
    }

    public static BigInteger ipv6toBigInt(String ipv6) {
        int compressIndex = ipv6.indexOf("::");
        if (compressIndex != -1) {
            String part1s = ipv6.substring(0, compressIndex);
            String part2s = ipv6.substring(compressIndex + 1);
            BigInteger part1 = ipv6toBigInt(part1s);
            BigInteger part2 = ipv6toBigInt(part2s);
            int part1hasDot = 0;
            char ch[] = part1s.toCharArray();
            for (char c : ch) {
                if (c == ':') {
                    part1hasDot++;
                }
            }
            // ipv6 has most 7 dot
            return part1.shiftLeft(16 * (7 - part1hasDot)).add(part2);
        }
        String[] str = ipv6.split(":");
        BigInteger big = BigInteger.ZERO;
        for (int i = 0; i < str.length; i++) {
            //::1
            if (str[i].isEmpty()) {
                str[i] = "0";
            }
            big = big.add(BigInteger.valueOf(Long.valueOf(str[i], 16))
                    .shiftLeft(16 * (str.length - i - 1)));
        }
        return big;
    }

    public static String bigIntToIpv6(BigInteger big) {
        String str = "";
        BigInteger ff = BigInteger.valueOf(0xffff);
        for (int i = 0; i < 8; i++) {
            str = big.and(ff).toString(16) + ":" + str;

            big = big.shiftRight(16);
        }
        //the last :
        str = str.substring(0, str.length() - 1);
        return str.replaceFirst("(^|:)(0+(:|$)){2,8}", "::");
    }

    public static String getIpStr(long ipv4) {
        return getIpStr(ipv4, 4);
    }

    public static String getIpStr(long ipv4, int version) {
        if (version == 4) {
            return longToIpv4(ipv4);
        } else {
            return bigIntToIpv6(BigInteger.valueOf(ipv4));
        }
    }

    public static String getIpStr(BigInteger ipv6) {
        return bigIntToIpv6(ipv6);
    }

    /**
     * @param ip1
     * @param ip2
     * @return -1, 0 or 1 as this BigInteger is numerically less than, equal
     * to, or greater than {@code val}.
     * @throws Exception
     */
    public static int greatThan(String ip1, String ip2) throws Exception {
        if (ip1 == null || ip2 == null || "".equals(ip1) || "".equals(ip2)) {
            throw new Exception("ip should not be blank!");
        }
        IpNetwork ipNetwork1 = IpNetworkFactory.getFromString(ip1);
        IpNetwork ipNetwork2 = IpNetworkFactory.getFromString(ip2);

        if (ipNetwork1 == null || ipNetwork2 == null || ipNetwork1.getVersion() != ipNetwork2.getVersion()) {
            throw new Exception("ip version should be equal!");
        }
        if (ipNetwork1.getVersion() == 4) {
            long ipv41 = ipv4ToLong(ipNetwork1.getAddress());
            long ipv42 = ipv4ToLong(ipNetwork2.getAddress());
            if (ipv41 - ipv42 == 0) {
                return 0;
            }
            return ipv41 - ipv42 > 0 ? 1 : -1;
        } else {
            BigInteger ipv61 = ipv6toBigInt(ip1);
            BigInteger ipv62 = ipv6toBigInt(ip2);
            return ipv61.compareTo(ipv62);
        }
    }

}
