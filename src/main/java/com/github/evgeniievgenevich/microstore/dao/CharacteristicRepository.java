package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.CharacteristicId;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Product Characteristic Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface CharacteristicRepository extends MongoRepository<CharacteristicData, CharacteristicId> {
    List<CharacteristicData> findByIdProduct(Product product);

    void deleteByIdProduct(Product product);
}
