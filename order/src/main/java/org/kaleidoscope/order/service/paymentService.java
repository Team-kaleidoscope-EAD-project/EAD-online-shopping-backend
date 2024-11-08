package org.kaleidoscope.order.service;

import org.kaleidoscope.order.repo.paymentRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kaleidoscope.order.dto.paymentDto;
import org.kaleidoscope.order.model.paymentModel;

import java.util.List;

@Service
@Transactional

public class paymentService {
    @Autowired
    private paymentRepo paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<paymentDto> getAllPayments() {
        List<paymentModel> paymentList = paymentRepository.findAll();
        return modelMapper.map(paymentList, new TypeToken<List<paymentDto>>() {
        }.getType());
    }

    public paymentDto addPayment(paymentDto paymentDto) {
        paymentRepository.save(modelMapper.map(paymentDto, paymentModel.class));
        return paymentDto;
    }

    public paymentDto updatePayment(paymentDto paymentDto) {
        paymentRepository.save(modelMapper.map(paymentDto, paymentModel.class));
        return paymentDto;
    }

    public String deletePatment(Integer paymentId) {
//        paymentRepository.delete(modelMapper.map(paymentDto, paymentModel.class));
        paymentRepository.deleteById(paymentId);
        return "Payment deleted";
    }

    public List<paymentDto> getPaymentByOrderId(Integer orderId) {
        List<paymentModel> paymentList = paymentRepository.findByOrderId(orderId);
        return modelMapper.map(paymentList, new TypeToken<List<paymentDto>>() {
        }.getType());
    }
}
