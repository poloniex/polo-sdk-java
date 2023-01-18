package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Borrow Rates Tier
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowRateTier {

    /**
     * tier for borrow rates
     */
    private String tier;

    /**
     * borrow rates per currency
     */
    private List<BorrowRate> rates;
}
