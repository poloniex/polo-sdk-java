package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Accounts transfer record
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountsTransferRecord {

    /**
     * transfer ID
     */
    private String id;

    /**
     * the account, from which the currency is transferred.
     */
    private String fromAccount;

    /**
     * the account, to which the currency is transferred.
     */
    private String toAccount;

    /**
     * the transferred currency
     */
    private String currency;

    /**
     * the transferred amount
     */
    private String amount;

    /**
     * the state of transfer operation
     */
    private String state;

    /**
     * the datetime of transfer operation
     */
    private Long createTime;
}
