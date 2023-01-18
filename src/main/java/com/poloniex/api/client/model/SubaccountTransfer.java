package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Info for a subaccount transfer
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubaccountTransfer {

    /**
     * transfer ID
     */
    private String transferId;

    /**
     * transfer ID
     */
    private String id;

    /**
     * external UID of the from account
     */
    private String fromAccountId;

    /**
     * name of the from account
     */
    private String fromAccountName;

    /**
     * from account type (SPOT or FUTURES)
     */
    private String fromAccountType;

    /**
     * external UID of the to account
     */
    private String toAccountId;

    /**
     * to account type (SPOT or FUTURES)
     */
    private String toAccountType;

    /**
     * name of the from account
     */
    private String toAccountName;

    /**
     * the transferred currency
     */
    private String currency;

    /**
     * the transferred amount
     */
    private String amount;

    /**
     * the state of transfer operation e.g. SUCCESS, PROCESSSING, FAILED
     */
    private String state;

    /**
     * the datetime of transfer operation
     */
    private Long createTime;
}
