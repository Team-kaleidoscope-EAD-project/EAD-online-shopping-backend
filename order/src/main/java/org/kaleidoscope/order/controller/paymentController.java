package org.kaleidoscope.order.controller;

import org.kaleidoscope.order.dto.paymentDto;
import org.kaleidoscope.order.service.paymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/order/")

public class paymentController {
    @Autowired
    private paymentService paymentServiceService;

    @GetMapping("/getallpayments")
    public List<paymentDto> getAllPayments() {
        return paymentServiceService.getAllPayments();
    }
    @PostMapping("/addpayment")
    public paymentDto addOrderItem(@RequestBody paymentDto paymentDto) {
        return paymentServiceService.addPayment(paymentDto);
    }
    @PutMapping("/updatepayment")
    public paymentDto updateOrderItem(@RequestBody paymentDto paymentDto) {
        return paymentServiceService.updatePayment(paymentDto);
    }

    @DeleteMapping("/deletepayment/{paymentId}")
    public String deletePayment(@PathVariable Integer paymentId) {
        return paymentServiceService.deletePatment(paymentId);
    }

    @GetMapping("/getpaymentbyorderid/{orderId}")
    public List<paymentDto> getAllOrderItemsByOrderId(@PathVariable Integer orderId) {
        return paymentServiceService.getPaymentByOrderId(orderId);
    }
}
