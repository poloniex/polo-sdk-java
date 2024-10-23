package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Currency Information
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    /**
     * currency id
     */
    private Integer id;

    /**
     * currency name
     */
    private String name;

    /**
     * the type of blockchain the currency runs on
     */
    private String description;

    /**
     * currency type
     */
    private String type;

    /**
     * The network fee necessary to withdraw this currency
     */
    private String withdrawalFee;

    /**
     * the minimum number of blocks necessary before a deposit can be credited to an account
     */
    private Integer minConf;

    /**
     * if available, the deposit address for this currency.
     */
    private String depositAddress;

    /**
     * the blockchain the currency runs on
     */
    private String blockchain;

    /**
     * designates whether (true) or not (false) this currency has been delisted from the exchange
     */
    private Boolean delisted;

    /**
     * currency trading state: NORMAL or OFFLINE
     */
    private String tradingState;

    /**
     * currency state: ENABLED or DISABLED
     */
    private String walletState;

    /**
     * only displayed when includeMultiChainCurrencies is set to true. The parent chain
     */
    private String parentChain;

    /**
     * only displayed when includeMultiChainCurrencies is set to true. Indicates whether (true) or not (false) this currency is a multi chain
     */
    private Boolean isMultiChain;

    /**
     * only displayed when includeMultiChainCurrencies is set to true. Indicates whether (true) or not (false) this currency is a child chain
     */
    private Boolean isChildChain;

    /**
     * 	indicates if this currency supports collateral in cross margin
     */
    private Boolean supportCollateral;

    /**
     * 	indicates if this currency supports borrows in cross margin
     */
    private Boolean supportBorrow;

    /**
     * only displayed when includeMultiChainCurrencies is set to true. The child chains
     */
    private String[] childChains;
}
