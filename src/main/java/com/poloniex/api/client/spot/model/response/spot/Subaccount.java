package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Info for a subaccount
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subaccount {

    /**
     * external account ID
     */
    private String accountId;

    /**
     * name of the account
     */
    private String accountName;

    /**
     * account's state
     */
    private String accountState;

    /**
     * true if account is primary; false if a subaccount
     */
    private String isPrimary;

    /**
     * currently SPOT or FUTURES
     */
    private String accountType;

    /**
     * account's assets
     */
    private List<Balance> balances;
}
