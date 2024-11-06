package com.kaleidoscope.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class shippingModel {
    @Id
    private int id;
    private String shippingAddress;
    private int orderId;
    private LocalDate shippingDate;
}
