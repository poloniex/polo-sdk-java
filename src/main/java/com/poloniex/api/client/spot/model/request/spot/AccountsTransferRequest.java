package com.poloniex.api.client.spot.model.request.spot;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Accounts transfer request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountsTransferRequest {

    /**
     * The currency to transfer, like USDT
     */
    private String currency;

    /**
     * The amount to transfer
     */
    private String amount;

    /**
     * The account, from which the currency is transferred
     */
    private String fromAccount;

    /**
     * The account, to which the currency is transferred
     */
    private String toAccount;
}
