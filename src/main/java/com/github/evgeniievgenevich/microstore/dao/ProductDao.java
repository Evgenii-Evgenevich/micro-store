package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Product;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductDao {
    List<Product> findByCharacteristicKeyIn(Collection<String> keys);

    List<Product> findByCharacteristicContains(Map<String, Object> characteristic);
}
