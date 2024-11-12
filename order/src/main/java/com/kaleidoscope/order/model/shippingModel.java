package com.kaleidoscope.order.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String shippingAddress;
    @OneToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id", nullable = false)
    private orderModel orderModel;
    private LocalDate shippingDate;
}
