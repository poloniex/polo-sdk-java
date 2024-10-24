package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersEvent {
    private String symbol; // A trading pair
    private String side; // Enumerate: BUY, SELL
    private String type; // Enumerate: MARKET, LIMIT, LIMIT_MAKER
    private String mgnMode; // Enumerate: ISOLATED, CROSS, CASH
    private String timeInForce; // Enumerate: FOK, IOC, GTC
    private String clOrdId; // Client order ID
    private String sz; // Amount or Conts of the order
    private String px; // Price, applicable for limit orders only
    private Boolean reduceOnly; // Only applicable to futures; serves solely as a reduction indicator
    private String posSide; // Only applicable to futures. Enumerate BOTH, LONG, and SHORT; with BOTH by default under the hedge mode
    private String ordId; // Order ID
    private String state; // Order status. Enumerate: NEW, PARTIAL_FILLED, FILLED, PARTIAL_CANCELED, CANCELED
    private String cancelReason; // Enumerate: various reasons for cancellation
    private String source; // Order source
    private String avgPx; // Average execution price
    private String execQty; // Cumulative execution amount
    private String execAmt; // Cumulative execution value
    private String feeCcy; // Name of the currency used for trading fees
    private String feeAmt; // Cumulative amount of trading fee
    private String deductCcy; // Name of the currency used to deduct trading fees
    private String deductAmt; // Cumulative amount of trading fee deducted
    private Long cTime; // Order creation time with a UTC timestamp (MS)
    private Long uTime; // Order update time with a UTC timestamp (MS)

}
