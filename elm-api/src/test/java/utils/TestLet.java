package utils;

import org.junit.Test;

/**
 * Created by wxcsdb88 on 2017/8/17 17:57.
 */
public class TestLet {

    @Test
    public void testLet() {
        System.out.println(String.format("%1$.2f", 5689.0));
        System.out.println(String.format("{} %1$.2f", 5689.0));
    }
}
