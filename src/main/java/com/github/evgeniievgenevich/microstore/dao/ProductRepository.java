package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Product Data Access Object
 * Товар
 *
 * @author Evgenii Evgenevich
 */
public interface ProductRepository extends Repository<Product, ObjectId> {
    Product save(Product product);

    Optional<Product> findById(ObjectId id);

    Page<Product> findByOrderByTitle(Pageable pageable);

    Page<Product> findByTitleContainingIgnoreCaseOrderByTitle(String titleContainingIgnoreCase, Pageable pageable);
}
