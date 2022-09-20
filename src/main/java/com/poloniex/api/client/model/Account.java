package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Account Information
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Account {

    /**
     * account ID
     */
    private String accountId;

    /**
     * account type
     */
    private String accountType;

    /**
     * account's state, e.g. NORMAL, LOCKED
     */
    private String accountState;
}
