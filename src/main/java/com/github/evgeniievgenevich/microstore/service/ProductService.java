package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.dto.ProductCreateDto;
import com.github.evgeniievgenevich.microstore.dto.ProductDetailDto;
import com.github.evgeniievgenevich.microstore.dto.ProductFilterDto;
import com.github.evgeniievgenevich.microstore.dto.ProductShortDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Product Service
 *
 * @author Evgenii Evgenevich
 */
public interface ProductService {
    ProductDetailDto create(ProductCreateDto createDto);

    ProductDetailDto update(ObjectId id, ProductCreateDto dto);

    ProductDetailDto product(ObjectId id);

    Page<ProductShortDto> products(int page, int count);

    Page<ProductShortDto> findByTitle(String titleContainingIgnoreCase, int page, int count);

    Set<String> characteristicKeySet();

    List<ProductShortDto> findByKeys(Collection<String> keys);

    List<ProductShortDto> findByCharacteristic(Map<String, Object> characteristic);

    List<ProductShortDto> findByFilter(ProductFilterDto filterDto);
}
