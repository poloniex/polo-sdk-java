package com.poloniex.api.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Subscribe request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubscribeRequest {

    /**
     * subscribe event
     */
    private String event;

    /**
     * channels to subscribe to
     */
    private List<String> channel;

    /**
     * symbol names
     */
    private List<String> symbols;

    /**
     * depth
     */
    private Integer depth;

    /**
     * params required to establish authenticated subscription
     */
    private AuthParams params;
}
