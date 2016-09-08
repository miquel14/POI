package com.worldline.data.util.security;

import com.tempos21.t21crypt.crypter.Crypter;
import com.tempos21.t21crypt.factory.CryptMethod;
import com.tempos21.t21crypt.factory.CrypterFactory;

/**
 * TODO: Add your comments
 */
public final class TokenCrypter {

    // TODO Change it for your key token
    private static final String KEY_TOKEN = "t21_cL34N";

    private TokenCrypter() {
    }

    public static String encryptToken(String token) {
        Crypter crypter = CrypterFactory.buildCrypter(CryptMethod.AES256);
        return crypter.encrypt(KEY_TOKEN, token);
    }

    public static String decryptToken(String token) {
        Crypter crypter = CrypterFactory.buildCrypter(CryptMethod.AES256);
        return crypter.decrypt(KEY_TOKEN, token);
    }
}