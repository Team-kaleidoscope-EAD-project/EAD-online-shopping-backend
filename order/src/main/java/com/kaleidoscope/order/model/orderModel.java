package com.kaleidoscope.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class orderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String status;
    private Float totalAmount;
    private String userId;
    private LocalDate orderDate;
    private Float discountAmount;

    @OneToMany(mappedBy = "orderModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<orderItemModel> orderItems;

    @OneToOne(mappedBy = "orderModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private paymentModel payment;

    @OneToOne(mappedBy = "orderModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private shippingModel shipping;
}