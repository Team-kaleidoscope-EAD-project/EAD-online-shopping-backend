package org.kaleidoscope.order.repo;

import org.kaleidoscope.order.model.paymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface paymentRepo extends JpaRepository<paymentModel, Integer>{
    List<paymentModel> findByOrderId(Integer orderId);
}
