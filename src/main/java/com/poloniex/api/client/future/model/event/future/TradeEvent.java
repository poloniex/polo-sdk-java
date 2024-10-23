package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeEvent {
    private String symbol; // A trading pair
    private String side; // Enumerate: BUY, SELL
    private String ordId; // Order ID
    private String clOrdId; // Client Order ID as assigned by the client
    private String role; // Execution Role: MAKER, TAKER
    private String trdId; // Transaction ID
    private String feeCcy; // Name of the currency used for trading fees
    private String feeAmt; // Cumulative amount of trading fee
    private String deductCcy; // Name of the currency used to deduct trading fees
    private String deductAmt; // Cumulative amount of trading fee deducted
    private String fpx; // Price
    private String fqty; // Size
    private String uTime; // Trade update time with a UTC timestamp (MS)

}
