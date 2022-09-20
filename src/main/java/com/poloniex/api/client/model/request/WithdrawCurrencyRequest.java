package com.poloniex.api.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Withdraw currency request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WithdrawCurrencyRequest {

    /**
     * currency name
     */
    private String currency;

    /**
     * withdrawal amount
     */
    private String amount;

    /**
     * withdrawal address
     */
    private String address;

    /**
     * paymentID for currencies that use a command deposit address
     */
    private String paymentID;
}
