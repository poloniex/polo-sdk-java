package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Market Information
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Market {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * base currency name
     */
    private String baseCurrencyName;

    /**
     * quote currency name
     */
    private String quoteCurrencyName;

    /**
     * symbol display name
     */
    private String displayName;

    /**
     * NORMAL, PAUSE, POST_ONLY
     */
    private String state;

    /**
     * visible start time
     */
    private Long visibleStartTime;

    /**
     * tradable start time
     */
    private Long tradableStartTime;

    /**
     * symbol market configuration
     */
    private SymbolTradeLimit symbolTradeLimit;
}
