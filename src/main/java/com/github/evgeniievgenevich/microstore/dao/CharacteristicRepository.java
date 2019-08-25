package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.CharacteristicId;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Product Characteristic Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public interface CharacteristicRepository extends Repository<CharacteristicData, CharacteristicId> {
    List<CharacteristicData> saveAll(Iterable<CharacteristicData> data);

    List<CharacteristicData> findByIdProduct(Product product);

    void deleteByIdProduct(Product product);

    Stream<CharacteristicData> findByIdKeyIdInOrderByIdProductId(Collection<String> keys);

    Stream<CharacteristicData> findByIdKeyIdAndValue(String key, Object value);

    Stream<CharacteristicData> findByIdProductTitleContainingIgnoreCaseAndIdKeyIdInOrderByIdProductId(String titleContainingIgnoreCase, Collection<String> keys);

    Stream<CharacteristicData> findByIdProductTitleContainingIgnoreCaseAndIdKeyIdAndValue(String titleContainingIgnoreCase, String key, Object value);
}
