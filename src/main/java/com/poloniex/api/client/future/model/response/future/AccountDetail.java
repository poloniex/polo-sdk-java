package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetail {
    private String ccy;
    private String eq;
    private String isoEq;
    private String avail;
    private String upl;
    private String isoAvail;
    private String isoHold;
    private String isoUpl;
    private String im;
    private String mm;
    private String mmr;
    private String imr;
    private String cTime;
    private String uTime;
}
