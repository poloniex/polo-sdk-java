package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Maximum Buy/Sell Amount
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaxSize {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * max leverage for the symbol
     */
    private Integer maxLeverage;

    /**
     * available amount for the quote currency that can be bought
     */
    private String availableBuy;

    /**
     * maximum amount in quote currency that can be bought including margin
     */
    private String maxAvailableBuy;

    /**
     * available amount for the base currency that can be sold
     */
    private String availableSell;

    /**
     * maximum amount in base currency that can be sold including margin
     */
    private String maxAvailableSell;
}
