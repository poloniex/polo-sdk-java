package com.poloniex.api.client.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Ticker event
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerEvent {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * daily change in decimal
     */
    private String dailyChange;

    /**
     * highest price over the last 24h
     */
    private String high;

    /**
     * quote units traded over the last 24h
     */
    private String amount;

    /**
     * base units traded over the last 24h
     */
    private String quantity;

    /**
     * count of trades
     */
    private Integer tradeCount;

    /**
     * lowest price over the last 24h
     */
    private String low;

    /**
     * close time for the 24h interval
     */
    private Long closeTime;

    /**
     * start time for the 24h interval
     */
    private Long startTime;

    /**
     * price at the end time
     */
    private String close;

    /**
     * price at the start time
     */
    private String open;

    /**
     * time the record was pushed
     */
    private Long ts;

    /**
     * mark price at the end time
     */
    private String markPrice;
}
