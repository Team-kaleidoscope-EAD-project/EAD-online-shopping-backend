package com.kaleidoscope.order.repo;

import com.kaleidoscope.order.model.orderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderItemRepo extends JpaRepository<orderItemModel, Integer> {
}
