package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.dao.ProductRepository;
import com.github.evgeniievgenevich.microstore.dto.ProductCreateDto;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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

    private static NoSuchElementException notFound(ObjectId id) {
        return new NoSuchElementException("could not found Product with id " + id);
    }

    @Override
    @Transactional
    public Product create(ProductCreateDto createDto) {
        return this.repository.save(createDto.to(new Product()));
    }

    @Override
    @Transactional
    public Product update(ObjectId id, ProductCreateDto dto) {
        return this.repository.save(dto.to(this.repository.findById(id).orElseThrow(() -> notFound(id))));
    }

    @Override
    @Transactional(readOnly = true)
    public Product product(ObjectId id) {
        return this.repository.findById(id).orElseThrow(() -> notFound(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> products(int page, int count) {
        return this.repository.findByOrderByTitle(PageRequest.of(page, count));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findByTitle(String titleContainingIgnoreCase, int page, int count) {
        return this.repository.findByTitleContainingIgnoreCaseOrderByTitle(titleContainingIgnoreCase, PageRequest.of(page, count));
    }
}
