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
public class GetPremiumIndexK_lineDataResponse {
    private int code;
    private String msg;
    // 使用自定义反序列化器
    @JsonDeserialize(using = GetPremiumIndexK_lineDataDeserializer.class)
    private List<GetPremiumIndexK_lineData> data=new ArrayList<>();

    // 自定义反序列化器处理 List<GetPremiumIndexK_lineData>
    public static class GetPremiumIndexK_lineDataDeserializer extends JsonDeserializer<List<GetPremiumIndexK_lineData>> {
        @Override
        public List<GetPremiumIndexK_lineData> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            List<GetPremiumIndexK_lineData> list = new ArrayList<>();

            // 解析数组开始
            if (p.isExpectedStartArrayToken()) {
                while (p.nextToken() != com.fasterxml.jackson.core.JsonToken.END_ARRAY) {
                    // 读取每个子数组并转换为 GetPremiumIndexK_lineData 对象
                    List<String> values = p.readValueAs(List.class);
                    if (values.size() == 6) { // 确保子数组长度符合预期
                        GetPremiumIndexK_lineData item = new GetPremiumIndexK_lineData();
                        item.setO(values.get(0)); // Opening price
                        item.setH(values.get(1)); // Highest price
                        item.setL(values.get(2)); // Lowest price
                        item.setC(values.get(3)); // Closing price
                        item.setST(values.get(4)); // Start time
                        item.setCT(values.get(5)); // End time
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
    public static class GetPremiumIndexK_lineData {
        private String o; // Opening price
        private String h; // Highest price
        private String l; // Lowest price
        private String c; // Closing price
        private String sT; // Start time in UNIX timestamp format
        private String cT; // End time in UNIX timestamp format
    }
}
