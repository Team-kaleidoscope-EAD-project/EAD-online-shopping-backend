package com.kaleidoscope.order.service;



import com.kaleidoscope.order.model.orderModel;
import com.kaleidoscope.order.repo.orderItemRepo;
import com.kaleidoscope.order.repo.orderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaleidoscope.order.dto.orderItemDto;
import com.kaleidoscope.order.model.orderItemModel;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class orderItemService {

    @Autowired
    private orderItemRepo orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private orderRepo orderRepo;



    public List<orderItemDto> getOrderItems(){
        List<orderItemModel> orderItemList = orderItemRepository.findAll();
        return modelMapper.map(orderItemList, new TypeToken<List<orderItemDto>>(){}.getType());
    }

    public orderItemDto addOrderItem(orderItemDto orderItemDto) {
        orderModel orderModel = orderRepo.findById(orderItemDto.getOrderid())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderItemDto.getOrderid()));

        orderItemModel orderItemModel = new orderItemModel();
        orderItemModel.setOrderModel(orderModel);
        orderItemModel.setPrice(orderItemDto.getPrice());
        orderItemModel.setQuantity(orderItemDto.getQuantity());
        orderItemModel.setProductId(orderItemDto.getProductId());

        orderItemModel savedItem = orderItemRepository.save(orderItemModel);

        // Convert back to DTO if needed
        return new orderItemDto(
                savedItem.getId(),
                orderItemDto.getOrderid(),
                savedItem.getPrice(),
                savedItem.getQuantity(),
                savedItem.getProductId()
        );
    }

    public orderItemDto updateOrderItem(orderItemDto orderItemDto){
        orderItemModel existingOrderItem = orderItemRepository.findById(orderItemDto.getId())
                .orElseThrow(() -> new RuntimeException("Order item not found with ID: " + orderItemDto.getId()));


        orderModel associatedOrderModel = orderRepo.findById(orderItemDto.getOrderid())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderItemDto.getOrderid()));


        existingOrderItem.setOrderModel(associatedOrderModel);
        existingOrderItem.setPrice(orderItemDto.getPrice());
        existingOrderItem.setQuantity(orderItemDto.getQuantity());
        existingOrderItem.setProductId(orderItemDto.getProductId());


        orderItemModel updatedOrderItem = orderItemRepository.save(existingOrderItem);


        return new orderItemDto(
                updatedOrderItem.getId(),
                updatedOrderItem.getOrderModel().getId(),
                updatedOrderItem.getPrice(),
                updatedOrderItem.getQuantity(),
                updatedOrderItem.getProductId()
        );
    }

    public String deleteOrderItem(Integer ItemId){
//        orderItemRepository.delete(modelMapper.map(orderItemDto, orderItemModel.class));
        orderItemRepository.deleteById(ItemId);
        return "Order item deleted";
    }

    public List<orderItemDto> getOrderItemsByOrderModel(int orderId) {
        List<orderItemModel> orderItems = orderItemRepository.findByOrderModelId(orderId);

        return orderItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private orderItemDto convertToDto(orderItemModel orderItem) {
        return new orderItemDto(
                orderItem.getId(),
                orderItem.getOrderModel().getId(),
                orderItem.getPrice(),
                orderItem.getQuantity(),
                orderItem.getProductId()
        );
    }
}
