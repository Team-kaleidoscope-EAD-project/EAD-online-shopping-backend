package com.kaleidoscope.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class InventoryItem {
    @Id
    @Column(nullable = false, unique = true, length = 16)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime lastRestockedAt;
}

