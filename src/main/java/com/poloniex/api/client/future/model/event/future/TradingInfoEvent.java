package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingInfoEvent {
    private Long id; // Trade ID
    private Long ts; // Push time
    private String s; // Trading pair
    private String px; // Execution price
    private String qty; // Trading unit, the quantity of the base currency, or Cont for the number of contracts
    private String amt; // Trading unit, the quantity of the quote currency
    private String side; // Enumerate: BUY, SELL
    private Long cT; // Timestamp of execution time

    // Getters and setters can be added here if needed

}
