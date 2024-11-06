package org.kaleidoscope.order.controller;

import org.kaleidoscope.order.service.orderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.kaleidoscope.order.dto.orderItemDto;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/order/")
public class orderItemController {
    @Autowired
    private orderItemService orderItemService;

    @GetMapping("/getallorderitem")
    public List<orderItemDto> getAllOrderItems() {
        return orderItemService.getOrderItems();
    }
    @PostMapping("/addorderitem")
    public orderItemDto addOrderItem(@RequestBody orderItemDto orderItemDto) {
        return orderItemService.addOrderItem(orderItemDto);
    }
    @PutMapping("/updateorderitem")
    public orderItemDto updateOrderItem(@RequestBody orderItemDto orderItemDto) {
        return orderItemService.updateOrderItem(orderItemDto);
    }

    @DeleteMapping("/deletorderitem/{itemId}")
    public String deleteOrderItem(@PathVariable Integer itemId) {
        return orderItemService.deleteOrderItem(itemId);
    }

    @GetMapping("/getallorderitemsbyorderid/{orderId}")
    public List<orderItemDto> getAllOrderItemsByOrderId(@PathVariable Integer orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }

}
