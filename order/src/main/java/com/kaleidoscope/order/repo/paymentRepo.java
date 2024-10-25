package com.kaleidoscope.order.repo;

import com.kaleidoscope.order.model.paymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface paymentRepo extends JpaRepository<paymentModel, Integer>{

}
