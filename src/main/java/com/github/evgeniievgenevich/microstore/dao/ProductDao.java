package com.github.evgeniievgenevich.microstore.dao;

import com.mongodb.DBObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Product Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface ProductDao {
    <Product> List<Product> findByCharacteristicKeyIn(Collection<String> keys, Function<DBObject, Product> mapper);

    <Product> List<Product> findByCharacteristicContains(Map<String, Object> characteristic, Function<DBObject, Product> mapper);

    <Product> List<Product> findByTitleContainingIgnoreCaseAndCharacteristicContains(String titleContainingIgnoreCase, Map<String, Object> characteristic, Function<DBObject, Product> mapper);
}
