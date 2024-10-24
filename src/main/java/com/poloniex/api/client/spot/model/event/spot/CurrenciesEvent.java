package com.poloniex.api.client.spot.model.event.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Currencies event
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrenciesEvent {
    /** String: currency */
    private String currency;

    /** Integer: currency id */
    private Integer id;

    /** String: currency name */
    private String name;

    /** String: the type of blockchain the currency runs on */
    private String description;

    /** String: currency type */
    private String type;

    /** String: The network fee necessary to withdraw this currency */
    private String withdrawalFee;

    /** Integer: the minimum number of blocks necessary before a deposit can be credited to an account */
    private Integer minConf;

    /** String: if available, the deposit address for this currency */
    private String depositAddress;

    /** String: the blockchain the currency runs on */
    private String blockchain;

    /** Boolean: designates whether (true) or not (false) this currency has been delisted from the exchange */
    private Boolean delisted;

    /** String: currency trading state: NORMAL or OFFLINE */
    private String tradingState;

    /** String: currency state: ENABLED or DISABLED */
    private String walletState;

    /** String: only displayed when includeMultiChainCurrencies is set to true. The parent chain */
    private String parentChain;

    /** Boolean: Indicates whether (true) or not (false) this currency is a multi chain */
    private Boolean isMultiChain;

    /** Boolean: Indicates whether (true) or not (false) this currency is a child chain */
    private Boolean isChildChain;

    /** Boolean: indicates if this currency supports collateral in cross margin */
    private Boolean supportCollateral;

    /** Boolean: Indicates if this currency supports borrows in cross margin */
    private Boolean supportBorrow;

    /** Array of String: The child chains */
    private List<String> childChains;
}
