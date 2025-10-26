package com.ecommerce.spring_ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, message = "Product name must be at least 3 characters long")
    private String name;

    private String description;
    private String image;

    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 10000, message = "Quantity cannot exceed 10000")
    private int quantity;

    @Min(value = 0, message = "Price cannot be negative")
    private double price;

    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value = 100, message = "Discount cannot be more than 100%")
    private double discount = 0.0;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public double getSpecialPrice() {
        return this.price - (this.price * this.discount / 100);
    }
}
