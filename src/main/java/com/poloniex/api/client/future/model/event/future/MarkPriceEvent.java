package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkPriceEvent {
    private String s;   // symbol

    private String mPx;    // Mark price

    private Long ts; // Push time (millisecond)

}
