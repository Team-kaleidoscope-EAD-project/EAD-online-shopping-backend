package org.kaleidoscope.order.controller;

import org.kaleidoscope.order.service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.kaleidoscope.order.dto.orederDto;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/order/")
public class orderController {
    @Autowired
    private orderService orderService;

    @GetMapping("/getallorders")
    public List<orederDto> getAllOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/addorder")
    public orederDto addOrder(@RequestBody orederDto orederDto) {
        return orderService.addOrder(orederDto);
    }

    @PutMapping("/updateOrder")
    public orederDto updateOrder(@RequestBody orederDto orederDto) {
        return orderService.updateOrder(orederDto);
    }

    @DeleteMapping("/deletorder/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrder(orderId);
    }

}
