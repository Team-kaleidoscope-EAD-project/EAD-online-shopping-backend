package com.kaleidoscope.order.controller;

import com.kaleidoscope.order.dto.orederDto;
import com.kaleidoscope.order.service.placeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kaleidoscope.order.dto.placeOrderDto;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/placeorder/")
public class placeOrderController {

    @Autowired
    private placeOrderService placeOrderService;

    @PostMapping("/addplaceorder")
    public placeOrderDto placeOrder(@RequestBody placeOrderDto placeOrderDto) {

        return placeOrderService.placeOrder(placeOrderDto);
    }
}
