package com.kaleidoscope.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeDto {
    private long amount;
    private String currency;
    private String methodType;

}


