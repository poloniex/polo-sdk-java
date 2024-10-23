package com.poloniex.api.client.spot.rest.spot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poloniex.api.client.spot.security.AuthenticationRequestInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * PoloApiServiceGenerator使用Retrofit生成poloppublicapiservice和polopprivateapiservice的实例
 */
public class SpotPoloApiServiceGenerator {

 /*   private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    private static final JacksonConverterFactory converterFactory = JacksonConverterFactory.create(objectMapper);

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();*/


    // 创建并配置ObjectMapper实例。ObjectMapper是Jackson库的核心类，用于处理JSON数据的序列化和反序列化。
// setSerializationInclusion方法配置序列化规则，JsonInclude.Include.NON_EMPTY表示序列化时忽略空（null或“”）的属性。
    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    // 使用上面配置好的ObjectMapper创建JacksonConverterFactory实例。
// JacksonConverterFactory是Retrofit用于转换JSON数据的转换器工厂。这里的目的是让Retrofit使用Jackson处理JSON数据。
    private static final JacksonConverterFactory converterFactory = JacksonConverterFactory.create(objectMapper);

    // 创建HttpLoggingInterceptor实例，配置为记录完整的请求和响应体。
// HttpLoggingInterceptor是OkHttp的一个拦截器，用于记录HTTP请求和响应的详细信息，非常有助于开发和调试。
// Level.BODY表示记录请求和响应的头信息和体内容。注意：在生产环境中记录体内容可能会暴露敏感信息。
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    // 创建OkHttpClient实例。
// OkHttpClient是OkHttp库的核心类，用于发送和接收HTTP请求。这里使用默认配置创建实例。
// 实际应用中，你可以通过OkHttpClient.Builder来添加拦截器、设置超时时间、配置缓存等。
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    /**
     * No instances of PoloApiServiceGenerator should be created
     */
    private SpotPoloApiServiceGenerator(){}


    /**
     * Create a Polo Private Api Service
     *
     * @param host host base url
     * @param apiKey Polo API Key for user
     * @param secret Polo secret for API Key
     * @return a new instance of Polo Private Api Service
     * 这段代码展示了如何使用 Retrofit 和 OkHttp 客户端来创建一个配置了身份验证的私有 API 服务。
     * 这个服务可以发送包含了 API 密钥和签名的 HTTP 请求，这些验证信息通常是私有 API 访问所必需的。具体步骤如下：
     *
     * 创建带有身份验证拦截器的 OkHttp 客户端：首先基于一个全局 httpClient 实例创建一个新的 OkHttpClient 实例，
     * 通过添加一个自定义的拦截器（AuthenticationRequestInterceptor），在每个发出的请求中添加身份验证信息（API 密钥和签名
     *
     * 配置 Retrofit.Builder：使用刚刚创建的带有身份验证拦截器的 OkHttp 客户端，初始化 Retrofit.Builder 实例，并设置 API 基础 URL 和转换工厂。
     *
     * 构建 Retrofit 实例并创建 API 服务接口实现：通过 Retrofit.Builder 实例构建 Retrofit 实例，然后使用 Retrofit 实例的 create() 方法，根据 SpotPoloPrivateApiService.class 接口创建一个实例。这个实例可用于发起网络请求，而这些请求都将包含通过拦截器添加的身份验证信息。
     *
     * 通过这种方式，你可以轻松地将身份验证集成到你的网络请求中，保障私有 API 的安全访问。
     */
    public static SpotPoloPrivateApiService createPrivateService(String host, String apiKey, String secret) {
        // 在原有的httpClient基础上创建一个新的OkHttpClient实例，并添加一个拦截器
        // 拦截器用于在每个请求中添加必要的身份验证信息（如API密钥和签名）
        OkHttpClient client = httpClient.newBuilder()
                .addInterceptor(new AuthenticationRequestInterceptor(apiKey, secret)) // 添加自定义拦截器进行身份验证
                .build(); // 构建新的客户端实例

        // 初始化Retrofit.Builder对象，并设置API的基础URL、转换工厂以及上面创建的OkHttpClient客户端
        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(host) // 设置基础URL
                .addConverterFactory(converterFactory) // 添加转换工厂，用于将HTTP响应转换为Java对象
                .client(client); // 使用上面构建的包含身份验证拦截器的客户端

        // 构建Retrofit实例
        final Retrofit retrofit = retrofitBuilder.build();

        // 使用Retrofit动态代理机制创建SpotPoloPrivateApiService接口的实现
        return retrofit.create(SpotPoloPrivateApiService.class);
    }
    /*public static SpotPoloPrivateApiService createPrivateService(String host, String apiKey, String secret) {

        OkHttpClient client = httpClient.newBuilder()
                .addInterceptor(new AuthenticationRequestInterceptor(apiKey, secret))
                .build();

        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(converterFactory)
            .client(client);

        final Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(SpotPoloPrivateApiService.class);
    }*/

    /**
     * Create a Polo Private Api Service
     *
     * @param host host base url
     * @return a new instance of Polo Public Api Service
     * 这段代码演示了如何使用 Retrofit 库来创建一个 API 服务的实例，这个实例允许你将 HTTP 请求映射为 Java 方法调用。这里是具体步骤的详解：
     */
    public static SpotPoloPublicApiService createPublicService(String host) {
        // 初始化 Retrofit.Builder 对象，并设置 API 的基础 URL 地址
        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(host) // 设置基础 URL
                .addConverterFactory(converterFactory) // 添加转换工厂，用于将 HTTP 响应转换为 Java 对象
                .client(httpClient); // 设置 OkHttp 客户端，用于执行网络请求

        // 构建 Retrofit 实例
        final Retrofit retrofit = retrofitBuilder.build();

        // 使用 Retrofit 动态代理机制创建 SpotPoloPublicApiService 接口的实现
        return retrofit.create(SpotPoloPublicApiService.class);
    }
   /* public static SpotPoloPublicApiService createPublicService(String host) {

        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(converterFactory)
                .client(httpClient);

        final Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(SpotPoloPublicApiService.class);
    }*/

}
