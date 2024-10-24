package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Market Information
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    /**
     * symbol cross margin info
     */
    private CrossMargin crossMargin;
}
