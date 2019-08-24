package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.dao.ProductRepository;
import com.github.evgeniievgenevich.microstore.dto.ProductCreateDto;
import com.github.evgeniievgenevich.microstore.dto.ProductDetailDto;
import com.github.evgeniievgenevich.microstore.dto.ProductShortDto;
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
    public ProductDetailDto create(ProductCreateDto createDto) {
        return new ProductDetailDto(this.repository.save(createDto.to(new Product())));
    }

    @Override
    @Transactional
    public ProductDetailDto update(ObjectId id, ProductCreateDto dto) {
        return new ProductDetailDto(this.repository.save(dto.to(this.repository.findById(id).orElseThrow(() -> notFound(id)))));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailDto product(ObjectId id) {
        return new ProductDetailDto(this.repository.findById(id).orElseThrow(() -> notFound(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductShortDto> products(int page, int count) {
        return this.repository.findByOrderByTitle(PageRequest.of(page, count)).map(ProductShortDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductShortDto> findByTitle(String titleContainingIgnoreCase, int page, int count) {
        return this.repository.findByTitleContainingIgnoreCaseOrderByTitle(titleContainingIgnoreCase, PageRequest.of(page, count)).map(ProductShortDto::new);
    }
}
