package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlaceOrderRequest {
    @NonNull
    private String symbol;      // A trading pair, consisting of two currencies: base currency and quote currency
    @NonNull
    private String side;        // Enumerate: BUY and SELL
    @NonNull
    private String type;        // Order type; enumerate MARKET, LIMIT, and LIMIT_MAKER
    private String clOrdId;     // Order ID as assigned by the user, optional
    private String px;          // Price, applicable for limit orders only. No price input is required for market orders, optional.
    @NonNull
    private String sz;          // Order size, specifically in Cont
    private Boolean reduceOnly; // Reduce only, optional
    private String timeInForce; // Enumerate FOK, IOC, and GTC. It is an optional field with GTC by default, optional.
    private String stpMode;     // Enumerate - EXPIRE_TAKER, EXPIRE_MAKER, EXPIRE_BOTH. Default is NONE, optional.
    private String mgnMode;
    private String posSide;
}
