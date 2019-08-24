package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.dao.CharacteristicRepository;
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
    private final ProductRepository productRepository;
    private final CharacteristicRepository characteristicRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CharacteristicRepository characteristicRepository) {
        this.productRepository = productRepository;
        this.characteristicRepository = characteristicRepository;
    }

    private static NoSuchElementException notFound(ObjectId id) {
        return new NoSuchElementException("could not found Product with id " + id);
    }

    @Override
    @Transactional
    public ProductDetailDto create(ProductCreateDto createDto) {
        Product product = this.productRepository.save(createDto.to(new Product()));
        return new ProductDetailDto(
                product,
                this.characteristicRepository.saveAll(createDto.characteristicData(product))
        );
    }

    @Override
    @Transactional
    public ProductDetailDto update(ObjectId id, ProductCreateDto dto) {
        Product product = this.productRepository.save(dto.to(this.productRepository.findById(id).orElseThrow(() -> notFound(id))));
        this.characteristicRepository.deleteByIdProduct(product);
        return new ProductDetailDto(
                product,
                this.characteristicRepository.saveAll(dto.characteristicData(product))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailDto product(ObjectId id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> notFound(id));
        return new ProductDetailDto(
                product,
                this.characteristicRepository.findByIdProduct(product)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductShortDto> products(int page, int count) {
        return this.productRepository.findBy(PageRequest.of(page, count)).map(ProductShortDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductShortDto> findByTitle(String titleContainingIgnoreCase, int page, int count) {
        return this.productRepository.findByTitleContainingIgnoreCase(titleContainingIgnoreCase, PageRequest.of(page, count)).map(ProductShortDto::new);
    }
}
