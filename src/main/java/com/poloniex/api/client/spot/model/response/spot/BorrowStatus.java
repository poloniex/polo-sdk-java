package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Borrow Status
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowStatus {

    /**
     * currency name
     */
    private String currency;

    /**
     * amount of available currency
     */
    private String available;

    /**
     * borrowed amount
     */
    private String borrowed;

    /**
     * frozen amount
     */
    private String hold;

    /**
     * amount that can be withdrawn, including what's borrowable with margin
     */
    private String maxAvailable;

    /**
     * borrow rate per hour
     */
    private String hourlyBorrowRate;

    /**
     * current version of the currency
     */
    private String version;
}
