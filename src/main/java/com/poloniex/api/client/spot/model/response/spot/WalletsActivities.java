package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Wallets activities.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletsActivities {

    /**
     * list of adjustments activities
     */
    private List<Adjustment> adjustments;

    /**
     * list of deposits activities
     */
    private List<Deposit> deposits;

    /**
     * list of withdrawals activities
     */
    private List<Withdrawal> withdrawals;
}
