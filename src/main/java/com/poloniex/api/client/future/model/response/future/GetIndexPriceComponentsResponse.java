package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetIndexPriceComponentsResponse {
    private int code;
    private String msg;
    private List<GetIndexPriceComponents> data=new ArrayList<>();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetIndexPriceComponents {

        private ComponentSymbol[] cs; // Array of Json representing a symbol's price from different exchanges and its weight factor
        private String px; // Index price
        private String s; // A trading pair

        @Data
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ComponentSymbol {
//            private String s; // A trading pair

            private String cPx; // A trading pair's index price
            private String e; // Exchange
            private String sPx; // Price of a trading pair
            private String w; // Weight factor
        }
    }
}
