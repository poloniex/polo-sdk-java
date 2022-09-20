package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Market Data Ticker
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Ticker24h {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * price at the start time
     */
    private String open;

    /**
     * lowest price over the last 24h
     */
    private String low;

    /**
     * highest price over the last 24h
     */
    private String high;

    /**
     * price at the end time
     */
    private String close;

    /**
     * base units traded over the last 24h
     */
    private String quantity;

    /**
     * quote units traded over the last 24h
     */
    private String amount;

    /**
     * count of trades
     */
    private Integer tradeCount;

    /**
     * start time for the 24h interval
     */
    private Long startTime;

    /**
     * close time for the 24h interval
     */
    private Long closeTime;

    /**
     * symbol display name
     */
    private String displayName;

    /**
     * daily change in decimal
     */
    private String dailyChange;

    /**
     * time the record was pushed
     */
    private Long ts;
}
