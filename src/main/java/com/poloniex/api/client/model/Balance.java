package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Account Currency Balance
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Balance {

    /**
     * currency id
     */
    private String currencyId;

    /**
     * currency name
     */
    private String currency;

    /**
     * available amount for the currency
     */
    private String available;

    /**
     * frozen amount for the currency
     */
    private String hold;

    // SPOT

    /**
     * amount of currency that can be transferred
     */
    private String maxAvailable;

    // FUTURES

    /**
     * equal to margin Balance + unrealised PNL
     */
    private String accountEquity;

    /**
     * unrealised profit and loss
     */
    private String unrealisedPNL;

    /**
     * equal to positionMargin + orderMargin + frozenFunds + availableBalance
     */
    private String marginBalance;

    /**
     * position margin
     */
    private String positionMargin;

    /**
     * order margin
     */
    private String orderMargin;

    /**
     * frozen funds
     */
    private String frozenFunds;

    /**
     * available balance
     */
    private String availableBalance;

    /**
     * realised profit and loss
     */
    private String pnl;

}
