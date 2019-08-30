package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Product Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface ProductRepository extends Repository<Product, ObjectId>, ProductDao {
    Product save(Product product);

    Optional<Product> findById(ObjectId id);

    Page<Product> findBy(Pageable pageable);

    Page<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase, Pageable pageable);

    Stream<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase);

    Stream<Product> findAll();
}
