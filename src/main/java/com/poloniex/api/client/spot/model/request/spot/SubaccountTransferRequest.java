package com.poloniex.api.client.spot.model.request.spot;

import lombok.Builder;
import lombok.Data;

/**
 * Request to transfer amount of currency from an account and account type to another account and account type among the
 * accounts in the account group
 */
@Data
@Builder
public class SubaccountTransferRequest {

    /**
     * The currency to transfer, like USDT
     */
    private String currency;

    /**
     * The amount to transfer
     */
    private String amount;

    /**
     * external UID of the from account
     */
    private String fromAccountId;

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
}
