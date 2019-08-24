package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.dao.ProductRepository;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Product Service
 * Товар
 *
 * @author Evgenii Evgenevich
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return this.repository.save(product);
    }

    @Override
    public Page<Product> products(int page, int count) {
        return this.repository.findByOrderByTitle(PageRequest.of(page, count));
    }

    @Override
    public Page<Product> findByTitle(String titleContainingIgnoreCase, int page, int count) {
        return this.repository.findByTitleContainingIgnoreCaseOrderByTitle(titleContainingIgnoreCase, PageRequest.of(page, count));
    }
}
