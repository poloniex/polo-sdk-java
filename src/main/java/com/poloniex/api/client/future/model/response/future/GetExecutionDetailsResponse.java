package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetExecutionDetailsResponse {

    private int code;
    private String msg;
    private List<GetExecutionDetails> data=new ArrayList<>();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetExecutionDetails {
        private String symbol;
        private String clOrdId; // Client Order ID
        private String trdId; // Trade ID
        private String side; // BUY or SELL
        private String type; // Execution Type
        private String mgnMode; // Margin Mode
        private String ordType; // Order Type
        private String role; // Execution Role
        private String px; // Deal Price
        private String sz; // Deal Size
        private Long cTime; // Execution Time
        private Long uTime; // Update Time
        private String feeCcy; // Currency for Trading Fees
        private String feeAmt; // Cumulative Fee Amount
        private String deductCcy; // Currency for Deducted Fees
        private String deductAmt; // Cumulative Deducted Fee Amount
        private String feeRate; // Fee Rate
    }
}
