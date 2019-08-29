package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;]
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProductDaoImpl implements ProductDao {
    private final MongoCollection collection;

    @Autowired
    public ProductDaoImpl(MongoTemplate mongoTemplate) {
        collection = mongoTemplate.getCollection(mongoTemplate.getCollectionName(Product.class));
    }

    @Override
    public List<Product> findByCharacteristicKeyIn(Collection<String> keys) {
        QueryBuilder queryBuilder = QueryBuilder.start();
        keys.forEach(key -> {
            String characteristicKey = "characteristic." + key;
            queryBuilder.or(
                    QueryBuilder.start()
                            .and(characteristicKey).exists(true)
                            .get()
            );
        });
        MongoIterable<Product> products = collection.find((BasicDBObject)queryBuilder.get(), Product.class);
        return products.into(new ArrayList<>());
    }

    @Override
    public List<Product> findByCharacteristicContains(Map<String, Object> characteristic) {
        QueryBuilder queryBuilder = QueryBuilder.start();
        characteristic.forEach((key, value) -> {
            String characteristicKey = "characteristic." + key;
            queryBuilder.or(
                    QueryBuilder.start()
                            .and(characteristicKey).is(value)
                            .get()
            );
        });
        MongoIterable<Product> products = collection.distinct("id", (BasicDBObject)queryBuilder.get(), Product.class);
        return products.into(new ArrayList<>());
    }
}
