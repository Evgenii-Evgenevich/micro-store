package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.CharacteristicId;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Characteristic Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface CharacteristicRepository extends JpaRepository<CharacteristicData, CharacteristicId>, CharacteristicDao {
    default void deleteByIdProduct(Product product) {
        deleteAll(findByIdProduct(product));
    }
}
