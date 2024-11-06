package org.kaleidoscope.order.repo;

import org.kaleidoscope.order.model.orderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepo extends JpaRepository<orderModel, Integer> {

}
