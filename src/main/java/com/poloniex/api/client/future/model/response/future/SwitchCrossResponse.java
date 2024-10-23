package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwitchCrossResponse {
    private int code;
    private String msg;
    private SwitchCross data=new SwitchCross();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SwitchCross {
    private String symbol;
    private String mgnMode;}
}
