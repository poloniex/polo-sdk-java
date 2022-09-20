package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Withdraw currency response
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WithdrawCurrencyResponse {
    /**
     * the withdrawal reference ID
     */
    private Long withdrawalRequestsId;
}
