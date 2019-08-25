package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Key;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Product Characteristic Key Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface KeyRepository extends Repository<Key, String> {
    List<Key> findAll();

    Key save(Key key);

    Optional<Key> findById(String id);
}
