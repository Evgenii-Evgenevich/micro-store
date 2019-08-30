package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.dao.KeyRepository;
import com.github.evgeniievgenevich.microstore.dao.ProductRepository;
import com.github.evgeniievgenevich.microstore.dto.ProductCreateDto;
import com.github.evgeniievgenevich.microstore.dto.ProductDetailDto;
import com.github.evgeniievgenevich.microstore.dto.ProductFilterDto;
import com.github.evgeniievgenevich.microstore.dto.ProductShortDto;
import com.github.evgeniievgenevich.microstore.model.Key;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Product Service
 *
 * @author Evgenii Evgenevich
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final KeyRepository keyRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository
            , KeyRepository keyRepository
    ) {
        this.productRepository = productRepository;
        this.keyRepository = keyRepository;
    }

    private static NoSuchElementException notFound(ObjectId id) {
        return new NoSuchElementException("could not found Product with id " + id);
    }

    private Key key(String id) {
        //ключи как справочник (key book)
        //Key key = keyRepository.findById(id)
        //        .orElseThrow(() -> new NoSuchElementException("could not found Key " + id));

        return keyRepository.save(new Key(id));//accept all
    }

    @Override
    @Transactional
    public ProductDetailDto create(ProductCreateDto createDto) {
        return new ProductDetailDto(this.productRepository.save(createDto.toDocument(new Product(), this::key)));
    }

    @Override
    @Transactional
    public ProductDetailDto update(ObjectId id, ProductCreateDto dto) {
        return new ProductDetailDto(this.productRepository.save(dto.toDocument(this.productRepository.findById(id).orElseThrow(() -> notFound(id)), this::key)));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailDto product(ObjectId id) {
        return new ProductDetailDto(this.productRepository.findById(id).orElseThrow(() -> notFound(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductShortDto> products(int page, int count) {
        return productRepository.findBy(PageRequest.of(page, count)).map(ProductShortDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductShortDto> findByTitle(String titleContainingIgnoreCase, int page, int count) {
        return productRepository.findByTitleContainingIgnoreCase(titleContainingIgnoreCase, PageRequest.of(page, count)).map(ProductShortDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> characteristicKeySet() {
        List<Key> keyList = keyRepository.findAll();
        if (keyList == null) return null;
        Set<String> keySet = new HashSet<>(keyList.size());
        keyList.forEach(e -> keySet.add(e.getId()));
        return keySet;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductShortDto> findByKeys(Collection<String> keys) {
        return productRepository.findByCharacteristicKeyIn(keys, ProductShortDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductShortDto> findByCharacteristic(Map<String, Object> characteristic) {
        return productRepository.findByCharacteristicContains(characteristic, ProductShortDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductShortDto> findByFilter(ProductFilterDto filterDto) {
        if (filterDto == null || (!StringUtils.hasText(filterDto.getTitle()) && CollectionUtils.isEmpty(filterDto.getCharacteristic()))) {
            return productRepository.findAll().map(ProductShortDto::new).collect(Collectors.toList());
        } else if (StringUtils.hasText(filterDto.getTitle()) && CollectionUtils.isEmpty(filterDto.getCharacteristic())) {
            return productRepository.findByTitleContainingIgnoreCase(filterDto.getTitle()).map(ProductShortDto::new).collect(Collectors.toList());
        } else if (!StringUtils.hasText(filterDto.getTitle()) && !CollectionUtils.isEmpty(filterDto.getCharacteristic())) {
            return productRepository.findByCharacteristicContains(filterDto.getCharacteristic(), ProductShortDto::new);
        } else {
            return productRepository.findByTitleContainingIgnoreCaseAndCharacteristicContains(filterDto.getTitle(), filterDto.getCharacteristic(), ProductShortDto::new);
        }
    }
}
