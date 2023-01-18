package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Account Margin Information
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountMargin {

    /**
     * the sum of the usd value of all balances plus unrealized pnl, including the value of non-collateral holdings
     */
    private String totalAccountValue;

    /**
     * collateral that can be used for margin
     */
    private String totalMargin;

    /**
     * amount of margin that has been used; cannot open new positions if total margin falls below this value
     */
    private String usedMargin;

    /**
     * available free margin
     */
    private String freeMargin;

    /**
     * minimum amount needed to keep account in good standing; enters liquidation mode if total margin falls below this value
     */
    private String maintenanceMargin;

    /**
     * is calculated as total margin / maintenance Margin; account enters liquidation mode if this value < 100%
     */
    private String marginRatio;

    /**
     * time the record was created
     */
    private Long time;
}
