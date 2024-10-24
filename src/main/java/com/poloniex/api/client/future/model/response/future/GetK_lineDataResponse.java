package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetK_lineDataResponse {
    private int code;
    private String msg;
    // 使用自定义反序列化器
    // 使用自定义反序列化器
    @JsonDeserialize(using = GetK_lineDataListDeserializer.class)
    private List<GetK_lineData> data=new ArrayList<>();

    // 自定义反序列化器处理 List<GetK_lineData>
    public static class GetK_lineDataListDeserializer extends JsonDeserializer<List<GetK_lineData>> {
        @Override
        public List<GetK_lineData> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            List<GetK_lineData> list = new ArrayList<>();
            // 处理数组开始
            if (p.isExpectedStartArrayToken()) {
                while (p.nextToken() != com.fasterxml.jackson.core.JsonToken.END_ARRAY) {
                    // 读取每个子数组作为 GetK_lineData 对象
                    List<String> values = p.readValueAs(List.class);
                    if (values.size() >= 9) { // 确保子数组长度符合预期
                        GetK_lineData item = new GetK_lineData();
                        item.setL(values.get(0)); // Lowest price
                        item.setH(values.get(1)); // Highest price
                        item.setO(values.get(2)); // Opening price
                        item.setC(values.get(3)); // Closing price
                        item.setAmt(values.get(4)); // Trading unit, quantity of quote currency
                        item.setQty(values.get(5)); // Trading unit, quantity of base currency
                        item.setTC(values.get(6)); // Trades count
                        item.setST(values.get(7)); // Start time
                        item.setCT(values.get(8)); // End time
                        list.add(item);
                    }
                }
            }
            return list;
        }
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class GetK_lineData{
        private String l; // Lowest price
        private String h; // Highest price
        private String o; // Opening price
        private String c; // Closing price
        private String amt; // Trading unit, the quantity of the quote currency
        private String qty; // Trading unit, the quantity of the base currency, or Cont for the number of contracts
        private String tC; // Trades count
        private String sT; // Start time
        private String cT; // End time
    }


}
