package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Market Data Price
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * current price
     */
    private String price;

    /**
     * time the record was created
     */
    private Long time;

    /**
     * daily change in decimal
     */
    private String dailyChange;

    /**
     * time the record was pushed
     */
    private Long ts;
}
