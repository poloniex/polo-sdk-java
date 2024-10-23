package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class K_lineDataEvent extends ArrayList<Object> {
//    private List<> data ; // 用于存储 K_lineData 对象

    /*@Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class K_lineData {
        private String s;    // Trading pair
        private String o;    // Opening price
        private String l;    // Lowest price in the past 24 hours
        private String h;    // Highest price in the past 24 hours
        private String c;    // Closing price
        private String qty;  // Trading unit, the quantity of the base currency, or contracts
        private String amt;  // Trading unit, the quantity of the quote currency
        private Long tC;     // Trades count
        private Long sT;     // Start time of the 24-hour interval
        private Long cT;     // End time of the 24-hour interval
        private Long ts;     // Timestamp of data generation (in milliseconds)
    }
*/
}

/*    private List<K_lineData> data;
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class K_lineData {
        private String s;    // Trading pair
        private String o;    // Opening price
        private String l;    // Lowest price in the past 24 hours
        private String h;    // Highest price in the past 24 hours
        private String c;    // Closing price
        private String qty;  // Trading unit, the quantity of the base currency, or contracts
        private String amt;  // Trading unit, the quantity of the quote currency
        private Long tC;     // Trades count
        private Long sT;     // Start time of the 24-hour interval
        private Long cT;     // End time of the 24-hour interval
        private Long ts;     // Timestamp of data generation (in milliseconds)
        @JsonCreator
        public K_lineData(List<Object> data) {
            if (data.size() >= 10) {
                this.s = (String) data.get(0);
                this.o = (String) data.get(1);
                this.l = (String) data.get(2);
                this.h = (String) data.get(3);
                this.c = (String) data.get(4);
                this.qty = (String) data.get(5);
                this.amt = (String) data.get(6);
                this.tC = ((Number) data.get(7)).longValue();
                this.sT = ((Number) data.get(8)).longValue();
                this.cT = ((Number) data.get(9)).longValue();
                // `ts` 可以根据需要进行设置
            }
        }// 修正构造函数定义*/




