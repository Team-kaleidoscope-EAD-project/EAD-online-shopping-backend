package com.kaleidoscope.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class placeOrderDto {


    private orederDto orderDetails;
    private paymentDto paymentDetails;
    private shippingDto shippingDetails;
    private List<orderItemDto> orderItems;
}
