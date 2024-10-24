package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBookV2Event {
    private List<List<String>> asks; // 修改为 List<List<String>>
    private List<List<String>> bids; // 修改为 List<List<String>>
    private Long lid;  // last version id
    private Long id;   // current version id
    private Long ts;   // Timestamp of data generation (in millisecond)
    private String s;   // A trading pair
    private Long cT;   // create time
}
