package com.kaleidoscope.order.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.kaleidoscope.order.service.stripeService;
import com.kaleidoscope.order.service.placeOrderService;
import com.kaleidoscope.order.dto.placeOrderDto;
import com.kaleidoscope.order.dto.StripeDto;

import java.util.Map;

@Service
@Transactional
public class ConfirmOrderService {

    @Autowired
    private placeOrderService placeOrderService;

    @Autowired
    private stripeService stripeService;

    public ResponseEntity<?> confirmOrder(placeOrderDto placeOrderDto, StripeDto stripeDto) {

        Map<String, Object> stripeResponse = stripeService.createPaymentIntent(stripeDto);

        String status = (String) stripeResponse.get("status");

        if ("succeeded".equalsIgnoreCase(status)) {

            placeOrderDto placedOrder = placeOrderService.placeOrder(placeOrderDto);
            return new ResponseEntity<>(placedOrder, HttpStatus.OK);
        } else {

            return new ResponseEntity<>("Payment failed. Status: " + status, HttpStatus.BAD_REQUEST);
        }
    }

}
