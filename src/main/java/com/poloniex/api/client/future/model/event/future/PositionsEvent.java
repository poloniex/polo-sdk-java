package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionsEvent {
    private String symbol; // A trading pair
    private String posSide; // Both sides
    private String side; // Enumerate: BUY, SELL
    private String mgnMode; // Margin Mode, Enumerate: ISOLATED, CROSS
    private String openAvgPx; // Average entry price
    private String qty; // Conts of positions (Position size after execution)
    private String oldQty; // Previous quantity (optional, as the description is not clear)
    private String availQty; // Conts available to close
    private String lever; // Leverage, from 1 to 75
    private String fee; // Position closing fee
    private String adl; // ADL (Auto-Deleveraging)
    private String liqPx; // Estimated liquidation price
    private String mgn; // Isolated margin
    private String im; // Initial margin
    private String mm; // Maintenance margin
    private String upl; // Unrealized PnL (calculated based on mark price)
    private String uplRatio; // Unrealized PnL% (calculated based on mark price)
    private String fPnl; // Future PnL (optional, as the description is not clear)
    private String pnl; // Realized PnL
    private String fFee;
    private String markPx;
    private String mgnRatio;
    private String state;
    private Long cTime;
    private Long uTime;

}
