package com.kaleidoscope.order.service;

import com.kaleidoscope.order.dto.orderItemDto;
import com.kaleidoscope.order.model.paymentModel;
import com.kaleidoscope.order.model.orderModel;
import com.kaleidoscope.order.repo.orderRepo;
import com.kaleidoscope.order.repo.paymentRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaleidoscope.order.dto.paymentDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class paymentService {
    @Autowired
    private paymentRepo paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private orderRepo orderRepo;

    @Autowired
    private DtoConvertService dtoConvertService;

    public List<paymentDto> getAllPayments() {
        List<paymentModel> paymentList = paymentRepository.findAll();
        return modelMapper.map(paymentList, new TypeToken<List<paymentDto>>() {
        }.getType());
    }

    public paymentDto addPayment(paymentDto paymentDto){

        orderModel orderModel = orderRepo.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + paymentDto.getOrderId()));

        paymentModel paymentModel = new paymentModel();
        paymentModel.setOrderModel(orderModel);
        paymentModel.setPaymentMethod(paymentDto.getPaymentMethod());
        paymentModel.setPaymentDate(paymentDto.getPaymentDate());
        paymentModel.setPaymentAmount(paymentDto.getPaymentAmount());

        paymentModel savedItem = paymentRepository.save(paymentModel);


        return new paymentDto(
                savedItem.getId(),
                savedItem.getPaymentMethod(),
                paymentDto.getOrderId(),
                savedItem.getPaymentDate(),
                savedItem.getPaymentAmount()
        );


    }

    public paymentDto updatePayment(paymentDto paymentDto){

        paymentModel existingPayment = paymentRepository.findById(paymentDto.getId())
                .orElseThrow(() -> new RuntimeException("Order item not found with ID: " + paymentDto.getId()));


        orderModel associatedPaymentModel = orderRepo.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + paymentDto.getOrderId()));


        existingPayment.setOrderModel(associatedPaymentModel);
        existingPayment.setPaymentMethod(paymentDto.getPaymentMethod());
        existingPayment.setPaymentDate(paymentDto.getPaymentDate());
        existingPayment.setPaymentAmount(paymentDto.getPaymentAmount());


        paymentModel updatedPayment = paymentRepository.save(existingPayment);


        return new paymentDto(
                updatedPayment.getId(),
                updatedPayment.getPaymentMethod(),
                updatedPayment.getOrderModel().getId(),
                updatedPayment.getPaymentDate(),
                updatedPayment.getPaymentAmount()
        );
    }

    public String deletePayment(Integer paymentId){
//        paymentRepository.delete(modelMapper.map(paymentDto, paymentModel.class));
        paymentRepository.deleteById(paymentId);
        return "Payment deleted";
    }

    public List<paymentDto> getPaymentByOrderId(Integer orderId) {
         List<paymentModel> payments = paymentRepository.findByOrderModelId(orderId);

        return payments.stream()
                .map(dtoConvertService::convertToPaymentDto)
                .collect(Collectors.toList());
    }

}
