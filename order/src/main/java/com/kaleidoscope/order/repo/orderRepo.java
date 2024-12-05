package com.kaleidoscope.order.repo;

import com.kaleidoscope.order.model.orderModel;
import com.kaleidoscope.order.model.paymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface orderRepo extends JpaRepository<orderModel, Integer> {
    List<orderModel> findByUserId(String userId);
}
