package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Currency Information
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyV2 {
    /** Integer: currency id */
    private int id;

    /** String: currency symbol */
    private String coin;

    /** Boolean: designates whether (true) or not (false) this currency has been delisted from the exchange */
    private boolean delisted;

    /** Boolean: designates whether (true) or not (false) this currency can be traded on the exchange */
    private boolean tradeEnable;

    /** String: currency name */
    private String name;

    /** Boolean: indicates if this currency supports collateral in cross margin */
    private boolean supportCollateral;

    /** Boolean: indicates if this currency supports borrows in cross margin */
    private boolean supportBorrow;

    /** Array of Json: the networks the currency runs on */
    private List<Network> networkList;
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Network {
        /**
         * Integer ID of the currency.
         */
        private int id;

        /**
         * String symbol of the currency.
         */
        private String coin;

        /**
         * String name of the currency.
         */
        private String name;

        /**
         * String indicating the type of currency: address or address-payment-id.
         */
        private String currencyType;

        /**
         * String specifying the blockchain networks the currency runs on.
         */
        private String blockchain;

        /**
         * Boolean indicating if withdrawal is enabled for this currency (true) or not (false).
         */
        private boolean withdrawalEnable;

        /**
         * Boolean indicating if deposit is enabled for this currency (true) or not (false).
         */
        private boolean depositEnable;

        /**
         * String representing the deposit address for this currency, if available.
         */
        private String depositAddress;

        /**
         * String representing the minimum withdrawal amount.
         * A value less than or equal to 0 means no minimum amount is specified.
         */
        private String withdrawMin;

        /**
         * String representing the number of decimal places the currency supports.
         */
        private String decimals;

        /**
         * String representing the withdrawal fee of the currency.
         */
        private String withdrawFee;

        /**
         * Integer representing the minimum number of blocks necessary
         * before a deposit can be credited to an account.
         */
        private int minConfirm;
    }
}
