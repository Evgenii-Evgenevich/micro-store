package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.dto.ProductCreateDto;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

/**
 * Product Service
 * Товар
 *
 * @author Evgenii Evgenevich
 */
public interface ProductService {
    Product create(ProductCreateDto createDto);

    Product update(ObjectId id, ProductCreateDto dto);

    Product product(ObjectId id);

    Page<Product> products(int page, int count);

    Page<Product> findByTitle(String titleContainingIgnoreCase, int page, int count);
}
