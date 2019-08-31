package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Product Characteristic Key Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface KeyRepository extends JpaRepository<Key, String> {
}
