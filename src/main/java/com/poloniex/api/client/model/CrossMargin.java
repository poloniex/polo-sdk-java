package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Cross Margin Information
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrossMargin {
    /**
     * indicates if symbol supports cross margin
     */
    private Boolean supportCrossMargin;

    /**
     * maximum supported leverage
     */
    private Integer maxLeverage;
}
