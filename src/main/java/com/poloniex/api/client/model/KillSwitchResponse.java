package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Kill Switch Response
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KillSwitchResponse {

    /**
     * time when timer is started (milliseconds since UNIX epoch)
     */
    private Long startTime;

    /**
     * time when timer is set to expire which will trigger cancellation (milliseconds since UNIX epoch)
     */
    private String cancellationTime;
}
