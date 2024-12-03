package com.kaleidoscope.order.controller;

import com.kaleidoscope.order.dto.InventoryUpdateDto;
import com.kaleidoscope.order.kafka.InventoryUpdateProducer;
import com.kaleidoscope.order.kafka.OrderProducer;
import com.kaleidoscope.order.service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kaleidoscope.order.dto.orderDto;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/order/")
public class orderController {
    @Autowired
    private orderService orderService;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private  InventoryUpdateProducer inventoryUpdateProducer;

    @GetMapping("/test")
    public Mono<String> testOrder() {
        String message = "Order is commited";
        orderProducer.sendMessage(message);
        return orderService.testOrder();
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody List<InventoryUpdateDto> inventoryUpdateDto) {
        inventoryUpdateProducer.sendInventoryUpdateList(inventoryUpdateDto);
        return ResponseEntity.ok("Message sent to Kafka successfully");
    
    }
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
