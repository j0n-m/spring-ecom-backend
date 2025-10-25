package com.ecommerce.spring_ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private int quantity;
    private double price;
    private double discount;
    private double specialPrice;
//    private Category category;

}
