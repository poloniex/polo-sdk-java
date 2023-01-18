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

    /**
     * allow order to be placed by borrowing funds (Default: false)
     */
    private Boolean allowBorrow;

    /**
     * if set to true then new order will be placed even if cancelation of the existing order fails; if set to false
     * (DEFAULT value) then new order will not be placed if the cancelation of the existing order fails
     */
    private String proceedOnFailure;

    public OrderRequest(String symbol, String side, String timeInForce, String type, String accountType, String price, String quantity, String amount, String clientOrderId) {
        this.symbol = symbol;
        this.side = side;
        this.timeInForce = timeInForce;
        this.type = type;
        this.accountType = accountType;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.clientOrderId = clientOrderId;
    }
}
