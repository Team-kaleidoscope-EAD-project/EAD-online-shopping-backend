package com.kaleidoscope.order.service;

import com.kaleidoscope.order.dto.orderDto;
import com.kaleidoscope.order.model.orderModel;
import com.kaleidoscope.order.repo.orderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
@Transactional
public class orderService {
    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;

    @Autowired
    private orderRepo orderrepo;

    @Autowired
    private ModelMapper modelMapper;

    public orderService(WebClient inventoryWebClient, WebClient productWebClient) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
    }

    public Mono<String> testOrder() {
        Mono<String> inventoryResponse = inventoryWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/test").build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, this::handleWebClientException);

        Mono<String> productResponse = productWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/test").build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, this::handleWebClientException);

        return Mono.zip(inventoryResponse, productResponse)
                .map(tuple -> "Inventory Response: " + tuple.getT1() + "\nProduct Response: " + tuple.getT2());
    }

    // Helper method for error handling
    private Mono<String> handleWebClientException(WebClientResponseException e) {
        if (e.getStatusCode().is5xxServerError()) {
            return Mono.just("Server error occurred while fetching the data.");
        } else {
            return Mono.error(e);
        }
    }


    public List<orderDto> getOrders() {
        List<orderModel> orderList = orderrepo.findAll();
        System.out.println(orderList);
        return modelMapper.map(orderList, new TypeToken<List<orderDto>>() {
        }.getType());
    }


    public orderDto addOrder(orderDto orederDto){
        System.out.println(orederDto);
        orderModel orderModel = modelMapper.map(orederDto, orderModel.class);

        orderModel savedOrder = orderrepo.saveAndFlush(orderModel);

        return modelMapper.map(savedOrder, orderDto.class);


    }

    public orderDto updateOrder(orderDto orderDto){
        if (orderrepo.existsById(orderDto.getId())) {
            orderModel existingOrder = orderrepo.findById(orderDto.getId()).orElse(null);

            if (existingOrder != null) {

                existingOrder.setStatus(orderDto.getStatus());
                existingOrder.setTotalAmount(orderDto.getTotalAmount());
                existingOrder.setUserId(orderDto.getUserId());
                existingOrder.setOrderDate(orderDto.getOrderDate());
                existingOrder.setDiscountAmount(orderDto.getDiscountAmount());
                existingOrder.setStatus(orderDto.getStatus());
                existingOrder.setTotalAmount(orderDto.getTotalAmount());
                existingOrder.setUserId(orderDto.getUserId());
                existingOrder.setOrderDate(orderDto.getOrderDate());
                existingOrder.setDiscountAmount(orderDto.getDiscountAmount());


                orderrepo.save(existingOrder);


                return modelMapper.map(existingOrder, orderDto.class);
               
            }
        }

        // Throw an exception or handle the case where the order doesn't exist
        throw new RuntimeException("Order not found with ID: " + orderDto.getId());
      


    }

    public String deleteOrder(Integer orderId) {
//        orderrepo.delete(modelMapper.map(orederDto, orderModel.class));
        orderrepo.deleteById(orderId);
        return "Order deleted";
    }
}
