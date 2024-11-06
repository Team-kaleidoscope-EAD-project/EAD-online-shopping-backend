package org.kaleidoscope.order.model;

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

public class paymentModel {
    @Id
    private int id;
    private String paymentMethod;
    private int orderId;
    private LocalDate paymentDate;
    private Float paymentAmount;
}
