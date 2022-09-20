package com.poloniex.api.client.security;

import com.poloniex.api.client.common.PoloApiException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.poloniex.api.client.common.PoloApiConstants.SIGNATURE_METHOD_VALUE;

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
            SecretKeySpec secKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SIGNATURE_METHOD_VALUE);
            hmacSha256.init(secKey);
        } catch (NoSuchAlgorithmException e) {
            throw new PoloApiException("No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new PoloApiException("Invalid key: " + e.getMessage());
        }
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(hash);
    }
}
