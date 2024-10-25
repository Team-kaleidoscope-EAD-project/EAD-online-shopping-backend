package com.kaleidoscope.order.repo;

import com.kaleidoscope.order.model.shippingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shippingRepo extends JpaRepository<shippingModel, Integer>{
}
