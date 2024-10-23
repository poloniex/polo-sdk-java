package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountChangeEvent {
    private String state; // Account Status
    private String eq; // Equity in assets
    private String isoEq; // Isolated asset equity
    private String im; // Initial margin
    private String mm; // Account maintenance margin
    private String mmr; // Account maintenance margin rate
    private String upl; // Unrealized profit or loss - cross
    private String availMgn; // Available margin
    private List<Details> details; // Details array


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Details {
        private String ccy; // Currency
        private String eq; // Asset equity of this currency
        private String isoEq; // Isolated position equity of this currency
        private String avail; // The currency is available
        private String upl; // Unrealized profit or loss
        private String isoAvail; // Isolated margin of this currency - Isolated position frozen
        private String isoHold; // This currency’s isolated margin orders are frozen
        private String isoUpl; // Isolated position unrealized profit and loss
        private String im; // Initial margin of this currency
        private String imr; // The initial margin rate of this currency, single currency mode
        private String mm; // The currency maintenance margin
        private String mmr; // This currency maintains a margin rate
        private String cTime; // Creation time
        private String uTime; // Update time

        // Getters and Setters for the Details class can be added here
    }

    // Getters and Setters for the Data class can be added here


    private String cTime; // Account creation time
    private String uTime; // Account update time
}
