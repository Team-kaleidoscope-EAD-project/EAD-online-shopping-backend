package org.kaleidoscope.inventory.repo;

import org.kaleidoscope.inventory.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryItem, Integer> {}

