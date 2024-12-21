package com.kaleidoscope.order.controller;

import com.kaleidoscope.order.dto.StripeDto;
import com.kaleidoscope.order.dto.orderDto;
import com.kaleidoscope.order.service.placeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kaleidoscope.order.dto.placeOrderDto;
import com.kaleidoscope.order.dto.StripeOrderDto;
import com.kaleidoscope.order.service.ConfirmOrderService;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/order/")
public class placeOrderController {

    @Autowired
    private placeOrderService placeOrderService;

    @Autowired
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/addplaceorder")
    public placeOrderDto placeOrder(@RequestBody placeOrderDto placeOrderDto) {

        return placeOrderService.placeOrder(placeOrderDto);
    }

    @PostMapping("/confirmorder")
    public ResponseEntity<?> confirmOrder(@RequestBody StripeOrderDto stripeOrderDto) {

        placeOrderDto placeOrder = new placeOrderDto(
                stripeOrderDto.getOrderDetails(),
                stripeOrderDto.getPaymentDetails(),
                stripeOrderDto.getShippingDetails(),
                stripeOrderDto.getOrderItems()
        );

        StripeDto stripeDto = stripeOrderDto.getStripeDetails();

        return confirmOrderService.confirmOrder(placeOrder,stripeDto);
    }
}
