package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Fee info for account
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeeInfo {

    /**
     * Discount exists if using TRX
     */
    private Boolean trxDiscount;

    /**
     * Maker rate
     */
    private String makerRate;

    /**
     * Taker rate
     */
    private String takerRate;

    /**
     * 30 days volume in USDT
     */
    private String volume30D;

    /**
     * Custom fee rates for symbols
     */
    private List<SpecialFeeRate> specialFeeRates;
}
