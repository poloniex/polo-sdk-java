package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Wallets activities.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
