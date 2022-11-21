package com.poloniex.api.client.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poloniex.api.client.security.AuthenticationRequestInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * PoloApiServiceGenerator uses Retrofit to generate instances of PoloPublicApiService and PoloPrivateApiService
 */
public class PoloApiServiceGenerator {

    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    private static final JacksonConverterFactory converterFactory = JacksonConverterFactory.create(objectMapper);

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();


    /**
     * No instances of PoloApiServiceGenerator should be created
     */
    private PoloApiServiceGenerator(){}


    /**
     * Create a Polo Private Api Service
     *
     * @param host host base url
     * @param apiKey Polo API Key for user
     * @param secret Polo secret for API Key
     * @return a new instance of Polo Private Api Service
     */
    public static PoloPrivateApiService createPrivateService(String host, String apiKey, String secret) {

        OkHttpClient client = httpClient.newBuilder()
                .addInterceptor(new AuthenticationRequestInterceptor(apiKey, secret))
                .build();

        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(converterFactory)
            .client(client);

        final Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(PoloPrivateApiService.class);
    }

    /**
     * Create a Polo Private Api Service
     *
     * @param host host base url
     * @return a new instance of Polo Public Api Service
     */
    public static PoloPublicApiService createPublicService(String host) {

        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(converterFactory)
                .client(httpClient);

        final Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(PoloPublicApiService.class);
    }

}
