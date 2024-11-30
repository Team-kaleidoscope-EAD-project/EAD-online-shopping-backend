package com.kaleidoscope.order.service;



import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaleidoscope.order.dto.placeOrderDto;
import com.kaleidoscope.order.dto.orederDto;
import com.kaleidoscope.order.dto.orderItemDto;
import com.kaleidoscope.order.dto.shippingDto;
import com.kaleidoscope.order.dto.paymentDto;


import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class placeOrderService {

    @Autowired
   private orderService orderService;

    @Autowired
    private shippingService shippingService;

    @Autowired
    private paymentService paymentService;

    @Autowired
    private orderItemService orderItemService;



    public placeOrderDto placeOrder(placeOrderDto placeOrderDto) {

        System.out.println(placeOrderDto.getOrderDetails());
        orederDto createOrder = orderService.addOrder(placeOrderDto.getOrderDetails());

        Integer newOrderId = createOrder.getId();
        if (newOrderId == null || newOrderId <= 0) {
            throw new RuntimeException("Failed to create order or invalid Order ID generated.");
        }
        System.out.println("Order successfully created with ID: " + newOrderId);

        shippingDto shippingDto = new shippingDto();
        shippingDto.setOrderId(newOrderId);
        shippingDto.setShippingAddress(placeOrderDto.getShippingDetails().getShippingAddress());
        shippingDto.setShippingDate(placeOrderDto.getShippingDetails().getShippingDate());
        shippingDto createShipping = shippingService.addShipping(shippingDto);
        System.out.println("Shipping successfully created for Order ID: " + newOrderId);

        paymentDto paymentDto = new paymentDto();
        paymentDto.setOrderId(newOrderId);
        paymentDto.setPaymentMethod(placeOrderDto.getPaymentDetails().getPaymentMethod());
        paymentDto.setPaymentAmount(placeOrderDto.getPaymentDetails().getPaymentAmount());
        paymentDto.setPaymentDate(placeOrderDto.getPaymentDetails().getPaymentDate());
        paymentDto createPayment = paymentService.addPayment(paymentDto);

        List<orderItemDto> orderItems = placeOrderDto.getOrderItems().stream()
                .peek(item -> item.setOrderid(newOrderId))
                .collect(Collectors.toList());
        List<orderItemDto> createdOrderItems = orderItemService.addOrderItems(orderItems);

        return new placeOrderDto(createOrder, createPayment, createShipping ,createdOrderItems);

    }
}


