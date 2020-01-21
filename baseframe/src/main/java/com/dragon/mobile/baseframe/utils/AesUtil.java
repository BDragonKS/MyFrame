package com.dragon.mobile.baseframe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.IntDef;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * <dl>  Class Description
 * <dd> 项目名称：BaseFrame
 * <dd> 类名称：AesUtil
 * <dd> 类描述：AES加密工具类，Android P以后系统删除了Crypto Provider,此类已经做了适配
 * <dd> 类描述：需要注意的是此适配在Android P系统上和小于P的系统生成的密文不一致
 * <dd> 创建时间：2017/12/1
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author Jing Lu
 * @version 1.0
 */
public class AesUtil {

    private final static String SHA1_P_RNG = "SHA1PRNG";
    private final static String KEY_SP_SALT = "salt";
    private final static int KEY_Length_128 = 128;
    private final static int KEY_Length_192 = 192;
    private final static int KEY_Length_256 = 256;

    private Context mContext;

    // AES 支持 128、192 和 256 比特长度的密钥
    private int keyLength;

    /**
     * 加密类初始化
     * @param keyLength AES 支持 128、192 和 256 bite长度的密钥
     * @param context 上下文对象，用于将算法生成的salt值保存在SharedPreferences中
     */
    public void init(int keyLength, Context context) {
        this.keyLength = keyLength;
        this.mContext = context;
    }


    /**
     * Aes加密/解密
     * 已经适配Android P
     *
     * @param content  字符串
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     * @return 加密/解密结果字符串
     */
    @SuppressLint({"DeletedProvider", "GetInstance"})
    public String des(String content, String password, @AESType int type) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(password)) {
            return null;
        }
        try {
            SecretKeySpec secretKeySpec;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                secretKeySpec = deriveKeyInsecurely(password);
            } else {
                secretKeySpec = fixSmallVersion(password);
            }
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(type, secretKeySpec);
            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
                return parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = parseHexStr2Byte(content);
                return new String(cipher.doFinal(byteContent));
            }
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("DeletedProvider")
    private SecretKeySpec fixSmallVersion(String password) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            secureRandom = SecureRandom.getInstance(SHA1_P_RNG, new CryptoProvider());
        } else {
            secureRandom = SecureRandom.getInstance(SHA1_P_RNG, "Crypto");
        }
        secureRandom.setSeed(password.getBytes());
        generator.init(keyLength, secureRandom);
        byte[] enCodeFormat = generator.generateKey().getEncoded();
        return new SecretKeySpec(enCodeFormat, "AES");
    }

    /**
     * 由于Google官方InsecureSHA1PRNGKeyDerivator这个类无法下载，所以需要自己实现功能
     *
     * @param password 密钥
     * @return 返回SecretKeySpec类
     */
    /*private SecretKeySpec deriveKeyInsecurely(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        // 盐值的字节数组长度，注意这里是字节数组的长度
        // 其长度值需要和最终输出的密钥字节数组长度一致
        // 由于这里密钥的长度是 256 比特，则最终密钥将以 256/8 = 32 位长度的字节数组存在
        // 所以盐值的字节数组长度也应该是 32
        int saltLength = keyLength / 8;

        // 你需要将此次生成的盐值保存到磁盘上下次再从字符串换算密钥时传入
        // 如果盐值不一致将导致换算的密钥值不同
        // 保存密钥的逻辑官方并没写，需要自行实现
        //自行实现将salt的值以String的形式保存在SharedPreferences中
        byte[] mSaltCache;
        String lSaveStr = SpUtil.getInstance(mContext).getSpString(KEY_SP_SALT, "");
        if (TextUtils.isEmpty(lSaveStr)) {
            //生成salt
            byte[] salt = new byte[saltLength]; // Should be of saltLength
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);
            //将salt以String的形式保存在SharedPreferences中
            SpUtil.getInstance(mContext).putSpString(KEY_SP_SALT, toStr(salt));
            mSaltCache = salt;
        } else {
            mSaltCache = toArr(lSaveStr);
        }

        // 将密码明文、盐值等使用新的方法换算密钥
        int iterationCount = 1000;
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), mSaltCache, iterationCount, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }*/

    private  SecretKeySpec deriveKeyInsecurely(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(passwordBytes, keyLength), "AES");
    }

    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    @IntDef({Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE})
    @interface AESType {
    }

    private static final class CryptoProvider extends Provider {
        CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }

    private String toStr(byte[] aBytes) {
        return toStr(aBytes, null);
    }

    private String toStr(byte[] aBytes, String aSplit) {
        if (aBytes == null) return null;
        int lLength = aBytes.length;
        if (lLength <= 0) return "";
        if (TextUtils.isEmpty(aSplit)) aSplit = ",";
        StringBuilder lSB = new StringBuilder();
        for (int i = 0; i < lLength; i++) {
            lSB.append(aBytes[i]);
            if (i != lLength - 1) lSB.append(aSplit);
        }
        return lSB.toString();
    }

    private byte[] toArr(String aStr) {
        return toArr(aStr, null, (byte) 0);
    }

    private byte[] toArr(String aStr, String aSplit, byte aDefaultValue) {
        if (TextUtils.isEmpty(aStr)) return null;
        if (TextUtils.isEmpty(aSplit)) aSplit = ",";
        String[] lItems = aStr.split(aSplit);
        int lLength = lItems.length;
        byte[] lResult = new byte[lLength];
        for (int i = 0; i < lLength; i++) {
            try {
                lResult[i] = Byte.parseByte(lItems[i]);
            } catch (Exception e) {
                lResult[i] = aDefaultValue;
            }
        }
        return lResult;
    }
}
