package com.kaleidoscope.order.service;


import com.kaleidoscope.order.repo.orderItemRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaleidoscope.order.dto.orderItemDto;
import com.kaleidoscope.order.model.orderItemModel;
import java.util.List;

@Service
@Transactional

public class orderItemService {

    @Autowired
    private orderItemRepo orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<orderItemDto> getOrderItems(){
        List<orderItemModel> orderItemList = orderItemRepository.findAll();
        return modelMapper.map(orderItemList, new TypeToken<List<orderItemDto>>(){}.getType());
    }

    public orderItemDto addOrderItem(orderItemDto orderItemDto){
        orderItemRepository.save(modelMapper.map(orderItemDto, orderItemModel.class));
        return orderItemDto;
    }

    public orderItemDto updateOrderItem(orderItemDto orderItemDto){
        orderItemRepository.save(modelMapper.map(orderItemDto, orderItemModel.class));
        return orderItemDto;
    }

    public String deleteOrderItem(Integer ItemId){
//        orderItemRepository.delete(modelMapper.map(orderItemDto, orderItemModel.class));
        orderItemRepository.deleteById(ItemId);
        return "Order item deleted";
    }
}
