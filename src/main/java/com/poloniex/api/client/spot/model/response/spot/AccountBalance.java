package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Account balance
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
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
