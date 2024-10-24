package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetIndexPriceResponse {

    private int code;
    private String msg;
    private GetIndexPrice data=new GetIndexPrice();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetIndexPrice {

        private String s;  // Trading pair
        private String iPx; // Index price
       /* private List<SymbolPrice> cs; // Array of symbol's price from different exchanges and its weight factor

        // Nested class for SymbolPrice
        public static class SymbolPrice {
            //        private String s;    // Trading pair
            private String sPx;  // Price of a trading pair
            private String w;    // Weight factor
            private String cPx;  // A trading pair's index price
            private String e;    // Exchange
        }*/
    }
    }
