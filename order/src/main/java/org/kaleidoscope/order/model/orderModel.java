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

public class orderModel {
    @Id
    private int id;
    private String status;
    private Float totalAmount;
    private Integer userId;
    private LocalDate orderDate;
    private Float discountAmount;
}
