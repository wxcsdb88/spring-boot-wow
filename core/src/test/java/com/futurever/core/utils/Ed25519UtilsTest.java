package com.futurever.core.utils;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-12 22:24
 **/

import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * TODO
 * @author wxcsdb88
 * @since 2017-09-12 22:24
 **/
public class Ed25519UtilsTest {

    @Test
    public void generateKeyPairTest() {
        Ed25519.KeyPair keyPair = Ed25519.generateKeyPair();
        System.out.println("pub: " + keyPair.getPublicKey());
        System.out.println("pri: " + keyPair.getPrivateKey());
    }

    @Test
    public void signTest() {
//        String pub = "HeY6gTnaiknMBmJbwT8hUooWiKgKuarbh9nPcciEfTYk";
        String pri = "5sv7WkrvfqBXjabuWsYtgnFkJqvKmHEeABKRfCw2rm3J";
        String msg = "hello java 2017";
        String sig = Ed25519.sign(pri, msg);
        System.out.println(sig);
    }

    @Test
    public void verifyTest() {
        String pub = "HeY6gTnaiknMBmJbwT8hUooWiKgKuarbh9nPcciEfTYk";
//        String pri = "5sv7WkrvfqBXjabuWsYtgnFkJqvKmHEeABKRfCw2rm3J";
        String msg = "hello java 2017";
        String sig = "3d3B9Yth44XfP4beFETbZNvmEkw3WZfMFkUpyp3om65fhc4dAmSNNMrJrov2zVMqeSgqTk5myirNtPg8som3Wxxo";
        boolean isOk = Ed25519.verify(msg, sig, pub);
        Assert.assertTrue(isOk);
    }
}
