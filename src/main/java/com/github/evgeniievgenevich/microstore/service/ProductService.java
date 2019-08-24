package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.model.Product;
import org.springframework.data.domain.Page;

/**
 * Product Service
 * Товар
 *
 * @author Evgenii Evgenevich
 */
public interface ProductService {
    Product save(Product product);

    Page<Product> products(int page, int count);

    Page<Product> findByTitle(String titleContainingIgnoreCase, int page, int count);
}
