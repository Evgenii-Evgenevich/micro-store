package com.github.evgeniievgenevich.microstore.dto;


import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.Product;
import lombok.Data;
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

    private Map<String, Object> characteristic;

    public ProductDetailDto(Product product, List<CharacteristicData> characteristicList) {
        this.id = product.getId().toString();
        this.dateTime = LocalDateTime.ofEpochSecond(product.getId().getTimestamp(), 0, ZoneOffset.UTC);
        this.title = product.getTitle();
        this.description = product.getDescription();
        if (!CollectionUtils.isEmpty(characteristicList)) {
            this.characteristic = new HashMap<>(characteristicList.size());
            characteristicList.forEach(data -> this.characteristic.put(data.getId().getKey().getId(), data.getValue()));
        }
    }
}
