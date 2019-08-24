package com.github.evgeniievgenevich.microstore.dto;


import com.github.evgeniievgenevich.microstore.model.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Product Detail Data Transfer Object
 * детали товара
 *
 * @author Evgenii Evgenevich
 */
@Data
public class ProductDetailDto {
    private String id;

    private LocalDateTime dateTime;

    private String title;

    private String description;

    public ProductDetailDto() {
    }

    public ProductDetailDto(Product product) {
        this.id = product.getId().toString();
        this.dateTime = LocalDateTime.ofEpochSecond(product.getId().getTimestamp(), 0, ZoneOffset.UTC);
        this.title = product.getTitle();
        this.description = product.getDescription();
    }
}
