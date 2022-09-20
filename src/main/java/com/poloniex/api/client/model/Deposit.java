package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Wallet Deposit
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Deposit {

    /**
     * the unique deposit ID for this deposit
     */
    private Long depositNumber;

    /**
     * the currency of this deposit
     */
    private String currency;

    /**
     * the address to which this deposit was sent
     */
    private String address;

    /**
     * the total value of the deposit (network fees will not be included in this)
     */
    private String amount;

    /**
     * the total number of confirmations for this deposit
     */
    private Integer confirmations;

    /**
     * the blockchain transaction ID of this deposit
     */
    private String txid;

    /**
     * the UNIX timestamp when this deposit was first noticed
     */
    private Long timestamp;

    /**
     * the current status of this deposit (either PENDING or COMPLETED)
     */
    private String status;
}
