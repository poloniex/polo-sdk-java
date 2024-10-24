package com.poloniex.api.client.spot.model.request.spot;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Create Order request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateOrderRequest {
    /**
     * The symbol to trade, like BTC_USDT
     */
    private String symbol;

    /**
     * BUY or SELL
     */
    private String side;

    /**
     * GTC, IOC, FOK (Default: GTC)
     * Time in force for the order
     */
    private String timeInForce;

    /**
     * MARKET, LIMIT, LIMIT_MAKER (Default: MARKET)
     * Type of the order
     */
    private String type;

    /**
     * SPOT is the default and only supported one.
     * Account type for the order
     */
    private String accountType;

    /**
     * Price is required for non-market orders
     */
    private String price;

    /**
     * Base units for the order. Quantity is required for MARKET SELL or any LIMIT orders
     */
    private String quantity;

    /**
     * Quote units for the order. Amount is required for MARKET BUY order
     */
    private String amount;

    /**
     * Maximum 64-character length.
     * Client-provided order ID (optional)
     */
    private String clientOrderId;

    /**
     * Allow order to be placed by borrowing funds (Default: false)
     */
    private Boolean allowBorrow;

    /**
     * Self-trade prevention mode. Defaults to EXPIRE_TAKER.
     * - None: enable self-trade
     * - EXPIRE_TAKER: Taker order will be canceled when self-trade happens
     */
    private String stpMode;

    /**
     * Used to control the maximum slippage ratio, the value range is greater than 0 and less than 1
     */
    private String slippageTolerance;
}
