package com.kaleidoscope.order.service;

import com.kaleidoscope.order.dto.InventoryUpdateDto;
import com.kaleidoscope.order.dto.orderItemDto;
import com.kaleidoscope.order.dto.paymentDto;
import com.kaleidoscope.order.dto.shippingDto;
import com.kaleidoscope.order.model.orderItemModel;
import com.kaleidoscope.order.model.paymentModel;
import com.kaleidoscope.order.model.shippingModel;
import org.springframework.stereotype.Service;

@Service
public class DtoConvertService {
    public InventoryUpdateDto convertToInventoryUpdateDto(orderItemDto orderItem) {
        InventoryUpdateDto inventoryUpdateDto = new InventoryUpdateDto();
        inventoryUpdateDto.setSku(orderItem.getSku());
        inventoryUpdateDto.setQuantity(orderItem.getQuantity());

        return inventoryUpdateDto;
    }

    public paymentDto convertToPaymentDto(paymentModel payment) {
        return new paymentDto(
                payment.getId(),
                payment.getPaymentMethod(),
                payment.getOrderModel().getId(),
                payment.getPaymentDate(),
                payment.getPaymentAmount()
        );
    }

    public orderItemDto convertOrderItemToDto(orderItemModel orderItem) {
        return new orderItemDto(
                orderItem.getId(),
                orderItem.getOrderModel().getId(),
                orderItem.getPrice(),
                orderItem.getQuantity(),
                orderItem.getSku()
        );
    }

    public shippingDto convertToShippingDto(shippingModel shipping) {
        return new shippingDto(
                shipping.getId(),
                shipping.getShippingAddress(),
                shipping.getOrderModel().getId(),
                shipping.getShippingDate()

        );
    }
}
