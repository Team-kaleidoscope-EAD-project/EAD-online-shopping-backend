package com.kaleidoscope.feedback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "product_ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "user_id"}))
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Product ID must not be null")
    private Long productId;

    @NotNull(message = "Rating value must not be null")
    private Integer ratingValue;

}
