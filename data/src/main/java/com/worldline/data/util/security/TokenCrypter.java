package com.worldline.data.util.security;


import com.tempos21.t21crypt.crypter.Crypter;
import com.tempos21.t21crypt.exception.CrypterException;
import com.tempos21.t21crypt.exception.DecrypterException;
import com.tempos21.t21crypt.exception.EncrypterException;
import com.tempos21.t21crypt.factory.CryptMethod;
import com.tempos21.t21crypt.factory.CrypterFactory;
import com.worldline.data.util.DeviceUtil;

import android.content.Context;
import android.text.TextUtils;

/**
 * TODO: Add your comments. This is just an example
 */
public final class TokenCrypter {

    // TODO Change it for your key token
    private static final String KEY_TOKEN = "t21_cL34N";

    private TokenCrypter() {
    }

    public String encrypt(Context context, String initialText) throws CrypterException, EncrypterException {
        Crypter crypter = CrypterFactory.buildCrypter(CryptMethod.AES256, getCipherKey(context));
        return crypter.encrypt(initialText);
    }

    public String decrypt(Context context, String cipherText) throws DecrypterException, CrypterException {
        Crypter crypter = CrypterFactory.buildCrypter(CryptMethod.AES256, getCipherKey(context));
        return crypter.decrypt(cipherText);
    }

    private String getCipherKey(Context context) {
        String android_id = DeviceUtil.getDeviceUniqueId(context);
        return !TextUtils.isEmpty(android_id) ? android_id : KEY_TOKEN;
    }
}