package org.kaleidoscope.order.repo;

import org.kaleidoscope.order.model.orderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface orderItemRepo extends JpaRepository<orderItemModel, Integer> {
    List<orderItemModel> findByOrderId(Integer orderId);
}
