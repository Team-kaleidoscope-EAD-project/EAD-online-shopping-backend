package com.kaleidoscope.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeOrderDto extends placeOrderDto{
    private StripeDto stripeDetails;
}
