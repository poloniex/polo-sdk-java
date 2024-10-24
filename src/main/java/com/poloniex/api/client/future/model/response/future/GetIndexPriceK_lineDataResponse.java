package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetIndexPriceK_lineDataResponse {

    private int code;
    private String msg;
    private List<GetIndexPriceK_lineData> data=new ArrayList<>();

    // 新增方法将原始数据转换为 GetIndexPriceK_lineData 对象
    public void setData(List<List<String>> rawData) {
        this.data = new ArrayList<>();
        for (List<String> entry : rawData) {
            if (entry.size() == 6) { // 确保有正确的数量
                GetIndexPriceK_lineData lineData = new GetIndexPriceK_lineData();
                lineData.setO(entry.get(0)); // Opening price
                lineData.setH(entry.get(1)); // Highest price
                lineData.setL(entry.get(2)); // Lowest price
                lineData.setC(entry.get(3)); // Closing price
                lineData.setST(entry.get(4)); // Start time
                lineData.setCT(entry.get(5)); // End time
                this.data.add(lineData);
            }
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetIndexPriceK_lineData {
        private String o; // Opening price
        private String h; // Highest price
        private String l; // Lowest price
        private String c; // Closing price
        private String sT; // Start time
        private String cT; // End time
    }
}
