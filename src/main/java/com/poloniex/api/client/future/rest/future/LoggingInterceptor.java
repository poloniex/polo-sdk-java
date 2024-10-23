package com.poloniex.api.client.future.rest.future;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // 打印请求的信息
        System.out.println("Sending request to URL: " + request.url()+ request.method()+ request.headers());
        // 打印请求参数（如有）
        if (request.body() != null) {
            // 这里的请求体可能需要更复杂的处理，如果是表单或JSON体
            // 您可以将请求体转换为字符串（如果可以的话），并打印
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            System.out.println("Request body: " + buffer.readUtf8());
        }

        // 继续请求
        return chain.proceed(request);
    }
}
