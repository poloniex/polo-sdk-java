package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * Symbol Trade Limit for markets
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolTradeLimit {
    /**
     * symbol name
     */
    private String symbol;

    /**
     * decimal precision for price
     */
    private Integer priceScale;

    /**
     * decimal precision for quantity
     */
    private Integer quantityScale;

    /**
     * decimal precision for amount
     */
    private Integer amountScale;

    /**
     * minimum required quantity
     */
    private String minQuantity;

    /**
     * minimum required amount
     */
    private String minAmount;

    /**
     * maximum allowed bid price (market bound)
     */
    private String highestBid;

    /**
     * minimum allowed ask price (market bound)
     */
    private String lowestAsk;

}
