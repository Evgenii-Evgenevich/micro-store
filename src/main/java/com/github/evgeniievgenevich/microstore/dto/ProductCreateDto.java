package com.github.evgeniievgenevich.microstore.dto;

import com.github.evgeniievgenevich.microstore.model.Product;
import lombok.Data;

/**
 * Product Create Data Transfer Object
 * Товар
 *
 * @author Evgenii Evgenevich
 */
@Data
public class ProductCreateDto {
    private String title;

    private String description;

    public Product to(Product product) {
        product.setTitle(this.title);
        product.setDescription(this.description);
        return product;
    }
}
