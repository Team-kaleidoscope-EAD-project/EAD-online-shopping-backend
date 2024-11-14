package com.kaleidoscope.order.repo;

import com.kaleidoscope.order.model.orderItemModel;
import com.kaleidoscope.order.model.orderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface orderItemRepo extends JpaRepository<orderItemModel, Integer> {
    List<orderItemModel> findByOrderModelId(int orderId);
}
