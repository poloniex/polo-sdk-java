package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Wallet Adjustment
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Adjustment {

    /**
     * the currency of this adjustment
     */
    private String currency;

    /**
     * the total value of the adjustment
     */
    private String amount;

    /**
     * the UNIX timestamp when the adjustment was credited
     */
    private Long timestamp;

    /**
     * adjustment status (only COMPLETED)
     */
    private String status;

    /**
     * always adjustment
     */
    private String category;

    /**
     * the type of adjustment
     */
    private String adjustmentTitle;

    /**
     * a human-readable description of the adjustment
     */
    private String adjustmentDesc;

    /**
     * a help center link to describe the adjustment
     */
    private String adjustmentHelp;
}
