package com.poloniex.api.client.spot.model.event.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Trade event
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolEvent {
    /** String: symbol name, e.g., BTC_USDT */
    private String symbol;

    /** String: base currency name */
    private String baseCurrencyName;

    /** String: quote currency name */
    private String quoteCurrencyName;

    /** String: symbol display name */
    private String displayName;

    /** String: state of the symbol (NORMAL, PAUSE, POST_ONLY) */
    private String state;

    /** Long: time since symbol is visible in the frontend */
    private Long visibleStartTime;

    /** Long: time since symbol is tradable */
    private Long tradableStartTime;

    /** Map: symbol market configuration */
    private SymbolTradeLimit symbolTradeLimit;

    /** Map: symbol cross margin info */
    private CrossMargin crossMargin;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SymbolTradeLimit {
        /** String: symbol name */
        private String symbol;

        /** Integer: decimal precision for price */
        private Integer priceScale;

        /** Integer: decimal precision for quantity */
        private Integer quantityScale;

        /** Integer: decimal precision for amount */
        private Integer amountScale;

        /** String: minimum required quantity */
        private String minQuantity;

        /** String: minimum required amount */
        private String minAmount;

        /** String: maximum allowed bid price (market bound) */
        private String highestBid;

        /** String: minimum allowed ask price (market bound) */
        private String lowestAsk;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CrossMargin {
        /** Boolean: indicates if symbol supports cross margin */
        private Boolean supportCrossMargin;

        /** Integer: maximum supported leverage */
        private Integer maxLeverage;
    }
}
