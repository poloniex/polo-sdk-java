package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPositionHistoryResponse {
    private int code;
    private String msg;
    private List<GetPositionHistory> data=new ArrayList<>();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetPositionHistory {
        private String id;
        private String symbol;
        private String posSide;
        private String side;
        private String mgnMode;
        private String openAvgPx;
        private String closeAvgPx;
        private String qty;
        private String closedQty;
        private String availQty;
        private String pnl;
        private String fee;
        private String fFee; // Cumulative funding charges
        private String state;
        private String cTime; // Creation time
        private String uTime; // Update time
    }
}
