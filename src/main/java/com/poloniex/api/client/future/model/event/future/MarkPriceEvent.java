package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkPriceEvent {
    private String s;   // 操作类型：subscribe（订阅）或 unsubscribe（取消订阅）

    private String mPx;    // 错误代码
    private Long ts; // 错误消息

}
