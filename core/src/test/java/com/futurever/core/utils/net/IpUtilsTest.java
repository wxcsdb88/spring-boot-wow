package com.futurever.core.utils.net;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author: wxcsdb88
 * @since: 2017/10/13 18:09
 */
public class IpUtilsTest {

    private static List<IpNetwork> ipNetworks = new ArrayList<>();

    @Before
    public void init() {
        ipNetworks.add(IpNetworkFactory.getFromString("2001:da8:8000:1:202:120:2:100"));
        ipNetworks.add(IpNetworkFactory.getFromString("172.168.1.103"));
        ipNetworks.add(IpNetworkFactory.getFromString("211.102.192.66"));
        ipNetworks.add(IpNetworkFactory.getFromString("172.168.1.103"));
        ipNetworks.add(IpNetworkFactory.getFromString("2001:da8:202:10::36"));
        ipNetworks.add(IpNetworkFactory.getFromString("2001:470:20::2"));
        ipNetworks.add(IpNetworkFactory.getFromString("74.82.42.42"));
        System.out.println("init end");
    }

    @Test
    public void testIpConvert() throws Exception {
        ipNetworks.parallelStream().forEach(ipNetwork -> {
            String ip = ipNetwork.getAddress();
            int version = ipNetwork.getVersion();
            if (version == 4) {
                long longIp = IpUtils.ipv4ToLong(ip);
                System.out.println(String.format("ip=%s, version=%d, val=%d, orignIp=%s, ipv4Str=%s, ipv6Str=%s", ip, version, longIp, IpUtils.longToIpv4(longIp), IpUtils.getIpStr(longIp, 4), IpUtils.getIpStr(longIp, 6)));
            } else {
                BigInteger bigIntegerIp = IpUtils.ipv6toBigInt(ip);
                System.out.println(String.format("ip=%s, version=%d, val=%d, orignIp=%s", ip, version, bigIntegerIp, IpUtils.bigIntToIpv6(bigIntegerIp)));
            }
        });
        System.out.println("testIpConvert end");
    }

    @Test
    public void testIpCompare() throws Exception {
        String ip0 = ipNetworks.get(0).getAddress();
        String ip1 = ipNetworks.get(1).getAddress();
        String ip2 = ipNetworks.get(2).getAddress();
        String ip3 = ipNetworks.get(3).getAddress();
        String ip4 = ipNetworks.get(4).getAddress();
        String ip5 = ipNetworks.get(5).getAddress();
        String ip6 = ipNetworks.get(6).getAddress();

        System.out.println(String.format("ip%d(%s) > ip%d(%s) ? %d", 0, ip0, 4, ip4, IpUtils.greatThan(ip0, ip4)));
        System.out.println(String.format("ip%d(%s) > ip%d(%s) ? %d", 0, ip0, 5, ip5, IpUtils.greatThan(ip0, ip5)));
        System.out.println(String.format("ip%d(%s) > ip%d(%s) ? %d", 1, ip1, 2, ip2, IpUtils.greatThan(ip1, ip2)));
        System.out.println(String.format("ip%d(%s) > ip%d(%s) ? %d", 1, ip1, 3, ip3, IpUtils.greatThan(ip1, ip3)));
        System.out.println(String.format("ip%d(%s) > ip%d(%s) ? %d", 1, ip1, 6, ip6, IpUtils.greatThan(ip1, ip6)));
        System.out.println("testIpCompare end");
    }
}
