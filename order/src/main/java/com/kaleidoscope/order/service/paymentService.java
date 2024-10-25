package com.kaleidoscope.order.service;

import com.kaleidoscope.order.repo.paymentRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaleidoscope.order.dto.paymentDto;
import com.kaleidoscope.order.model.paymentModel;
import java.util.List;

@Service
@Transactional

public class paymentService {
    @Autowired
    private paymentRepo shippingRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<paymentDto> getAllPayments() {
        List<paymentModel> paymentList = shippingRepository.findAll();
        return modelMapper.map(paymentList, new TypeToken<List<paymentDto>>(){}.getType());
    }

    public paymentDto addPayment(paymentDto paymentDto){
        shippingRepository.save(modelMapper.map(paymentDto, paymentModel.class));
        return paymentDto;
    }

    public paymentDto updatePayment(paymentDto paymentDto){
        shippingRepository.save(modelMapper.map(paymentDto, paymentModel.class));
        return paymentDto;
    }

    public String deletePatment(Integer paymentId){
//        shippingRepository.delete(modelMapper.map(shippingDto, paymentModel.class));
        shippingRepository.deleteById(paymentId);
        return "Payment deleted";
    }
}
