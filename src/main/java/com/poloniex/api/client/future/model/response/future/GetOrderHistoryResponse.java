package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetOrderHistoryResponse {
    private int code;
    private String msg;
    private List<GetOrderHistory> data=new ArrayList<>();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetOrderHistory {
        private String symbol;
        private String side;
        private String type;
        private String ordId;
        private String clOrdId;
        private String mgnMode;
        private String px;
        private String sz;
        private String state;
        private String cancelReason;
        private String source;
        private Boolean reduceOnly;
        private String timeInForce = "GTC"; // Default value as GTC
        private String avgPx;
        private String execQty;
        private String execAmt;
        private String feeCcy;
        private String feeAmt;
        private String deductCcy;
        private String deductAmt;
        private String stpMode = "NONE"; // Default value as NONE
        private String cTime;
        private String uTime;
    }
}
