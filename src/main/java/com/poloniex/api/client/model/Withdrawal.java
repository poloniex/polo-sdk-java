package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Wallet Withdrawal
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Withdrawal {

    /**
     * the unique ID for this withdrawal
     */
    private Long withdrawalNumber;

    /**
     * the currency of this withdrawal
     */
    private String currency;

    /**
     * the address to which the withdrawal was made
     */
    private String address;

    /**
     * the total amount withdrawn including the fee
     */
    private String amount;

    /**
     * the fee paid to the exchange for this withdrawal
     */
    private String fee;

    /**
     * the Unix timestamp of the withdrawal
     */
    private Long timestamp;

    /**
     * the status of the withdrawal (one of PENDING, AWAITING APPROVAL, COMPLETED or COMPLETE ERROR)
     */
    private String status;

    /**
     * the blockchain transaction ID of this withdrawal
     */
    private String txid;

    /**
     * the IP address which initiated the withdrawal request
     */
    private String ipAddress;

    /**
     * the paymentID specified for this withdrawal. If none were specified, the field will be null
     */
    private String paymentID;
}
