package org.kaleidoscope.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class paymentDto {
    private int id;
    private String paymentMethod;
    private int orderId;
    private LocalDate paymentDate;
    private Float paymentAmount;
}
