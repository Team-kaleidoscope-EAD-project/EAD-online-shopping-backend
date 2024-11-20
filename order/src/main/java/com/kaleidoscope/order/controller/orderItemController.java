package com.kaleidoscope.order.controller;

import com.kaleidoscope.order.model.orderModel;
import com.kaleidoscope.order.service.orderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kaleidoscope.order.dto.orderItemDto;
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



    @PostMapping("/addorderitems")
    public List<orderItemDto> addOrderItems(@RequestBody List<orderItemDto> orderItemDtos) {
        return orderItemService.addOrderItems(orderItemDtos);
    }

    @PutMapping("/updateorderitem")
    public orderItemDto updateOrderItem(@RequestBody orderItemDto orderItemDto) {
        return orderItemService.updateOrderItem(orderItemDto);
    }

    @DeleteMapping("/deletorderitem/{itemId}")
    public String deleteOrderItem(@PathVariable("itemId") Integer itemId) {

        return orderItemService.deleteOrderItem(itemId);
    }

    @GetMapping("/getallorderitemsbyorderid/{orderId}")
    public List<orderItemDto> getAllOrderItemsByOrderId(@PathVariable("orderId") Integer orderId) {
        return orderItemService.getOrderItemsByOrderModel(orderId);
    }

}
