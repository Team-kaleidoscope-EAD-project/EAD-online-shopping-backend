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
public class orderItemModel {
    @Id
    private int id;
    private int orderId;
    private Float price;
    private int quantity;
    private int productId;
}
