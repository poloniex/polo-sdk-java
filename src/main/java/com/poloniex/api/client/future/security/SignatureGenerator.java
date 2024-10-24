package com.poloniex.api.client.future.security;

import com.poloniex.api.client.future.common.PoloApiException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.poloniex.api.client.future.common.PoloApiConstants.SIGNATURE_METHOD_VALUE;

/**
 * Generator for signatures to authenticate Rest and websocket calls
 */
public class SignatureGenerator {
    /**
     * Generate signature string.
     *
     * @param secret  the account's API key secret
     * @param payload the payload to encrypt
     * @return signature
     */
    public static String generateSignature(String secret, String payload) {
        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance(SIGNATURE_METHOD_VALUE);
//            这行声明了一个Mac（Message Authentication Code）类型的变量hmacSha256，但没有初始化。Mac类是用于生成消息认证码的类。

            SecretKeySpec secKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SIGNATURE_METHOD_VALUE);
//            这行代码尝试获取一个Mac实例，使用SIGNATURE_METHOD_VALUE作为参数。这里假设SIGNATURE_METHOD_VALUE是一个静态常量，虽然未在代码中显示定义，但我们可以推断其值应该是”HmacSHA256”，表示使用的是HMAC SHA-256算法。

            hmacSha256.init(secKey);
//            这行代码用前面创建的密钥初始化hmacSha256对象。
        } catch (NoSuchAlgorithmException e) {
            throw new PoloApiException("No such algorithm: " + e.getMessage());
//            如果捕获到NoSuchAlgorithmException，这行代码将抛出一个自定义的异常PoloApiException，并附带错误消息。

        } catch (InvalidKeyException e) {
            throw new PoloApiException("Invalid key: " + e.getMessage());
        }
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
//            这行代码将payload字符串转换为UTF-8编码的字节序列，然后使用hmacSha256对象对其进行最终的消息认证码计算，结果保存在字节数组hash中。

        return Base64.getEncoder().encodeToString(hash);
//        最后，这行代码将hash数组编码为Base64字符串，并将其返回。Base64编码常用于将二进制数据转换为ASCII字符串。
    }
}
