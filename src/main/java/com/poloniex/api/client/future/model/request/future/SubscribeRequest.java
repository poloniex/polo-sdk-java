package com.poloniex.api.client.future.model.request.future;

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
public class SubscribeRequest<T> {

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
     * params required to establish authenticated subscription or other params
     */
    private T params;
}
