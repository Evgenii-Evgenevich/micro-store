package com.github.evgeniievgenevich.microstore.dto;


import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.Key;
import com.github.evgeniievgenevich.microstore.model.Product;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Product Detail Data Transfer Object
 * детали товара
 *
 * @author Evgenii Evgenevich
 */
@Data
public class ProductDetailDto {
    private String id;

    private LocalDateTime dateTime;

    private String title;

    private String description;

    private Map<Key, Object> characteristic;

    public ProductDetailDto(Product product) {
        this(product, product.getCharacteristic());
    }

    public ProductDetailDto(Product product, List<CharacteristicData> characteristicData) {
        setId(product.getId());
        title = product.getTitle();
        description = product.getDescription();
        if (!CollectionUtils.isEmpty(characteristicData)) {
            characteristic = new HashMap<>(characteristicData.size());
            characteristicData.forEach(data -> characteristic.put(data.getId().getKey(), data.getValue()));
        }
    }

    public void setId(ObjectId id) {
        this.id = id.toString();
        this.dateTime = LocalDateTime.ofEpochSecond(id.getTimestamp(), 0, ZoneOffset.UTC);
    }
}
