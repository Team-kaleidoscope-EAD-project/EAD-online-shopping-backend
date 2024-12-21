package com.kaleidoscope.order.controller;

import com.kaleidoscope.order.dto.StripeDto;
import com.kaleidoscope.order.service.stripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping(value = "api/v1/order/stripepayment")
public class stripePaymentController {

    private final stripeService stripePaymentService;

    @Autowired
    public stripePaymentController(stripeService stripePaymentService) {
        this.stripePaymentService = stripePaymentService;
    }

    @PostMapping("/create-payment-intent")
    public Map<String, Object> createPaymentIntent(@RequestBody StripeDto requestData) {
        return stripePaymentService.createPaymentIntent(requestData);
    }
}
