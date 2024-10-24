package com.poloniex.api.client.spot.model.event.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Exchange event
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeEvent {
    /** String: maintenance mode e.g: ON or OFF */
    private String MM;

    /** String: post only mode e.g: ON or OFF */
    private String POM;
}
