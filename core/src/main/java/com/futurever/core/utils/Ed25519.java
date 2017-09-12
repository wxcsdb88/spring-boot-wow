package com.futurever.core.utils;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-12 22:23
 **/

import java.util.Arrays;

/**
 * Description:
 * Ed25519
 * @author wxcsdb88
 * @since 2017-09-12 22:23
 **/
public class Ed25519 {
    private final static int CRYPTO_SIGN_PUBLICKEYBYTES = 32;
    private final static int CRYPTO_SIGN_SECRETKEYBYTES = 64;
    private final static int CRYPTO_SIGN_BYTES = 64;

    public static KeyPair generateKeyPair() {
        byte[] pk = new byte[CRYPTO_SIGN_PUBLICKEYBYTES];
        byte[] skTemp = new byte[CRYPTO_SIGN_SECRETKEYBYTES];
        TweetNaCl.crypto_sign_keypair(pk, skTemp, false);
        byte[] sk = Arrays.copyOf(skTemp, CRYPTO_SIGN_PUBLICKEYBYTES);
        return new KeyPair(Base58.encode(pk), Base58.encode(sk));
    }

    public static String getPublicKeyByPrivateKey(String privateKey) {
        byte[] sk = Base58.decode(privateKey);
        KeyPair keyPair = generateKeyPairFromSeed(sk);
        return keyPair.getPublicKey();
    }

    private static KeyPair generateKeyPairFromSeed(byte[] seed) {
        byte[] pk = new byte[CRYPTO_SIGN_PUBLICKEYBYTES];
        byte[] sk = new byte[CRYPTO_SIGN_SECRETKEYBYTES];
        for (int i = 0; i < CRYPTO_SIGN_PUBLICKEYBYTES; i++) {
            sk[i] = seed[i];
        }
        TweetNaCl.crypto_sign_keypair(pk, sk, true);
        return new KeyPair(Base58.encode(pk), Base58.encode(sk));
    }


    public static String sign(String privateKey, String msg) {
        byte[] seed = Base58.decode(privateKey);
        byte[] pk = new byte[CRYPTO_SIGN_PUBLICKEYBYTES];
        byte[] sk = new byte[CRYPTO_SIGN_SECRETKEYBYTES];
        for (int i = 0; i < CRYPTO_SIGN_PUBLICKEYBYTES; i++) {
            sk[i] = seed[i];
        }
        TweetNaCl.crypto_sign_keypair(pk, sk, true);
        byte[] msgByteArr = msg.getBytes();
        byte[] sigByteArrTemp = TweetNaCl.crypto_sign(msgByteArr, sk);
        byte[] sig = Arrays.copyOf(sigByteArrTemp, CRYPTO_SIGN_BYTES);
        return Base58.encode(sig);
    }

    public static boolean verify(String msg, String sig, String publicKey) {
        byte[] sigByteArr = Base58.decode(sig);
        byte[] msgByteArr = msg.getBytes();
        byte[] pk = Base58.decode(publicKey);
        return TweetNaCl.crypto_sign_verify(msgByteArr, sigByteArr, pk);
    }

    static class KeyPair {
        private String publicKey;
        private String privateKey;

        KeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }
    }

}
