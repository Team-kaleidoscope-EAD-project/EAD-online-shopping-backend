package com.kaleidoscope.order.controller;

import com.kaleidoscope.order.service.shippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kaleidoscope.order.dto.shippingDto;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/order/")

public class shippingController {
    @Autowired
    private shippingService shippingService;

    @GetMapping("/getallshippings")
    public List<shippingDto> getAllShippings() {
        return shippingService.getAllShipping();
    }

    @PostMapping("/addshipping")
    public shippingDto addOrderItem(@RequestBody shippingDto shippingDto) {
        return shippingService.addShipping(shippingDto);
    }

    @PutMapping("/updateshipping")
    public shippingDto updateOrderItem(@RequestBody shippingDto shippingDto) {
        return shippingService.updateShipping(shippingDto);
    }

    @DeleteMapping("/deleteshipping/{shippingId}")
    public String deleteShipping(@PathVariable Integer shippingId) {
        return shippingService.deleteShipping(shippingId);
    }

    @GetMapping("/getshippingbyorderid/{orderId}")
    public List<shippingDto> getAllOrderItemsByOrderId(@PathVariable Integer orderId) {
        return shippingService.getShippingByOrderId(orderId);
    }
}
