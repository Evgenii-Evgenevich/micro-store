package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Product Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public class ProductDaoImpl implements ProductDao {
    private final MongoCollection collection;

    @Autowired
    public ProductDaoImpl(MongoTemplate mongoTemplate) {
        collection = mongoTemplate.getCollection(mongoTemplate.getCollectionName(Product.class));
    }

    @Override
    public <Product> List<Product> findByCharacteristicKeyIn(Collection<String> keys, Function<DBObject, Product> mapper) {
        QueryBuilder queryBuilder = QueryBuilder.start();
        keys.forEach(key -> {
            String characteristicKey = "characteristic." + key;
            queryBuilder.or(
                    QueryBuilder.start()
                            .and(characteristicKey).exists(true)
                            .get()
            );
        });
        DBObject query = queryBuilder.get();
        System.out.println(query);
        MongoIterable<DBObject> products = collection.find((BasicDBObject) query, DBObject.class);
        return StreamSupport.stream(products.spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    private DBObject queryByCharacteristicContains(Map<String, Object> characteristic) {
        QueryBuilder queryBuilder = QueryBuilder.start();
        characteristic.forEach((key, value) -> {
            String characteristicKey = "characteristic." + key;
            queryBuilder.or(
                    QueryBuilder.start()
                            .and(characteristicKey).is(value)
                            .get()
            );
        });
        return queryBuilder.get();
    }

    @Override
    public <Product> List<Product> findByCharacteristicContains(Map<String, Object> characteristic, Function<DBObject, Product> mapper) {
        DBObject query = queryByCharacteristicContains(characteristic);
        System.out.println(query);
        MongoIterable<DBObject> products = collection.find((BasicDBObject) query, DBObject.class);
        return StreamSupport.stream(products.spliterator(), false).map(mapper).collect(Collectors.toList());
    }

    @Override
    public <Product> List<Product> findByTitleContainingIgnoreCaseAndCharacteristicContains(String titleContainingIgnoreCase, Map<String, Object> characteristic, Function<DBObject, Product> mapper) {
        DBObject containingIgnoreCase = new BasicDBObject().append("$regex", ".*" + titleContainingIgnoreCase + ".*").append("$options", 'i');
        DBObject query = QueryBuilder.start().and(
                QueryBuilder.start().and("title").is(containingIgnoreCase).get(),
                queryByCharacteristicContains(characteristic)
        ).get();
        System.out.println(query);
        MongoIterable<DBObject> products = collection.find((BasicDBObject) query, DBObject.class);
        return StreamSupport.stream(products.spliterator(), false).map(mapper).collect(Collectors.toList());
    }
}
