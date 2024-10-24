package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMarkPriceResponse {
    private int code;
    private String msg;
    // 使用自定义反序列化器

    // 使用自定义反序列化器
    @JsonDeserialize(using = GetMarkPriceDeserializer.class)
    private GetMarkPrice data=new GetMarkPrice();

    // 自定义反序列化器处理 GetMarkPrice
    public static class GetMarkPriceDeserializer extends JsonDeserializer<GetMarkPrice> {
        @Override
        public GetMarkPrice deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return p.readValueAs(GetMarkPrice.class);
        }
    }


    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetMarkPrice {
        private String s;
        private String mPx;


    }
}
