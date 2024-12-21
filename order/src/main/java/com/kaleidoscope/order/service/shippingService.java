package com.kaleidoscope.order.service;

import com.kaleidoscope.order.dto.paymentDto;
import com.kaleidoscope.order.model.orderModel;
import com.kaleidoscope.order.model.paymentModel;
import com.kaleidoscope.order.repo.orderRepo;
import com.kaleidoscope.order.repo.shippingRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaleidoscope.order.dto.shippingDto;
import com.kaleidoscope.order.model.shippingModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class shippingService {
    @Autowired
    private shippingRepo shippingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private orderRepo orderRepo;

    public List<shippingDto> getAllShipping(){
        List<shippingModel> shippingList = shippingRepository.findAll();
        return modelMapper.map(shippingList, new TypeToken<List<shippingDto>>() {
        }.getType());
    }

    public shippingDto addShipping(shippingDto shippingDto){

        orderModel orderModel = orderRepo.findById(shippingDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + shippingDto.getOrderId()));

        shippingModel shippingModel = new shippingModel();
        shippingModel.setOrderModel(orderModel);
        shippingModel.setShippingAddress(shippingDto.getShippingAddress());
        shippingModel.setShippingDate(shippingDto.getShippingDate());


        shippingModel savedItem = shippingRepository.save(shippingModel);


        return new shippingDto(
                savedItem.getId(),
                savedItem.getShippingAddress(),
                shippingDto.getOrderId(),
                savedItem.getShippingDate()
        );
    }

    public shippingDto updateShipping(shippingDto shippingDto){

        shippingModel existingShipping = shippingRepository.findById(shippingDto.getId())
                .orElseThrow(() -> new RuntimeException("Shipping not found with ID: " + shippingDto.getId()));


        orderModel associatedShippingModel = orderRepo.findById(shippingDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + shippingDto.getOrderId()));


        existingShipping.setOrderModel(associatedShippingModel);
        existingShipping.setShippingDate(shippingDto.getShippingDate());
        existingShipping.setShippingAddress(shippingDto.getShippingAddress());



        shippingModel updatedShipping = shippingRepository.save(existingShipping);


        return new shippingDto(
                updatedShipping.getId(),
                updatedShipping.getShippingAddress(),
                updatedShipping.getOrderModel().getId(),
                updatedShipping.getShippingDate()
        );

    }

    public String deleteShipping(Integer ItemId) {
//        shippingRepository.delete(modelMapper.map(shippingDto, shippingModel.class));
        shippingRepository.deleteById(ItemId);
        return "Shipping deleted";
    }

    public List<shippingDto> getShippingByOrderId(Integer orderId) {

        List<shippingModel> shippings = shippingRepository.findByOrderModelId(orderId);

        return shippings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private shippingDto convertToDto(shippingModel shipping) {
        return new shippingDto(
                shipping.getId(),
                shipping.getShippingAddress(),
                shipping.getOrderModel().getId(),
                shipping.getShippingDate()

        );
    }


    }

