package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Account balance
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountBalance {
    /**
     * account ID
     */
    private String accountId;
    /**
     * account type
     */
    private String accountType;
    /**
     * account's assets
     */
    private List<Balance> balances;
}
