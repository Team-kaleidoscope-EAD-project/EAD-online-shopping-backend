package com.kaleidoscope.order.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.kaleidoscope.order.dto.orderDto;
import lombok.Getter;

@Getter
public class SucessOrderRespnse implements OrderResponse{
    @JsonUnwrapped
    private final orderDto order;

    public SucessOrderRespnse(orderDto order) {
        this.order = order;
    }
}
