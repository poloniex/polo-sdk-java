package com.poloniex.api.client.spot.model.request.spot;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Unsubscribe request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UnsubscribeRequest {

    /**
     * unsubscribe event
     */
    private String event;

    /**
     * channels to unsubscribe from
     */
    private List<String> channel;

    /**
     * symbol names
     */
    private List<String> symbols;
}
