package com.poloniex.api.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Kill switch request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KillSwitchRequest {

    /**
     * timer value in seconds; range is -1 and 10 to 600
     */
    private String timeout;
}
