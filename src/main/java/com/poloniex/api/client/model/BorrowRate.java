package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Borrow Rate
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowRate {

    /**
     * currency name
     */
    private String currency;

    /**
     * borrow rate per day
     */
    private String dailyBorrowRate;

    /**
     * borrow rate per hour
     */
    private String hourlyBorrowRate;

    /**
     * borrow limit amount for the currency
     */
    private String borrowLimit;
}
