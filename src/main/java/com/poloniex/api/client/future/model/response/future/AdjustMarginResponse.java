package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdjustMarginResponse {
    private int code;
    private String msg;
    private AdjustMargin data=new AdjustMargin();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AdjustMargin {
        private String symbol; // A trading pair, e.g., BTC/USD
        private String amt;    // Margin amount
        private String type;   // Operation type: ADD or REDUCE
        private String lever;  // Leverage of the current position, from 1 to 75
        private String posSide;
    }
}
