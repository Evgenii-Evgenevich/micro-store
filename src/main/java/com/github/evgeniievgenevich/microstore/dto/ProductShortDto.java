package com.github.evgeniievgenevich.microstore.dto;

import com.github.evgeniievgenevich.microstore.model.Product;
import lombok.Data;

/**
 * Product Short Data Transfer Object
 * товар
 *
 * @author Evgenii Evgenevich
 */
@Data
public class ProductShortDto {
    private String id;

    private String title;

    private String description;

    public ProductShortDto(Product product) {
        this.id = product.getId().toString();
        this.title = product.getTitle();
        this.description = product.getDescription();
    }
}
