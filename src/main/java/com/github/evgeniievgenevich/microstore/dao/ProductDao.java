package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Product Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface ProductDao {
    Stream<Product> streamAll();

    //Page<Product> findAll(Pageable pageable);

    Stream<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase);

    Page<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase, Pageable pageable);

    Stream<Product> findByCharacteristicKeyIn(Collection<String> keys);

    Stream<Product> findByCharacteristicContains(Map<String, String> characteristic);

    Stream<Product> findByTitleContainingIgnoreCaseAndCharacteristicContains(String titleContainingIgnoreCase, Map<String, String> characteristic);
}
