package org.kaleidoscope.order.service;

import org.kaleidoscope.order.repo.shippingRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kaleidoscope.order.dto.shippingDto;
import org.kaleidoscope.order.model.shippingModel;

import java.util.List;

@Service
@Transactional

public class shippingService {
    @Autowired
    private shippingRepo shippingRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<shippingDto> getAllShipping() {
        List<shippingModel> shippingList = shippingRepository.findAll();
        return modelMapper.map(shippingList, new TypeToken<List<shippingDto>>() {
        }.getType());
    }

    public shippingDto addShipping(shippingDto shippingDto) {
        shippingRepository.save(modelMapper.map(shippingDto, shippingModel.class));
        return shippingDto;
    }

    public shippingDto updateShipping(shippingDto shippingDto) {
        shippingRepository.save(modelMapper.map(shippingDto, shippingModel.class));
        return shippingDto;
    }

    public String deleteShipping(Integer ItemId) {
//        shippingRepository.delete(modelMapper.map(shippingDto, shippingModel.class));
        shippingRepository.deleteById(ItemId);
        return "Shipping deleted";
    }

    public List<shippingDto> getShippingByOrderId(Integer orderId) {
        List<shippingModel> shippingtList = shippingRepository.findByOrderId(orderId);
        return modelMapper.map(shippingtList, new TypeToken<List<shippingDto>>() {
        }.getType());
    }
}
