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
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMarkPriceK_LineResponse {
    private int code;
    private String msg;
    @JsonDeserialize(using = GetMarkPriceK_LineListDeserializer.class)
    private List<GetMarkPriceK_Line> data=new ArrayList<>(); // 更改为 List，以存储多个价格数据


    // 将 List<List<String>> 转换为 List<GetMarkPriceK_Line>
    /*public void setData(List<List<String>> rawData) {
        this.data = new ArrayList<>();
        for (List<String> entry : rawData) {
            if (entry.size() == 6) { // 确保每个条目都有正确的数量
                GetMarkPriceK_Line lineData = new GetMarkPriceK_Line();
                lineData.setO(entry.get(0)); // Opening price
                lineData.setH(entry.get(1)); // Highest price
                lineData.setL(entry.get(2)); // Lowest price
                lineData.setC(entry.get(3)); // Closing price
                lineData.setST(Long.parseLong(entry.get(4))); // Start time
                lineData.setCT(Long.parseLong(entry.get(5))); // End time
                this.data.add(lineData);
            }
        }
    }*/
    // 自定义反序列化器处理 List<GetK_lineData>
    public static class GetMarkPriceK_LineListDeserializer extends JsonDeserializer<List<GetMarkPriceK_Line>> {
        @Override
        public List<GetMarkPriceK_Line> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            List<GetMarkPriceK_Line> list = new ArrayList<>();
            // 处理数组开始
            if (p.isExpectedStartArrayToken()) {
                while (p.nextToken() != com.fasterxml.jackson.core.JsonToken.END_ARRAY) {
                    // 读取每个子数组作为 GetK_lineData 对象
                    List<String> values = p.readValueAs(List.class);
                    if (values.size() >= 9) { // 确保子数组长度符合预期
                        GetMarkPriceK_Line item = new GetMarkPriceK_Line();
                        item.setO(values.get(0)); // Lowest price
                        item.setH(values.get(1)); // Highest price
                        item.setL(values.get(2)); // Opening price
                        item.setC(values.get(3)); // Closing price
                        item.setST(Long.valueOf(values.get(4))); // Trading unit, quantity of quote currency
                        item.setCT(Long.valueOf(values.get(5))); // Trading unit, quantity of base currency

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
    public static class GetMarkPriceK_Line {
        private String o; // Opening price
        private String h; // Highest price
        private String l; // Lowest price
        private String c; // Closing price
        private Long sT; // Start time in UNIX timestamp format
        private Long cT; // End time in UNIX timestamp format
    }
}
