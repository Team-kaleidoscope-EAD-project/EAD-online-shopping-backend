package com.kaleidoscope.order.dto;
import com.kaleidoscope.order.model.orderModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class orderItemDto {
    private int id;
    private int orderid;
    private Float price;
    private int quantity;
    private int productId;


}
