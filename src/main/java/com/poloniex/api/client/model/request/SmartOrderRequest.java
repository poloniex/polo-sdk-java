package com.poloniex.api.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Create smart order request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SmartOrderRequest {

    /**
     * Only supported one.
     */
    private String symbol;

    /**
     * BUY, SELL
     */
    private String side;

    /**
     * FOK, IOC, GTC (Default: GTC)
     */
    private String timeInForce;

    /**
     * STOP, STOP_LIMIT (Default: STOP if price not specified or STOP_LIMIT if price is specified)
     */
    private String type;

    /**
     * SPOT is the default and only supported one.
     */
    private String accountType;

    /**
     * Required for STOP_LIMIT
     */
    private String price;

    /**
     * Price at which order is triggered
     */
    private String stopPrice;

    /**
     * base units for the order
     */
    private String quantity;

    /**
     * quote units for the order
     */
    private String amount;

    /**
     * Maximum 64-character length
     */
    private String clientOrderId;
}
