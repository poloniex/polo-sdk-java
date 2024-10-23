package com.poloniex.api.client.spot.model.request.spot;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * WithdrawV2 currency request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WithdrawCurrencyV2Request {
    /** String: the currency to use for the deposit address, like "BTC, USDT or USDD" */
    private String coin;

    /** String: The target network to withdraw to, like "BTC, ETH or TRX" */
    private String network;

    /** String: withdrawal amount */
    private String amount;

    /** String: withdrawal address */
    private String address;

    /** String: memo for currencies that use a command deposit address */
    private String addressTag;

    /** Boolean: allow to transfer borrowed funds (Default: false) */
    private Boolean allowBorrow;
}
