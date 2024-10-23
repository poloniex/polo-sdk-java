package com.poloniex.api.client.spot.security;

import com.poloniex.api.client.spot.common.PoloApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * The Authentication request interceptor intercepts and adds signatures to requests for authenticated rest endpoints
 * 这段代码是一个OkHttp的拦截器方法，主要用于在发送请求前自动添加API密钥、当前时间戳和请求签名到请求头中，以实现请求的认证。
 * 此方法首先检查apiKey和secret是否非空，然后生成时间戳和签名负载，使用提供的secret对负载进行签名。
 * 最后，将API密钥、时间戳和签名作为请求头添加到新的请求构造器中，并发送这个新构造的请求。
 */
@AllArgsConstructor
@Slf4j
public class AuthenticationRequestInterceptor implements Interceptor {

    private String apiKey;
    private String secret;

    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request original = chain.request();
//        Request.Builder newRequestBuilder = original.newBuilder();
//
//        if(isNotEmpty(apiKey) && isNotEmpty(secret)) {
//            String timestamp = String.valueOf(System.currentTimeMillis());
//            String signaturePayload = generateSignaturePayload(original.method(), original.url(), timestamp, original.body());
//            String signature = SignatureGenerator.generateSignature(secret, signaturePayload);
//
//            newRequestBuilder
//                    .addHeader(HEADER_KEY, apiKey)
//                    .addHeader(HEADER_TIMESTAMP, timestamp)
//                    .addHeader(HEADER_SIGNATURE, signature);
//        }
//
//        return chain.proceed(newRequestBuilder.build());
//    }
    public Response intercept(Chain chain) throws IOException { // 定义拦截器方法，用于处理传入的请求并返回响应
        Request original = chain.request(); // 获取原始请求
        Request.Builder newRequestBuilder = original.newBuilder(); // 创建一个新的请求构造器，基于原始请求

        if(isNotEmpty(apiKey) && isNotEmpty(secret)) { // 检查 apiKey 和 secret 是否非空
            String timestamp = String.valueOf(System.currentTimeMillis()); // 获取当前时间戳
            // 生成签名负载，包括原始请求的方法、URL、时间戳和请求体
            String signaturePayload = generateSignaturePayload(original.method(), original.url(), timestamp, original.body());
            // 使用 secret 和签名负载生成签名
            String signature = SignatureGenerator.generateSignature(secret, signaturePayload);

            // 在新请求构造器中添加认证相关的头信息：API 密钥、时间戳和签名
            newRequestBuilder
                    .addHeader(PoloApiConstants.HEADER_KEY, apiKey) // 添加 API 密钥头
                    .addHeader(PoloApiConstants.HEADER_TIMESTAMP, timestamp) // 添加时间戳头
                    .addHeader(PoloApiConstants.HEADER_SIGNATURE, signature); // 添加签名头
        }

        // 使用新构造的请求（添加了认证头）继续请求链，并返回响应
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
     * 这段代码主要功能是构建用于生成请求签名的负载字符串。它首先通过 TreeMap 的排序特性确保所有参与签名的参数
     * （包括 URL 的查询参数、请求体内容（如果有）和时间戳）都是按照字典序排列的，从而保证签名的一致性和可验证性。
     * 然后，它将这些参数拼接成一个特定格式的字符串，这个字符串将被用作后续的签名计算。
     * 这种方法是保护 API 安全的常见做法，通过确保请求未被篡改且来自认证的来源，可以有效防止恶意攻击。
     */
    public String generateSignaturePayload(String method, HttpUrl url, String timestamp, RequestBody body) throws IOException {
        // 使用 TreeMap 来自动排序参数，确保签名的一致性
        TreeMap<String, Object> sortedMap = new TreeMap<>();

        // 遍历并存储 URL 的查询参数，确保它们是有序的
        url.queryParameterNames().forEach(qp -> sortedMap.put(qp, url.queryParameterValues(qp).get(0)));

        // 如果请求体不为空且内容长度大于0，则读取其内容并添加到排序映射中
        if(nonNull(body) && body.contentLength() > 0) {
            final Buffer buffer = new Buffer();
            body.writeTo(buffer); // 将请求体内容写入 Buffer
            sortedMap.put(PoloApiConstants.REQUEST_BODY, buffer.readUtf8()); // 从 Buffer 中读取 UTF-8 编码的内容，并存储
        }

        // 将时间戳添加到排序映射中
        sortedMap.put(PoloApiConstants.HEADER_TIMESTAMP, timestamp);

        // 使用 StringBuilder 构建最终的签名负载
        StringBuilder payloadBuffer = new StringBuilder();
        payloadBuffer.append(method).append("\n") // 添加 HTTP 方法
                .append(url.encodedPath()).append("\n"); // 添加经过编码的 URL 路径

        // 遍历排序映射，将所有参数以 “键=值&” 的格式添加到负载字符串中
        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            payloadBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        // 返回负载字符串，移除最后一个多余的 “&”
        return payloadBuffer.substring(0, payloadBuffer.length() - 1);
    }
    /*public String generateSignaturePayload(String method, HttpUrl url, String timestamp, RequestBody body) throws IOException {

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
    }*/
}
