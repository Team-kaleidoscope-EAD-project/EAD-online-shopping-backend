package org.kaleidoscope.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class shippingDto {
    private int id;
    private String shippingAddress;
    private int orderId;
    private LocalDate shippingDate;
}
