package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Activity Response
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityResponse {

    /**
     * Activity id
     */
    private String id;

    /**
     * Currency like BTC, ETH etc
     */
    private String currency;

    /**
     * Amount of the activity (can be negative)
     */
    private String amount;

    /**
     * State of the activity (ex. SUCCESS)
     */
    private String state;

    /**
     * Datetime of the activity
     */
    private Long createTime;

    /**
     * Activity details
     */
    private String description;

    /**
     * Type of activity
     */
    private Integer activityType;
}
