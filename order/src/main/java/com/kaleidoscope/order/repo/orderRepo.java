package com.kaleidoscope.order.repo;

import com.kaleidoscope.order.model.orderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepo extends JpaRepository<orderModel, Integer> {

}
