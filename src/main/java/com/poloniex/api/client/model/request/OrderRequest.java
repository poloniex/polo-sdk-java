package com.poloniex.api.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Create order request.
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderRequest {

    /**
     * The symbol to trade, like BTC_USDT
     */
    private String symbol;

    /**
     * BUY, SELL
     */
    private String side;

    /**
     * GTC, IOC, FOK (Default: GTC)
     */
    private String timeInForce;

    /**
     * MARKET, LIMIT, LIMIT_MAKER (Default: MARKET)
     */
    private String type;

    /**
     * SPOT is the default and only supported one.
     */
    private String accountType;

    /**
     * Price is required for non-market orders
     */
    private String price;

    /**
     * Base units for the order. Quantity is required for MARKET type and SELL side.
     */
    private String quantity;

    /**
     * Quote units for the order. Amount is required for MARKET and BUY side.
     */
    private String amount;

    /**
     * Maximum 64-character length
     */
    private String clientOrderId;
}
