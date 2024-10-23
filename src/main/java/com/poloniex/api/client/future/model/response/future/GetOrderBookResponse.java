package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetOrderBookResponse {
    private int code;
    private String msg;
    @JsonDeserialize(using = GetOrderBookDeserializer.class) // 使用自定义反序列化器
    private GetOrderBook data=new GetOrderBook();

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetOrderBook{
        private String ts; // Timestamp
        private String s; // Level of market depth
        private List<List<String>> bids;  // 假设 bids 是一个字符串数组的列表
        private List<List<String>> asks;   // 假设 asks 也是一个字符串数组的列表

    }
    // 自定义反序列化器
    public static class GetOrderBookDeserializer extends JsonDeserializer<GetOrderBook> {
        @Override
        public GetOrderBook deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            if (p.getCurrentToken() == JsonToken.VALUE_STRING && p.getText().isEmpty()) {
                return new GetOrderBook(); // 返回一个新的空对象
            }
            return p.readValueAs(GetOrderBook.class); // 正常反序列化
        }
    }

}
