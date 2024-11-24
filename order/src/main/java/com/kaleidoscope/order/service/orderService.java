package com.kaleidoscope.order.service;

import com.kaleidoscope.order.repo.orderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaleidoscope.order.dto.orederDto;
import com.kaleidoscope.order.model.orderModel;

import java.util.List;

@Service
@Transactional

public class orderService {
    @Autowired
    private orderRepo orderrepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<orederDto> getOrders() {
        List<orderModel> orderList = orderrepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<orederDto>>() {
        }.getType());
    }

    public orederDto addOrder(orederDto orederDto) {
        orderrepo.save(modelMapper.map(orederDto, orderModel.class));
        return orederDto;
    }

    public orederDto updateOrder(orederDto orederDto){
        if (orderrepo.existsById(orederDto.getId())) {
            orderModel existingOrder = orderrepo.findById(orederDto.getId()).orElse(null);

            if (existingOrder != null) {

                existingOrder.setStatus(orederDto.getStatus());
                existingOrder.setTotalAmount(orederDto.getTotalAmount());
                existingOrder.setUserId(orederDto.getUserId());
                existingOrder.setOrderDate(orederDto.getOrderDate());
                existingOrder.setDiscountAmount(orederDto.getDiscountAmount());


                orderrepo.save(existingOrder);


                return modelMapper.map(existingOrder, orederDto.class);
            }
        }

        // Throw an exception or handle the case where the order doesn't exist
        throw new RuntimeException("Order not found with ID: " + orederDto.getId());


    }

    public String deleteOrder(Integer orderId) {
//        orderrepo.delete(modelMapper.map(orederDto, orderModel.class));
        orderrepo.deleteById(orderId);
        return "Order deleted";
    }
}
