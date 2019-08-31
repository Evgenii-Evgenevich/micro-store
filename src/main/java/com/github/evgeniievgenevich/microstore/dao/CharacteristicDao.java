package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.Product;

import java.util.List;

/**
 * Characteristic Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface CharacteristicDao {
    List<CharacteristicData> findByIdProduct(Product product);
}
