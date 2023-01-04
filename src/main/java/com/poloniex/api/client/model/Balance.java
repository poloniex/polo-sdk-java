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
}
