package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Product Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface ProductRepository extends JpaRepository<Product, ObjectId>, ProductDao {
    Optional<Product> findById(ObjectId id);

    Stream<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase);

    Page<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase, Pageable pageable);
}
