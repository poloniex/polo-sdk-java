package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCurrentPositionResponse {
    private int code;
    private String msg;
    private List<GetCurrentPosition> data=new ArrayList<>();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetCurrentPosition {
        private String symbol;
        private String posSide;
        private String side;
        private String mgnMode;
        private String ctType; // Contract Type: Linear, Inverse
        private String openAvgPx;
        private String qty; // Position size
        private String availQty; // Available quantity for closing
        private String lever; // Leverage
        private String fee; // Position closing fee
        private String adl; // Auto-Deleveraging indicator
        private String liqPx; // Estimated liquidation price
        private String im; // Initial Margin
        private String mm; // Maintenance Margin
        private String mgn; // Isolated Margin
        private String upl; // Unrealized PnL
        private String uplRatio; // Unrealized PnL Percentage
        private String pnl; // Realized PnL
        private String markPx; // Mark Price
        private String lastPx; // Last Price
        private String mgnRatio; // Margin Ratio
        private String state; // Position Status
        private String cTime; // Creation Time
        private String uTime; // Update Time
    }
}
