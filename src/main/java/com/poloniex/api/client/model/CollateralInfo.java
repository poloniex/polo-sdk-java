package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Collateral Information
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollateralInfo {

    /**
     * currency name
     */
    private String currency;

    /**
     * collateral rate of the currency in cross margin
     */
    private String collateralRate;

    /**
     * initial margin rate of this currency
     */
    private String initialMarginRate;

    /**
     * maintenance margin rate of this currency
     */
    private String maintenanceMarginRate;
}
