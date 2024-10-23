package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickersEvent {
    private String s; // Trading pair
    private String o; // Opening price
    private String l; // Lowest price in the past 24 hours
    private String h; // Highest price in the past 24 hours
    private String c; // Closing price
    private String qty; // Trading unit, the quantity of the base currency, or Cont for the number of contracts
    private String amt; // Trading unit, the quantity of the quote currency
    private Long tC; // Trades count
    private Long sT; // Start time of the 24-hour interval
    private Long cT; // End time of the 24-hour interval
    private String dC; // Daily price change in decimal
    private String bPx; // Best bid price
    private String bSz; // Quantity at the best bid price
    private String aPx; // Best ask price
    private String aSz; // Quantity at the best ask price
    private Long ts; // Timestamp of data generation (in milliseconds)

}
