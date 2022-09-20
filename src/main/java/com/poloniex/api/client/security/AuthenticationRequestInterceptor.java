package com.poloniex.api.client.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static com.poloniex.api.client.common.PoloApiConstants.*;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * The Authentication request interceptor intercepts and adds signatures to requests for authenticated rest endpoints
 */
@AllArgsConstructor
@Slf4j
public class AuthenticationRequestInterceptor implements Interceptor {

    private String apiKey;
    private String secret;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder newRequestBuilder = original.newBuilder();

        if(isNotEmpty(apiKey) && isNotEmpty(secret)) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String signaturePayload = generateSignaturePayload(original.method(), original.url(), timestamp, original.body());
            String signature = SignatureGenerator.generateSignature(secret, signaturePayload);

            newRequestBuilder
                    .addHeader(HEADER_KEY, apiKey)
                    .addHeader(HEADER_TIMESTAMP, timestamp)
                    .addHeader(HEADER_SIGNATURE, signature);
        }

        return chain.proceed(newRequestBuilder.build());
    }

    /**
     * Generate signature payload string.
     *
     * @param method    http method
     * @param url       url
     * @param timestamp timestamp
     * @param body      request body
     * @return signature payload
     * @throws IOException io exception from invalid body
     */
    public String generateSignaturePayload(String method, HttpUrl url, String timestamp, RequestBody body) throws IOException {

        TreeMap<String, Object> sortedMap = new TreeMap<>();

        url.queryParameterNames().forEach(qp -> sortedMap.put(qp, url.queryParameterValues(qp).get(0)));

        if(nonNull(body) && body.contentLength() > 0) {
            final Buffer buffer = new Buffer();
            body.writeTo(buffer);
            sortedMap.put(REQUEST_BODY, buffer.readUtf8());
        }

        sortedMap.put(HEADER_TIMESTAMP, timestamp);

        StringBuilder payloadBuffer = new StringBuilder();
        payloadBuffer.append(method).append("\n")
                .append(url.encodedPath()).append("\n");

        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            payloadBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return payloadBuffer.substring(0, payloadBuffer.length() - 1);
    }
}
