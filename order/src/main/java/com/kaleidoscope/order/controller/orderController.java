package com.kaleidoscope.order.controller;

import com.kaleidoscope.order.service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kaleidoscope.order.dto.orderDto;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/order/")
public class orderController {
    @Autowired
    private orderService orderService;

    @GetMapping("/getallorders")
    public List<orderDto> getAllOrders() {
        return orderService.getOrders();

    }

    @PostMapping("/addorder")
    public orderDto addOrder(@RequestBody orderDto orderDto) {

        return orderService.addOrder(orderDto);
    }
    @PutMapping("/updateOrder")
    public orderDto updateOrder(@RequestBody orderDto orderDto) {

        return orderService.updateOrder(orderDto);
    }

    @DeleteMapping("/deleteorder/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Integer orderId) {
        return orderService.deleteOrder(orderId);
    }

}
