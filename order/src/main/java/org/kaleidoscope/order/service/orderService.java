package org.kaleidoscope.order.service;

import org.kaleidoscope.order.repo.orderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kaleidoscope.order.dto.orederDto;
import org.kaleidoscope.order.model.orderModel;
import java.util.List;

@Service
@Transactional

public class orderService {
    @Autowired
    private orderRepo orderrepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<orederDto> getOrders(){
        List<orderModel> orderList = orderrepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<orederDto>>(){}.getType());
    }

    public orederDto addOrder(orederDto orederDto){
        orderrepo.save(modelMapper.map(orederDto, orderModel.class));
        return orederDto;
    }

    public orederDto updateOrder(orederDto orederDto){
        orderrepo.save(modelMapper.map(orederDto, orderModel.class));
        return orederDto;
    }

    public String deleteOrder(Integer orderId){
//        orderrepo.delete(modelMapper.map(orederDto, orderModel.class));
        orderrepo.deleteById(orderId);
        return "Order deleted";
    }
}
